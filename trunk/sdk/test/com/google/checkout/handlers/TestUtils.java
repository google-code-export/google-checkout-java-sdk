package com.google.checkout.handlers;

import com.google.checkout.EnvironmentType;
import com.google.checkout.MerchantInfo;

import com.google.checkout.CheckoutSystemException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class TestUtils {
  
  public static String readMessage(String resourceFile) {
    InputStream is = TestUtils.class.getResourceAsStream(resourceFile);
    return convert(is);
  }

  public static String convert(InputStream is) {
    StringWriter sw = new StringWriter();
    try {
    while (is.available() > 0) {
      sw.write(is.read());
    }
    } catch (IOException ioe) {
      throw new CheckoutSystemException("Can not read file: " + ioe.getMessage());
    }
    return sw.toString();
  }
  
  public static MerchantInfo createMockMerchantInfo() {
    String merchantId = "812318588721976";
    String merchantKey = "c1YAeK6wMizfJ6BmZJG9Fg";
    String env = "Sandbox";
    String currencyCode = "USD";
    String sandboxRoot = "https://sandbox.google.com/checkout/api/checkout/v2";
    String productionRoot = "https://checkout.google.com/api/checkout/v2";
    String checkoutCommand = "checkout";
    String merchantCheckoutCommand = "merchantCheckout";
    String requestCommand = "request";

    String checkoutUrl = "";
    String merchantCheckoutUrl = "";
    String requestUrl = "";

    if (EnvironmentType.Sandbox.equals(env)) {
      checkoutUrl =
          sandboxRoot + "/" + checkoutCommand + "/Merchant/" + merchantId;
      merchantCheckoutUrl =
          sandboxRoot + "/" + merchantCheckoutCommand + "/Merchant/"
              + merchantId;
      requestUrl =
          sandboxRoot + "/" + requestCommand + "/Merchant/" + merchantId;
    } else if (EnvironmentType.Production.equals(env)) {
      checkoutUrl =
          productionRoot + "/" + checkoutCommand + "/Merchant/" + merchantId;
      merchantCheckoutUrl =
          productionRoot + "/" + merchantCheckoutCommand + "/Merchant/"
              + merchantId;
      requestUrl =
          productionRoot + "/" + requestCommand + "/Merchant/" + merchantId;
    } else {
      throw new CheckoutSystemException("Env must be one of "
          + EnvironmentType.Sandbox + " or " + EnvironmentType.Production + ".");
    }
    return new MerchantInfo(merchantId, merchantKey, env, currencyCode,
        checkoutUrl, merchantCheckoutUrl, requestUrl);
  }
}
