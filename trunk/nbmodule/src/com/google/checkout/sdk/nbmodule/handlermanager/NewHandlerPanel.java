package com.google.checkout.sdk.nbmodule.handlermanager;

import com.google.checkout.sdk.nbmodule.config.CheckoutConfigManager;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ui.OpenProjects;

public class NewHandlerPanel extends javax.swing.JPanel {
    
    // Combo box models
    DefaultComboBoxModel projectModel;
    DefaultComboBoxModel locationModel;
    DefaultComboBoxModel packageModel;
    DefaultComboBoxModel messageClassModel;
    DefaultComboBoxModel messageTypeModel;
    DefaultComboBoxModel implementationModel;
    
    // Map of open projects
    HashMap projects;
    
    CheckoutConfigManager configManager;
    
    /** Creates new form NewHandlerPanel */
    public NewHandlerPanel() {
        // Init models
        projectModel = new DefaultComboBoxModel();
        locationModel = new DefaultComboBoxModel();
        packageModel = new DefaultComboBoxModel();
        messageClassModel = new DefaultComboBoxModel();
        messageTypeModel = new DefaultComboBoxModel();
        implementationModel = new DefaultComboBoxModel();
        
        // Init projects
        projects = new HashMap();
        
        configManager = new CheckoutConfigManager();
        
        // Run computer generated code
        initComponents();
        
        // Initialize the map of projects
        Project[] openProjects = OpenProjects.getDefault().getOpenProjects();
        for (int i=0; i<openProjects.length; i++) {
            Project p = openProjects[i];
            ProjectInformation info = (ProjectInformation)p.getLookup().lookup(ProjectInformation.class);
            if (info != null) {
                projects.put(info.getDisplayName(), p);
            }
        }
        
        // Initialize models
        initModels();
    }
    
