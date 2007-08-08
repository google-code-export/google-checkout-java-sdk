/*
 * IntegrationWizardDescriptor.java
 *
 * Created on August 7, 2007, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkoutsdk.nbmodule.integrationwizard;

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
    
    /** Creates a new instance of IntegrationWizardDescriptor */
    public IntegrationWizardDescriptor(WizardDescriptor.Panel[] panels) {
        super(panels);
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
}
