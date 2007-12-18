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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import java.util.ArrayList;
import java.util.List;

/** 
 * Queries Google Base for a customer specific feed of products.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class BaseFeedRetriever {
  /**
   * Default URL to use to fetch JSON objects. Note that the contents of this
   * JSON result were as a result of requesting the following URL:
   */
  private static String DEFAULT_SEARCH_URL = "";
//      "http://www.google.com/base/feeds/snippets?max-results=17&alt=json-in-script&callback=jsonCallback";
  
  private List listeners = new ArrayList();
  
  public BaseFeedRetriever() {
    ProjectPropertiesReaderAsync propertiesReader = 
      (ProjectPropertiesReaderAsync)GWT.create(ProjectPropertiesReader.class);
    
    ServiceDefTarget endpoint = (ServiceDefTarget) propertiesReader;
    String moduleRelativeURL = GWT.getModuleBaseURL() + "propertiesReader";
    endpoint.setServiceEntryPoint(moduleRelativeURL);
    

    AsyncCallback jsonFeedCallback = new AsyncCallback() {
      public void onSuccess(Object result) {
        DEFAULT_SEARCH_URL = result.toString();
      }
      
      public void onFailure(Throwable caught) {
        throw new RuntimeException("Unable to find json-feed to retrieve items", caught);
      }
    };
    
    propertiesReader.getProjectPropertyValue("json-feed", jsonFeedCallback);
  }
  
  private class JSONResponseHandler {
    public void onCompletion(JavaScriptObject response) {
      JSONObject jsonObj = new JSONObject(response);
      
      for (int i = 0; i < listeners.size(); i++) {
        ((BaseFeedListener) listeners.get(i)).handleResponse(jsonObj);
      }
    }
  }
  
  public void registerListener(BaseFeedListener listener) {
    listeners.add(listener);
  }
  
  public void fetchProductsFromBase(long customerId) {
    String url = DEFAULT_SEARCH_URL + constructQuery(customerId);
    doFetchURL(new JSONResponseHandler(), url);
  }
  
  private String constructQuery(long customerId) {
    return "&bq=" + URL.encode("[customer id:" + customerId + "]");
  }
  
  /**
   * Fetch the requested URL.
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
