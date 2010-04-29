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

package com.google.checkout.sdk.util;

import com.google.checkout.sdk.commands.CheckoutException;
import com.google.checkout.sdk.domain.ItemId;
import com.google.checkout.sdk.domain.ObjectFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Various XML utilities.
 *
*
 * @version 1.1 - ksim - March 6th, 2007 - Added functions regarding streaming
 * @version 1.2 - ksim - March 10th, 2007 - Added functions regarding DOM
 *          manipulation
 */

public class Utils {
  public static final String SEND_AND_RECEIVE_DEBUGGING_STRING =
    "Response Code:\t{0}\n"
    + "With Url:\t[ {1} ]\n"
    + "Request:\t[ {2} ]\n"
    + "Response:\t[ {3} ]";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String XML_MIME_TYPE = "application/xml";

  private static final Logger logger = Logger.getLogger(Utils.class.getName());
  private static final SimpleDateFormat sdf = new SimpleDateFormat(
      "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
  private static final JAXBContext context = makeJaxbContext();

  private static JAXBContext makeJaxbContext() {
    try {
      return JAXBContext.newInstance("com.google.checkout.sdk.domain");
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static ObjectFactory objectFactory() {
    return new ObjectFactory();
  }

  @SuppressWarnings("unchecked")
  public static <T> JAXBElement<T> fromXML(InputStream is) throws JAXBException {
    return (JAXBElement<T>)context.createUnmarshaller().unmarshal(is);
  }

  public static void toXML(JAXBElement<?> jaxbElement, OutputStream os) {
    JAXB.marshal(jaxbElement, os);
  }

  @SuppressWarnings("unchecked")
  public static <T> JAXBElement<T> fromXML(String xmlString) throws JAXBException {
    return (JAXBElement<T>)context.createUnmarshaller().unmarshal(new StringReader(xmlString));
  }

  public static String toXML(JAXBElement<?> jaxbElement) {
    StringWriter stringWriter = new StringWriter();
    JAXB.marshal(jaxbElement, stringWriter);
    return stringWriter.toString();
  }

  /**
   * @param socket An opened connection to a listening checkout service with all
   *    headers already set. It must not have been otherwise used or processed.
   *    It will always be disconnected when this method returns.
   * @param xmlString The data to send across the wire.
   * @return The string as returned by the server
   * @throws HttpUrlException If any operation fails
   */
  public static String postXML(HttpURLConnection socket, String xmlString) throws HttpUrlException {
    try {
      Exception underlying = null;
      try {
        Writer writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write(xmlString);
        writer.close();
        if (socket.getResponseCode() == 200) {
          String slurp = slurp(socket.getInputStream());
          logger.log(Level.INFO, "Received from:\t{0}\nContent:\t{1}",
              new Object[]{socket.getURL(), slurp});
          return slurp;
        }
      } catch (IOException e) {
        underlying = e;
      }
      throw makeUrlException(socket, xmlString, underlying);
    } finally {
      socket.disconnect();
    }
  }

  @SuppressWarnings("unchecked")
  public static <V> V postJAXB(
      HttpURLConnection socket, JAXBElement<?> jaxbElement) throws CheckoutException {
    try {
      Exception underlying = null;
      try {
        toXML(jaxbElement, socket.getOutputStream());
        if (socket.getResponseCode() == 200) {
          JAXBElement<V> response = fromXML(socket.getInputStream());
          logger.log(Level.INFO, SEND_AND_RECEIVE_DEBUGGING_STRING,
              new Object[]{200, socket.getURL(), jaxbElement.getValue(), response.getValue()});
          return response.getValue();
        }
      } catch (IOException e) {
        underlying = e;
      } catch (JAXBException e) {
        underlying = e;
      }
      throw makeUrlException(socket, jaxbElement.getValue(), underlying);
    } finally {
      socket.disconnect();
    }
  }
  
  public static String slurp(InputStream from) throws IOException {
    if (from == null) {
      return "";
    }
    final char[] buffer = new char[1024];
    StringBuilder out = new StringBuilder();
    Reader in = new InputStreamReader(from, "UTF-8");
    int read;
    do {
      read = in.read(buffer, 0, buffer.length);
      if (read>0) {
        out.append(buffer, 0, read);
      }
    } while (read>=0);
    return out.toString();
  }
  
  public static String getDateString(XMLGregorianCalendar date) {
    if (date == null) {
      return "null";
    }
    
    TimeZone tz = TimeZone.getTimeZone("UTC");
    sdf.setTimeZone(tz);
    String ret = sdf.format(date);
    
    return ret.substring(0, ret.length() - 5) + "Z";
  }

  /**
   * Detects whether the "notification" received is actually a ping indicating
   * Google Checkout must be contacted to fetch the notification.
   * This library can operate in serial number request mode, where the client
   * doesn't need a certificate to authenticate themselves; instead, Google
   * Checkout doesn't post the entire notification but simply indicates that
   * one should be fetched.
   * @return True if a notification should be fetched rather than parsed.
   */
  public static boolean isSerialNumberRequest(HttpServletRequest request) {
    String mimeType = request.getHeader(CONTENT_TYPE);
    if (mimeType == null || mimeType.isEmpty()) {
      return true;
    }
    return !mimeType.toLowerCase().contains(XML_MIME_TYPE);
  }
  
  public static ItemId makeItemId(String itemId) { 
    ItemId id = new ItemId();
    id.setMerchantItemId(itemId);
    return id;
  }
  
  public static BigDecimal normalize(BigDecimal value) {
    value = value.stripTrailingZeros();
    if (value.scale() < 0) {
      value = value.setScale(0);
    }
    return value;
  }

  private static HttpUrlException makeUrlException(HttpURLConnection hurlc,
      Object triedToSend,
      Throwable possibleUnderlying) {
    URL url = hurlc.getURL();
    int responseCode;
    String slurp;
    try {
      slurp = Utils.slurp(hurlc.getErrorStream());
    } catch (IOException e) {
      if (possibleUnderlying != null) {
        logger.log(Level.SEVERE,
            "Masking exception while processing communication error from checkout:", e);
      } else {
        possibleUnderlying = e;
      }
      slurp = "<Error reading cause of error>";
    }    
    try {
      responseCode = hurlc.getResponseCode();
    } catch (IOException e) {
      if (possibleUnderlying != null) {
        logger.log(Level.SEVERE,
            "Masking exception while processing communication error from checkout:", e);
      } else {
        possibleUnderlying = e;
      }
      responseCode = Integer.MAX_VALUE;
    }
    return new HttpUrlException(url, responseCode,
        triedToSend, slurp, possibleUnderlying);
  }
}
