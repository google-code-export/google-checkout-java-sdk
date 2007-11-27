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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class BaseSnippetPuller {
  /**
   * Url of the Google Base data API snippet feed.
   */
  private static final String SNIPPETS_FEED = "http://base.google.com/base/feeds/snippets";

  /**
   * The query that is sent over to the Google Base data API server.
   */
  private static final String QUERY = "[customer id:2828467]";
  
  public static Category BASE_CATEGORY = null;
  
  private static HashMap<Category, ProductList> products = null;
  
  /**
   * Create a <code>QueryExa
  private static HashMapmple3</code> instance and call
   * <code>displayMyItems</code>, which displays all items that belong to the
   * currently authenticated user.
   */
  public static HashMap<Category, ProductList> getBaseData() {
    return products;
  }

  public void loadBaseData() {
    try {
      BASE_CATEGORY = Category.getCategory("Base Data"); 
      products = new HashMap<Category, ProductList>();
      displayItems();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }  
  }
  
  public void reloadData(String username, String password) {
    loadBaseData();
  }
  
  /**
   * Connect to the Google Base data API server, retrieve the items that match
   * <code>QUERY</code> and call <code>DisplayTitlesHandler</code> to extract
   * and display the titles from the XML response.
   */
  public void displayItems() throws IOException, SAXException,
          ParserConfigurationException {
    /*
     * Create a URL object, open an Http connection on it and get the input
     * stream that reads the Http response.
     */
    URL url = new URL(SNIPPETS_FEED + "?bq=" + 
        URLEncoder.encode(QUERY, "UTF-8"));
    System.out.println(url);
    HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
    InputStream inputStream = httpConnection.getInputStream();
    System.out.println(inputStream);
    /*
     * Create a SAX XML parser and pass in the input stream to the parser.
     * The parser will use DisplayTitleHandler to extract the titles from the 
     * XML stream. 
     */
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    parser.parse(inputStream, new DisplayDataHandler());
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
}