    private void initModels() {
        // Initialize the list of projects 
        Object[] keys = projects.keySet().toArray();
        for (int i=0; i<keys.length; i++) {
            projectModel.addElement((String) keys[i]);
        }
        
        // Initialize the list of locations
        locationModel.addElement("Source Packages");
        locationModel.addElement("Test Packages");
        
        // Initialize the list of message classes
        messageClassModel.addElement("Notification");
        messageClassModel.addElement("Callback");
        
        // Initialize the list of implementation types
        implementationModel.addElement("Empty Class");
        implementationModel.addElement("File Handler");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        classNameLabel = new javax.swing.JLabel();
        classNameTextField = new javax.swing.JTextField();
        projectLabel = new javax.swing.JLabel();
        projectComboBox = new javax.swing.JComboBox();
        locationLabel = new javax.swing.JLabel();
        locationComboBox = new javax.swing.JComboBox();
        packageLabel = new javax.swing.JLabel();
        packageComboBox = new javax.swing.JComboBox();
        createdFileLabel = new javax.swing.JLabel();
        createdFileTextField = new javax.swing.JTextField();
        separator = new javax.swing.JSeparator();
        messageTypeLabel = new javax.swing.JLabel();
        messageTypeComboBox = new javax.swing.JComboBox();
        implementationLabel = new javax.swing.JLabel();
        implementationComboBox = new javax.swing.JComboBox();
        updateCheckBox = new javax.swing.JCheckBox();
        messageClassLabel = new javax.swing.JLabel();
        messageClassComboBox = new javax.swing.JComboBox();

        classNameLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        classNameLabel.setText("Class Name:");

        classNameTextField.setText("NewHandler");

        projectLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        projectLabel.setText("Project:");

        projectComboBox.setModel(projectModel);
        projectComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                projectComboBoxItemStateChanged(evt);
            }
        });

        locationLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        locationLabel.setText("Location:");

        locationComboBox.setModel(locationModel);

        packageLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        packageLabel.setText("Package:");

        packageComboBox.setModel(packageModel);

        createdFileLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        createdFileLabel.setText("Created File:");

        createdFileTextField.setEditable(false);

        messageTypeLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        messageTypeLabel.setText("Message Type:");

        messageTypeComboBox.setModel(messageTypeModel);

        implementationLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        implementationLabel.setText("Implementation:");

        implementationComboBox.setModel(implementationModel);

        updateCheckBox.setFont(new java.awt.Font("Dialog", 0, 12));
        updateCheckBox.setSelected(true);
        updateCheckBox.setText("Update Handler Manager with new handler");
        updateCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        updateCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        messageClassLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        messageClassLabel.setText("Message Class:");

        messageClassComboBox.setModel(messageClassModel);
        messageClassComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                messageClassComboBoxItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(separator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .add(classNameLabel)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(createdFileLabel)
                            .add(packageLabel)
                            .add(projectLabel)
                            .add(locationLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, locationComboBox, 0, 291, Short.MAX_VALUE)
                            .add(packageComboBox, 0, 291, Short.MAX_VALUE)
                            .add(createdFileTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(classNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                            .add(projectComboBox, 0, 291, Short.MAX_VALUE)))
                    .add(updateCheckBox)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(implementationLabel)
                            .add(messageClassLabel)
                            .add(messageTypeLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, messageClassComboBox, 0, 271, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, implementationComboBox, 0, 271, Short.MAX_VALUE)
                            .add(messageTypeComboBox, 0, 271, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(classNameLabel)
                    .add(classNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(27, 27, 27)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(projectLabel)
                    .add(projectComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationLabel)
                    .add(locationComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(packageLabel)
                    .add(packageComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(createdFileLabel)
                    .add(createdFileTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(separator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(messageClassLabel)
                    .add(messageClassComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(messageTypeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(messageTypeLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(implementationLabel)
                    .add(implementationComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(updateCheckBox)
                .add(29, 29, 29))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void messageClassComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_messageClassComboBoxItemStateChanged
        if (evt.getStateChange() == evt.SELECTED) {
            String[] types;
            if (((String) messageClassComboBox.getSelectedItem()).equals("Callback")) {
                types = configManager.getCallbackTypes();
            } else {
                types = configManager.getNotificationTypes();
            }
            messageTypeModel.removeAllElements();
            for (int i=0; i<types.length; i++) {
                messageTypeModel.addElement(types[i]);
            }
        }
    }//GEN-LAST:event_messageClassComboBoxItemStateChanged

    private void projectComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_projectComboBoxItemStateChanged
        /*if (evt.getStateChange() == evt.SELECTED)  {
            // Get the selected project and sources
            Project p = (Project) projects.get((String) projectComboBox.getSelectedItem());
            Sources sources = (Sources) p.getLookup().lookup(Sources.class);
            
            // Update location combo box
            locationModel.removeAllElements();
            SourceGroup[] sourceGroups = sources.getSourceGroups(sources.TYPE_GENERIC);
            for (int i=0; i<sourceGroups.length; i++) {
                locationModel.addElement(sourceGroups[i].getDisplayName());
            }
        }*/
    }//GEN-LAST:event_projectComboBoxItemStateChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel classNameLabel;
    private javax.swing.JTextField classNameTextField;
    private javax.swing.JLabel createdFileLabel;
    private javax.swing.JTextField createdFileTextField;
    private javax.swing.JComboBox implementationComboBox;
    private javax.swing.JLabel implementationLabel;
    private javax.swing.JComboBox locationComboBox;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JComboBox messageClassComboBox;
    private javax.swing.JLabel messageClassLabel;
    private javax.swing.JComboBox messageTypeComboBox;
    private javax.swing.JLabel messageTypeLabel;
    private javax.swing.JComboBox packageComboBox;
    private javax.swing.JLabel packageLabel;
    private javax.swing.JComboBox projectComboBox;
    private javax.swing.JLabel projectLabel;
    private javax.swing.JSeparator separator;
    private javax.swing.JCheckBox updateCheckBox;
    // End of variables declaration//GEN-END:variables
    
}