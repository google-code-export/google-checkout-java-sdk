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

package com.google.checkout.sdk.nbmodule.handlermanager;

import java.awt.Dialog;
import java.io.FileNotFoundException;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class HandlerManagerAction extends CallableSystemAction {
  
  public void performAction() {
    // Create the Handler Manager dialog
    String errorMsg = null;
    try {
      HandlerManagerPanel panel = new HandlerManagerPanel();
      if (panel.success()) {
        // Success, show Handler Manager
        DialogDescriptor desc = new DialogDescriptor(
            panel,  // panel to display
            "Google Checkout Handler Manager",  // dialog title
            true,  // modal
            new Object[] {"Save", "Cancel"},  // options
            "Save",  // initial value (selected option)
            DialogDescriptor.DEFAULT_ALIGN,  // options alignment
            null,  // help control
            null);  // action listener

        // Show the Handler Manager dialog
        Dialog dialog = DialogDisplayer.getDefault().createDialog(desc);
        dialog.setVisible(true);
        dialog.toFront();

        // Save if "Save" was pressed
        if (desc.getValue() instanceof String) {
          // Note: I check the type because if a button named "Cancel" is
          // pressed, Java returns the int -1; otherwise, it returns the
          // name of the button.
          panel.save();
        }
      } else {
        // No checkout-integrated projects, show error
        errorMsg = "No open projects containing checkout-config.xml.\n" +
            "Run the Integration Wizard on a project first.";
      }
    } catch (FileNotFoundException fnfe) {
      errorMsg = fnfe.getMessage();
      fnfe.printStackTrace();
    }
    
    if (errorMsg != null) {
      NotifyDescriptor d = 
          new NotifyDescriptor.Message(errorMsg, NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(d);
    }
  }
  
  public String getName() {
    return NbBundle.getMessage(HandlerManagerAction.class, 
        "CTL_HandlerManagerAction");
  }
  
  protected void initialize() {
    super.initialize();
    putValue("noIconInMenu", Boolean.TRUE);
  }
  
  public HelpCtx getHelpCtx() {
    return HelpCtx.DEFAULT_HELP;
  }
  
  protected boolean asynchronous() {
    return false;
  }
  
}
