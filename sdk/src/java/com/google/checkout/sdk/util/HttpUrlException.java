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
package com.google.checkout.sdk.util;

import com.google.checkout.sdk.commands.CheckoutException;

import java.net.URL;
import java.text.MessageFormat;

/**
 * Signal that an unexpected (exceptional) response was received from a remote
 * server.
 *
 */
public class HttpUrlException extends CheckoutException {
  private final URL toUrl;
  private final int responseCode;
  private final Object triedToSend;
  private final Object responseMessage;

  public HttpUrlException(URL toUrl, int responseCode, Object sent, Object received, Throwable causedBy) {
    super(causedBy);
    this.toUrl = toUrl;
    this.responseCode = responseCode;
    this.triedToSend = sent;
    this.responseMessage = received;
  }

  @Override
  public String getMessage() {
    return MessageFormat.format(Utils.SEND_AND_RECEIVE_DEBUGGING_STRING,
        responseCode, toUrl, triedToSend, responseMessage);
  }

  public int getCode() {
    return responseCode;
  }

  public String getSent() {
    return triedToSend.toString();
  }

  public String getResponse() {
    return responseMessage.toString();
  }
}
