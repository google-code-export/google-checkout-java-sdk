package com.google.checkout.util;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {

  public void testReplaceMultipleStringsStringString() {
    fail("Not yet implemented");
  }

  public void testReplaceMultipleStringsStringStringString() {
    fail("Not yet implemented");
  }

  public void testReplaceMultipleStringsStringStringArray() {
    fail("Not yet implemented");
  }

  public void testReplaceXMLReservedChars() {
    assertEquals("abc&#x3c;def", StringUtil.replaceXMLReservedChars("abc<def"));
    assertEquals("abc&#x3c;d&#x3e;ef", StringUtil.replaceXMLReservedChars("abc<d>ef"));
    assertEquals("abc&#x26;def", StringUtil.replaceXMLReservedChars("abc&def"));
  }

  public void testRemoveChar() {
    fail("Not yet implemented");
  }

}
