package com.google.checkout.checkout;

import com.google.checkout.util.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DigitalContent {

  private final Document document;

  private final Element element;

  /**
   * A constructor which takes the document and element pointing to the
   * &lt;digital-content&gt; tag.
   * 
   * @param document The document.
   * @param element The element.
   */
  public DigitalContent(Document document, Element element) {
    this.document = document;
    this.element = element;
  }

  /**
   * The default constructor.
   */
  public DigitalContent() {
    document = Utils.newEmptyDocument();
    element = document.createElement("digital-content");
    document.appendChild(element);
  }

  public boolean isEmailDelivery() {
    return Utils.getElementBooleanValue(document, element, "email-delivery");
  }

  public void setEmailDelivery(boolean emailDelivery) {
    Utils.findElementAndSetElseCreateAndSet(document, element,
        "email-delivery", emailDelivery);
  }

  public String getDescription() {
    return Utils.getElementStringValue(document, element, "description");
  }

  public void setDescription(String description) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "description",
        description);
  }

  public String getKey() {
    return Utils.getElementStringValue(document, element, "key");
  }

  public void setKey(String key) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "key", key);
  }

  public String getUrl() {
    return Utils.getElementStringValue(document, element, "url");
  }

  public void setUrl(String url) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "url", url);
  }

  /**
   * Get the root element, &lt;digital-content&gt;
   * 
   * @return The root element, &lt;digital-content&gt;.
   */
  public Element getRootElement() {
    return element;
  }
}
