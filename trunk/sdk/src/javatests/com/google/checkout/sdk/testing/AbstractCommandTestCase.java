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
package com.google.checkout.sdk.testing;

import com.google.checkout.sdk.commands.ApiContext;
import com.google.checkout.sdk.commands.CheckoutException;
import com.google.checkout.sdk.commands.Environment;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Base test for all Command classes. Because the builder hierarchy is rooted
 * at the ApiContext, this provides one set up for a pretend merchant in a
 * pretend environment (easily testable!); it also disables actual network traffic.
 * 
*
 */
public class AbstractCommandTestCase extends TestCase {
  protected static final String BASE64_ENCODED_ID_AND_KEY =
    "bWVyY2hhbnRJZDptZXJjaGFudEtleQ==";

  private static final String TEST_CURRENCY_CODE = "XXX";

  protected Environment environment;

  @Override
  protected void setUp() throws Exception {
    environment = new Environment(
        "http://cartPost.example.net/%s",
        "http://postPurchase.example.net/%s",
        "http://reportsUrl/%s");
    boolean shouldSignCarts = false;
  }

  protected ApiContext apiContext() {
    return new TestingApiContext(
        environment,
        new ByteArrayInputStream(new byte[0]),
        new ByteArrayOutputStream(),
        new ByteArrayInputStream(new byte[0]));
  }
  
  protected TestingApiContext apiContext(String input) {
    try {
      return new TestingApiContext(
          environment,
          new ByteArrayInputStream(input.getBytes("utf-8")),
          new ByteArrayOutputStream(),
          new ByteArrayInputStream(new byte[0]));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  protected static class TestingApiContext extends ApiContext {
    protected final InputStream input;
    protected final ByteArrayOutputStream output;
    protected final InputStream error;

    public TestingApiContext(Environment environment,
        String merchantId, String merchantKey, String currencyCode, String response) {
      super(environment, merchantId, merchantKey, currencyCode);
      try {
        this.input =  new ByteArrayInputStream(response.getBytes("utf-8"));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      this.output = new ByteArrayOutputStream();
      this.error = new ByteArrayInputStream(new byte[0]);
    }
    
    public TestingApiContext(Environment environment,
        InputStream input, ByteArrayOutputStream output, InputStream error) {
      super(environment, "merchantId", "merchantKey", TEST_CURRENCY_CODE);
      this.input = input;
      this.output = output;
      this.error = error;
    }
    
    public String getOutput() {
      try {
        return output.toString("utf-8");
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    protected HttpURLConnection openHttpConnection(String toUrl) throws CheckoutException {
      try {
        return new FakeHttpConnection(
            new URL(toUrl), input, output, error);
      } catch (MalformedURLException e) {
        throw new CheckoutException(e);
      }
    }
  }

  protected static class FakeHttpConnection extends HttpURLConnection {
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final InputStream errorStream;
    protected FakeHttpConnection(URL u, InputStream inputStream,
        OutputStream outputStream, InputStream errorStream) {
      super(u);
      this.inputStream = inputStream;
      this.outputStream = outputStream;
      this.errorStream = errorStream;
    }

    @Override
    public synchronized void disconnect() {
    }

    @Override
    public synchronized boolean usingProxy() {
      return false;
    }

    @Override
    public synchronized void connect() {
      this.connected = true;
    }
    
    @Override
    public synchronized void addRequestProperty(String key, String value) {
      super.addRequestProperty(key, value);
    }
    
    @Override
    public synchronized InputStream getErrorStream() {
      return errorStream;
    }
    
    @Override
    public synchronized InputStream getInputStream() {
      return inputStream;
    }
    
    @Override
    public synchronized OutputStream getOutputStream() {
      return outputStream;
    }
    
    @Override
    public int getResponseCode() {
      return 200;
    }
  }
}
