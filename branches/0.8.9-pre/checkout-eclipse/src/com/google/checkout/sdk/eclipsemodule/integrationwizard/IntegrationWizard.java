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

package com.google.checkout.sdk.eclipsemodule.integrationwizard;

import com.google.checkout.sdk.eclipsemodule.handlermanager.HandlerManagerPage;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.panels.ConfigWizardPage;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.panels.ConfirmationWizardPage;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.panels.ProjectWizardPage;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.panels.SamplesWizardPage;
import com.google.checkout.sdk.eclipsemodule.integrationwizard.panels.WebXmlWizardPage;
import com.google.checkout.sdk.module.exceptions.CheckoutConfigException;
import com.google.checkout.sdk.module.exceptions.CheckoutSdkException;
import com.google.checkout.sdk.module.exceptions.SamplesJspException;
import com.google.checkout.sdk.module.exceptions.WebXmlException;
import com.google.checkout.sdk.module.integrationwizard.Integrator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class IntegrationWizard extends Wizard implements INewWizard {  
  private ProjectWizardPage projectPage;
  private ConfigWizardPage configPage;
  private WebXmlWizardPage webXmlPage;
  private SamplesWizardPage samplesPage;
  private ConfirmationWizardPage confirmationPage;
  
  private static EclipseSettings settings;
  
  protected IStructuredSelection selection;
  protected IWorkbench workbench;
  
  public IntegrationWizard() {
    super();
    settings = new EclipseSettings();
    
    projectPage = new ProjectWizardPage();
    webXmlPage = new WebXmlWizardPage();
    configPage = new ConfigWizardPage();
    samplesPage = new SamplesWizardPage();
    confirmationPage = new ConfirmationWizardPage();
  }
  
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    this.workbench = workbench;
    this.selection = selection;
    
    setWindowTitle("Google Checkout Integration Wizard");
  }
  
  @Override
  public boolean canFinish() {
    // can only complete wizard from the last page
    return (getContainer().getCurrentPage() == confirmationPage);
  }
  
  @Override
  public boolean performFinish() {
    boolean success = performIntegration();
    
    if (success) {
      try {
        // refresh the project settings
        settings.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
      } catch (CoreException ex) {
        MessageDialog.openError(getShell(), "", ex.getMessage());
      }
    }
    
    if (settings.launchHandlerManager()) {
      // TODO(cdang): figure out how to launch Handler Manager from here

    }
    
    return true;
  }
  
  @Override
  public void addPages() {
    if (ResourcesPlugin.getWorkspace().getRoot().getProjects().length > 0) {
      createWizardPages();
    } else {
      String msg = 
        "No open projects.\n" +
        "Open your web application in Eclipse first, then run the " + 
        "Integration Wizard again.";
 
      MessageDialog.openError(getShell(), "", msg);
    }
  }
  
  private void createWizardPages() {
    // first page of the wizard
    addPage(projectPage);
    
    // second page of the wizard
    addPage(webXmlPage);
    
    // third page of the wizard
    addPage(configPage);

    // fourth page of the wizard
    addPage(samplesPage);
 
    // fifth page of the wizard
    addPage(confirmationPage);
    
    // sixth page of the wizard
//    addPage(handlerPage);
  }
  
  /*************************************************************************/
  /*                         SETTINGS ACCESSORS                            */
  /*************************************************************************/
  
  public EclipseSettings getSettings() {
    return settings;
  }
  
  public ProjectWizardPage getProjectPage() {
    return projectPage;
  }
  
  public ConfigWizardPage getConfigPage() {
    return configPage;
  }
  
  public WebXmlWizardPage getWebXmlPage() {
    return webXmlPage;
  }
  
  public SamplesWizardPage getSamplesPage() {
    return samplesPage;
  }
  
  public ConfirmationWizardPage getConfirmationPage() {
    return confirmationPage;
  }
  
  public HandlerManagerPage getHandlerManagerPage() {
    return null;
  }
  
  private boolean performIntegration() {
    Integrator integrator = new Integrator(settings);
    
    String errorMsg = null;
    
    try {
      integrator.integrate();
    } catch (CheckoutSdkException ex) {
      errorMsg = ex.getMessage();
    } catch (WebXmlException ex) {
      errorMsg = ex.getMessage();
    } catch (CheckoutConfigException ex) {
      errorMsg = ex.getMessage();
    } catch (SamplesJspException ex) {
      errorMsg = ex.getMessage();
    }
    
    if (errorMsg == null) {
      return true;
    }
    
    MessageDialog.openError(getShell(), "", errorMsg);
    
    return false;
  }
}
