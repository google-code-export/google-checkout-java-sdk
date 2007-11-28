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

package com.google.checkout.checkout;

import junit.framework.TestCase;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class RoundingModeTests extends TestCase {
  public void testRoundingModeConstants() {
    assertEquals("CEILING", RoundingMode.CEILING.toString());
    assertEquals("DOWN", RoundingMode.DOWN.toString());
    assertEquals("FLOOR", RoundingMode.FLOOR.toString());
    assertEquals("HALF_DOWN", RoundingMode.HALF_DOWN.toString());
    assertEquals("HALF_EVEN", RoundingMode.HALF_EVEN.toString());
    assertEquals("HALF_UP", RoundingMode.HALF_UP.toString());
    assertEquals("UNECESSARY", RoundingMode.UNNECESSARY.toString());
    assertEquals("UP", RoundingMode.UP.toString());
  }
}
