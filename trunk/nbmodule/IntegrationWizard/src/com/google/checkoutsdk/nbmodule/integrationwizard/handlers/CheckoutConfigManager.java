package com.google.checkoutsdk.nbmodule.integrationwizard.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CheckoutConfigManager {
    
    // The actual config file be read from and written to
    // Will be null
    private File file;
    
    // The fully qualified path to a new file, used to create a new file if
    // 'file' is null when writeConfig() is called
    private String newFileName;
    
    // Basic merchant info
    private String merchantId;
    private String merchantKey;
    private String env;
    private String currencyCode;
    private String sandboxRoot;
    private String productionRoot;
    private String checkoutSuffix;
    private String merchantCheckoutSuffix;
    private String requestSuffix;
    
    /**
     * Creates a new instance of CheckoutConfigManager, one that isn't bound
     * to a specefic file
     */
    public CheckoutConfigManager() {
        file = null;
        setNewFileName(null);
        
        // Init fields to default values
        // TODO: Read these from some type of config file
        merchantId = "812318588721976";
        merchantKey = "c1YAeK6wMizfJ6BmZJG9Fg";
        env = "Sandbox";
        currencyCode = "USD";
        sandboxRoot = "https://sandbox.google.com/checkout/cws/v2/Merchant";
        productionRoot = "https://checkout.google.com/cws/v2/Merchant";
        checkoutSuffix = "checkout";
        merchantCheckoutSuffix = "merchantCheckout";
        requestSuffix = "request";
    }
    
    /*************************************************************************/
    /*                             FIELD ACCESSORS                           */
    /*************************************************************************/
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    
    /*************************************************************************/
    /*                         UTILITY METHODS                               */
    /*************************************************************************/
    
    public void getHandlers() {
        //LinkedList handlers = new LinkedList();
        //BufferedReader reader = new BufferedReader(new FileReader(file));
        
    }
    
    /*************************************************************************/
    /*                         FILE IO METHODS                               */
    /*************************************************************************/
    
    /**
     * Reads information from the config file.
     *
     * @return true if successful
     */
    public boolean readConfig() {
        return true;
    }
    
    /**
     * Writes the information out to the config file.
     *
     * @return true if successful
     */
    public boolean writeConfig() {
        boolean retVal = true;
        
        // Generate a new file if 'file' doesn't already exist
        if (file == null && getNewFileName() != null) {
            file = new File(getNewFileName());
        }
        
        if (file == null) {
            // File creation failed
            retVal = false;
        } else {
            String output = "";

            // Begin
            output += "<checkout-config>\n";

            // Merchant info
            output += "  <metchant-info>\n"
                    + "    <merchant-id>" + merchantId + "</merchant-id>\n"
                    + "    <merchant-key>" + merchantKey + "</merchant-key>\n"
                    + "    <env>" + env + "</env>\n"
                    + "    <currency-code>" + currencyCode + "</currency-code>\n"
                    + "    <sandbox-root>" + sandboxRoot + "</sandbox-root>\n"
                    + "    <production-root>" + productionRoot + "</production-root>\n"
                    + "    <checkout-suffix>" + checkoutSuffix + "</checkout-suffix>\n"
                    + "    <merchant-checkout-suffix>" + merchantCheckoutSuffix + "</merchant-checkout-suffix>\n"
                    + "    <request-suffix>" + requestSuffix + "</request-suffix>\n"
                    + "  </merchant-info>\n";

            // Notification handlers
            output += "  <notification-handlers>\n";
            for (int i=0; i<10; i++) {

            }
            output += "  </notification-handlers>\n";

            // Callback handlers
            output += "  <callback-handlers>\n";
            for (int i=0; i<10; i++) {

            }
            output += "  </callback-handlers>\n";

            // End
            output += "</checkout-config>";

            // Write to the file
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(output);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
               retVal = false;
            }
        }
        
        return retVal;
    }
}
