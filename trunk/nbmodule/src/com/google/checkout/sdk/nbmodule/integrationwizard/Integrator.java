package com.google.checkout.sdk.nbmodule.integrationwizard;

import com.google.checkout.sdk.nbmodule.common.CheckoutFileWriter;
import com.google.checkout.sdk.nbmodule.common.ProgressTracker;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Integrator {
    // The settings built by the Integration Wizard
    Settings settings;
    
    // The progress tracker shown during integration
    ProgressTracker progressTracker;
    
    // The error message generated by a call to integrate()
    String errorMessage;
    
    public Integrator(Settings settings, ProgressTracker progressTracker) {
        this.settings = settings;
        this.progressTracker = progressTracker;
        calculateBarSize();
        errorMessage = null;
    }
    
    private void calculateBarSize() {
        int size = 0;
        
        // Copying checkout-sdk.jar (weight = 3)
        if (!copyCheckoutSdkJar()) {
            size = size + 3;
        }
        
        // Modifying web.xml (weight = 1)
        if (settings.getModifiedWebXml() != null) {
            size++;
        }
        
        // Adding checkout-config.xml (weight = 1)
        size++;
        
        // Adding sample JSPs (weight = 1)
        if (settings.addSamples()) {
            size++;
        }
        
        // Actually set size
        progressTracker.setMaxProgress(size);
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public boolean integrate() {
        boolean success = true;
        
        // Add checkout-sdk.jar to your WEB_INF/lib directory
        progressTracker.setCurrentOperation("Creating checkout-sdk.jar...");
        if (!copyCheckoutSdkJar()) {
            success = false;
        }
        progressTracker.incProgress(3);
        
        // Modify web.xml
        if (success && settings.getModifiedWebXml() != null) {
            progressTracker.setCurrentOperation("Modifying web.xml...");
            if (!writeModifiedWebXml()) {
                success = false;
            }
            progressTracker.incProgress();
        }
        
        // Create checkout-config.xml
        if (success) {
            progressTracker.setCurrentOperation("Creating checkout-config.xml...");
            if (!createCheckoutConfigXml()) {
                success = false;
            }
            progressTracker.incProgress();
        }
        
        // Add sample JSPs
        if (success && settings.addSamples()) {
            progressTracker.setCurrentOperation("Adding sample JPS...");
            if (!copySamplesJsps()) {
                success = false;
            }
            progressTracker.incProgress();
        }
        
        return success;
    }
    
    /*************************************************************************/
    /*                   STEP-BY-STEP INTEGRATION METHODS                    */
    /*************************************************************************/
    
    private boolean copyCheckoutSdkJar() {
        boolean success = true;
        
        // Get the checkout-sdk.jar.txt resource
        InputStream source = getClass().getResourceAsStream("/com/google/checkout/sdk/nbmodule/sources/checkout-sdk.jar");
        
        // Get the checkout-sdk.jar path
        String path = settings.getWebInfDirectory().getPath() + "/lib/checkout-sdk.jar";
        File dest = new File(path);
            
        // Write the file
        try {
            CheckoutFileWriter.writeFileFromStream(source, dest);
        } catch (IOException ex) {
            success = false;
            errorMessage = "Could not write checkout-sdk.jar";
        }
        
        return success;
    }
    
    private boolean writeModifiedWebXml() {
        boolean success = true;
        
        // Get the source and destination
        String source = settings.getModifiedWebXml();
        File dest = settings.getWebXmlFile();
        
        // Write the file
        try {
            CheckoutFileWriter.writeFileFromString(source, dest);
        } catch (IOException ex) {
            success = false;
            errorMessage = "Could not write web.xml";
        }
        
        return success;
    }
    
    private boolean createCheckoutConfigXml() {
        boolean success = true;
        
        // Get the source and destination
        String source = settings.getConfigManager().getBody();
        File dest = settings.getConfigManager().getFile();
        
        // Write the file
        try {
            CheckoutFileWriter.writeFileFromString(source, dest);
        } catch (IOException ex) {
            success = false;
            errorMessage = "Could not write checkout-config.xml";
        }
        
        return success;
    }
    
    private boolean copySamplesJsps() {
        boolean success = true;
        
        // Get the sample directory provided by the user
        File destDirectory = settings.getSamplesDirectory();
        
        // Get the sample names
        String[] samples = getSampleNames();
        
        // Loop through each of the samples
        for (int i=0; i<samples.length  && success; i++) {
            // Get the source
            String name = "/com/google/checkout/sdk/nbmodule/sources/samples/" + samples[i];
            InputStream source = getClass().getResourceAsStream(name);
        
            // Get the destination
            String path = destDirectory.getPath() + "/" + samples[i];
            File dest = new File(path);

            // Write the file
            try {
                CheckoutFileWriter.writeFileFromStream(source, dest);
            } catch (IOException ex) {
                success = false;
                errorMessage = "Could not write " + samples[i];
            }
        }
        
        return success;
    }
    
    /*************************************************************************/
    /*                    HARDCODED METHODS (REMOVE ASAP)                    */
    /*************************************************************************/
    
    // TODO: Remove
    
    private static String[] getSampleNames() {
        String[] samples = new String[18];
        
        samples[0] = "addmerchantordernumber.jsp";
        samples[1] = "addtrackingdata.jsp";
        samples[2] = "archiveorder.jsp";
        samples[3] = "authorizeorder.jsp";
        samples[4] = "cancelorder.jsp";
        samples[5] = "chargeorder.jsp";
        samples[6] = "deliverorder.jsp";
        samples[7] = "index.jsp";
        samples[8] = "left_bottom.jsp";
        samples[9] = "left_top.jsp";
        samples[10] = "order_detail.jsp";
        samples[11] = "orders.jsp";
        samples[12] = "processorder.jsp";
        samples[13] = "refundorder.jsp";
        samples[14] = "sendbuyermessage.jsp";
        samples[15] = "shipping-fragment.jsp";
        samples[16] = "shopping_cart.jsp";
        samples[17] = "unarchiveorder.jsp";
        
        return samples;
    }
}
