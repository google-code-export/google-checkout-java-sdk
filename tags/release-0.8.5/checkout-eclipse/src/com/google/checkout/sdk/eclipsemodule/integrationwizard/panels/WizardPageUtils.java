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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class WizardPageUtils {
  
  public static void createLine(Composite composite, int ncol) {
    Label line = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = ncol;
    line.setLayoutData(gridData);
  }
  
  public static void showErrorMessage(IStatus status, IWizard wizard, WizardPage wizardPage) {
    String message = status.getMessage();
    if (message.length() == 0) message = null;
    switch (status.getSeverity()) {
      case IStatus.OK:
          wizardPage.setErrorMessage(null);
          wizardPage.setMessage(message);
          break;
      case IStatus.WARNING:
          wizardPage.setErrorMessage(null);
          wizardPage.setMessage(message, WizardPage.WARNING);
          break;              
      case IStatus.INFO:
          wizardPage.setErrorMessage(null);
          wizardPage.setMessage(message, WizardPage.INFORMATION);
          break;          
      default:
          wizardPage.setErrorMessage(message);
          wizardPage.setMessage(null);
          break;      
    }
    
    wizard.getContainer().updateButtons();
  }
}
