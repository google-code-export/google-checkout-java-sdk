<%-- 
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
--%>
<%-- 
 * This JSP fragment page provides common HTML fragments for various shipping
 * related operations.  
 * To use this page, invoke it in a JSP include and specify the needed 
 * parameters. For example: 
   // Following invocation generates a <select> tag with various shipping
   // options
   <jsp:include page="shipping-fragment.jsp">
     <jsp:param name="carrier" value="<%=carrier%>"/>
   </jsp:include>                  

   // Following invocation generates a <select> tag with a sendemail option
   <jsp:include page="shipping-fragment.jsp">
     <jsp:param name="sendEmail" value="<%=sendEmail%>"/>
   </jsp:include>      
 * @author inder            
--%>
<%!
  public String showValueWithSelection(String value, String expected) {
    String returnValue = "value=\"" + value + "\""; 
    if (value.equalsIgnoreCase(expected)) { 
      returnValue += " selected=\"selected\""; 
    }
    return returnValue;
  }
%>
<%
  String carrier = request.getParameter("carrier");
  if (carrier != null) { 
%>
<select name="carrier">
	<option <%=showValueWithSelection("DHL", carrier)%>>DHL</option>
	<option <%=showValueWithSelection("FedEx", carrier)%>>FedEx</option>
	<option <%=showValueWithSelection("UPS", carrier)%>>UPS</option>
	<option <%=showValueWithSelection("USPS", carrier)%>>USPS</option>
	<option <%=showValueWithSelection("Other", carrier)%>>Other</option>
</select>
<%
  }
  
  String sendEmail = request.getParameter("sendEmail");
  if (sendEmail != null) {
%>
<select name="sendemail">
  <option <%=showValueWithSelection("true", carrier)%>>true</option>
  <option <%=showValueWithSelection("false", carrier)%>>false</option>
</select>
<%    
  }
%>
