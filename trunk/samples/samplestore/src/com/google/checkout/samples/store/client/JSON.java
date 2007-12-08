// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.store.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 
 * Queries base for a customer specific feed of products.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class JSON {

  /**
   * Default URL to use to fetch JSON objects. Note that the contents of this
   * JSON result were as a result of requesting the following URL:
   */
  private static String DEFAULT_SEARCH_URL =  
      "http://www.google.com/base/feeds/snippets?max-results=17&alt=json-in-script&callback=jsonCallback";
  
  private List listeners = new ArrayList();
  
  private class JSONResponseHandler {
    public void onCompletion(JavaScriptObject response) {
      JSONObject json = new JSONObject(response);
      HashMap products = parseJSON(json);
      for (int i = 0; i < listeners.size(); i++) {
        ((BaseFeedListener) listeners.get(i)).updateList(products);
      }
    }
  }
  
  public void registerListener(BaseFeedListener listener) {
    listeners.add(listener);
  }
  
  public void fetchProductsFromBase(String customerId) {
    String url = DEFAULT_SEARCH_URL + constructQuery(customerId);
    doFetchURL(new JSONResponseHandler(), url);
  }
  
  private String constructQuery(String customerId) {
    return "&bq=" + URL.encode("[customer id:" + customerId + "]");
  }

  private HashMap parseJSON(JSONObject json) {
    JSONArray ary = json.get("feed").isObject().get("entry").isArray();
    HashMap map = new HashMap();
    List productList = new ArrayList();
    for (int i = 0; i < ary.size(); ++i) {
      JSONObject entry = ary.get(i).isObject();

      Product p = new Product(
          entry.get("id").isObject().get("$t").toString(),
          entry.get("title").isObject().get("$t").toString(),
          (entry.get("g$image_link") != null ? entry
              .get("g$image_link").isArray().get(0).isObject().get("$t").toString() : ""),
          entry.get("content").isObject().get("$t").toString(),
          (entry.get("g$price") != null ? entry
              .get("g$price").isArray().get(0).isObject().get("$t").toString() : "")
          );
      String category = entry.get("g$item_type").isArray().get(0).isObject()
          .get("$t").toString().split("\"")[1];
      if (map.get(Category.getCategory(category)) == null) {
        map.put(Category.getCategory(category), 
            new ProductList(Category.getCategory(category), p));  
      } else {
        ((ProductList) map.get(Category.getCategory(category))).add(p);  
      }
    }
    return map;
  }
  
  /**
   * Fetch the requested URL.
   */
  private native void doFetchURL(JSONResponseHandler handler, String url) /*-{
    window["jsonCallback"] = function(data) {
      handler.@com.google.checkout.samples.store.client.JSON.JSONResponseHandler::onCompletion(Lcom/google/gwt/core/client/JavaScriptObject;)(data);
    }
    var elem = document.createElement("script");
    elem.setAttribute("language", "JavaScript");
    elem.setAttribute("src", url);
    document.getElementsByTagName("body")[0].appendChild(elem);
  }-*/;
  
}
