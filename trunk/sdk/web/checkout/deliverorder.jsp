<!-- 
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
-->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Date" %>
<%@ page import="com.google.checkout.GoogleOrder" %>
<%@ page import="com.google.checkout.MerchantInfo" %>
<%@ page import="com.google.checkout.orderprocessing.DeliverOrderRequest" %>
<%@ page import="com.google.checkout.CheckoutResponse" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Deliver Order</title>
  </head>
  <body>
    <script language="JavaScript">
  function postIt(v) {
     document.forms[0].button.value=v;
     document.forms[0].submit();
  };
    </script>
    <%
    String button = request.getParameter("button");
    
    MerchantInfo mi = (MerchantInfo) getServletContext().getAttribute("merchant-info");
    
    DeliverOrderRequest deliverRequest;
    String orderNumber = request.getParameter("orderNumber") == null ? "" : request.getParameter("orderNumber");
    String carrier = "";
    String trackingNumber = "";
    String sendEmail = "";
    
    String prettyXml = null;
    String responseXml = null;
    
    if (button == null || button.equals("")) {
      deliverRequest = new DeliverOrderRequest(mi);
      session.setAttribute("deliverRequest", deliverRequest);
      prettyXml = deliverRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("NewRequest")) {
      deliverRequest = new DeliverOrderRequest(mi);
      session.setAttribute("deliverRequest", deliverRequest);
      prettyXml = deliverRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("AddValues")) {
      deliverRequest = (DeliverOrderRequest) session.getAttribute("deliverRequest");
      
      orderNumber = request.getParameter("orderNumber");
      carrier = request.getParameter("carrier");
      trackingNumber = request.getParameter("trackingnumber");
      sendEmail = request.getParameter("sendemail");
      
      deliverRequest.setGoogleOrderNo(orderNumber);
      deliverRequest.setCarrier(carrier);
      deliverRequest.setTrackingNo(trackingNumber);
      deliverRequest.setSendEmail(Boolean.valueOf(sendEmail).booleanValue());
      
      prettyXml = deliverRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("Submit")) {
      deliverRequest = (DeliverOrderRequest) session.getAttribute("deliverRequest");
      CheckoutResponse res = deliverRequest.send();
      
      prettyXml = deliverRequest.getXmlPretty();
      responseXml = res.getXmlPretty();
      
      GoogleOrder order = GoogleOrder.findOrCreate(mi.getMerchantId(), orderNumber);
      order.addOutgoingMessage(new Date(), "deliver-order", prettyXml, responseXml);
    }
    %>
    <p>
    <h1>Deliver Order</h1>
    <form action="deliverorder.jsp" method="POST">
      <input type="hidden" name="button" value=""/>      
      <strong>MerchantId: </strong>    
      <%=mi.getMerchantId()%>
      <br/>
      <br/>      
      <table>  
        <tr>
          <td colspan='1'><strong>Enter the Delivery Details</strong></td>
        </tr>
        <tr>
          <td>
            <table>
              <tr>
                <td><input type="button" value="Set Values" onClick="postIt('AddValues')"/></td>
                <td>Order Number:</td><td><input type="text" name="orderNumber" value="<%=orderNumber %>"/></td>
                <td>Carrier:</td>
                <td>
                  <select name="carrier" value="<%=carrier %>">
                    <option value ="DHL">DHL</option>
                    <option value ="FedEx">FedEx</option>	      	  
                    <option value ="UPS">UPS</option>	      	  
                    <option value ="USPS">USPS</option>	      	  
                    <option value ="Other">Other</option>	      	  
                  </select>
                </td>
                <td>Tracking Number:</td><td><input type="text" name="trackingnumber" value="<%=trackingNumber %>"/></td>
                <td>Send Email:</td>
                <td>
                  <select name="sendemail" value="<%=sendEmail %>">
                    <option value ="true">true</option>
                    <option value ="false">false</option>
                  </select>
                </td>     
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan='1'><strong>Request XML</strong></td>
        </tr>
        <tr>
          <td colspan='1'><textarea name="cart" readonly="true" cols="145" rows="20"><%=prettyXml%></textarea></td>
        </tr>
        <tr>
          <td align="left">
            <table>
              <tr>
                <td align="center">
                  <input type="button" value="Submit" onClick="postIt('Submit')"/>
                </td>
                <td align="center" valign="top">
                  <input type="button" value="Or Create a New Request" onClick="postIt('NewRequest')"/>
                </td>
              </tr>
              <tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan='1'><strong>Response XML</strong></td>
        </tr>
        <tr>
          <td colspan='1'><textarea name="response" readonly="true" cols="145" rows="8"><%=responseXml%></textarea></td>
        </tr>
      </table>
    </form>
  </body>
</html>