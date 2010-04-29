/*******************************************************************************
 * Copyright (C) 2010 Google Inc.
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
package com.google.checkout.sdk.notifications;

import com.google.checkout.sdk.domain.OrderSummary;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Common interface implemented by all Google Checkout notifications.
 *
*
 */
public interface Notification {
  String getGoogleOrderNumber();
  void setGoogleOrderNumber(String googleOrderNumber);
  
  OrderSummary getOrderSummary();
  void setOrderSummary(OrderSummary orderSummary);
  
  String getSerialNumber();
  void setSerialNumber(String serialNumber);
  
  XMLGregorianCalendar getTimestamp();
  void setTimestamp(XMLGregorianCalendar value);
}
