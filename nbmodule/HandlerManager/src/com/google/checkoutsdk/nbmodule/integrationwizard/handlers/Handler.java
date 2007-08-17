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
}
