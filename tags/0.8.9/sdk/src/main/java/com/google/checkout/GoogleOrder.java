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

package com.google.checkout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class GoogleOrder implements Serializable {
  private String merchantId;
  
  private String orderNumber;
  
  private String lastFinStatus;
  
  private String lastFulStatus;
  
  private Date lastUpdateTime;
  
  private String buyerEmail;
  
  private String orderAmount;
  
  private Collection events = new ArrayList();
  
  static String dir = System.getProperty("java.io.tmpdir");
  
  static File fDir = new File(dir);
  
  public static GoogleOrder findOrCreate(String merchantId, String orderNumber)
    throws CheckoutException {
    
    GoogleOrder ret;
    String fileName = "GCO_" + merchantId + "_" + orderNumber + ".ser";
    File file = new File(fDir, fileName);
    
    if (file.exists()) {
      return readFromFile(file);
    }
    
    ret = new GoogleOrder(merchantId, orderNumber);
    return ret;
  }
  
  /**
   * Read a GoogleOrder from a file
   * 
   * @param file The file from which to read the GoogleOrder
   * @return A GoogleOrder containing the data read from file
   * @throws com.google.checkout.CheckoutException if there was an 
   * error reading the file
   */
  public static GoogleOrder readFromFile(File file) throws CheckoutException {
    
    GoogleOrder ret;
 
    String fileName = file.getName();
 
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
      ret = (GoogleOrder) in.readObject();
      in.close();
      return ret;
    } catch (FileNotFoundException ex) {
        throw new CheckoutException("Could not find file " + fileName);
    } catch (IOException ex) {
        throw new CheckoutException("Encountered the following error when attempting" +
          "to read file " + fileName + ": " + ex.getMessage());
    } catch (ClassNotFoundException ex) {
        throw new CheckoutException("Encountered the following error when attempting" +
          "to read file " + fileName + ": " + ex.getMessage());        
    }
  }
  
  public static GoogleOrder[] findAll(String merchantId) throws CheckoutException {
    
    File[] files = fDir.listFiles(new OrderFilter(merchantId));
    GoogleOrder[] ret = new GoogleOrder[files.length];
    
    for (int i = 0; i < files.length; i++) {
      ret[i] = readFromFile(files[i]);
    }
    return ret;
  }
  
  public String getBuyerEmail() {
    return buyerEmail;
  }
  
  public void setBuyerEmail(String buyerEmail) {
    this.buyerEmail = buyerEmail;
  }
  
  public String getLastFinStatus() {
    return lastFinStatus;
  }
  
  public void setLastFinStatus(String lastFinStatus) {
    this.lastFinStatus = lastFinStatus;
  }
  
  public String getLastFulStatus() {
    return lastFulStatus;
  }
  
  public void setLastFulStatus(String lastFulStatus) {
    this.lastFulStatus = lastFulStatus;
  }
  
  public Date getLastUpdateTime() {
    return lastUpdateTime;
  }
  
  public void setLastUpdateTime(Date lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }
  
  public String getMerchantId() {
    return merchantId;
  }
  
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }
  
  public Collection getEvents() {
    return events;
  }
  
  public void setNotifications(Collection events) {
    this.events = events;
  }
  
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }
  
  public GoogleOrder(String merchantId, String orderNumber) {
    this.merchantId = merchantId;
    this.orderNumber = orderNumber;
  }
  
  public synchronized void addIncomingMessage(Date timestamp, String type,
      String request, String response) throws CheckoutException {
    
    lastUpdateTime = timestamp;
    events.add(new Message(true, timestamp, type, request, response));
    save();
  }
  
  public synchronized void addOutgoingMessage(Date timestamp, String type,
      String request, String response) throws CheckoutException {
    events.add(new Message(false, timestamp, type, request, response));
    save();
  }
  
  /**
   * 
   * @throws com.google.checkout.CheckoutException if the path,
   * where the file is to be saved, is not valid
   */
  private void save() throws CheckoutException {
    String fileName = "GCO_" + merchantId + "_" + orderNumber + ".ser";
    
    try {
      ObjectOutput out = new ObjectOutputStream(
        new FileOutputStream(new File(dir, fileName)));
      out.writeObject(this);
      out.close();
    } catch (FileNotFoundException ex) {
        throw new CheckoutException("Could not find file " + fileName);
    } catch (IOException ex) {
        throw new CheckoutSystemException("Encountered the following error when attempting" +
          "to save file " + fileName + ": " + ex.getMessage());
    }
  }
  
  public String getOrderNumber() {
    return orderNumber;
  }
  
  public static class OrderFilter implements FilenameFilter {
    String mid;
    
    public OrderFilter(String mid) {
      this.mid = mid;
    }
    
    public boolean accept(File file, String name) {
      if (name.startsWith("GCO_" + mid)) {
        return true;
      }
      return false;
    }
  }
  
  public String getOrderAmount() {
    return orderAmount;
  }
  
  public void setOrderAmount(String orderAmount) {
    this.orderAmount = orderAmount;
  }
}
