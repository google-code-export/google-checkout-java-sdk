<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.google.checkout.samples.store.model.*,com.google.checkout.samples.store.web.*" %>
<% 
  String category = "Birds 0";
  Category cat = Category.getCategory(category);
  // TODO: remove this
  if (category == null) {
    cat = DummyData.BIRDS0_CATEGORY;
  }
  if (cat != null) {
    ModelFacade mf = new ModelFacade();
    ProductList products = mf.getProductsFor(cat);
    String json = JsonConverter.toJson(products);
    out.write(json);
  }
%>