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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** 
 * Queries Google Base for a customer specific feed of products. After querying
 * GoogleBase, BaseFeedRetriever will pass all BaseFeedListeners registered
 * with this instance the JSONObject corresponding to the response it received.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class BaseFeedRetriever {
  /**
   * Default URL to use to fetch JSON objects. Note that the contents of this
   * JSON result were as a result of requesting the following URL:
   */
//  private static String DEFAULT_SEARCH_URL = 
//    "http://www.google.com/base/feeds/snippets?max-results=17&alt=json-in-script&callback=jsonCallback";

  private static String searchUrl = "http://www.google.com/base/feeds/snippets?";
  
  /**
   * Objects that will want to handle the data retrieved from Google Base.
   */
  private List listeners = new ArrayList();
  
  private class JSONResponseHandler {
    public void onCompletion(JavaScriptObject response) {
      JSONObject jsonObj = new JSONObject(response);
      
      for (int i = 0; i < listeners.size(); i++) {
        ((BaseFeedListener) listeners.get(i)).handleResponse(jsonObj);
      }
    }
  }
  
  /**
   * Registers a BaseFeedListener with the BaseFeedRetriever.
   * 
   * @param listener The BaseFeedListener to register.
   */
  public void registerListener(BaseFeedListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Retrieves the products from Google Base corresponding to the items we 
   * would like to query.
   * 
   * @param customerId The Google Base Customer Id corresponding to the items 
   * we want to query.
   */
  public void fetchProductsFromBase(long customerId, int maxResults) {
    String url = searchUrl + constructMaxResults(maxResults) + appendJsonInfo() 
      + constructQuery(customerId);
    doFetchURL(new JSONResponseHandler(), url);
  }
  
  /**
   * 
   * @param maxResults
   * @return
   */
  private String constructMaxResults(int maxResults) {
    return "max-results=" + maxResults; 
  }
  
  /**
   * 
   * @return
   */
  private String appendJsonInfo() {
    return "&alt=json-in-script&callback=jsonCallback";
  }
  
  /**
   * Appends to the URL the customer id whose items we would like to query.
   * 
   * @param customerId The Google Base Customer Id corresponding to the items
   * we want to query.
   *   
   * @return The URL with the Google Base customer info appended to the query
   * string.
   */
  private String constructQuery(long customerId) {
    return "&bq=" + URL.encode("[customer id:" + customerId + "]");
  }
  
  /**
   * Fetch the requested URL from Google Base.
   *  
   * @param handler The handler to call once the call has completed.
   * @param url The URL where the items will be retrieved.
   */
  private native void doFetchURL(JSONResponseHandler handler, String url) /*-{
    window["jsonCallback"] = function(data) {
      handler.@com.google.checkout.samples.samplestore.client.BaseFeedRetriever.JSONResponseHandler::onCompletion(Lcom/google/gwt/core/client/JavaScriptObject;)(data);
    }
    var elem = document.createElement("script");
    elem.setAttribute("language", "JavaScript");
    elem.setAttribute("src", url);
    document.getElementsByTagName("body")[0].appendChild(elem);
  }-*/;
  
}
