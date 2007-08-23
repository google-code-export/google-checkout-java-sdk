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

import com.google.checkout.sdk.nbmodule.common.CheckoutConfigManager;
import com.google.checkout.sdk.nbmodule.common.CheckoutFileWriter;
import com.google.checkout.sdk.nbmodule.common.FileFinder;
import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;

public class HandlerManagerPanel extends javax.swing.JPanel {
  
  // Combo box models
  DefaultComboBoxModel projectModel;
  
  // Table models
  DefaultTableModel notificationTableModel;
  DefaultTableModel callbackTableModel;
  
  // Map of open projects
  HashMap projects;
  
  // Map of checkout-config.xml managers
  HashMap configManagers;
  
  // The name of the currently selected project
  String selectedProject;
  
  /** Creates new form HandlerManagerPanel */
  public HandlerManagerPanel() {
    projectModel = new DefaultComboBoxModel();
    notificationTableModel = new DefaultTableModel();
    callbackTableModel = new DefaultTableModel();
    projects = new HashMap();
    configManagers = new HashMap();
    selectedProject = null;
    
    initComponents();
    initMaps();
    initTableModels();
    initProjects();
  }
  
  /*************************************************************************/
  /*                            INITIALIZERS                               */
  /*************************************************************************/
  
  private void initMaps() {
    Project[] openProjects = OpenProjects.getDefault().getOpenProjects();
    for (int i=0; i<openProjects.length; i++) {
      // Get the project's directory and information
      Project p = openProjects[i];
      FileObject directory = p.getProjectDirectory();
      ProjectInformation info =
          (ProjectInformation)p.getLookup().lookup(ProjectInformation.class);
      
      // Find checkout-config.xml
      FileObject config = FileFinder.findFile("checkout-config.xml", directory);
      
      // If this project is checkout-integrated, add it to projects
      if (config != null && info != null) {
        projects.put(info.getDisplayName(), p);
        configManagers.put(info.getDisplayName(),
            new CheckoutConfigManager(FileUtil.toFile(config)));
      }
    }
  }
  
  private void initTableModels() {
    // Init notifications
    notificationTableModel.addColumn("Message Type");
    notificationTableModel.addColumn("Handler Class");
    
    // Init callbacks
    callbackTableModel.addColumn("Message Type");
    callbackTableModel.addColumn("Handler Class");
  }
  
  private void initProjects() {
    // Initialize the list of projects
    String name = getDefaultProjectName();
    Object[] keys = projects.keySet().toArray();
    for (int i=0; i<keys.length; i++) {
      projectModel.addElement((String) keys[i]);
      
      // Select if default project
      if (name != null && ((String) keys[i]).equals(name)) {
        projectList.setSelectedIndex(i);
      }
    }
  }
  
  /*************************************************************************/
  /*                          UTILITY METHODS                              */
  /*************************************************************************/
  
  private String getDefaultProjectName() {
    Project defaultProject = OpenProjects.getDefault().getMainProject();
    if (defaultProject != null) {
      Lookup lookup = defaultProject.getLookup();
      ProjectInformation info = 
          (ProjectInformation) lookup.lookup(ProjectInformation.class);
      return info.getDisplayName();
    } else {
      return null;
    }
  }
  
  private void removeAllRows(DefaultTableModel table) {
    while (table.getRowCount() > 0) {
      table.removeRow(0);
    }
  }
  
  private void readProjectFromTables(String projectName) {
    Project p = (Project) projects.get(projectName);
    CheckoutConfigManager config =
        (CheckoutConfigManager) configManagers.get(projectName);
    
    // Save notification handlers
    for (int i=0; i<notificationTableModel.getRowCount(); i++) {
      String type = (String) notificationTableModel.getValueAt(i, 0);
      String name = (String) notificationTableModel.getValueAt(i, 1);
      if (type != null && name != null) {
        config.setNotificationHandler(type, name);
      }
    }
    
    // Save callback handlers
    for (int i=0; i<callbackTableModel.getRowCount(); i++) {
      String type = (String) callbackTableModel.getValueAt(i, 0);
      String name = (String) callbackTableModel.getValueAt(i, 1);
      if (type != null && name != null) {
        config.setCallbackHandler(type, name);
      }
    }
  }
  
  private void writeProjectToTables(String projectName) {
    Project p = (Project) projects.get(projectName);
    CheckoutConfigManager config =
        (CheckoutConfigManager) configManagers.get(projectName);
    
    // Write notification handlers
    removeAllRows(notificationTableModel);
    String[] types = config.getNotificationTypes();
    for (int i=0; i<types.length; i++) {
      Object name = config.getNotificationHandler(types[i]);
      Object[] row = new Object[2];
      row[0] = types[i];
      row[1] = name != null ? (String) name : "";
      notificationTableModel.addRow(row);
    }
    
    // Write callback handlers
    removeAllRows(callbackTableModel);
    types = config.getCallbackTypes();
    for (int i=0; i<types.length; i++) {
      Object name = config.getCallbackHandler(types[i]);
      Object[] row = new Object[2];
      row[0] = types[i];
      row[1] = name != null ? (String) name : "";
      callbackTableModel.addRow(row);
    }
  }
  
