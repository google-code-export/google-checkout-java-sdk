/*******************************************************************************
 * Copyright (C) 2009 Google Inc.
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
package com.google.checkout.sdk.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Easily overridable servlet request for testing.
 *
 */
public class NullHttpServletRequest implements HttpServletRequest {

  @Override
  public String getAuthType() {
    return null;
  }

  @Override
  public String getContextPath() {
    return null;
  }

  @Override
  public Cookie[] getCookies() {
    return null;
  }

  @Override
  public long getDateHeader(String name) {
    return 0;
  }

  @Override
  public String getHeader(String name) {
    return null;
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    return null;
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    return null;
  }

  @Override
  public int getIntHeader(String name) {
    return 0;
  }

  @Override
  public String getMethod() {
    return null;
  }

  @Override
  public String getPathInfo() {
    return null;
  }

  @Override
  public String getPathTranslated() {
    return null;
  }

  @Override
  public String getQueryString() {
    return null;
  }

  @Override
  public String getRemoteUser() {
    return null;
  }

  @Override
  public String getRequestURI() {
    return null;
  }

  @Override
  public StringBuffer getRequestURL() {
    return null;
  }

  @Override
  public String getRequestedSessionId() {
    return null;
  }

  @Override
  public String getServletPath() {
    return null;
  }

  @Override
  public HttpSession getSession() {
    return null;
  }

  @Override
  public HttpSession getSession(boolean create) {
    return null;
  }

  @Override
  public Principal getUserPrincipal() {
    return null;
  }

  @Override
  public boolean isRequestedSessionIdFromCookie() {
    return false;
  }

  @Override
  public boolean isRequestedSessionIdFromURL() {
    return false;
  }

  @Override
  @Deprecated
  public boolean isRequestedSessionIdFromUrl() {
    return false;
  }

  @Override
  public boolean isRequestedSessionIdValid() {
    return false;
  }

  @Override
  public boolean isUserInRole(String role) {
    return false;
  }

  @Override
  public Object getAttribute(String name) {
    return null;
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return null;
  }

  @Override
  public String getCharacterEncoding() {
    return null;
  }

  @Override
  public int getContentLength() {
    return 0;
  }

  @Override
  public String getContentType() {
    return null;
  }

  /**
   * @throws IOException
   */
  @Override
  public ServletInputStream getInputStream() throws IOException {
    return null;
  }

  @Override
  public String getLocalAddr() {
    return null;
  }

  @Override
  public String getLocalName() {
    return null;
  }

  @Override
  public int getLocalPort() {
    return 0;
  }

  @Override
  public Locale getLocale() {
    return null;
  }

  @Override
  public Enumeration<String> getLocales() {
    return null;
  }

  @Override
  public String getParameter(String name) {
    return null;
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    return null;
  }

  @Override
  public Enumeration<String> getParameterNames() {
    return null;
  }

  @Override
  public String[] getParameterValues(String name) {
    return null;
  }

  @Override
  public String getProtocol() {
    return null;
  }

  /**
   * @throws IOException
   */
  @Override
  public BufferedReader getReader() throws IOException {
    return null;
  }

  @Override
  @Deprecated
  public String getRealPath(String path) {
    return null;
  }

  @Override
  public String getRemoteAddr() {
    return null;
  }

  @Override
  public String getRemoteHost() {
    return null;
  }

  @Override
  public int getRemotePort() {
    return 0;
  }

  @Override
  public RequestDispatcher getRequestDispatcher(String path) {
    return null;
  }

  @Override
  public String getScheme() {
    return null;
  }

  @Override
  public String getServerName() {
    return null;
  }

  @Override
  public int getServerPort() {
    return 0;
  }

  @Override
  public boolean isSecure() {
    return false;
  }

  @Override
  public void removeAttribute(String name) {
  }

  @Override
  public void setAttribute(String name, Object o) {
  }

  /**
   * @throws UnsupportedEncodingException
   */
  @Override
  public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
  }
}
