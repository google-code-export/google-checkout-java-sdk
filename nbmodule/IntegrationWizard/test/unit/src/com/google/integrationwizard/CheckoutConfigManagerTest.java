/*
 * CheckoutConfigManagerTest.java
 *
 * Created on August 6, 2007, 1:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.integrationwizard;

import com.google.checkoutsdk.handlers.CheckoutConfigManager;
import com.google.integrationwizard.*;
import java.io.File;
import junit.framework.TestCase;

public class CheckoutConfigManagerTest extends TestCase {
    
    public void testFileWrite() {
        CheckoutConfigManager manager = new CheckoutConfigManager();
        manager.setFile(new File("/home/rubel/temp"));
        manager.writeConfig();
    }
    
}
