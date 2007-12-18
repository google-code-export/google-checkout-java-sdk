package com.google.checkout.samples.samplestore.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProjectPropertiesReader extends RemoteService {
  public String getProjectPropertyValue(String propertyName);
}
