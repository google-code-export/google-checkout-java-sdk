// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.store.client;

import java.util.HashMap;

/**
 * Listens for product updates from JSON Base feed.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public interface BaseFeedListener {

  public void updateList(HashMap items);
  
}
