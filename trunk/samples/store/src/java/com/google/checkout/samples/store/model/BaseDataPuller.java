/* Copyright (c) 2007 Google Inc.                                                                                                                                                                                
 *                                                                                                                                                                                                               
 * Licensed under the Apache License, Version 2.0 (the "License");                                                                                                                                               
 * you may not use this file except in compliance with the License.                                                                                                                                              
 * You may obtain a copy of the License at                                                                                                                                                                       
 *                                                                                                                                                                                                               
 *     http://www.apache.org/licenses/LICENSE-2.0                                                                                                                                                                
 *                                                                                                                                                                                                               
 * Unless required by applicable law or agreed to in writing, software                                                                                                                                           
 * distributed under the License is distributed on an "AS IS" BASIS,                                                                                                                                             
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                                                                                                                                      
 * See the License for the specific language governing permissions and                                                                                                                                           
 * limitations under the License.                                                                                                                                                                                
 */

package com.google.checkout.samples.store.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Display all items of a specific customer.
 */
public class BaseDataPuller {
  /**
   * URL of the authenticated customer feed.
   */
  private static final String ITEMS_FEED = "http://base.google.com/base/feeds/items";

  /**
   * Insert here the developer key obtained for an "installed application" at
   * http://code.google.com/apis/base/signup.html
   */
  private static final String DEVELOPER_KEY = 
      "ABQIAAAA6v51rCG8B8GRdR_uIh5pdRT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRgsnCfYNgWrV5L6lI_BBxMVBz1-A";
  
  /**
   * URL used for authenticating and obtaining an authentication token. 
   * More details about how it works:
   * <code>http://code.google.com/apis/accounts/AuthForInstalledApps.html<code>
   */
  private static final String AUTHENTICATION_URL = "https://www.google.com/accounts/ClientLogin";

  /**
   * Fill in your Google Account email here.
   */
  private static final String EMAIL = "gbc.merchant@gmail.com";
  
  /**
   * Fill in your Google Account password here.
   */
  private static final String PASSWORD = "storefront";
  
  public static final Category BASE_CATEGORY = Category.getCategory("Base Stuff");
  
  private static HashMap<Category, ProductList> products = new HashMap<Category, ProductList>();
  
  /**
   * Create a <code>QueryExample3</code> instance and call
   * <code>displayMyItems</code>, which displays all items that belong to the
   * currently authenticated user.
   */
  public static HashMap<Category, ProductList> getBaseData() {
    return products;
  }
  
  public void loadBaseData() {
    try {
      //BaseDataPuller queryExample = new BaseDataPuller();
      String token = authenticate();
      System.out.println("loadBaseData");
      displayMyItems(token);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }  
  }
  
  /**
   * Retrieves the authentication token for the provided set of credentials.
   * @return the authorization token that can be used to access authenticated
   *         Google Base data API feeds
   */
  public String authenticate() {
    // create the login request
    String postOutput = null;
    try {
      URL url = new URL(AUTHENTICATION_URL);
      postOutput = makeLoginRequest(url);
    } catch (IOException e) {
      System.out.println("Could not connect to authentication server: " 
          + e.toString());
      System.exit(1);
    }
  
    // Parse the result of the login request. If everything went fine, the 
    // response will look like
    //      HTTP/1.0 200 OK
    //      Server: GFE/1.3
    //      Content-Type: text/plain 
    //      SID=DQAAAGgA...7Zg8CTN
    //      LSID=DQAAAGsA...lk8BBbG
    //      Auth=DQAAAGgA...dk3fA5N
    // so all we need to do is look for "Auth" and get the token that comes after it
  
    StringTokenizer tokenizer = new StringTokenizer(postOutput, "=\n ");
    String token = null;
    
    while (tokenizer.hasMoreElements()) {
      if (tokenizer.nextToken().equals("Auth")) {
        if (tokenizer.hasMoreElements()) {
          token = tokenizer.nextToken(); 
        }
        break;
      }
    }
    if (token == null) {
      System.out.println("Authentication error. Response from server:\n" + postOutput);
      System.exit(1);
    }
    return token;
  }

  /**
   * Displays the "items" feed, that is the feed that contains the items that
   * belong to the currently authenticated user.
   * 
   * @param token the authorization token, as returned by
   *        <code>authenticate<code>
   * @throws IOException if an IOException occurs while creating/reading the 
   *         request
   */
  public void displayMyItems(String token) throws IOException, SAXException,
          ParserConfigurationException, MalformedURLException {
    HttpURLConnection connection = (HttpURLConnection)(new URL(ITEMS_FEED)).openConnection() ;
    // Set properties of the connection
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Authorization", "GoogleLogin auth=" + token);
    connection.setRequestProperty("X-Google-Key", "key=" + DEVELOPER_KEY);
    
    int responseCode = connection.getResponseCode();
    InputStream inputStream;
    if (responseCode == HttpURLConnection.HTTP_OK) {
      inputStream = connection.getInputStream();
    } else {
      inputStream = connection.getErrorStream();
    }
    
    //System.out.println(toString(inputStream));
    
    /*
     * Create a SAX XML parser and pass in the input stream to the parser.
     * The parser will use DisplayTitleHandler to extract the titles from the 
     * XML stream. 
     */
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    System.out.println("displayMyItems");
    parser.parse(inputStream, new DisplayDataHandler());
   }
  
