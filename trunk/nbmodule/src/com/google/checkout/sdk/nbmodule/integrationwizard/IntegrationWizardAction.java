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

import com.google.checkout.sdk.module.exceptions.CheckoutConfigException;
import com.google.checkout.sdk.module.exceptions.CheckoutSdkException;
import com.google.checkout.sdk.module.exceptions.SamplesJspException;
import com.google.checkout.sdk.module.exceptions.WebXmlException;

import com.google.checkout.sdk.module.integrationwizard.Integrator;

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

  private CheckoutIntegrationPanel[] panels;
  private static HandlerManagerAction handlerManagerAction = new HandlerManagerAction();
  /**
   * Creates and shows the wizard when the action button is pressed.
   */

  public void performAction() {
    if (OpenProjects.getDefault().getOpenProjects().length <= 0) {
      displayNoOpenProjectsDialog();
    } else if (OpenProjects.getDefault().getMainProject() == null) {
      displayNoMainProjectDialog();
    } else {
      createPanels();

      // Create the integration wizard
      IntegrationWizardDescriptor wizardDescriptor =
              new IntegrationWizardDescriptor(panels);

      // need to pass in the IntegrationWizardDescriptor to the panels so they
      // can disable/enable the 'Next' button accordingly when their state changes
      for (int i = 0; i < panels.length; ++i) {
        panels[i].setIntegrationWizardDescriptor(wizardDescriptor);
      }

      wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
      wizardDescriptor.setTitle("Google Checkout Integration Wizard");

      // Show the integration wizard
      Dialog dialog =
              DialogDisplayer.getDefault().createDialog(wizardDescriptor);
      dialog.setVisible(true);
      dialog.toFront();

      // Handle the integration after the wizard closes      
      if (!(wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION)) {
        // The user did not click on the 'Cancel' button

        // Create the progress dialog
        ProgressPanel progressPanel = new ProgressPanel();
        DialogDescriptor desc = new DialogDescriptor(progressPanel, // panel to display
                "Integrating Google Checkout SDK", // dialog title
                false, // modal
                new Object[0], // options
                null, // initial value (selected option)
                DialogDescriptor.DEFAULT_ALIGN, // options alignment
                null, // help control
                null);  // action listener

        // Show the progress dialog
        dialog = DialogDisplayer.getDefault().createDialog(desc);
        dialog.setVisible(true);
        dialog.toFront();

        performIntegration(wizardDescriptor, dialog);
      }
    }
  }

  public String getName() {
    return NbBundle.getMessage(IntegrationWizardAction.class,
            "CTL_IntegrationWizardAction");
  }

  public HelpCtx getHelpCtx() {
    return HelpCtx.DEFAULT_HELP;
  }

  /**
   * Display an error dialog notifying the user that no projects are currently 
   * opened.
   */
  private void displayNoOpenProjectsDialog() {
    // No open projects, display error
    String msg = "No open projects.\n" +
            "Open your web application in NetBeans first,\n" +
            "then run the Integration Wizard again.";

    NotifyDescriptor d =
            new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);

    DialogDisplayer.getDefault().notify(d);
  }

  /**
   * Display an error dialog notifying the user that no project has been set as
   * the main project
   */
  private void displayNoMainProjectDialog() {
    String msg = "Please set one of the open projects as \n" +
            "the main project";

    NotifyDescriptor d =
            new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);

    DialogDisplayer.getDefault().notify(d);
  }

  /**
   * Launches the integrator that performs all required steps required to
   * integrate a project with the Google Checkout API 
   */
  private void performIntegration(IntegrationWizardDescriptor wizardDescriptor,
          Dialog dialog) {

    // Create the integrator that does the actual integration work
    NetbeansSettings settings = wizardDescriptor.getSettings();
    Integrator integrator = new Integrator(settings);

    String errorMsg = null;
    // Integrate
    try {
      integrator.integrate();
      settings.getProject().getProjectDirectory().refresh(true);
      // Close the progress dialog
      dialog.setVisible(false);

      // Refresh the file system to update changes in the IDE
      try {
        Project p = settings.getProject();
        FileObject dir = p.getProjectDirectory();

        // file events should not be marked as expected change
        dir.getFileSystem().refresh(false);
      } catch (FileStateInvalidException ex) {
      // Simply didn't refresh; this is okay
      }

      // Launch handler manager if requested
      if (settings.launchHandlerManager()) {
        handlerManagerAction.performAction();
      }
    } catch (CheckoutSdkException ex) {
      errorMsg = ex.getMessage();
    } catch (WebXmlException ex) {
      errorMsg = ex.getMessage();
    } catch (CheckoutConfigException ex) {
      errorMsg = ex.getMessage();
    } catch (SamplesJspException ex) {
      errorMsg = ex.getMessage();
    }

    if (errorMsg != null) {
      NotifyDescriptor d =
              new NotifyDescriptor.Message(errorMsg, NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(d);
    }
  }

  /**
   * Initialize panels representing individual wizard's steps and sets
   * various properties for them influencing wizard appearance.
   */
  private void createPanels() {
    if (panels == null) {
      panels = new CheckoutIntegrationPanel[]{new ProjectWizardPanel.Panel(),
              new WebXmlWizardPanel.Panel(),
              new ConfigWizardPanel.Panel(),
              new SamplesWizardPanel.Panel(),
              new ConfirmationWizardPanel.Panel()};

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
  }

  protected boolean asynchronous() {
    return false;
  }
}
