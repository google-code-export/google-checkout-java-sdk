package com.google.checkout.samples.samplestore.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectPropertiesReaderAsync {
  public void getProjectPropertyValue(String propertyName, AsyncCallback callback);
}