  /**
   * Makes a HTTP POST request to the provided {@code url} given the provided
   * {@code parameters}. It returns the output from the POST handler as a
   * String object.
   * 
   * @param url the URL to post the request
   * @return the output from the Google Accounts server, as string
   * @throws IOException if an I/O exception occurs while
   *           creating/writing/reading the request
   */
  private String makeLoginRequest(URL url)
      throws IOException {
    // Create a login request. A login request is a POST request that looks like
    // POST /accounts/ClientLogin HTTP/1.0
    // Content-type: application/x-www-form-urlencoded
    // Email=johndoe@gmail.com&Passwd=north23AZ&service=gbase&source=Insert Example

    // Open connection
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
  
    
    // Set properties of the connection
    urlConnection.setRequestMethod("POST");
    urlConnection.setDoInput(true);
    urlConnection.setDoOutput(true);
    urlConnection.setUseCaches(false);
    urlConnection.setRequestProperty("Content-Type",
                                     "application/x-www-form-urlencoded");
  
    // Form the POST parameters
    StringBuilder content = new StringBuilder();
    content.append("Email=").append(URLEncoder.encode(EMAIL, "UTF-8"));
    content.append("&Passwd=").append(URLEncoder.encode(PASSWORD, "UTF-8"));
    content.append("&service=").append(URLEncoder.encode("gbase", "UTF-8"));
    content.append("&source=").append(URLEncoder.encode("Google Base data API example", "UTF-8"));

    OutputStream outputStream = urlConnection.getOutputStream();
    outputStream.write(content.toString().getBytes("UTF-8"));
    outputStream.close();
  
    // Retrieve the output
    int responseCode = urlConnection.getResponseCode();
    InputStream inputStream;
    if (responseCode == HttpURLConnection.HTTP_OK) {
      inputStream = urlConnection.getInputStream();
    } else {
      inputStream = urlConnection.getErrorStream();
    }
  
    return toString(inputStream);
  }
  
  /**
   * Simple SAX event handler, which prints out the titles of all entries in the 
   * Atom response feed. 
   */
  private class DisplayDataHandler extends DefaultHandler {
    /**
     * Stack containing the opening XML tags of the response.
     */
    private Stack<String> xmlTags = new Stack<String>(); 
    
    /**
     * Counter that keeps track of the currently parsed item.
     */
    private int itemNo = 0;
    
    /**
     * True if we are inside of a data entry's title, false otherwise.
     */
    private boolean insideEntryTitle = false;
    private boolean insideEntryContent = false;
    private boolean insideEntryImage = false;
    private boolean insideEntryItemType = false;
    
    //private String productId;
    private String productName = "";
    //private String productThumbnailUrl;
    private String productImageUrl = "";
    private String productDescription = "";
    private String productItemType = "";
    
    /**
     * Receive notification of an opening XML tag: push the tag to 
     * <code>xmlTags</code>. If the tag is a title tag inside an entry tag, 
     * turn <code>insideEntryTitle</code> to <code>true</code>.
     */
    @Override
    public void startElement(String uri, String localName, String qName,
        Attributes attributes) throws SAXException {
      if (qName.equals("title") && xmlTags.peek().equals("entry")) {
        insideEntryTitle = true;
      } else if (qName.equals("content") && xmlTags.peek().equals("entry")) {
        insideEntryContent = true;
      } else if (qName.equals("g:image_link") && xmlTags.peek().equals("entry")) {
        insideEntryImage = true;
      } else if (qName.equals("g:item_type") && xmlTags.peek().equals("entry")) {
        insideEntryItemType = true;
      }
      xmlTags.push(qName);
    }
    
    /**
     * Receive notification of a closing XML tag: remove the tag from teh stack.
     * If we were inside of an entry's title, turn <code>insideEntryTitle</code>
     * to <code>false</code>.
     */
    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {
      
      if (insideEntryItemType) {
        boolean found = false;
        for (Category subCategory : BASE_CATEGORY.getSubCategories()) {
          if (productItemType.equals(subCategory.getName())) {
            found = true;  
            break;
          }
        }
        if (!found)
            BASE_CATEGORY.getSubCategories().add(Category.getCategory(productItemType));
        insideEntryItemType = false;
      }
        
      if (insideEntryImage) {
        if (products.get(Category.getCategory(productItemType)) == null) {
          products.put(Category.getCategory(productItemType), 
              new ProductList(Category.getCategory(productItemType), 
              new Product("base" + itemNo++, productName, 
              productImageUrl, productImageUrl, productDescription)));  
        } else {
          products.get(Category.getCategory(productItemType)).add(
              new Product("base" + itemNo++, productName, 
              productImageUrl, productImageUrl, productDescription));  
        }
        insideEntryImage = false;
        System.out.println(productName);
      }
      // If a "title" element is closed, we start a new line, to prepare
      // printing the new title.
      xmlTags.pop();
      insideEntryTitle = false;
      insideEntryContent = false;
      if (qName.equals("entry")) {
          productName = "";
          productDescription = "";
          productImageUrl = "";
          productItemType = "";
      }      
    }
    
    /**
     * Callback method for receiving notification of character data inside an
     * XML element. 
     */
    @Override
    public void characters(char[] ch, int start, int length)
        throws SAXException {

      String value = new String(ch, start, length);
      if (insideEntryTitle) {
        productName = value;  
      } else if (insideEntryContent) {
        productDescription += value;  
      } else if (insideEntryImage) {
        productImageUrl += value;
      } else if (insideEntryItemType) {
        productItemType = value;
      }
    }
  }
  
  /**
   * Writes the content of the input stream to a <code>String<code>.
   */
  private String toString(InputStream inputStream) throws IOException {
    String string;
    StringBuilder outputBuilder = new StringBuilder();
    if (inputStream != null) {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(inputStream));
      while (null != (string = reader.readLine())) {
        outputBuilder.append(string).append('\n');
      }
    }
    return outputBuilder.toString();
  }
}
