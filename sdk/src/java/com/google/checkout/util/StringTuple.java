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

/**
 * @author ksim
 * @version 1.0
 */
public final class StringTuple extends Tuple {
	public StringTuple(String fElement, String sElement) {
		super(fElement, sElement);
	}

	public String getFirstElementString() {
		Object fElement = this.getFirstElement();

		if (fElement == null)
			return "";

		else if (fElement instanceof String)
			return (String) fElement;

		return "";
	}

	public String getSecondElementString() {
		Object sElement = this.getSecondElement();

		if (sElement == null)
			return "";

		else if (sElement instanceof String)
			return (String) sElement;

		return "";
	}

	public void setFirstElementString(String fElement) {
		this.setFirstElement(fElement);
		return;
	}

	public void setSecondElementString(String sElement) {
		this.setSecondElement(sElement);
		return;
	}

}
