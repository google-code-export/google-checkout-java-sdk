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
<%@ page import="com.google.checkout.orderprocessing.SendBuyerMessageRequest" %>
<%@ page import="com.google.checkout.CheckoutResponse" %>
<%@ page import="com.google.checkout.MerchantInfo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Send Buyer Message</title>
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
    
    SendBuyerMessageRequest messageRequest;
    String orderNumber = request.getParameter("orderNumber") == null ? "" : request.getParameter("orderNumber");
    String message = "message";
    String sendEmail = "";
    
    String prettyXml = null;
    String responseXml = null;
    
    if (button == null || button.equals("")) {
      messageRequest = new SendBuyerMessageRequest(mi);
      session.setAttribute("messageRequest", messageRequest);
      prettyXml = messageRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("NewRequest")) {
      messageRequest = new SendBuyerMessageRequest(mi);
      session.setAttribute("messageRequest", messageRequest);
      prettyXml = messageRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("AddValues")) {
      messageRequest = (SendBuyerMessageRequest) session.getAttribute("messageRequest");
      
      orderNumber = request.getParameter("orderNumber");
      message = request.getParameter("message");
      sendEmail = request.getParameter("sendemail");
      
      messageRequest.setGoogleOrderNo(orderNumber);
      messageRequest.setMessage(message);
      messageRequest.setSendEmail(Boolean.valueOf(sendEmail).booleanValue());
      
      prettyXml = messageRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("Submit")) {
      messageRequest = (SendBuyerMessageRequest) session.getAttribute("messageRequest");
      CheckoutResponse res = messageRequest.send();
      
      prettyXml = messageRequest.getXmlPretty();
      responseXml = res.getXmlPretty();
      
      GoogleOrder order = GoogleOrder.findOrCreate(mi.getMerchantId(), orderNumber);
      order.addOutgoingMessage(new Date(), "send-buyer-message", prettyXml, responseXml);
    }
    %>
    <p>
    <h1>Send Buyer Message</h1>
    <form action="sendbuyermessage.jsp" method="POST">
      <input type="hidden" name="button" value=""/>      
      <strong>MerchantId: </strong>    
      <%=mi.getMerchantId()%>
      <br/>
      <br/>      
      <table>  
        <tr>
          <td colspan='1'><strong>Enter the Message Details</strong></td>
        </tr>
        <tr>
          <td>
            <table>
              <tr>
                <td><input type="button" value="Set Values" onClick="postIt('AddValues')"/></td>
                <td>Order Number:</td><td><input type="text" name="orderNumber" value="<%=orderNumber %>"/></td>
                <td>Message:</td><td><input type="text" name="message" value="<%=message %>"/></td>
                <td>Send Email:</td>
                <td>
                  <jsp:include page="shipping-fragment.jsp">
                    <jsp:param name="sendEmail" value="<%=sendEmail%>"/>
                  </jsp:include>                  
                </td>     
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan='1'><strong>Request XML</strong></td>
        </tr>
        <tr>
          <td colspan='1'><textarea name="cart" cols="145" rows="20"><%=prettyXml%></textarea></td>
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
          <td colspan='1'><textarea name="response" cols="145" rows="8"><%=responseXml%></textarea></td>
        </tr>
      </table>
    </form>
  </body>
</html>