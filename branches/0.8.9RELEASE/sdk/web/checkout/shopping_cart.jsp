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

<%@ page import="com.google.checkout.checkout.CheckoutShoppingCartRequest" %>
<%@ page import="com.google.checkout.CheckoutResponse" %>
<%@ page import="com.google.checkout.checkout.ShippingRestrictions" %>
<%@ page import="com.google.checkout.checkout.TaxArea" %>
<%@ page import="com.google.checkout.MerchantInfo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Shopping Cart Builder</title>
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
   
    CheckoutShoppingCartRequest cart;
    String prettyXml = null;
    String responseXml = null;
    String redirectUrl = null;
    
    if (button == null || button.equals("")) {
      cart = new CheckoutShoppingCartRequest(mi);
      session.setAttribute("cart", cart);
      prettyXml = cart.getXmlPretty();
      responseXml = "";
      redirectUrl = "";
    } else if (button.equals("NewCart")) {
      cart = new CheckoutShoppingCartRequest(mi);
      session.setAttribute("cart", cart);
      
      prettyXml = cart.getXmlPretty();
      responseXml = "";
      redirectUrl = "";
    } else if (button.equals("AddItem")) {
      cart = (CheckoutShoppingCartRequest) session.getAttribute("cart");
      
      String itemName = request.getParameter("itemName");
      String itemDescription = request.getParameter("itemDescription");
      String itemPrice = request.getParameter("itemPrice");
      String itemQuantity = request.getParameter("itemQuantity");
      
      cart.addItem(itemName, itemDescription, Float.parseFloat(itemPrice), Integer.parseInt(itemQuantity));
      
      prettyXml = cart.getXmlPretty();
      responseXml = "";
      redirectUrl = "";
    } else if (button.equals("AddTaxByState")) {
      cart = (CheckoutShoppingCartRequest) session.getAttribute("cart");
      
      String taxState = request.getParameter("taxState");
      String taxAmount = request.getParameter("taxAmount");
      String shippingTaxed = request.getParameter("shippingTaxed");
      
      TaxArea ta = new TaxArea();
      ta.addStateCode(taxState);
      cart.addDefaultTaxRule(Double.parseDouble(taxAmount), Boolean.valueOf(shippingTaxed).booleanValue(), ta);
      
      prettyXml = cart.getXmlPretty();
      responseXml = "";
      redirectUrl = "";
    } else if (button.equals("AddShippingByState")) {
      cart = (CheckoutShoppingCartRequest) session.getAttribute("cart");
      
      String shipName = request.getParameter("shipName");
      String shipState = request.getParameter("shipState");
      String shipAmount = request.getParameter("shipAmount");
      
      ShippingRestrictions sr = new ShippingRestrictions();
      sr.addAllowedStateCode(shipState);
      cart.addFlatRateShippingMethod(shipName, Float.parseFloat(shipAmount), sr);
      
      prettyXml = cart.getXmlPretty();
      responseXml = "";
      redirectUrl = "";
    } else if (button.equals("Submit")) {
      
      cart = (CheckoutShoppingCartRequest) session.getAttribute("cart");
      cart = new CheckoutShoppingCartRequest(mi, request.getParameter("cart"));
      session.setAttribute("cart", cart);
      
      CheckoutResponse res = cart.send();
      prettyXml = cart.getXmlPretty();
      responseXml = res.getXmlPretty();
      redirectUrl = res.getRedirectUrl();
    }
    %>
    <p>
    <h1>Shopping Cart Builder</h1>
    <form action="shopping_cart.jsp" method="POST">
      <input type="hidden" name="button" value=""/>      
      <strong>MerchantId: </strong>    
      <%=mi.getMerchantId()%>
      <br/>
      <br/>      
      <table>  
        <tr>
          <td colspan='1'><strong>Build the Cart</strong></td>
        </tr>
        <tr>
          <td>
            <table>
              <tr>
                <td><input type="button" value="Add Item" onClick="postIt('AddItem')"/></td>
                <td>Name:</td><td><input type="text" name="itemName" value="iPod Nano"/></td>
                <td>Description:</td><td><input type="text" name="itemDescription" value="Super small MP3 player."/></td>
                <td>Price:</td><td><input type="text" name="itemPrice" value="100"/></td>
                <td>Quantity:</td><td><input type="text" name="itemQuantity" value="1"/></td>
              </tr>
              <tr>	
                <td><input type="button" value="Add Tax" onClick="postIt('AddTaxByState')"></td>
                <td>State:</td><td><input type="text" name="taxState" value="CA"></td>
                <td>Amount:</td><td><input type="text" name="taxAmount" value="0.08"></td>
                <td>Shipping Taxed:</td>
                <td>
                  <select name="shippingTaxed">
                    <option value ="true">true</option>
                    <option value ="false">false</option>
                  </select>
                </td>
              </tr>
              <tr>
                <td><input type="button" value="Add Ship" onClick="postIt('AddShippingByState')"></td>
                <td>Name:</td><td><input type="text" name="shipName" value="Fed Ex"></td>
                <td>State:</td><td><input type="text" name="shipState" value="CA"></td>
                <td>Amount:</td><td><input type="text" name="shipAmount" value="5"></td>
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
                  <a href="javascript:{postIt('Submit')}"><img src="https://checkout.google.com/buttons/checkout.gif?merchant_id=<%=mi.getMerchantId()%>&w=160&h=43&style=white&variant=text&loc=en_US" border="0"/></a>
                </td>
                <td align="center" valign="top">
                  <input type="button" value="Or Empty the Cart" onClick="postIt('NewCart')"/>
                </td>
              </tr>
              <tr>
                <td align="center">
                  <A HREF="javascript:void(window.open('http://checkout.google.com/seller/what_is_google_checkout.html','whatischeckout','scrollbars=0,resizable=1,directories=0,height=250,width=400'));" OnMouseOver="return window.status = 'What is Google Checkout?'" OnMouseOut="return window.status = ''"><font size="-1">What is Google Checkout?</font></A>
                </td>
              </tr>
              <tr>
            </table>
          </td>
        </tr>
        <tr>
        <td colspan='1'><strong>Response XML</strong></td>
        <tr>
          <td colspan='1'><textarea name="response" cols="145" rows="8"><%=responseXml%></textarea></td>
        </tr>
        <tr>
          <td colspan='1'><a target="_blank" href="<%=redirectUrl%>">Follow redirect (in a new window)</a></td>
        </tr>
      </table>
    </form>
  </body>
</html>