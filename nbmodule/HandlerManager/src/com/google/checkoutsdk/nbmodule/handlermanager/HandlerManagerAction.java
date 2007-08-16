package com.google.checkoutsdk.nbmodule.handlermanager;

import java.awt.Dialog;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class HandlerManagerAction extends CallableSystemAction {
    
    public void performAction() {
        // Create the Handler Manager dialog
        HandlerManagerPanel panel = new HandlerManagerPanel();
        DialogDescriptor desc = new DialogDescriptor(
                panel,  // panel to display
                "Google Checkout Handler Manager",  // dialog title
                true,  // modal
                new Object[] {"Close"},  // options
                "Close",  // initial value (selected option)
                DialogDescriptor.DEFAULT_ALIGN,  // options alignment
                null,  // help control
                null);  // action listener

        // Show the Handler Manager dialog
        Dialog dialog = DialogDisplayer.getDefault().createDialog(desc);
        dialog.setVisible(true);
        dialog.toFront();
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
