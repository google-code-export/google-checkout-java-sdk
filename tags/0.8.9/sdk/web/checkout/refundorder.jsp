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
<%@ page import="com.google.checkout.orderprocessing.RefundOrderRequest" %>
<%@ page import="com.google.checkout.CheckoutResponse" %>
<%@ page import="com.google.checkout.MerchantInfo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Refund Order</title>
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
    
    RefundOrderRequest refundRequest;
    String orderNumber = request.getParameter("orderNumber") == null || request.getParameter("orderNumber").equals("null") ? "" 
        : request.getParameter("orderNumber");
    float amount = (request.getParameter("amount") == null || request.getParameter("amount").equals("null")) ? 0f 
        : Float.parseFloat(request.getParameter("amount"));

    String comment = "comment";
    String reason = "reason";
    
    String prettyXml = null;
    String responseXml = null;
    
    if (button == null || button.equals("")) {
      refundRequest = new RefundOrderRequest(mi);
      session.setAttribute("refundRequest", refundRequest);
      prettyXml = refundRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("NewRequest")) {
      refundRequest = new RefundOrderRequest(mi);
      session.setAttribute("refundRequest", refundRequest);
      prettyXml = refundRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("AddValues")) {
      refundRequest = (RefundOrderRequest) session.getAttribute("refundRequest");
      
      orderNumber = request.getParameter("orderNumber");
      amount = Float.parseFloat(request.getParameter("amount"));
      comment = request.getParameter("comment");
      reason = request.getParameter("reason");
      
      refundRequest.setGoogleOrderNo(orderNumber);
      refundRequest.setAmount(amount);
      refundRequest.setComment(comment);
      refundRequest.setReason(reason);
      
      prettyXml = refundRequest.getXmlPretty();
      responseXml = "";
    } else if (button.equals("Submit")) {
      refundRequest = (RefundOrderRequest) session.getAttribute("refundRequest");
      CheckoutResponse res = refundRequest.send();
      
      prettyXml = refundRequest.getXmlPretty();
      responseXml = res.getXmlPretty();
      
      GoogleOrder order = GoogleOrder.findOrCreate(mi.getMerchantId(), orderNumber);
      order.addOutgoingMessage(new Date(), "refund-order", prettyXml, responseXml);
    }
    %>
    <p>
    <h1>Refund Order</h1>
    <form action="refundorder.jsp" method="POST">
      <input type="hidden" name="button" value=""/>      
      <strong>MerchantId: </strong>    
      <%=mi.getMerchantId()%>
      <br/>
      <br/>      
      <table>  
        <tr>
          <td colspan='1'><strong>Enter the Refund Details</strong></td>
        </tr>
        <tr>
          <td>
            <table>
              <tr>
                <td><input type="button" value="Set Values" onClick="postIt('AddValues')"/></td>
                <td>Order Number:</td><td><input type="text" name="orderNumber" value="<%=orderNumber %>"/></td>
                <td>Amount:</td><td><input type="text" name="amount" value="<%=amount %>"/></td>
                <td>Comment:</td><td><input type="text" name="comment" value="<%=comment %>"/></td>
                <td>Reason:</td><td><input type="text" name="reason" value="<%=reason %>"/></td>
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