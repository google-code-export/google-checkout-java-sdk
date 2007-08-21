package com.google.checkout.sdk.nbmodule.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Represents a physical checkout-config.xml file, storing information about
 * a merchant and keeping maps of notification and callback handlers.
 */
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
        merchantId = "";
        merchantKey = "";
        env = "Sandbox";
        currencyCode = "USD";
        sandboxRoot = "https://sandbox.google.com/checkout/cws/v2/Merchant";
        productionRoot = "https://checkout.google.com/cws/v2/Merchant";
        checkoutSuffix = "checkout";
        merchantCheckoutSuffix = "merchantCheckout";
        requestSuffix = "request";
        
        notificationHandlers.put("new-order-notification", 
                "com.google.checkout.sdk.NewOrderNotificationHandler");

        notificationHandlers.put("risk-information-notification", 
                "com.google.checkout.sdk.RiskInformationNotificationHandler");

        notificationHandlers.put("order-state-change-notification", 
                "com.google.checkout.sdk.OrderStateChangeNotificationHandler");
      
        notificationHandlers.put("charge-amount-notification", 
                "com.google.checkout.sdk.ChargeAmountNotificationHandler");
      
        notificationHandlers.put("refund-amount-notification", 
                "com.google.checkout.sdk.RefundAmountNotificationHandler");

        notificationHandlers.put("chargeback-amount-notification", 
                "com.google.checkout.sdk.ChargebackAmountNotificationHandler");
      
        notificationHandlers.put("authorization-amount-notification", 
                "com.google.checkout.sdk.AuthorizationAmountNotificationHandler");
      
        callbackHandlers.put("merchant-calculation-callback", 
                "com.google.checkout.sdk.MerchantCalculationCallbackHandler");

    }
    
    /**
     * Creates a new instance of CheckoutConfigManager with the specified file.
     */
    public CheckoutConfigManager(File file) {
        this();
        this.file = file;
        readFile();
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
    /*                           MAP ACCESSORS                               */
    /*************************************************************************/
    
    public Object getNotificationHandler(String type) {
        return notificationHandlers.get(type);
    }
    
    public void setNotificationHandler(String type, String name) {
        if (name.trim().equals("")) {
            name = null;
        }
        notificationHandlers.put(type,name);
    }
    
    public Object getCallbackHandler(String type) {
        return callbackHandlers.get(type);
    }
    
    public void setCallbackHandler(String type, String name) {
        if (name.trim().equals("")) {
            name = null;
        }
        callbackHandlers.put(type,name);   
    }
    
    /*************************************************************************/
    /*                         UTILITY METHODS                               */
    /*************************************************************************/
    
    /**
     * Creates the standard batch of notification handlers.
     */
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
    
    /**
     * Creates the standard batch of callback handlers.
     */
    private void initCallbackHandlers() {
        if (callbackHandlers == null) {
            callbackHandlers = new HashMap();
        }
        
        // Insert a key-value pair for each message type
        // TODO: Read these from some type of config file
        callbackHandlers.put("merchant-calculation-callback", null);
    }
    
    /**
     * Gets an array of notification message types.
     *
     * @return Array of notification message types
     */
    public String[] getNotificationTypes() {
        Object[] keys = notificationHandlers.keySet().toArray();
        String[] types = new String[keys.length];
        
        for (int i=0; i<types.length; i++) {
            types[i] = (String) keys[i];
        }
        
        return types;
    }
    
    /**
     * Gets an array of callback message types.
     *
     * @return Array of callback message types
     */
    public String[] getCallbackTypes() {
        Object[] keys = callbackHandlers.keySet().toArray();
        String[] types = new String[keys.length];
        
        for (int i=0; i<types.length; i++) {
            types[i] = (String) keys[i];
        }
        
        return types;
    }
    
    /**
     * Reads the value of an XML element from a parent element.
     *
     * @param parent The parent element to read from
     * @param name The name of the element to look for
     * @return The value of the element
     */
    private String read(Element parent, String name) {
        Element elem = (Element) parent.getElementsByTagName(name).item(0);
        Node value = (Node)elem.getChildNodes().item(0);
        
        if (value == null) {
            return "";
        } else {
            return value.getNodeValue().trim();
        }
    }
    
    /*************************************************************************/
    /*                            FILE METHODS                               */
    /*************************************************************************/

    /**
     * Uses a simple SAX parser to read the checkout-config.xml file.  Reads
     * only one element of each root type (merchant-info, notification-handlers,
     * callback-handlers).
     * 
     * @return true if read successfully
     */
    public boolean readFile() {
        boolean success = true;
        
        if (file != null) {
            try {
                // Get the document
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();
                
                // Read merchant information
                Element merchantInfo = (Element) doc.getElementsByTagName("merchant-info").item(0);
                if (merchantInfo != null) {
                    merchantId = read(merchantInfo, "merchant-id");
                    merchantKey = read(merchantInfo, "merchant-key");
                    env = read(merchantInfo, "env");
                    currencyCode = read(merchantInfo, "currency-code");
                    sandboxRoot = read(merchantInfo, "sandbox-root");
                    productionRoot = read(merchantInfo, "production-root");
                    checkoutSuffix = read(merchantInfo, "checkout-suffix");
                    merchantCheckoutSuffix = read(merchantInfo, "merchant-checkout-suffix");
                    requestSuffix = read(merchantInfo, "request-suffix");
                }
                
                // Read notification handlers
                Element notificationRoot = (Element) doc.getElementsByTagName("notification-handlers").item(0);
                if (notificationRoot != null) {
                    NodeList nodes = notificationRoot.getElementsByTagName("notification-handler");
                    for( int i=0; i<nodes.getLength(); i++) {
                        Element elem = (Element) nodes.item(i);
                        String type = read(elem, "message-type");
                        String name = read(elem, "handler-class");
                        setNotificationHandler(type, name);
                    }
                }
                
                // Read notification handlers
                Element callbackRoot = (Element) doc.getElementsByTagName("callback-handlers").item(0);
                if (callbackRoot != null) {
                    NodeList nodes = callbackRoot.getElementsByTagName("callback-handler");
                    for( int i=0; i<nodes.getLength(); i++) {
                        Element elem = (Element) nodes.item(i);
                        String type = read(elem, "message-type");
                        String name = read(elem, "handler-class");
                        setCallbackHandler(type, name);
                    }
                }
            } catch (ParserConfigurationException ex) {
                success = false;
            } catch (IOException ex) {
                success = false;
            } catch (SAXException ex) {
                success = false;
            }
        } else {
            success = false;
        }
        
        return success;
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
