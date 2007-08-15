package com.google.checkoutsdk.nbmodule.integrationwizard;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.text.MessageFormat;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
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
            String msg = "Error: No open projects\n" +
                    "Open your web application in NetBeans first,\n" +
                    "then run the Integration Wizard again.";
            NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.INFORMATION_MESSAGE);
            DialogDisplayer.getDefault().notify(d);      
        } else {
            // Create and show the wizard
            IntegrationWizardDescriptor wizardDescriptor = new IntegrationWizardDescriptor(getPanels());
            wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
            wizardDescriptor.setTitle("Google Checkout Integration Wizard");
            Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
            dialog.setVisible(true);
            dialog.toFront();

            // Handle the integration after the wizard closes
            boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
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
                
                // Show error box if necessary
                if (!success) {
                    String msg = "Error: " + integrator.getErrorMessage();
                    NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.INFORMATION_MESSAGE);
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
                    jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
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
        return NbBundle.getMessage(IntegrationWizardAction.class, "CTL_IntegrationWizardAction");
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
