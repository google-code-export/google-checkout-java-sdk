package com.google.checkout.handlers;

import com.google.checkout.EnvironmentType;
import com.google.checkout.MerchantInfo;

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
      throw new RuntimeException("Can not read file: " + ioe.getMessage());
    }
    return sw.toString();
  }
  
  public static MerchantInfo createMockMerchantInfo() {
    String merchantId = "812318588721976";
    String merchantKey = "c1YAeK6wMizfJ6BmZJG9Fg";
    String env = "Sandbox";
    String currencyCode = "USD";
    String sandboxRoot = "https://sandbox.google.com/checkout/cws/v2/Merchant";
    String productionRoot = "https://checkout.google.com/cws/v2/Merchant";
    String checkoutSuffix = "checkout";
    String merchantCheckoutSuffix = "merchantCheckout";
    String requestSuffix = "request";

    String checkoutUrl = "";
    String merchantCheckoutUrl = "";
    String requestUrl = "";

    if (EnvironmentType.Sandbox.equals(env)) {
      checkoutUrl = sandboxRoot + "/" + merchantId + "/" + checkoutSuffix;
      merchantCheckoutUrl =
          sandboxRoot + "/" + merchantId + "/" + merchantCheckoutSuffix;
      requestUrl = sandboxRoot + "/" + merchantId + "/" + requestSuffix;
    } else if (EnvironmentType.Production.equals(env)) {
      checkoutUrl = productionRoot + "/" + merchantId + "/" + checkoutSuffix;
      merchantCheckoutUrl =
          productionRoot + "/" + merchantId + "/" + merchantCheckoutSuffix;
      requestUrl = productionRoot + "/" + merchantId + "/" + requestSuffix;
    } else {
      throw new RuntimeException("Env must be one of "
          + EnvironmentType.Sandbox + " or " + EnvironmentType.Production + ".");
    }
    return new MerchantInfo(merchantId, merchantKey, env, currencyCode,
        checkoutUrl, merchantCheckoutUrl, requestUrl);
  }
}
