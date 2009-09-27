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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import java.io.File;
import java.net.URI;

public class ProjectWizardPage extends WizardPage {
  
  // widgets on this page
  private Button browseButton;
  
  private Label locationLabel;
  private Label projectLabel;
  private Label webInfLabel;
   
  private IProject[] projects;
  private List projectList;
  
  private Text webInfTextField;

  private boolean validWebInfPath;
  
  private IStatus webInfStatus;
  private IStatus integrationStatus;
    
  public ProjectWizardPage() {
    super("Project Wizard Page");
    
    this.validWebInfPath = true;
    
    this.webInfStatus = new Status(IStatus.OK, "not_used", 0, "", null);
    this.integrationStatus = new Status(IStatus.OK, "not_used", 0, "", null);
    
    setTitle("Project Wizard Page");
    setPageComplete(true);
  }
 
  public void createControl(Composite parent) {
    
    projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

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
    gd.horizontalSpan = ncol-1;
    
    projectLabel = new Label(composite, SWT.NONE);
    projectLabel.setText("Select a project to integrate with.");   
    projectLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    gd.heightHint = 200;
    
    // project list part
    projectList = new List(composite, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
    projectList.addListener(SWT.Selection, new Listener() {
        public void handleEvent(Event evt) {
          projectListValueChanged();
        }
    });
    try {
      for (int i=0; i< projects.length; ++i) {
        projectList.add(projects[i].getDescription().getName());
      }
      projectList.select(0);
    } catch (CoreException ex) { 
      MessageDialog.openError(getShell(), "", ex.getMessage());
      
      ex.printStackTrace();
    }
    projectList.setLayoutData(gd);
    
    // warning label
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol; 
    
    // create a separator
    WizardPageUtils.createLine(composite, ncol);
    
    gd.verticalSpan = 2;
    
    new Label(composite, SWT.NULL).setLayoutData(gd);
    
    webInfLabel = new Label(composite, SWT.NULL);
    webInfLabel.setText("Locate this project's WEB-INF directory");
    webInfLabel.setLayoutData(gd);
    
    // location label
    gd = new GridData(GridData.BEGINNING);
    
    locationLabel = new Label(composite, SWT.NULL);
    locationLabel.setText("Location:");
    locationLabel.setLayoutData(gd);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol-2;
    webInfTextField = new Text(composite, SWT.BORDER);
    webInfTextField.addListener(SWT.KeyUp, new Listener() {
      public void handleEvent(Event evt) {
        webInfTextFieldKeyReleased();
      }
    });
    
    webInfTextField.setLayoutData(gd);
    
    gd = new GridData();
    gd.horizontalAlignment = GridData.END;
    
    browseButton = new Button(composite, SWT.NULL);
    browseButton.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event evt) {
        browseButtonActionPerformed();
      }
    });
    
    browseButton.setText("Browse");
    browseButton.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event evt) {
        browseButtonActionPerformed();
      }
    });
    browseButton.setLayoutData(gd);   
    
    projectListValueChanged();
    
    // set the composite as the control for this page
    setControl(composite);      
  }
  
  @Override
  public boolean canFlipToNextPage() {
    return validWebInfPath;
  }
  
  /**
   * Returns the next page.
   * Saves the values from this page in the settings associated with the wizard.
   * Initializes the widgets on the next page.
   */
  @Override
  public IWizardPage getNextPage() {
    WebXmlWizardPage webXmlPage = ((IntegrationWizard)getWizard()).getWebXmlPage();
    webXmlPage.onEnterPage();
    return webXmlPage;
  }
  
  private void updateWebInfTextField() {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    EclipseSettings tempSettings = intWizard.getSettings();
    
    // Generate default WEB-INF directory if none are provided
    if (tempSettings.getWebInfDirectory() == null) {
      URI uri = tempSettings.getProject().getLocationURI();
      File file = 
        new File(uri.resolve(tempSettings.getProject().getName() + "/WebContent/WEB-INF"));
      tempSettings.setWebInfDirectory(file);
      
      // Show the WEB-INF directory in the text field
      webInfTextField.setText(tempSettings.getWebInfDirectory().getPath()); 
    }
  }
  
  /*************************************************************************/
  /*                           EVENT HANDLERS                              */
  /*************************************************************************/
  
  private void webInfTextFieldKeyReleased() {
    boolean pageComplete = hasValidWebInfPath(webInfTextField.getText());
    
    // Initialize a variable with the no error status
    Status status = new Status(IStatus.OK, "not_used", 0, "", null);
    
    // If the event is triggered by the browseButton, set the corresponding
    // status variable to the right value
    if (!validWebInfPath) {
      status = new Status(IStatus.ERROR, "not_used", 0, 
          "Please select a valid web-inf directory", null);
    }
    webInfStatus = status;
    
    WizardPageUtils.showErrorMessage(findMostSevere(), getWizard(), this);
    
    setPageComplete(pageComplete);
  }
  
  private void browseButtonActionPerformed() {    
    // Generate and show the file chooser
    DirectoryDialog dd = new DirectoryDialog(getShell());
    dd.setText("WEB-INF Directory");
    dd.setFilterPath(webInfTextField.getText());
    File selectedFile = new File(dd.open());
    
    if (selectedFile != null) {
      String text = selectedFile.getPath();
      webInfTextField.setText(text);
      validWebInfPath = hasValidWebInfPath(text);
    }
    
    // Initialize a variable with the no error status
    Status status = new Status(IStatus.OK, "not_used", 0, "", null);
    
    // If the event is triggered by the browseButton, set the corresponding
    // status variable to the right value
    if (!validWebInfPath) {
      status = new Status(IStatus.ERROR, "not_used", 0, 
          "Please select a valid web-inf directory", null);
    }
    webInfStatus = status;
    
    WizardPageUtils.showErrorMessage(findMostSevere(), getWizard(), this);
  }
  
  private void projectListValueChanged() {
    IProject newProject = projects[projectList.getSelectionIndex()];
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    EclipseSettings tempSettings = intWizard.getSettings();
    
    if (tempSettings.getProject() != newProject) {
      // Project selection has changed, so reset settings (to clear
      // info from other panels)
      tempSettings.reset();
      
      // Set the new project
      tempSettings.setProject(newProject);
      
      // Check to see if the selected project has already been integrated
      checkIntegration();
      
      // Update the WEB-INF text field
      updateWebInfTextField();
    }
    
    setPageComplete(validWebInfPath);
    WizardPageUtils.showErrorMessage(findMostSevere(), getWizard(), this);
  }
  
  /*************************************************************************/
  /*                          UTILITY METHODS                              */
  /*************************************************************************/
  
  private IStatus findMostSevere() {
    if (webInfStatus.matches(IStatus.ERROR)) {
      return webInfStatus;
    } else { // (integrationStatus.matches(IStatus.WARNING))
      return integrationStatus;
    }
  }
  
  /**
   * Checks whether the selected project has already been integrated (i.e.
   * already contains the checkout-config.xml)
   */
  private void checkIntegration() {
    IntegrationWizard intWizard = (IntegrationWizard)getWizard();
    EclipseSettings tempSettings = intWizard.getSettings();

    String prefix = tempSettings.getProject().getLocationURI()
      .resolve(tempSettings.getProject().getName()).toASCIIString();
  
    if (prefix.startsWith("file:")) {
      prefix = prefix.substring(5);
    }
    
    File file = new File(prefix + "/WebContent/WEB-INF/checkout-config.xml");
    
    // Initialize a variable with the no error status
    Status status = new Status(IStatus.OK, "not_used", 0, "", null);
    
    // If the event is triggered by the projectList, set the corresponding
    // status variable to the right value    
    if (file.exists()) {
      status = new Status(IStatus.WARNING, "not_used", 0, 
          "The selected project has already been integrated.", null);
    }
    integrationStatus = status;
    
    WizardPageUtils.showErrorMessage(findMostSevere(), getWizard(), this);
  }
  
  private boolean hasValidWebInfPath(String path) {
    File temp = new File(path);

    return (validWebInfPath = (temp.exists() ? true : false));
  }
}