  /*************************************************************************/
  /*                           PUBLIC METHODS                              */
  /*************************************************************************/
  
  public boolean success() {
    return projects.size() > 0;
  }
  
  public boolean save() {
    boolean success = true;
    
    // Get most recent info
    readProjectFromTables(selectedProject);
    
    Object[] configManagerArray = configManagers.values().toArray();
    for (int i=0; i<configManagerArray.length && success; i++) {
      CheckoutConfigManager configManager = 
          (CheckoutConfigManager) configManagerArray[i];
      
      // Get the source and destination
      String source = configManager.getBody();
      File dest = configManager.getFile();
      
      // Write the file
      try {
        CheckoutFileWriter.writeFileFromString(source, dest);
      } catch (IOException ex) {
        success = false;
      }
    }
    
    return success;
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        tabbedPane = new javax.swing.JTabbedPane();
        notificationScrollPane = new javax.swing.JScrollPane();
        notificationTable = new javax.swing.JTable();
        callbackScrollPane = new javax.swing.JScrollPane();
        callbackTable = new javax.swing.JTable();
        newHandlerButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();

        notificationTable.setModel(notificationTableModel);
        notificationScrollPane.setViewportView(notificationTable);

        tabbedPane.addTab("Notifications", notificationScrollPane);

        callbackTable.setModel(callbackTableModel);
        callbackScrollPane.setViewportView(callbackTable);

        tabbedPane.addTab("Callbacks", callbackScrollPane);

        newHandlerButton.setText("Create New Handler");
        newHandlerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newHandlerButtonActionPerformed(evt);
            }
        });

        projectList.setFont(new java.awt.Font("Dialog", 0, 12));
        projectList.setModel(projectModel);
        projectList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                projectListValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(projectList);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText("Projects:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(newHandlerButton)
                    .add(tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(layout.createSequentialGroup()
                        .add(tabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 229, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(newHandlerButton))
                    .add(jScrollPane1))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /*************************************************************************/
    /*                            EVENT HANDLERS                             */
    /*************************************************************************/
    
    private void projectListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_projectListValueChanged
      String newProject = (String) projectList.getSelectedValue();
      
      // If project is changing or this is the first selection
      if (selectedProject == null || !selectedProject.equals(newProject)) {
        // Unselect boxes to prevent values carrying over to a new project
        notificationTable.clearSelection();
        notificationTable.removeEditor();
        callbackTable.clearSelection();
        callbackTable.removeEditor();
        
        // Save the old project if there is one
        if (selectedProject != null) {
          readProjectFromTables(selectedProject);
        }
        
        // Load the newly selected project
        writeProjectToTables(newProject);
        
        // Assign newly selected project
        selectedProject = newProject;
      }
    }//GEN-LAST:event_projectListValueChanged
    
    private void newHandlerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newHandlerButtonActionPerformed
      // Create the new handler dialog
      NewHandlerPanel panel = 
          new NewHandlerPanel((Project)projects.get(selectedProject));
      DialogDescriptor desc = new DialogDescriptor(
          panel,  // panel to display
          "Create New Checkout Handler",  // dialog title
          true,  // modal
          new Object[] {"Create", "Close"},  // options
          "Create",  // initial value (selected option)
          DialogDescriptor.DEFAULT_ALIGN,  // options alignment
          null,  // help control
          null);  // action listener
      
      // Show the Handler Manager dialog
      Dialog dialog = DialogDisplayer.getDefault().createDialog(desc);
      dialog.setVisible(true);
      dialog.toFront();
      
      // Get the handler creation data
      HandlerCreationData handlerData = panel.getHandlerCreationData();
      
      // Create the new handler
      boolean success = new HandlerCreator().createHandler(handlerData);
      
      if (!success) {
        // Failure: show error message
        String msg = "Failed to create " + handlerData.getHandlerName() + ".java.";
        NotifyDescriptor d = 
            new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
        DialogDisplayer.getDefault().notify(d);
      } else {
        // Update Handler Manager if requested
        if (panel.updateHandlerManager()) {
          // Get information
          String type = handlerData.getHandlerType();
          String name = handlerData.getHandlerPackage() + "." +
              handlerData.getHandlerName();
          
          // Select the right table
          DefaultTableModel table;
          if (handlerData.getHandlerClass().
              equals(HandlerCreationData.NOTIFICATION)) {
            table = notificationTableModel;
          } else {
            table = callbackTableModel;
          }
          
          // Change the entry in the table
          for (int i=0; i<table.getRowCount(); i++) {
            if (((String) table.getValueAt(i, 0)).equals(type)) {
              table.setValueAt(name, i, 1);
            }
          }
        }
      }
    }//GEN-LAST:event_newHandlerButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane callbackScrollPane;
    private javax.swing.JTable callbackTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newHandlerButton;
    private javax.swing.JScrollPane notificationScrollPane;
    private javax.swing.JTable notificationTable;
    private javax.swing.JList projectList;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
    
}
