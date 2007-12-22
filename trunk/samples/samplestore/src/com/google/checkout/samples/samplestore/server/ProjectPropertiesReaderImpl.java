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

package com.google.checkout.samples.samplestore.server;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.checkout.samples.samplestore.client.ProjectProperties;
import com.google.checkout.samples.samplestore.client.ProjectPropertiesReader;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class ProjectPropertiesReaderImpl extends RemoteServiceServlet 
  implements ProjectPropertiesReader {
  
  private Document document;
  
  /**
   * Default constructor. Parses the GridStore.xml for the Google Base customer
   * info.
   */
  public ProjectPropertiesReaderImpl() {
    DocumentBuilderFactory factory = null;
    DocumentBuilder builder = null;

    
    try {
      factory = DocumentBuilderFactory.newInstance();
      builder = factory.newDocumentBuilder();
      document = builder.parse(getClass().getClassLoader().getResourceAsStream("com/google/checkout/samples/samplestore/server/GridStore.xml"));
      
    } catch (ParserConfigurationException ex) {
      throw new RuntimeException("Could not create new empty document.");
    } catch (SAXException ex) {
      throw new RuntimeException("Got the following error when attemtping to " +
          "create new document from the given xml string. Error message: " 
          + ex.getMessage());
    } catch (IOException ex) {
      throw new RuntimeException("Error creating new document from the " + 
          "specified string. Error message: " + ex.getMessage());
    }
  }
  
  public ProjectProperties getProjectProperties() {
    NodeList baseCustomerId = 
      document.getDocumentElement().getElementsByTagName("base-customer-id");
    NodeList storeName = 
      document.getDocumentElement().getElementsByTagName("store-name");
    NodeList maxItems = 
      document.getDocumentElement().getElementsByTagName("max-results");
    
    ProjectProperties properties =  new ProjectProperties();
    
    properties.setBaseCustomerId(Long.parseLong(baseCustomerId.item(0).getTextContent()));
    properties.setStoreName(storeName.item(0).getTextContent());
    properties.setMaxResults(Integer.parseInt(maxItems.item(0).getTextContent()));
    
    return properties;
  }
}
