/*
 * IntegrationWizardDescriptor.java
 *
 * Created on August 7, 2007, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkoutsdk.nbmodule.integrationwizard;

import com.google.checkoutsdk.nbmodule.integrationwizard.handlers.CheckoutConfigManager;
import java.io.File;
import org.netbeans.api.project.Project;
import org.openide.WizardDescriptor;

/**
 *
 * @author rubel
 */
public class IntegrationWizardDescriptor extends WizardDescriptor {
    private Project project;
    private File webXmlFile;
    private String modifiedWebXml;
    private CheckoutConfigManager configManager;
    private boolean addDefaultHandlers;
    private boolean addSamples;
    private boolean launchHandlerManager;
    
    /** Creates a new instance of IntegrationWizardDescriptor */
    public IntegrationWizardDescriptor(WizardDescriptor.Panel[] panels) {
        super(panels);
        project = null;
        webXmlFile = null;
        modifiedWebXml = null;
        configManager = null;
        addDefaultHandlers = true;
        addSamples = true;
        launchHandlerManager = false;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }

    public File getWebXmlFile() {
        return webXmlFile;
    }

    public void setWebXmlFile(File webXmlFile) {
        this.webXmlFile = webXmlFile;
    }

    public String getModifiedWebXml() {
        return modifiedWebXml;
    }
    
    public void setModifiedWebXml(String modifiedWebXml) {
        this.modifiedWebXml = modifiedWebXml;
    }

    public CheckoutConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(CheckoutConfigManager configManager) {
        this.configManager = configManager;
    }

    public boolean addDefaultHandlers() {
        return addDefaultHandlers;
    }

    public void setAddDefaultHandlers(boolean addDefaultHandlers) {
        this.addDefaultHandlers = addDefaultHandlers;
    }

    public boolean addSamples() {
        return addSamples;
    }

    public void setAddSamples(boolean addSamples) {
        this.addSamples = addSamples;
    }

    public boolean launchHandlerManager() {
        return launchHandlerManager;
    }

    public void setLaunchHandlerManager(boolean launchHandlerManager) {
        this.launchHandlerManager = launchHandlerManager;
    }
}
