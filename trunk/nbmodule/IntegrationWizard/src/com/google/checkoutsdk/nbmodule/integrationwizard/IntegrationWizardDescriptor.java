package com.google.checkoutsdk.nbmodule.integrationwizard;

import com.google.checkoutsdk.nbmodule.integrationwizard.handlers.CheckoutConfigManager;
import java.io.File;
import org.netbeans.api.project.Project;
import org.openide.WizardDescriptor;

public class IntegrationWizardDescriptor extends WizardDescriptor {
    private Settings settings;
    
    /** Creates a new instance of IntegrationWizardDescriptor */
    public IntegrationWizardDescriptor(WizardDescriptor.Panel[] panels) {
        super(panels);
        settings = new Settings();
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    
    
}
