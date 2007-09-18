package com.google.checkout.checkout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;item&gt; element.
 * 
 * @author simonjsmith
 * 
 */
public class Item {

  private Document document;

  private Element element;

  /**
   * A constructor which takes the document and element pointing to the
   * &lt;item&gt; tag.
   * 
   * @param document The document.
   * @param element The element.
   */
  public Item(Document document, Element element) {
    this.element = element;
  }

  /**
   * Retrive the contents of the &lt;item-description&gt; tag.
   * 
   * @return The item description.
   */
  public String getItemDescription() {
    return Utils.getElementStringValue(document, element, "item-description");
  }

  /**
   * Retrive the contents of the &lt;item-name&gt; tag.
   * 
   * @return The item name.
   */
  public String getItemName() {
    return Utils.getElementStringValue(document, element, "item-name");
  }

  /**
   * Retrive the contents of the &lt;merchant-item-id&gt; tag.
   * 
   * @return The merchant item id.
   */
  public String getMerchantItemId() {
    return Utils.getElementStringValue(document, element, "merchant-item-id");
  }

  /**
   * Retrive the contents of the &lt;merchant-private-item-data&gt; tag as an
   * array of Elements.
   * 
   * @return The private data Elements.
   * 
   * @see Element
   */
  public Element[] getMerchantPrivateItemData() {
    Element privateData = Utils.findElementOrContainer(document, element,
        "merchant-private-item-data");
    return Utils.getElements(document, privateData);
  }

  /**
   * Retrive the contents of the &lt;quantity&gt; tag.
   * 
   * @return The quantity as an int.
   */
  public int getQuantity() {
    return Utils.getElementIntValue(document, element, "quantity");
  }

  /**
   * Retrive the contents of the &lt;tax-table-selector&gt; tag.
   * 
   * @return The tax table selector name.
   */
  public String getTaxTableSelector() {
    return Utils.getElementStringValue(document, element, "tax-table-selector");
  }

  /**
   * Retrive the contents of the &lt;unit-price&gt; tag.
   * 
   * @return The unit price as a float.
   */
  public float getUnitPriceAmount() {
    return Utils.getElementFloatValue(document, element, "unit-price");
  }

  /**
   * Retrive the contents of the currency attribute of the &lt;unit-price&gt;
   * tag.
   * 
   * @return The currency code.
   */
  public String getUnitPriceCurrency() {
    Element unitPrice = Utils.findContainerElseCreate(document, element,
        "unit-price");
    return unitPrice.getAttribute("currency");
  }
}
