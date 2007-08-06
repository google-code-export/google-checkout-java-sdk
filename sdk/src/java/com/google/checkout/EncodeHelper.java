/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
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

package com.google.checkout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.checkout.util.Constants;
import com.google.checkout.util.StringTuple;

/**
 * @author ksim
 * @version 1.0 - ksim - March 6th, 2007 - Initial Version
 */

public final class EncodeHelper {
  
  /**
   * <summary> Escapes XML characters &lt; &gt; and &amp;. </summary> <param
   * name="In"> String that could contain &lt; &gt; and &amp; characters.
   * </param> <returns> A new string where <b>&amp;</b> has been replaced by
   * <b>&amp;#x26;</b>, <b>&lt;</b> has been replaced by <b>&amp;#x3c;</b>
   * and <b>&gt;</b> has been replaced by <b>&amp;#x3e;</b>. These
   * replacements are mandated here in the Dev Guide: <a
   * href="http://code.google.com/apis/checkout/developer/index.html#api_request_guidelines">
   * http://code.google.com/apis/checkout/developer/index.html#api_request_guidelines
   * </a> </returns>
   */
  public static String escapeXmlChars(String str) {
    
    String output = str;
    
    StringTuple[] arrStrTuple = {
      new StringTuple(Constants.ampStr, Constants.ampReplaceStr),
      new StringTuple(Constants.lessThanStr,
          Constants.lessThanReplaceStr),
          new StringTuple(Constants.greaterThanStr,
          Constants.greaterThanReplaceStr) };
    
    for (int i = 0; i < arrStrTuple.length; i++) {
      StringTuple strTuple = arrStrTuple[i];
      
      // Compile regular expression
      Pattern pattern = Pattern.compile(strTuple.getFirstElementString());
      
      // Replace all instances of the pattern
      Matcher matcher = pattern.matcher(output);
      output = matcher.replaceAll(strTuple.getSecondElementString());
    }
    return output;
  }
}
