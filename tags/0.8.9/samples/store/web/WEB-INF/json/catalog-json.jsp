<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.google.checkout.samples.store.model.*,com.google.checkout.samples.store.web.*,com.google.checkout.samples.store.web.mvc.*" %>
<%
  String category = "Simon Stuff";
  if (request != null) {
    category = request.getParameter(WebConstants.CATEGORY);
  }
  Category cat = Category.getCategory(category);
  
  if (cat != null) {
    ModelFacade mf = new ModelFacade();
    ProductList products = mf.getProductsFor(cat);
    String json = JsonConverter.toJson(products);
    out.write(json);
  }
%>