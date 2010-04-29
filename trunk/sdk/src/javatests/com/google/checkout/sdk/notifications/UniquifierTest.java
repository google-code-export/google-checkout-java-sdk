/*******************************************************************************
 * Copyright (C) 2010 Google Inc.
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
package com.google.checkout.sdk.notifications;

import com.google.checkout.sdk.testing.Uniquifier;

import junit.framework.TestCase;

import java.util.concurrent.locks.Lock;

/**
 * Test the Uniquifier class.
 * 
*
 */
public class UniquifierTest extends TestCase {
  public void testUnequalObjects() {
    Uniquifier<Object> uniquifier = new Uniquifier<Object>();
    Object a = new Object();
    Object b = new Object();
    Lock locka = uniquifier.getLock(a);
    Lock lockb = uniquifier.getLock(b);
    assertNotSame(locka, lockb);
  }

  public void testEqualObjects() {
    Uniquifier<String> uniquifier = new Uniquifier<String>();
    String a = "asdf1234";
    String b = "asdf" + get1234(); // it's hard to not inline strings!
    assertNotSame(a, b);
    Lock locka = uniquifier.getLock(a);
    Lock lockb = uniquifier.getLock(b);
    assertSame(locka, lockb);
  }
  
  protected int get1234() {
    return 1234;
  }
}
