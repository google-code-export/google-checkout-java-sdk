package com.google.checkout.sdk.eclipsemodule.handlermanager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Class associated with the popup menu for the folder
 * Start the wizard in the run method
 * 
 * @author cdang@google.com (Charles Dang)
 */
public class HandlerManagerAction implements IObjectActionDelegate {
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
    HandlerManager wizard = new HandlerManager();
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
