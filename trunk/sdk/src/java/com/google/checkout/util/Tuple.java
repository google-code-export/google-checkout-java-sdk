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
public abstract class Tuple {
	private Object firstElement;

	private Object secondElement;

	Tuple(Object fElement, Object sElement) {
		firstElement = fElement;
		secondElement = sElement;
	}

	public Object getFirstElement() {
		return firstElement;
	}

	public Object getSecondElement() {
		return secondElement;
	}

	public void setFirstElement(Object fElement) {
		firstElement = fElement;
		return;
	}

	public void setSecondElement(Object sElement) {
		secondElement = sElement;
		return;
	}

}
