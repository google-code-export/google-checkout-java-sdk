package com.google.checkoutsdk.nbmodule.integrationwizard;

import com.google.checkoutsdk.nbmodule.integrationwizard.handlers.CheckoutConfigManager;
import java.io.File;
import org.netbeans.api.project.Project;

public class Settings {
    
    private Project project;
    private File webXmlFile;
    private String modifiedWebXml;
    private CheckoutConfigManager configManager;
    private boolean addDefaultHandlers;
    private boolean addSamples;
    private File samplesDirectory;
    private boolean launchHandlerManager;
    
    public Settings() {
        reset();
    }
    
    // Reset all fields
    public void reset() {
        project = null;
        webXmlFile = null;
        modifiedWebXml = null;
        configManager = new CheckoutConfigManager();
        addDefaultHandlers = true;
        addSamples = true;
        samplesDirectory = null;
        launchHandlerManager = true;
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

    public File getSamplesDirectory() {
        return samplesDirectory;
    }

    public void setSamplesDirectory(File samplesDirectory) {
        this.samplesDirectory = samplesDirectory;
    }

    public boolean launchHandlerManager() {
        return launchHandlerManager;
    }

    public void setLaunchHandlerManager(boolean launchHandlerManager) {
        this.launchHandlerManager = launchHandlerManager;
    }
    
}
