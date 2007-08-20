package com.google.checkout.sdk.nbmodule.handlermanager;

import java.awt.Dialog;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class HandlerManagerAction extends CallableSystemAction {
    
    public void performAction() {
        // Create the Handler Manager dialog
        HandlerManagerPanel panel = new HandlerManagerPanel();
        if (!panel.success()) {
            // No checkout-integrated projects, show error
            String msg = "No open projects containing checkout-config.xml.\n" +
                    "Run the Integration Wizard on a project first.";
            NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(d);  
        } else {
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
        }
    }
    
    public String getName() {
        return NbBundle.getMessage(HandlerManagerAction.class, "CTL_HandlerManagerAction");
    }
    
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
