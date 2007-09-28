/*
 * CheckoutIntegrationPanel.java
 *
 * Created on September 25, 2007, 11:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkout.sdk.nbmodule.integrationwizard;

import org.openide.WizardDescriptor;

/**
 *
 * @author cdang
 */
public interface CheckoutIntegrationPanel extends WizardDescriptor.Panel {
  public void setIntegrationWizardDescriptor(IntegrationWizardDescriptor iwd);
}
