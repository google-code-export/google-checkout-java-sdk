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

package com.google.checkout.sdk.commands;

/**
 * This class is the base for any Checkout specific exceptions.
 *
 *
 */
public class CheckoutException extends RuntimeException {
  /**
   * Creates a new instance of <code>CheckoutException</code> without detail message.
   */
  public CheckoutException() {
    super();
  }

  /**
   * A constructor that takes a nested exception
   *
   * @param e The exception
   *
   * @see Exception
   */
  public CheckoutException(Throwable e) {
    super(e);
  }

  /**
   * A constructor that takes an error message and a nested exception
   *
   * @param msg The error message
   * @param e The exception
   */
  public CheckoutException(String msg, Throwable e) {
    super(msg, e);
  }

  /**
   * A constructor that takes an error message
   *
   * @param msg The error message
   */
  public CheckoutException(String msg) {
    super(msg);
  }
}
