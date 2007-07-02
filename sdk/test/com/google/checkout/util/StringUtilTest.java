package com.google.checkout.util;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {

  public void testReplaceMultipleStringsStringString() {
    assertEquals("abcdef", StringUtil.replaceMultipleStrings("abc{0}", "def"));
  }

  public void testReplaceMultipleStringsStringStringString() {
    assertEquals("abcdefghi", StringUtil.replaceMultipleStrings("abc{0}{1}", "def", "ghi"));
  }

  public void testReplaceMultipleStringsStringStringArray() {
    String[] replaceStr = {"def", "ghi"};
    assertEquals("abcdefghi", StringUtil.replaceMultipleStrings("abc{0}{1}", replaceStr));
  }

  public void testReplaceXMLReservedChars() {
    assertEquals("abc&#x3c;def", StringUtil.replaceXMLReservedChars("abc<def"));
    assertEquals("abc&#x3c;d&#x3e;ef", StringUtil.replaceXMLReservedChars("abc<d>ef"));
    assertEquals("abc&#x26;def", StringUtil.replaceXMLReservedChars("abc&def"));
  }

  public void testRemoveChar() {
    assertEquals("abdef", StringUtil.removeChar("abcdef", 'c'));
  }
}
