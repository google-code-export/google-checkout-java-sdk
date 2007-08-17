package com.google.checkout.sdk.nbmodule.integrationwizard;

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
