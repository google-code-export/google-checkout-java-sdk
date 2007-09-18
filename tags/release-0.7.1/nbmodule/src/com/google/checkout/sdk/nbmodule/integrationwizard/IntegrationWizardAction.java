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

package com.google.checkout.sdk.nbmodule.integrationwizard;

import com.google.checkout.sdk.nbmodule.handlermanager.HandlerManagerAction;
import com.google.checkout.sdk.nbmodule.integrationwizard.panels.ConfigWizardPanel;
import com.google.checkout.sdk.nbmodule.integrationwizard.panels.ConfirmationWizardPanel;
import com.google.checkout.sdk.nbmodule.integrationwizard.panels.ProgressPanel;
import com.google.checkout.sdk.nbmodule.integrationwizard.panels.ProjectWizardPanel;
import com.google.checkout.sdk.nbmodule.integrationwizard.panels.SamplesWizardPanel;
import com.google.checkout.sdk.nbmodule.integrationwizard.panels.WebXmlWizardPanel;
import java.awt.Component;
import java.awt.Dialog;
import java.text.MessageFormat;
import javax.swing.JComponent;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class IntegrationWizardAction extends CallableSystemAction {
  
  private WizardDescriptor.Panel[] panels;
  
  /**
   * Creates and shows the wizard when the action button is pressed.
   */
  public void performAction() {
    if (OpenProjects.getDefault().getOpenProjects().length <= 0) {
      // No open projects, display error
      String msg = "No open projects.\n" +
          "Open your web application in NetBeans first,\n" +
          "then run the Integration Wizard again.";
      NotifyDescriptor d =
          new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(d);
    } else {
      // Create and show the wizard
      IntegrationWizardDescriptor wizardDescriptor = 
          new IntegrationWizardDescriptor(getPanels());
      wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
      wizardDescriptor.setTitle("Google Checkout Integration Wizard");
      Dialog dialog = 
          DialogDisplayer.getDefault().createDialog(wizardDescriptor);
      dialog.setVisible(true);
      dialog.toFront();
      
      // Handle the integration after the wizard closes
      boolean cancelled = wizardDescriptor.getValue() != 
          WizardDescriptor.FINISH_OPTION;
      if (!cancelled) {
        // Create the progress dialog
        ProgressPanel progressPanel = new ProgressPanel();
        DialogDescriptor desc = new DialogDescriptor(
            progressPanel,  // panel to display
            "Integrating Google Checkout SDK",  // dialog title
            false,  // modal
            new Object[0],  // options
            null,  // initial value (selected option)
            DialogDescriptor.DEFAULT_ALIGN,  // options alignment
            null,  // help control
            null);  // action listener
        
        // Show the progress dialog
        dialog = DialogDisplayer.getDefault().createDialog(desc);
        dialog.setVisible(true);
        dialog.toFront();
        
        // Create the integrator that does the actual integration work
        Settings settings = wizardDescriptor.getSettings();
        Integrator integrator = new Integrator(settings, progressPanel);
        
        // Integrate
        boolean success = integrator.integrate();
        
        // Close the progress dialog
        dialog.setVisible(false);
        
        if (success) {
          // Refresh the file system to update changes in the IDE
          try {
            Project p = settings.getProject();
            FileObject dir = p.getProjectDirectory();
            dir.getFileSystem().refresh(false);
          } catch (FileStateInvalidException ex) {
            // Simply didn't refresh; this is okay
          }
          
          // Launch handler manager if requested
          if (settings.launchHandlerManager()) {
            new HandlerManagerAction().performAction();
          }
        } else {
          // Show error message
          String msg = integrator.getErrorMessage();
          NotifyDescriptor d = 
              new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
          DialogDisplayer.getDefault().notify(d);
        }
      }
    }
  }
  
  /**
   * Initialize panels representing individual wizard's steps and sets
   * various properties for them influencing wizard appearance.
   */
  private WizardDescriptor.Panel[] getPanels() {
    if (panels == null) {
      panels = new WizardDescriptor.Panel[] {
        new ProjectWizardPanel.Panel(),
        new WebXmlWizardPanel.Panel(),
        new ConfigWizardPanel.Panel(),
        new SamplesWizardPanel.Panel(),
        new ConfirmationWizardPanel.Panel()
      };
      String[] steps = new String[panels.length];
      for (int i = 0; i < panels.length; i++) {
        Component c = panels[i].getComponent();
        // Default step name to component name of panel. Mainly useful
        // for getting the name of the target chooser to appear in the
        // list of steps.
        steps[i] = c.getName();
        if (c instanceof JComponent) { // assume Swing components
          JComponent jc = (JComponent) c;
          // Sets step number of a component
          jc.putClientProperty("WizardPanel_contentSelectedIndex", 
              new Integer(i));
          // Sets steps names for a panel
          jc.putClientProperty("WizardPanel_contentData", steps);
          // Turn on subtitle creation on each step
          jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
          // Show steps on the left side with the image on the background
          jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
          // Turn on numbering of all steps
          jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
        }
      }
    }
    return panels;
  }
  
  public String getName() {
    return NbBundle.getMessage(IntegrationWizardAction.class, 
        "CTL_IntegrationWizardAction");
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
