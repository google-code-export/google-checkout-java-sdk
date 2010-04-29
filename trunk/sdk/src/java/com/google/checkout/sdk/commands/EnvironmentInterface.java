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
package com.google.checkout.sdk.commands;

/**
 * A Google Checkout environment, Production, Sandbox, or some test instance
 * your system might maintain. You should not have to implement this interface
 * yourself; you should use the constants {@code Enviornment.PRODUCTION} or
 * {@code Enviornment.SANDBOX}.
 * 
 * @see Environment
 * 
*
 */
public interface EnvironmentInterface {
  String getUrl(CommandType command, String merchantId);
  
  /**
   * One of Google Checkout's communication channels. Each "channel" (the word
   * is not intended to refer to any specific technology) has a separate URL,
   * and is demuxed via {@link EnvironmentInterface#getUrl}. You should not
   * have to reference this type directly unless you are overloading
   * {@link EnvironmentInterface} for testing purposes.
   */
  public static enum CommandType {
    /**
     * The URL to which to send server to server cart posts.
     * @see ApiContext#cartPoster()
     */
    CART_POST,
    /**
     * The URL to which to send post-purchase order processing requests.
     * @see ApiContext#orderCommands
     */
    ORDER_PROCESSING,
    /**
     * The URL to which to request information.
     * @see ApiContext#reportsRequester
     */
    REPORTS
  }
}
