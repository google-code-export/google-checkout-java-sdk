package com.google.checkoutsdk.nbmodule.integrationwizard.handlers;

public class Handler {
    
    // The name fo the actual handler class
    private String name;
    
    // The type of message that this handler accepts
    private String type;
    
    // True if callback, false if notification
    private boolean callback;
    
    public Handler(String name, String type, boolean callback) {
        this.name = name;
        this.type = type;
        this.callback = callback;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isCallback() {
        return callback;
    }
    
    public void setCallback(boolean callback) {
        this.callback = callback;
    }
    
    static public Handler[] getValues() {
        Handler[] values = new Handler[8];
        values[0] = new Handler("NewOrderNotificationHandler", "new-order-notification", false);
        values[1] = new Handler("RiskInformationNotificationHandler", "risk-information-notification", false);
        values[2] = new Handler("OrderStateChangeNotificationHandler", "order-state-change-notification", false);
        values[3] = new Handler("ChargeAmountNotificationHandler", "charge-amount-notification", false);
        values[4] = new Handler("RefundAmountNotificationHandler", "refund-amount-notification", false);
        values[5] = new Handler("ChargebackAmountNotificationHandler", "chargeback-amount-notification", false);
        values[6] = new Handler("AuthorizationAmountNotificationHandler", "authorization-amount-notification", false);
        values[7] = new Handler("MerchantCalculationCallbackHandler", "merchant-calculation-callback", true);
        
        return values;
    }
    
}
