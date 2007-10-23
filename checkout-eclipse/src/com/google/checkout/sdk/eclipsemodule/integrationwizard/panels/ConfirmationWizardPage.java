/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.google.checkout.sdk.eclipsemodule.integrationwizard.panels;

import com.google.checkout.sdk.eclipsemodule.handlermanager.HandlerManagerPage;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.IntegrationWizard;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.Settings;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import java.io.File;

public class ConfirmationWizardPage extends WizardPage {
  private Button launchHandlerManagerCheckBox;
  
  private Label confirmLabel;
  
  private Text changesTextField;
  
  public ConfirmationWizardPage() {
    super("Confirmation Page");
    
    setTitle("Confirmation Wizard Page");
    setPageComplete(true);
  }
  
  public void createControl(Composite parent) {
    // create the composite to hold the widgets
    GridData gd;
    Composite composite = new Composite(parent, SWT.NULL);
    
    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 4;
    gl.numColumns = ncol;
    composite.setLayout(gl);
    
    // create the widgets. If the appearance of the widget is different from the
    // default, create a GridData for it to set the alignment and define how
    // much space it will occupy
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    
    confirmLabel = new Label(composite, SWT.NULL);
    confirmLabel.setText("Clicking \"Finish\" will have the following effects:");
    confirmLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    gd.heightHint = 200;
    
    changesTextField = new Text(composite, 
      SWT.BORDER | SWT.READ_ONLY | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    changesTextField.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    
    launchHandlerManagerCheckBox = new Button(composite, SWT.CHECK);
    launchHandlerManagerCheckBox.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event evt) {
        launchHandlerManagerCheckBoxActionPerformed();
      }
    });
    launchHandlerManagerCheckBox.setText("Run the Handler Manager after this wizard");
    launchHandlerManagerCheckBox.setLayoutData(gd);
    
    setControl(composite);
  }

  /**
   * Returns the next page.
   * Saves the values from this page in the settings associated with the wizard.
   * Initializes the widgets on the next page.
   */
  @Override
  public IWizardPage getNextPage() {
    HandlerManagerPage handlerPage = 
      ((IntegrationWizard)getWizard()).getHandlerManagerPage();
    return handlerPage;
  }
  
  /*************************************************************************/
  /*                           EVENT HANDLERS                              */
  /*************************************************************************/
  
  private void launchHandlerManagerCheckBoxActionPerformed() {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    Settings tempSettings = intWizard.getSettings();
    
    // Update settings
    boolean selected = launchHandlerManagerCheckBox.getSelection();
    tempSettings.setLaunchHandlerManager(selected);
  }
  
  /*************************************************************************/
  /*                          UTILITY METHODS                              */
  /*************************************************************************/
  
  public void onEnterPage() {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    Settings tempSettings = intWizard.getSettings();
    
    // Update changes text area
    String changes = "";
    
    changes = "Modify " + 
    tempSettings.getProject().getName() + "\n\n"; 
    changes += "- Add checkout-sdk.jar to your WEB_INF/lib directory\n";
    if (tempSettings.getModifiedWebXml() != null) {
      changes += "- Modify " + shorten(tempSettings.getWebXmlFile()) + "\n";
    }
    changes += "- Create " + shorten(tempSettings.getWebInfDirectory()) + 
      "/checkout-config.xml\n";
    if (tempSettings.getAddSamples()) {
      changes += "- Add sample JSPs to " + 
        shorten(tempSettings.getSamplesDirectory()) + "\n";
    }
    
    changesTextField.setText(changes);

    // Update run handler manager check box
    launchHandlerManagerCheckBox.setSelection(tempSettings.launchHandlerManager());
  }
  
  private String shorten(File file) {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    Settings tempSettings = intWizard.getSettings();
    
    String prefix = tempSettings.getProject().getLocationURI()
      .resolve(tempSettings.getProject().getName()).toASCIIString();
    
    if (prefix.startsWith("file:/")) {
      prefix = prefix.substring(6);
    }
    
    String full = file.getPath();
    if (full.startsWith("/")) {
      full = full.substring(1);
    }
    
    if (!prefix.endsWith("/")) {
      prefix += "/";
    }
    return full.replace(prefix, "");
  }
}
