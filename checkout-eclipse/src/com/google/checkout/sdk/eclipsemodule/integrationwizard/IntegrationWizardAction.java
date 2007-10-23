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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class IntegrationWizardAction implements IObjectActionDelegate {

    private IWorkbenchPart part;
    private ISelection selection;
  
    /**
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart part) {
      this.part = part;
    }
  
    /**
     * @see IActionDelegate#run(IAction)
     * Instantiates the wizard and opens it in the wizard container
     */
    public void run(IAction action) {
      // Instantiates and initializes the wizard
      IntegrationWizard wizard = new IntegrationWizard();
      if ((selection instanceof IStructuredSelection) || (selection == null)) {
        wizard.init(part.getSite().getWorkbenchWindow().getWorkbench(), 
            (IStructuredSelection)selection);
      }
      
      // Instantiates the wizard container with the wizard and opens it
      WizardDialog dialog = new WizardDialog(part.getSite().getShell(), wizard);
      dialog.create();
      dialog.open();
    }
    
    /**
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
      this.selection = selection;
    }
}
