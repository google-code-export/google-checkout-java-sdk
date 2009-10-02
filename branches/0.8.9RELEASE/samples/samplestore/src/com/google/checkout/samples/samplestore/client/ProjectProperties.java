package com.google.checkout.samples.samplestore.client;

import java.io.Serializable;

public class ProjectProperties implements Serializable {
  private long baseCustomerId;
  private String storeName;
  private int maxResults;
  
  public ProjectProperties() {
  }
  
  public long getBaseCustomerId() {
    return baseCustomerId;
  }
  
  public void setBaseCustomerId(long baseCustomerId) {
    this.baseCustomerId = baseCustomerId;
  }
  
  public String getStoreName() {
    return storeName;
  }
  
  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }
  
  public int getMaxResults() {
    return maxResults;
  }
  
  public void setMaxResults(int maxResults) {
    this.maxResults = maxResults;
  }
}
