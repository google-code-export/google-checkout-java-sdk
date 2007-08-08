package com.google.checkoutsdk.nbmodule.integrationwizard;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public final class ConfigWizardPanel extends JPanel {
    
    /**
     * Creates the checkout-config.xml editing panel for the Integration Wizard. 
     */
    public ConfigWizardPanel() {        
        initComponents();
    }
    
    /**
     * Returns the name for this panel, which is used in the wizard as both the
     * title and the name of each step.
     *
     * @return Name of this panel
     */
    public String getName() {
        return "Create checkout-config.xml";
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "checkout-config.xml will be placed alongside web.xml at:");

        jTextField1.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, "Merchant ID:");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, "Merchant Key:");

        jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, "Create a default set of handlers");
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jCheckBox2.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, "Run the Handler Manager after this wizard");
        jCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .add(jLabel1)
                    .add(jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .add(21, 21, 21)
                        .add(jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextField3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                    .add(jCheckBox1)
                    .add(jCheckBox2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(16, 16, 16)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(33, 33, 33)
                .add(jCheckBox1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCheckBox2)
                .addContainerGap(123, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /*************************************************************************/
    /*                           EVENT HANDLERS                              */
    /*************************************************************************/
    
    /*************************************************************************/
    /*                          SWING VARIABLES                              */
    /*************************************************************************/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
    
    /*************************************************************************/
    /*                          UTILITY METHODS                              */
    /*************************************************************************/
    
    /*************************************************************************/
    /*                       SHARED DATA ACCESSORS                           */
    /*************************************************************************/
    
    /*************************************************************************/
    /*                       WIZARD DESCRIPTOR PANEL                         */
    /*************************************************************************/
    
    public static class Panel implements WizardDescriptor.Panel {
        // The visual component of this panel
        private ConfigWizardPanel component;

        public Component getComponent() {
            if (component == null) {
                component = new ConfigWizardPanel();
            }
            return component;
        }

        public HelpCtx getHelp() {
            return HelpCtx.DEFAULT_HELP;
        }

        public boolean isValid() {
            return true;
        }

        public final void addChangeListener(ChangeListener l) {}
        public final void removeChangeListener(ChangeListener l) {}
        public void readSettings(Object settings) {}
        public void storeSettings(Object settings) {}
    }
}

