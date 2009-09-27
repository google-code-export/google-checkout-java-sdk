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

import com.google.checkout.sdk.eclipsemodule.integrationwizard.EclipseSettings;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.IntegrationWizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import java.io.File;
import java.net.URI;

public class SamplesWizardPage extends WizardPage {

  private Button addSamplesCheckBox;
  private Label samplesDirectoryLabel;
  private Text samplesDirectoryTextField;
  private Button browseButton;

  public SamplesWizardPage() {
    super("Samples Wizard Page");

    setTitle("Samples Wizard Page");
    setPageComplete(true);
  }

  public void createControl(Composite parent) {
    // create the composite to hold the widgets
    GridData gd;
    Composite composite = new Composite(parent, SWT.NULL);

    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 2;
    gl.numColumns = ncol;
    composite.setLayout(gl);

    // create the widgets. If the appearance of the widget is different from the
    // default, create a GridData for it to set the alignment and define how
    // much space it will occupy

    gd = new GridData();
    gd.horizontalAlignment = SWT.BEGINNING;

    addSamplesCheckBox = new Button(composite, SWT.CHECK);
    addSamplesCheckBox.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event evt) {
        addSamplesCheckboxStateChanged();
      }
    });
    addSamplesCheckBox.setText("Add samples pages to this project");
    addSamplesCheckBox.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalAlignment = SWT.BEGINNING;
    gd.horizontalSpan = ncol;

    samplesDirectoryLabel = new Label(composite, SWT.NULL);
    samplesDirectoryLabel.setText("Samples directory: ");
    samplesDirectoryLabel.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol - 1;

    samplesDirectoryTextField = new Text(composite, SWT.BORDER);
    samplesDirectoryTextField.setLayoutData(gd);

    gd = new GridData();
    gd.horizontalAlignment = SWT.END;

    browseButton = new Button(composite, SWT.NULL);
    browseButton.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event evt) {
        browseButtonActionPerformed();
      }
    });
    browseButton.setText("Browse");
    browseButton.setLayoutData(gd);

    // set the composite as the control for this page
    setControl(composite);
  }

  /**
   * Returns the next page. Saves the values from this page in the settings
   * associated with the wizard. Initializes the widgets on the next page.
   */
  @Override
  public IWizardPage getNextPage() {
    ConfirmationWizardPage confirmPage =
        ((IntegrationWizard) getWizard()).getConfirmationPage();
    confirmPage.onEnterPage();
    return confirmPage;
  }

  /** ********************************************************************** */
  /* EVENT HANDLERS */
  /** ********************************************************************** */

  private void browseButtonActionPerformed() {
    DirectoryDialog dd = new DirectoryDialog(getShell());
    dd.setText("WEB-INF Directory");
    dd.setFilterPath(samplesDirectoryTextField.getText());
    File selectedFile = new File(dd.open());

    if (selectedFile != null) {
      String text = selectedFile.getPath();
      samplesDirectoryTextField.setText(text);
    }
  }

  private void addSamplesCheckboxStateChanged() {
    IntegrationWizard intWizard = (IntegrationWizard) getWizard();
    EclipseSettings tempSettings = intWizard.getSettings();

    boolean selected = addSamplesCheckBox.getSelection();
    samplesDirectoryTextField.setEnabled(selected);
    browseButton.setEnabled(selected);
    tempSettings.setAddSamples(selected);
  }

  /** ********************************************************************** */
  /* UTILITY METHODS */
  /** ********************************************************************** */

  public void onEnterPage() {
    IntegrationWizard intWizard = (IntegrationWizard) getWizard();
    EclipseSettings tempSettings = intWizard.getSettings();

    // Generate default samples directory if none provided
    if (tempSettings.getSamplesDirectory() == null) {
      URI uri = tempSettings.getProject().getLocationURI();
      File file =
          new File(uri.resolve(tempSettings.getProject().getName()
              + "/WebContent/checkout"));
      tempSettings.setSamplesDirectory(file);
    }

    // Show the samples directory in the text field
    samplesDirectoryTextField.setText(tempSettings.getSamplesDirectory()
        .getPath());

    // Set the samples check box
    addSamplesCheckBox.setSelection(tempSettings.addSamples());
    samplesDirectoryTextField.setEnabled(tempSettings.addSamples());
    browseButton.setEnabled(tempSettings.addSamples());
  }
}
