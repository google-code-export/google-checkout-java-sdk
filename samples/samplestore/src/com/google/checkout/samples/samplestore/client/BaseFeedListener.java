// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.samplestore.client;

import com.google.gwt.json.client.JSONObject;

/**
 * Listens for product updates from JSON Base feed.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public interface BaseFeedListener {
  public void handleResponse(JSONObject jsonObj);
}
