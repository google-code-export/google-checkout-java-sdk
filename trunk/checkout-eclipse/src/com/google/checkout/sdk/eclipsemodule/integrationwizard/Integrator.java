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

package com.google.checkout.sdk.eclipsemodule.integrationwizard;

import com.google.checkout.sdk.eclipsemodule.common.CheckoutFileWriter;
import com.google.checkout.sdk.eclipsemodule.common.exceptions.CheckoutConfigException;
import com.google.checkout.sdk.eclipsemodule.common.exceptions.CheckoutSdkException;
import com.google.checkout.sdk.eclipsemodule.common.exceptions.SamplesJspException;
import com.google.checkout.sdk.eclipsemodule.common.exceptions.WebXmlException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Integrator {
  // The settings built by the Integration Wizard
  private Settings settings;
  
  public Integrator(Settings settings) {
    this.settings = settings;
  }
  
  public void integrate() throws CheckoutSdkException, WebXmlException, 
      CheckoutConfigException, SamplesJspException {  
    // Add checkout-sdk.jar to your WEB_INF/lib directory
    copyCheckoutSdkJar();
    
    // Modify web.xml
    if (settings.getModifiedWebXml() != null) {
      writeModifiedWebXml();  
    }
    
    // Create checkout-config.xml
    createCheckoutConfigXml();
    
    // Add sample JSPs
    if (settings.getAddSamples()) {
      copySamplesJsps();
    }
  }
  
  /*************************************************************************/
  /*                   STEP-BY-STEP INTEGRATION METHODS                    */
  /*************************************************************************/
  
  private void copyCheckoutSdkJar() throws CheckoutSdkException {
    
    // Get the checkout-sdk.jar resource
    InputStream source = null;
    
    source = getClass().getResourceAsStream("/resources/checkout-sdk.jar");

    if (source == null) {
      throw new CheckoutSdkException("Could not find checkout-sdk.jar");
    }
    
    // Get the checkout-sdk.jar path
    String path = settings.getWebInfDirectory().getPath() +
        "/lib/checkout-sdk.jar";
    File dest = new File(path);

    // Write the file
    try {
      CheckoutFileWriter.writeFileFromStream(source, dest);
    } 
    catch (IOException ex) {
      throw new CheckoutSdkException("Could not write checkout-sdk.jar");
    }
 
    // Add checkout-sdk.jar to the project's classpath
    // TODO: Get this to work!  I can't find a way to do this yet...
    // FileObject f = settings.getProject().getProjectDirectory();

  }
  
  private void writeModifiedWebXml() throws WebXmlException {
    
    // Get the source and destination
    String source = settings.getModifiedWebXml();
    File dest = settings.getWebXmlFile();
    
    // Write the file
    try {
      CheckoutFileWriter.writeFileFromString(source, dest);
    } 
    catch (IOException ex) {
      throw new WebXmlException("Could not write web.xml");
    }
  }
  
  private void createCheckoutConfigXml() throws CheckoutConfigException {
    
    // Get the source and destination
    String source = settings.getConfigManager().getBody();
    File dest = new File(settings.getConfigManager().getOutputLocation());
    
    // Write the file
    try {
      CheckoutFileWriter.writeFileFromString(source, dest);
    } catch (IOException ex) {
      throw new CheckoutConfigException("Could not write checkout-config.xml");
    }
  }
  
  private void copySamplesJsps() throws SamplesJspException {
    // Get the sample directory provided by the user
    File destDirectory = settings.getSamplesDirectory();
    
    // Get the sample names
    String dir = "/resources/samples.jar";
    HashMap<String, String> strings = null;
    try {
      strings = getFilesFromJar(dir);
    } catch (IOException ex) {
      throw new SamplesJspException("Could not read samples.jar");
    }
    
    if (strings != null) {
      // Get the file names
      Object[] keys = strings.keySet().toArray();
      
      // Loop through each of the files
      for (int i=0; i<keys.length; i++) {
        // Get the source
        String source = strings.get(keys[i]);
        
        // Get the destination
        String path = destDirectory.getPath() + "/" + keys[i];
        File dest = new File(path);
        
        // Write the file
        try {
          CheckoutFileWriter.writeFileFromString(source, dest);
        } catch (IOException ex) {
          throw new SamplesJspException("Could not write " + keys[i]);
        }
      }
    }
  }
  
  /*************************************************************************/
  /*                         JAR READING METHODS                           */
  /*************************************************************************/
  
  private HashMap<String, String> getFilesFromJar(String jarPath) throws IOException {
    HashMap<String,String> strings = new HashMap<String, String>();
    
    // Get the jar input stream
    InputStream resource = getClass().getResourceAsStream(jarPath);
    if (resource == null) {
      throw new IOException(jarPath + " not found.");
    }
    JarInputStream jarStream = new JarInputStream(resource);
    
    // Step through each entry
    JarEntry entry = jarStream.getNextJarEntry();
    while (entry != null) {
      if (!entry.isDirectory()) {
        // Read the jar entry
        StringBuilder builder = new StringBuilder();
        int ch;
        while ((ch = jarStream.read()) >= 0) {
          builder.append((char) ch);
        }
        
        // Store the entry as a string
        strings.put(entry.getName(), builder.toString());
        
        // Move to the next entry
        entry = jarStream.getNextJarEntry();
      }
    }
    
    return strings;
  }
}
