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

package com.google.checkout.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ksim
 * @version 1.0 - ksim - March 6th, 2007 - Initial Version
 * @version 1.1 - ksim - March 10th, 2007 - Bug fix to regex function, and also
 *          added more functions
 */

public final class StringUtil {

	public final static String replaceMultipleStrings(String input,
			String replaceStr1) {
		String[] arrReplaceStr = new String[] { replaceStr1 };
		return replaceMultipleStrings(input, arrReplaceStr);
	}

	public final static String replaceMultipleStrings(String input,
			String replaceStr1, String replaceStr2) {
		String[] arrReplaceStr = new String[] { replaceStr1, replaceStr2 };
		return replaceMultipleStrings(input, arrReplaceStr);
	}

	// Imitations of Java 1.5 String format function using Regex
	// For maximum compatibility with Java 1.4
	public final static String replaceMultipleStrings(String input,
			String[] arrReplaceStr) {
		// System.out.println(input);
		// System.out.println(arrReplaceStr[0]);

		String output = input;
		for (int i = 0; i < arrReplaceStr.length; i++) {
			String patternStr = new StringBuffer("\\{").append(i).append("\\}")
					.toString();
			String replaceStr = arrReplaceStr[i];

			// Compile regular expression
			Pattern pattern = Pattern.compile(patternStr);

			// Replace all instances of the pattern
			Matcher matcher = pattern.matcher(output);
			output = matcher.replaceAll(replaceStr);
		}
		// System.out.println(output);

		return output;
	}

	public final static String replaceXMLReservedChars(String str) {
		str = str.replaceAll(Constants.ampStr, Constants.ampReplaceStr);
		str = str.replaceAll(Constants.lessThanStr,
				Constants.lessThanReplaceStr);
		str = str.replaceAll(Constants.greaterThanStr,
				Constants.greaterThanReplaceStr);
		return str;
	}

	public final static String removeChar(String str, char c) {
		String output = new String();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != c)
				output += str.charAt(i);
		}
		return output;
	}

}
