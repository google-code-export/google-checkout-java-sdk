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

package com.google.checkout.sdk.eclipsemodule.handlermanager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class HandlerManager extends Wizard implements INewWizard {
  
  private HandlerManagerPage handlerPage;
  
  public HandlerManager() {
    super();
  }
  
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    handlerPage = new HandlerManagerPage();
    
    setWindowTitle("Google Checkout Handler Manager");
  }
  
  @Override
  public boolean performFinish() {
    try {
      IProject selectedProject = handlerPage.getSelectedProject();
      
      if (selectedProject != null ) {
        selectedProject.refreshLocal(IResource.DEPTH_INFINITE, null);
      }
    } catch (CoreException ex) {
      // if refresh doesn't work, it's fine, let the user refresh it themselves
    }
    
    return handlerPage.save();
  }
  
  @Override
  public void addPages() {
    //
    if (handlerPage.success()) {
      createWizardPages();
    } else {
      String msg = 
        "No open projects containing checkout-config.xml.\n" +
        "Run the Integration Wizard on a project first.";
 
      MessageDialog.openError(getShell(), "", msg);
    }
  }
  
  @Override
  public boolean needsPreviousAndNextButtons() {
    return false;
  }
  
  /*************************************************************************/
  /*                         SETTINGS ACCESSORS                            */
  /*************************************************************************/
  
  public HandlerManagerPage getHandlerManagerPage() {
    return handlerPage;
  }
  
  /*************************************************************************/
  /*                          UTILITY METHODS                              */
  /*************************************************************************/
  
  private void createWizardPages() {
    addPage(handlerPage);
  }
}
