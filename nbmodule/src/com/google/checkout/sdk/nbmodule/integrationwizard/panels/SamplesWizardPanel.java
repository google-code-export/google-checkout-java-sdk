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

package com.google.checkout.sdk.nbmodule.integrationwizard.panels;

import com.google.checkout.sdk.nbmodule.integrationwizard.CheckoutIntegrationPanel;
import com.google.checkout.sdk.nbmodule.integrationwizard.IntegrationWizardDescriptor;
import com.google.checkout.sdk.nbmodule.integrationwizard.Settings;
import java.awt.Component;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.windows.WindowManager;

public class SamplesWizardPanel extends JPanel {
  
  // Integration settings, built by this wizard
  private Settings settings;
  private IntegrationWizardDescriptor wizardDescriptor;
  
  /**
   * Creates the samples selection panel for the Integration Wizard.
   */
  public SamplesWizardPanel() {
    initComponents();
  }
  
  public boolean isValid() {
    return true;
  }
  
  public void setIntegrationWizardDescriptor(IntegrationWizardDescriptor iwd) {
    wizardDescriptor = iwd;
  }
  
  public void updateState() {
    if (wizardDescriptor != null) {
      wizardDescriptor.updateState();
    }
  }
  
  /**
   * Returns the name for this panel, which is used in the wizard as both the
   * title and the name of each step.
   *
   * @return Name of this panel
   */
  public String getName() {
    return "Add Sample Pages";
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    addSamplesCheckBox = new javax.swing.JCheckBox();
    samplesDirectoryTextField = new javax.swing.JTextField();
    samplesDirectoryLabel = new javax.swing.JLabel();
    samplesBrowseButton = new javax.swing.JButton();

    addSamplesCheckBox.setFont(new java.awt.Font("Dialog", 0, 12));
    addSamplesCheckBox.setText("Add samples pages to this project");
    addSamplesCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    addSamplesCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
    addSamplesCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        addSamplesCheckBoxStateChanged(evt);
      }
    });

    samplesDirectoryTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        samplesDirectoryTextFieldKeyReleased(evt);
      }
    });

    samplesDirectoryLabel.setFont(new java.awt.Font("Dialog", 0, 12));
    samplesDirectoryLabel.setText("Samples directory:");

    samplesBrowseButton.setText("Browse");
    samplesBrowseButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        samplesBrowseButtonActionPerformed(evt);
      }
    });

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(addSamplesCheckBox)
          .add(samplesDirectoryLabel)
          .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
            .add(samplesDirectoryTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(samplesBrowseButton)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(addSamplesCheckBox)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(samplesDirectoryLabel)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(samplesBrowseButton)
          .add(samplesDirectoryTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(221, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void samplesDirectoryTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_samplesDirectoryTextFieldKeyReleased
    updateState();
  }//GEN-LAST:event_samplesDirectoryTextFieldKeyReleased

    /*************************************************************************/
    /*                           EVENT HANDLERS                              */
    /*************************************************************************/
      
    private void samplesBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_samplesBrowseButtonActionPerformed
      // Generate and show the file chooser
      File file = 
          FileUtil.toFile(settings.getProject().getProjectDirectory());
      JFileChooser jfc = new JFileChooser(file);
      jfc.setDialogTitle("Samples Directory");
      jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      jfc.showOpenDialog(WindowManager.getDefault().getMainWindow());
      
      // Fill the samples directory text field with the located directory
      File selectedFile = jfc.getSelectedFile();
      if (selectedFile != null) {
        String text = selectedFile.getPath();
        samplesDirectoryTextField.setText(text);
      }
    }//GEN-LAST:event_samplesBrowseButtonActionPerformed
    
    private void addSamplesCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_addSamplesCheckBoxStateChanged
      boolean selected = addSamplesCheckBox.isSelected();
      samplesDirectoryTextField.setEnabled(selected);
      samplesBrowseButton.setEnabled(selected);
      settings.setAddSamples(selected);
    }//GEN-LAST:event_addSamplesCheckBoxStateChanged
    
    /*************************************************************************/
    /*                          SWING VARIABLES                              */
    /*************************************************************************/
    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox addSamplesCheckBox;
  private javax.swing.JButton samplesBrowseButton;
  private javax.swing.JLabel samplesDirectoryLabel;
  private javax.swing.JTextField samplesDirectoryTextField;
  // End of variables declaration//GEN-END:variables
    
    /*************************************************************************/
    /*                          UTILITY METHODS                              */
    /*************************************************************************/
    
    private void updatePanel() {
      // Generate default samples directory if none provided
      if (settings.getSamplesDirectory() == null) {
        try {
          FileObject projectDirectory = 
              settings.getProject().getProjectDirectory();
          URI uri = projectDirectory.getURL().toURI();
          File file = new File(uri.resolve("web/checkout/"));
          settings.setSamplesDirectory(file);
        } catch (URISyntaxException ex) {
          // Okay to not have a default
        } catch (FileStateInvalidException ex) {
          // Okay to not have a default
        }
      }
      
      // Show the samples directory in the text field
      samplesDirectoryTextField.setText(settings.getSamplesDirectory().
          getPath());
      
      // Set the samples check box
      addSamplesCheckBox.setSelected(settings.addSamples());
      samplesDirectoryTextField.setEnabled(settings.addSamples());
      samplesBrowseButton.setEnabled(settings.addSamples());
    }
    
    private void recordSettings() {
      // TODO: Fix validation to check whether the text is really a directory
      // Validate samples directory
      File dir = new File(samplesDirectoryTextField.getText());
      if (!dir.exists() || dir.isDirectory()) {
        settings.setSamplesDirectory(dir);
      }
    }
    
    /*************************************************************************/
    /*                         SETTINGS ACCESSORS                            */
    /*************************************************************************/
    
    public Settings getSettings() {
      return settings;
    }
    
    public void setSettings(Settings settings) {
      this.settings = settings;
    }
    
    /*************************************************************************/
    /*                       WIZARD DESCRIPTOR PANEL                         */
    /*************************************************************************/
    
    public static class Panel implements CheckoutIntegrationPanel {
      // The visual component of this panel
      private SamplesWizardPanel component;
      
      public Component getComponent() {
        if (component == null) {
          component = new SamplesWizardPanel();
        }
        return component;
      }
      
      public void setIntegrationWizardDescriptor(IntegrationWizardDescriptor iwd) {
        component.setIntegrationWizardDescriptor(iwd);
      }
      
      public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
      }
      
      /**
       *  Determines whether the Next and Finish buttons are enabled and 
       *  disabled.
       *
       *  @return True if the Next and Finish buttons should be enabled;
       *          otherwise false.
       */
      public boolean isValid() {
        return component.isValid();
      }
      
      public final void addChangeListener(ChangeListener l) {}
      public final void removeChangeListener(ChangeListener l) {}
      
      public void readSettings(Object settings) {
        // Read shared info from the wizard descriptor
        IntegrationWizardDescriptor descriptor = 
            (IntegrationWizardDescriptor) settings;
        component.setSettings(descriptor.getSettings());
        
        // Update the samples directory text field & check box
        component.updatePanel();
      }
      
      public void storeSettings(Object settings) {
        // Record the page state into settings
        component.recordSettings();
        
        // Write shared info to the wizard descriptor
        IntegrationWizardDescriptor descriptor = 
            (IntegrationWizardDescriptor) settings;
        descriptor.setSettings(component.getSettings());
      }
    }
}