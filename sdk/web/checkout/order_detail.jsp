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

<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>

<%@ page import="com.google.checkout.GoogleOrder"%>
<%@ page import="com.google.checkout.Message"%>
<%@ page import="com.google.checkout.util.EncodeHelper"%>
<%@ page import="com.google.checkout.MerchantConstants"%>
<%@ page import="com.google.checkout.MerchantConstantsFactory"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Order Detail</title>
</head>
<body>
<script language="JavaScript">
  function toggle(e) {
    if(document.getElementById('div_'+e).style.display == 'none') {
      a = document.getElementById('ta_'+e).value.split('\n');
      document.getElementById('ta_'+e).rows = a.length;
      document.getElementById('div_'+e).style.display='block';    
    }
    else {
      document.getElementById('div_'+e).style.display='none';
    }
  };
  
</script>
<h1>Order Detail</h1>
<%
  MerchantConstants mc = MerchantConstantsFactory.getMerchantConstants();
  String orderNumber = request.getParameter("orderNumber");
  
  GoogleOrder order = GoogleOrder.findOrCreate(mc.getMerchantId(), orderNumber);
  Collection messages = order.getEvents();
  Iterator it = messages.iterator();
  Message m;
  int count = 0;
  while( it.hasNext() ) {
	  count++;
	  m = (Message) it.next();
	  String body = m.getBody();
 	  String header = EncodeHelper.EscapeXmlChars(m.getHeader());
%>
<a href="javascript:toggle('<%=count %>');"><strong><%=header%></strong></a>
<br />
<div id="div_<%=count %>" style='display:none;'><textarea
	id="ta_<%=count %>" readonly="true" cols="145" rows="20"><%=body%></textarea>
</div>
<%
  }
%>
</body>
</html>
