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
import com.google.checkout.sdk.module.integrationwizard.Settings;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class WebXmlWizardPage extends WizardPage {

  private Label webXmlLabel;
  private Text webXmlTextField;

  private Label previewLabel;
  private Label previewDescriptionLabel;
  private Text previewText;

  // The xml fragment to insert into web.xml
  private String webFragment;

  /**
   * Creates the web.xml choosing panel for the Integration Wizard
   */
  public WebXmlWizardPage() {
    super("Web Xml Wizard Page");

    setTitle("Web Xml Wizard Page");
    setPageComplete(true);

    readWebFragment();
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
    // default, create a GridData for it to set the alignment and how much space
    // it will occupy
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalAlignment = SWT.BEGINNING;
    gd.horizontalSpan = ncol;

    webXmlLabel = new Label(composite, SWT.NULL);
    webXmlLabel.setText("This will modify your web.xml file found at: ");
    webXmlLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;

    webXmlTextField = new Text(composite, SWT.BORDER);
    webXmlTextField.setEditable(false);
    webXmlTextField.setLayoutData(gd);

    WizardPageUtils.createLine(composite, ncol);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    
    previewLabel = new Label(composite, SWT.NULL);
    previewLabel.setText("Preview");
    previewLabel.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    
    previewDescriptionLabel = new Label(composite, SWT.NULL);
    previewDescriptionLabel
        .setText("Preview the changes, and modify them if necessary");
    previewDescriptionLabel.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    gd.heightHint = 200;

    previewText = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
    previewText.setLayoutData(gd);

    setControl(composite);
  }

  /**
   * Returns the next page.
   * Saves the values from this page in the settings associated with the wizard.
   * Initializes the widgets on the next page.
   */
  @Override
  public IWizardPage getNextPage() {
    ConfigWizardPage configPage = (((IntegrationWizard)getWizard()).getConfigPage());
    configPage.onEnterPage();
    return configPage;
  }
  
  @Override
  public boolean canFlipToNextPage() {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    EclipseSettings tempSettings = intWizard.getSettings();
    
    File f = new File(tempSettings.getWebInfDirectory().toURI().resolve("web.xml"));
    return f.exists();
  }
  
  /*************************************************************************/
  /*                          UTILITY METHODS                              */
  /*************************************************************************/
  
  /**
   * Reads the new web.xml file, generates the modified version (by inserting
   * the web fragment), and displays the final text or an error message.
   */
  private void processWebXmlFile() {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    EclipseSettings tempSettings = intWizard.getSettings();
    
    String errorMsg = null;

    if (webFragment == null) {
      errorMsg = "Error reading webFragment.xml file.";
    } else if (tempSettings.getWebXmlFile() == null) {
      errorMsg = "No file selected.";
    } else {
      tempSettings.setModifiedWebXml(null);

      // Read the file and display it in the preview area
      try {
        BufferedReader reader =
            new BufferedReader(new FileReader(tempSettings.getWebXmlFile()));
        String line = reader.readLine();
        StringBuilder lines = new StringBuilder();
        while (line != null) {
          lines.append(line + "\n");
          line = reader.readLine();
        }

        // Remove trailing new line
        if (lines.toString().endsWith("\n")) {
          lines.deleteCharAt(lines.length() - 1);
        }

        // Insert CheckoutSDK web.xml and set preview text
        if (insertWebFragment(lines.toString())) {
          previewText.setText(tempSettings.getModifiedWebXml());
        } else {
          errorMsg = "Unable to automatically insert web.xml fragment.";
        }
      } catch (FileNotFoundException ex) {
        errorMsg = "File web.xml not found.";
      } catch (IOException ex) {
        errorMsg = "Error reading file.";
      }

      // Handle errors
      if (errorMsg != null) {
        previewText.setText(errorMsg);
        tempSettings.setModifiedWebXml(null);
      }
    }
  }
  
  public void onEnterPage() {
    updatePanel();
  }
  
  private void updatePanel() {
    // Get the default web.xml if one doesn't already exist
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    Settings tempSettings = intWizard.getSettings();
    
    if (tempSettings.getWebXmlFile() == null) {
      URI uri = tempSettings.getWebInfDirectory().toURI().resolve("web.xml");
      tempSettings.setWebXmlFile(new File(uri));
    }

    // Set the web.xml field
    webXmlTextField.setText(tempSettings.getWebXmlFile().getPath());

    // Fill the preview area with a newly processed file or a modified file
    if (tempSettings.getModifiedWebXml() != null) {
      previewText.setText(tempSettings.getModifiedWebXml());
    } else {
      processWebXmlFile();
    }
  }
  
  private boolean insertWebFragment(String file) {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    Settings tempSettings = intWizard.getSettings();
    
    // TODO: Add coloring to new fragment
    int index = file.indexOf("</web-app>");

    if (index >= 0) {
      tempSettings.setModifiedWebXml(file.substring(0, index) + "\n" + webFragment
          + "\n" + file.substring(index));
      return true;
    }

    return false;
  }

  private void readWebFragment() {
    String loc = "/resources/webFragment.xml";
    InputStream source = getClass().getResourceAsStream(loc);
    if (source != null) {
      try {
        StringBuilder buf = new StringBuilder();

        int ch;
        while ((ch = source.read()) != -1) {
          buf.append((char) ch);
        }
        source.close();

        webFragment = buf.toString();
      } catch (IOException ex) {
        webFragment = null;
      }
    } else {
      webFragment = null;
    }
  }
}
