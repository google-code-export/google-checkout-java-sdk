package com.google.checkoutsdk.nbmodule.integrationwizard.handlers;

import java.io.File;
import java.util.HashMap;

public class CheckoutConfigManager {
    
    // The actual config file be read from and written to
    private File file;
    
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
    
    // The maps which store handlers
    HashMap notificationHandlers;
    HashMap callbackHandlers;
    
    /**
     * Creates a new instance of CheckoutConfigManager, one that isn't bound
     * to a specefic file
     */
    public CheckoutConfigManager() {
        file = null;
        
        // Init handlers maps
        initNotificationHandlers();
        initCallbackHandlers();
        
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
    
    private void initNotificationHandlers() {
        if (notificationHandlers == null) {
            notificationHandlers = new HashMap();
        }
        
        // Insert a key-value pair for each message type
        // TODO: Read these from some type of config file
        notificationHandlers.put("new-order-notification", null);
        notificationHandlers.put("risk-information-notification", null);
        notificationHandlers.put("order-state-change-notification", null);
        notificationHandlers.put("charge-amount-notification", null);
        notificationHandlers.put("refund-amount-notification", null);
        notificationHandlers.put("chargeback-amount-notification", null);
        notificationHandlers.put("authorization-amount-notification", null);
    }
    
    private void initCallbackHandlers() {
        if (callbackHandlers == null) {
            callbackHandlers = new HashMap();
        }
        
        // Insert a key-value pair for each message type
        // TODO: Read these from some type of config file
        callbackHandlers.put("merchant-calculation-callback", null);
    }
    
    public String[] getNotificationTypes() {
        Object[] keys = notificationHandlers.keySet().toArray();
        String[] types = new String[keys.length];
        
        for (int i=0; i<types.length; i++) {
            types[i] = (String) keys[i];
        }
        
        return types;
    }
    
    public String[] getCallbackTypes() {
        Object[] keys = callbackHandlers.keySet().toArray();
        String[] types = new String[keys.length];
        
        for (int i=0; i<types.length; i++) {
            types[i] = (String) keys[i];
        }
        
        return types;
    }
    
    /**
     * Generates the new body of checkout-config.xml based on all of this
     * class's fields.
     *
     * @return The new body
     */
    public String getBody() {
        String body = "";

        // Begin
        body += "<checkout-config>\n";

        // Merchant info
        body += "    <merchant-info>\n"
                + "        <merchant-id>" + merchantId + "</merchant-id>\n"
                + "        <merchant-key>" + merchantKey + "</merchant-key>\n"
                + "        <env>" + env + "</env>\n"
                + "        <currency-code>" + currencyCode + "</currency-code>\n"
                + "        <sandbox-root>" + sandboxRoot + "</sandbox-root>\n"
                + "        <production-root>" + productionRoot + "</production-root>\n"
                + "        <checkout-suffix>" + checkoutSuffix + "</checkout-suffix>\n"
                + "        <merchant-checkout-suffix>" + merchantCheckoutSuffix + "</merchant-checkout-suffix>\n"
                + "        <request-suffix>" + requestSuffix + "</request-suffix>\n"
                + "    </merchant-info>\n";

        // Notification handlers
        body += "    <notification-handlers>\n";
        Object[] keys = notificationHandlers.keySet().toArray();
        for (int i=0; i<keys.length; i++) {
            String key = (String) keys[i];
            String value = (String) notificationHandlers.get(key);
            if (value != null) {
                body += "        <notification-handler>\n"
                        + "            <message-type>" + key + "</message-type>\n"
                        + "            <handler-class>" + value + "</handler-class>\n"
                        + "        </notification-handler>\n";
            }
        }
        body += "    </notification-handlers>\n";

        // Callback handlers
        body += "    <callback-handlers>\n";
        keys = callbackHandlers.keySet().toArray();
        for (int i=0; i<keys.length; i++) {
            String key = (String) keys[i];
            String value = (String) callbackHandlers.get(key);
            if (value != null) {
                body += "        <callback-handler>\n"
                        + "            <message-type>" + key + "</message-type>\n"
                        + "            <handler-class>" + value + "</handler-class>\n"
                        + "        </callback-handler>\n";
            }
        }
        body += "    </callback-handlers>\n";

        // End
        body += "</checkout-config>";
        
        return body;
    }
}
