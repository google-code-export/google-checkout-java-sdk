package com.google.checkout.samples.samplestore.server;

import com.google.checkout.samples.samplestore.client.ProjectPropertiesReader;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ProjectPropertiesReaderImpl extends RemoteServiceServlet 
  implements ProjectPropertiesReader {
  
  private Document document;
  
  public ProjectPropertiesReaderImpl() {
    DocumentBuilderFactory factory = null;
    DocumentBuilder builder = null;

    try {
      factory = DocumentBuilderFactory.newInstance();
      builder = factory.newDocumentBuilder();
      document = builder.parse(new File("src/com/google/checkout/samples/samplestore/server/GridStore.xml"));
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
  
  public String getProjectPropertyValue(String propertyName) {
    NodeList nl = 
      document.getDocumentElement().getElementsByTagName(propertyName);
    if (nl.getLength() == 0) {
      return null;
    }
    return nl.item(0).getNodeValue();
  }
}
