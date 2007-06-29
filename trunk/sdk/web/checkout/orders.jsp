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

<%@ page import="com.google.checkout.GoogleOrder"%>
<%@ page import="com.google.checkout.MerchantConstants"%>
<%@ page import="com.google.checkout.MerchantConstantsFactory"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Orders</title>
</head>
<body>
<script language="JavaScript">
  function go(i) {
      s = document.getElementById('act_'+i);
      u = s.options[s.selectedIndex].value;
      window.location = u;
  };
</script>
<h1>Orders</h1>
<form>
<table border="1">
	<tr>
		<td><b>Google Order Number</b></td>
		<td><b>Buyer Email Address</b></td>
		<td><b>Financial Status</b></td>
		<td><b>Fulfilment Status</b></td>
		<td><b>Order Total</b></td>
		<td><b>Action</b></td>

	</tr>
	<%
	      MerchantConstants mc = MerchantConstantsFactory.getMerchantConstants();

	      GoogleOrder[] orders = GoogleOrder.findAll(mc.getMerchantId());
	      for (int i = 0; i < orders.length; i++) {
	        String orderNumber = orders[i].getOrderNumber();
	        String finStatus = orders[i].getLastFinStatus();
	        String fulStatus = orders[i].getLastFulStatus();
	        String buyer = orders[i].getBuyerEmail();
	        String amount = orders[i].getOrderAmount();
	%>
	<tr>
		<td><a href="order_detail.jsp?orderNumber=<%=orderNumber%>"><%=orderNumber%></a></td>
		<td><%=buyer%></td>
		<td><%=finStatus%></td>
		<td><%=fulStatus%></td>
		<td><%=amount%></td>
		<td><select id="act_<%=i %>">
			<option
				value="chargeorder.jsp?orderNumber=<%=orderNumber%>&amount=<%=amount %>">Charge
			Order</option>
			<option
				value="refundorder.jsp?orderNumber=<%=orderNumber%>&amount=<%=amount %>">Refund
			Order</option>
			<option value="cancelorder.jsp?orderNumber=<%=orderNumber%>">Cancel
			Order</option>
			<option value="authorizeorder.jsp?orderNumber=<%=orderNumber%>">Authorize
			Order</option>
			<option value="processorder.jsp?orderNumber=<%=orderNumber%>">Process
			Order</option>
			<option
				value="addmerchantordernumber.jsp?orderNumber=<%=orderNumber%>">Add
			Merchant Order Number</option>
			<option value="deliverorder.jsp?orderNumber=<%=orderNumber%>">Deliver
			Order</option>
			<option value="addtrackingdata.jsp?orderNumber=<%=orderNumber%>">Add
			Tracking Data</option>
			<option value="sendbuyermessage.jsp?orderNumber=<%=orderNumber%>">Send
			Buyer Message</option>
			<option value="archiveorder.jsp?orderNumber=<%=orderNumber%>">Archive
			Order</option>
			<option value="unarchiveorder.jsp?orderNumber=<%=orderNumber%>">Unarchive
			Order</option>
		</select> <input type="button" value="Go" onClick="javascript:go(<%=i %>)">
		</td>
	</tr>
	<%
	}
	%>
</table>
</form>
</body>
</html>