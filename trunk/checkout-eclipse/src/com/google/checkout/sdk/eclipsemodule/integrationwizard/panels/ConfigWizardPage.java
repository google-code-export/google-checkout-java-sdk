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

import com.google.checkout.sdk.eclipsemodule.common.CheckoutConfigManager;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.IntegrationWizard;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.Settings;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class ConfigWizardPage extends WizardPage {

  private Label configLabel;
  private Text configTextField;
  
  private Label merchantIdLabel;
  private Text merchantIdTextField;
  
  private Label merchantKeyLabel;
  private Text merchantKeyTextField;
  
  private Label envLabel;
  private Combo envComboBox;
  
  private Label currencyCodeLabel;
  private Text currencyCodeTextField;
  
  private IStatus textBoxFieldsStatus;
  
  private final static String[] ENV_OPTIONS = {"Sandbox", "Production"};
  
  public ConfigWizardPage() {
    super("Configuration Wizard Page");
    
    this.textBoxFieldsStatus = new Status(IStatus.OK, "not_used", 0, "", null);
    
    setTitle("Configuration Wizard Page");
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
    
    configLabel = new Label(composite, SWT.NULL);
    configLabel.setText("This will place checkout-config at:");
    configLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    
    configTextField = new Text(composite, SWT.BORDER);
    configTextField.setEditable(false);
    configTextField.setLayoutData(gd);
    
    WizardPageUtils.createLine(composite, ncol);

    // merchantId row
    gd = new GridData();
    gd.horizontalAlignment = SWT.BEGINNING;
    
    merchantIdLabel = new Label(composite, SWT.NULL);
    merchantIdLabel.setText("Merchant ID: ");
    merchantIdLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol-1;
    
    merchantIdTextField = new Text(composite, SWT.BORDER);
    merchantIdTextField.addListener(SWT.KeyUp, new Listener() {
      public void handleEvent(Event evt) {
        merchantIdTextFieldKeyReleased(evt);
      }
    });
    merchantIdTextField.setLayoutData(gd);
    
    // merchantKey row
    gd = new GridData();
    gd.horizontalAlignment = SWT.BEGINNING;
    
    merchantKeyLabel = new Label(composite, SWT.NULL);
    merchantKeyLabel.setText("Merchant Key: ");
    merchantKeyLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol-1;
    
    merchantKeyTextField = new Text(composite, SWT.BORDER);
    merchantKeyTextField.addListener(SWT.KeyUp, new Listener() {
      public void handleEvent(Event evt) {
        merchantKeyTextFieldKeyReleased(evt);
      }
    });
    merchantKeyTextField.setLayoutData(gd);
    
    gd = new GridData();
    gd.horizontalAlignment = SWT.BEGINNING;
    
    envLabel = new Label(composite, SWT.NULL);
    envLabel.setText("Environment: ");
    envLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol-1;
    
    envComboBox = new Combo(composite, SWT.READ_ONLY | SWT.SIMPLE);
    envComboBox.setItems(ENV_OPTIONS);
    envComboBox.select(0);
    envComboBox.setLayoutData(gd);
    
    gd = new GridData();
    gd.horizontalAlignment = SWT.BEGINNING;
    
    currencyCodeLabel = new Label(composite, SWT.NULL);
    currencyCodeLabel.setText("Currency: ");
    currencyCodeLabel.setLayoutData(gd);
    
    gd = new GridData();
    gd.horizontalSpan = ncol-1;
    
    currencyCodeTextField = new Text(composite, SWT.BORDER);
    currencyCodeTextField.addListener(SWT.KeyUp, new Listener() {
      public void handleEvent(Event evt) {
        currencyCodeTextFieldKeyReleased(evt);
      }
    });
    currencyCodeTextField.setLayoutData(gd);
    
    // set the composite as the control for this page
    setControl(composite);
  }
  
  /**
   * Returns the next page.
   * Saves the values from this page in the settings associated with the wizard.
   * Initializes the widgets on the next page.
   */
  @Override
  public IWizardPage getNextPage() {
    SamplesWizardPage samplesPage = ((IntegrationWizard)getWizard()).getSamplesPage();
    samplesPage.onEnterPage();
    return samplesPage;
  }
  
  @Override
  public boolean canFlipToNextPage() {
    return isValid();
  }
  
  /*************************************************************************/
  /*                           EVENT HANDLERS                              */
  /*************************************************************************/
  
  private void merchantIdTextFieldKeyReleased(Event evt) {
    updateState();
  }
  
  private void merchantKeyTextFieldKeyReleased(Event evt) {
    updateState();
  }
  
  private void currencyCodeTextFieldKeyReleased(Event evt) {
    updateState();
  }
  
  /*************************************************************************/
  /*                          UTILITY METHODS                              */
  /*************************************************************************/
  
  private void updateState() {
    // Initialize a variable with the no error status
    Status status = new Status(IStatus.OK, "not_used", 0, "", null);
    
    if (!isValid()) {
      status = new Status(IStatus.ERROR, "not_used", 0, 
          "Please fill in the fields for all textboxes", null);
    }
    
    textBoxFieldsStatus = status;
    
    WizardPageUtils.showErrorMessage(textBoxFieldsStatus, getWizard(), this);
  }
  
  public void onEnterPage() {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    Settings tempSettings = intWizard.getSettings();
    
    // Get the default checkout-config.xml if one doesn't already exist
    if (tempSettings.getConfigManager().getOutputLocation() == null) {
      tempSettings.getConfigManager().setOutputLocation(tempSettings
          .getWebInfDirectory().toURI().resolve("checkout-config.xml").getPath());
    }
    
    // Set the checkout-config.xml field
    configTextField.setText(tempSettings.getConfigManager().getOutputLocation());
    
    // Fill the text fields
    CheckoutConfigManager configManager = tempSettings.getConfigManager();
    merchantIdTextField.setText(configManager.getMerchantId());
    merchantKeyTextField.setText(configManager.getMerchantKey());

    int index = envComboBox.indexOf(configManager.getEnv());
    
    if (index >= 0) {
      envComboBox.select(index);
    }    
    
    currencyCodeTextField.setText(configManager.getCurrencyCode());
  }
  
  private boolean isValid() {
    return (!merchantIdTextField.getText().equals("") 
        && !merchantKeyTextField.getText().equals("") 
        && !currencyCodeTextField.getText().equals(""));
  }
}
