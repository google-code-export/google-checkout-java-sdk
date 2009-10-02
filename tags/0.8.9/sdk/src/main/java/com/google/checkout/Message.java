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

import java.io.Serializable;
import java.util.Date;

import com.google.checkout.util.Utils;

public class Message implements Serializable {
  
  private boolean incoming;
  
  private Date timestamp;
  
  private String type;
  
  private String request;
  
  private String response;
  
  public Message(boolean incoming, Date timestamp, String type,
      String request, String response) {
    this.incoming = incoming;
    this.timestamp = timestamp;
    this.type = type;
    this.request = request;
    this.response = response;
  }
  
  public Date getTimestamp() {
    return timestamp;
  }
  
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getHeader() {
    String ret = Utils.getDateString(getTimestamp()).replace('T', ' ')
        + " : " + type;
    return ret;
  }
  
  public String getBody() {
    StringBuffer sb = new StringBuffer();
    if (incoming) {
      sb.append("You Received:\n\n");
      sb.append(request + "\n");
      sb.append("You Sent:\n\n");
      sb.append(response + "\n");
    } else {
      sb.append("You Sent:\n\n");
      sb.append(request + "\n");
      sb.append("You Received:\n\n");
      sb.append(response + "\n");
    }
    return sb.toString();
  }
}
