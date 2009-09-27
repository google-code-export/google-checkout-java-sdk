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

package com.google.checkout.sdk.eclipsemodule.handlermanager;

import com.google.checkout.sdk.module.common.CheckoutConfigManager;
import com.google.checkout.sdk.module.common.CheckoutFileWriter;
import com.google.checkout.sdk.module.exceptions.HandlerCreationException;
import com.google.checkout.sdk.module.handlermanager.HandlerCreationData;
import com.google.checkout.sdk.module.handlermanager.HandlerCreator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class HandlerManagerPage extends WizardPage {
  
  private Button newHandlerButton;
  
  private HashMap<String, IProject> projects;

  private Label projectsLabel;
  
  private List projectList;
  
  private NewHandlerDialog handlerDialog;
 
  private TabFolder tabFolder;
  
  private TabItem callbackItem;  
  private TabItem notificationItem; 
  
  private Table callbackTable;
  private Table notificationTable;
  
  private TableColumn callbackMessageColumn;
  private TableColumn callBackHandlerColumn;
  private TableColumn notificationMessageColumn;
  private TableColumn notificationHandlerColumn;
  
  // Map of checkout-config.xml managers
  private HashMap<String, CheckoutConfigManager> configManagers;
  
  // The name of the currently selected project
  private String selectedProject;
  
  public HandlerManagerPage() {
    super("New Handler Page");
    
    this.projects = new HashMap<String, IProject>();
    this.configManagers = new HashMap<String, CheckoutConfigManager>();
    
    IProject[] openProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
    
    try {
      for (int i=0; i< openProjects.length; ++i) {
        // Get the project's information
        String prefix = openProjects[i].getLocationURI().resolve(
            openProjects[i].getName()).toASCIIString();
    
        if (prefix.startsWith("file:")) {
          // get rid of the 'file:' prefix
          prefix = prefix.substring(5);
        }
        
        File config = new File(prefix + "/WebContent/WEB-INF/checkout-config.xml");
          
        if (config.exists()) {
          projects.put(openProjects[i].getName(), openProjects[i]);
          
          CheckoutConfigManager tempManager = 
            new CheckoutConfigManager(new FileInputStream(config));
          
          // set the output path where the checkout-config.xml will be placed
          tempManager.setOutputLocation(config.getPath());
          configManagers.put(openProjects[i].getName(), tempManager);
        }
      }
    } catch (FileNotFoundException ex) {
      MessageDialog.openError(getShell(), "", ex.getMessage());
      ex.printStackTrace();
    }
    
    setTitle("New Handler Page");
  }
  
  public void createControl(Composite parent) {      
    // create the composite to hold the widgets
    GridData gd;
    Composite composite = new Composite(parent, SWT.NULL);
    
    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 4;
    gl.numColumns = ncol;
    composite.setLayout(gl);
    
    // create the widgets. If the appearance of the widget is different from the
    // default, create a GridData for it to set the alignment and how much space
    // it will occupy
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol;
    
    projectsLabel = new Label(composite, SWT.NULL);
    projectsLabel.setText("Projects:");
    projectsLabel.setLayoutData(gd);
    
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 2;
    gd.heightHint = 250;
    
    projectList = new List(composite, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI | SWT.H_SCROLL);
    projectList.addListener(SWT.Selection, new Listener() {
        public void handleEvent(Event evt) {
          projectListValueChanged();
        }
    });
    projectList.setLayoutData(gd);


    Collection<IProject> values = projects.values();
    try {
      Iterator<IProject> it = values.iterator();
      
      while (it.hasNext()) {
        IProject tempProj = it.next();
        projectList.add(tempProj.getDescription().getName()); 
      }
    } catch (CoreException ex) {
      MessageDialog.openError(getShell(), "", ex.getMessage());
    }
    
    projectList.setLayoutData(gd);
    projectList.setSelection(0);
    
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.horizontalSpan = ncol-2;
    gd.verticalSpan = 1;
    gd.heightHint = 200;
    gd.widthHint = 500;
    
    tabFolder = new TabFolder(composite, SWT.BORDER);
    tabFolder.setLayoutData(gd);
   
    gd = new GridData(GridData.FILL_BOTH);
    gd.horizontalSpan = ncol;
    
    // populate the stuff for the Notifications tab
    notificationTable = new Table(tabFolder, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL);
    notificationTable.setHeaderVisible(true);
    notificationTable.setLinesVisible(true);
    notificationTable.setLayoutData(gd);
    
    // initialize the table for notification models
    notificationMessageColumn = new TableColumn(notificationTable, SWT.NONE);
    notificationMessageColumn.setText("Message Type");
    notificationMessageColumn.setWidth(250);
    notificationHandlerColumn = new TableColumn(notificationTable, SWT.NONE);
    notificationHandlerColumn.setText("Handler Class");
    notificationHandlerColumn.setWidth(600);
    
    gd = new GridData(GridData.FILL_BOTH);
    gd.horizontalSpan = ncol;
    
    // populate the stuff for the Callbacks tab    
    callbackTable = new Table(tabFolder, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
    callbackTable.setHeaderVisible(true);
    callbackTable.setLinesVisible(true);
    callbackTable.setLayoutData(gd);
    
    // initialize table for callback models
    callbackMessageColumn = new TableColumn(callbackTable, SWT.NONE);
    callbackMessageColumn.setText("Message Type");
    callbackMessageColumn.setWidth(250);
    callBackHandlerColumn = new TableColumn(callbackTable, SWT.NONE);
    callBackHandlerColumn.setText("Handler Class");
    callBackHandlerColumn.setWidth(500);
 
    notificationItem = new TabItem(tabFolder, SWT.BORDER);
    notificationItem.setText("Notification");
    notificationItem.setControl(notificationTable);
    
    callbackItem = new TabItem(tabFolder, SWT.BORDER);
    callbackItem.setText("Callback");
    callbackItem.setControl(callbackTable);
    
    gd = new GridData();
    gd.horizontalSpan = 1;
    
    newHandlerButton = new Button(composite, SWT.NULL);
    newHandlerButton.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event evt) {
        newHandlerButtonActionPerformed();
      }
    });
    newHandlerButton.setText("Create New Handler");
    newHandlerButton.setLayoutData(gd);
    
    projectListValueChanged();
    
    setControl(composite);
  }
  
  /*************************************************************************/
  /*                           EVENT HANDLERS                              */
  /*************************************************************************/
  
  private void projectListValueChanged() {
    String newProject = projectList.getItem(projectList.getSelectionIndex());
    
    // If project is changing or this is the first selection
    if (selectedProject == null || !selectedProject.equals(newProject)) {
      // Unselect boxes to prevent values carrying over to a new project
       callbackTable.clearAll();     
       notificationTable.clearAll();
       
      // Save the old project if there is one
      if (selectedProject != null) {
        readProjectFromTables(selectedProject);
      }

      // Load the newly selected project
      writeProjectToTables(newProject);
      
      // Assign newly selected project
      selectedProject = newProject;
    }
  }
  
  private void newHandlerButtonActionPerformed() {

    handlerDialog = new NewHandlerDialog(getShell(), projects.get(selectedProject));
    handlerDialog.open();
    
    HandlerCreationData handlerData = handlerDialog.getHandlerCreationData();
    
    String errorMsg = null;
    
    // Create the new handler
    try {
      HandlerCreator hc = new HandlerCreator();
      hc.createHandler(handlerData);
      
      // update handler manager if requested
      if (handlerDialog.updateHandlerManager()) {
        // Get information
        String type = handlerData.getHandlerType();
        String handlerPackage = handlerData.getHandlerPackage();
        String name = handlerPackage.equals("") ? "" : handlerPackage + ".";
        name += handlerData.getHandlerName();
        
        // Select the right table
        Table table = null;
        if (handlerData.getHandlerClass().equals(HandlerCreationData.NOTIFICATION)) {
          table = notificationTable;
        } else {
          table = callbackTable;
        }
        
        // Change the entry in the table
        for (int i=0; i<table.getItemCount(); i++) {
          if (table.getItem(i).getText(0).equals(type)) {
            TableItem ti = table.getItem(i);
            ti.setText(1, name);
          }
        }
      }
    } catch (HandlerCreationException ex) {
      errorMsg = ex.getMessage();
    }
    
    if (errorMsg != null) { 
      MessageDialog.openError(getShell(), "", errorMsg);
    }
  }
  
  /*************************************************************************/
  /*                          UTILITY METHODS                              */
  /*************************************************************************/
 
  private void removeAllRows(Table table) {
    while (table.getItemCount() > 0) {
      table.remove(0);
    }
  }
  private void readProjectFromTables(String projectName) {
    CheckoutConfigManager config = configManagers.get(projectName);
    
    // Save callback handlers
    for (int i=0; i<callbackTable.getItemCount(); i++) {
      String type = callbackTable.getItem(i).getText(0);
      String name = callbackTable.getItem(i).getText(1);
      if (type != null && name != null) {
        config.setCallbackHandler(type, name);
      }
    }    
    
    // Save notification handlers
    for (int i=0; i<notificationTable.getItemCount(); i++) {
      String type = notificationTable.getItem(i).getText(0);
      String name = notificationTable.getItem(i).getText(1);
      if (type != null && name != null) {
        config.setNotificationHandler(type, name);
      }
    }
  }
  
  private void writeProjectToTables(String projectName) {
    CheckoutConfigManager config = configManagers.get(projectName);
    
    // Write notification handlers
    removeAllRows(notificationTable);
    String[] types = config.getNotificationTypes();
    for (int i=0; i<types.length; i++) {
      String handlerType = types[i];
      String name = (String)config.getNotificationHandler(handlerType);
      TableItem tempItem = new TableItem(notificationTable, SWT.NULL);
      tempItem.setText(0, handlerType);
      tempItem.setText(1, name);
    }
    
    // Write callback handlers
    removeAllRows(callbackTable);
    types = config.getCallbackTypes();
    for (int i=0; i<types.length; i++) {
      String handlerType = types[i];
      String name = (String)config.getCallbackHandler(types[i]);
      TableItem tempItem = new TableItem(callbackTable, SWT.NULL);
      tempItem.setText(0, handlerType);
      tempItem.setText(1, name);
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
      File dest = new File(configManager.getOutputLocation());
      
      // Write the file
      try {
        CheckoutFileWriter.writeFileFromString(source, dest);
      } catch (IOException ex) {
        success = false;
      }
    }
    
    return success;
  }
  
  public IProject getSelectedProject() {
    return projects.get(selectedProject);
  }
}
