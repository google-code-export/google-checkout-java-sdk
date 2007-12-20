/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.google.checkout.samples.samplestore.client;

import com.google.gwt.json.client.JSONObject;

/**
 * Listens for product updates from JSON Base feed.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public interface BaseFeedListener {
  
  /**
   * Handles the data retrieved from Google Base given by the Base Feed
   * Retriever.
   * 
   * @param jsonObj Contains the information of the response from 
   * BaseFeedRetriever.
   */
  public void handleResponse(JSONObject jsonObj);
}
