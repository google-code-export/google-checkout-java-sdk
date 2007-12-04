package com.google.checkout.checkout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.CheckoutException;
import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;item&gt; element.
 * 
 * @author simonjsmith
 * 
 */
public class Item {

  private final Document document;

  private final Element element;

  /**
   * A constructor which takes the document and element pointing to the
   * &lt;item&gt; tag.
   * 
   * @param document The document.
   * @param element The element.
   */
  public Item(Document document, Element element) {
    this.document = document;
    this.element = element;
  }

  /**
   * The default constructor.
   */
  public Item() {
    document = Utils.newEmptyDocument();
    element = document.createElement("item");
    document.appendChild(element);
  }

  /**
   * Retrieve the contents of the &lt;item-description&gt; tag.
   * 
   * @return The item description.
   */
  public String getItemDescription() {
    return Utils.getElementStringValue(document, element, "item-description");
  }

  /**
   * Retrieve the contents of the &lt;item-name&gt; tag.
   * 
   * @return The item name.
   */
  public String getItemName() {
    return Utils.getElementStringValue(document, element, "item-name");
  }

  /**
   * Retrieve the contents of the &lt;merchant-item-id&gt; tag.
   * 
   * @return The merchant item id.
   */
  public String getMerchantItemId() {
    return Utils.getElementStringValue(document, element, "merchant-item-id");
  }

  /**
   * Retrieve the contents of the &lt;merchant-private-item-data&gt; tag as an
   * array of Elements.
   * 
   * @return The private data Elements.
   * 
   * @see Element
   */
  public Element[] getMerchantPrivateItemData() {
    Element privateData =
        Utils.findElementOrContainer(document, element,
            "merchant-private-item-data");
    if (privateData == null) {
      return null;
    }
    return Utils.getElements(document, privateData);
  }

  /**
   * Retrieve the contents of the &lt;quantity&gt; tag.
   * 
   * @return The quantity as an int.
   */
  public int getQuantity() {
    return Utils.getElementIntValue(document, element, "quantity");
  }

  /**
   * Retrieve the contents of the &lt;tax-table-selector&gt; tag.
   * 
   * @return The tax table selector name.
   */
  public String getTaxTableSelector() {
    return Utils.getElementStringValue(document, element, "tax-table-selector");
  }

  /**
   * Retrieve the contents of the &lt;unit-price&gt; tag.
   * 
   * @return The unit price as a float.
   */
  public float getUnitPriceAmount() {
    return Utils.getElementFloatValue(document, element, "unit-price");
  }

  /**
   * Retrieve the contents of the currency attribute of the &lt;unit-price&gt;
   * tag.
   * 
   * @return The currency code.
   */
  public String getUnitPriceCurrency() {
    Element unitPrice =
        Utils.findContainerElseCreate(document, element, "unit-price");
    return unitPrice.getAttribute("currency");
  }

  /**
   * Retrieve the contents of the &lt;item-weight&gt; tag.
   * 
   * @return The item weight.
   */
  public float getItemWeight() {
    Element itemWeight =
        Utils.findElementOrContainer(document, element, "item-weight");
    if (itemWeight == null) {
      return -1;
    }
    return Float.parseFloat(itemWeight.getAttribute("value"));
  }

  /**
   * Retrieve the contents of the unit attribute of the &lt;item-weight&gt; tag.
   * 
   * @return The item weight unit.
   */
  public String getItemWeightUnit() {
    Element itemWeight =
        Utils.findElementOrContainer(document, element, "item-weight");
    if (itemWeight == null) {
      return null;
    }
    return itemWeight.getAttribute("unit");
  }

  /**
   * Set the contents of the &lt;item-description&gt; tag.
   * 
   * @param description The item description.
   */
  public void setItemDescription(String description) {
    Utils.findElementAndSetElseCreateAndSet(document, element,
        "item-description", description);
  }

  /**
   * Set the contents of the &lt;item-name&gt; tag.
   * 
   * @param itemName The item name.
   */
  public void setItemName(String itemName) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "item-name",
        itemName);
  }

  /**
   * Set the contents of the &lt;merchant-item-id&gt; tag.
   * 
   * @param merchantItemId The merchant item id.
   */
  public void setMerchantItemId(String merchantItemId) {
    Utils.findElementAndSetElseCreateAndSet(document, element,
        "merchant-item-id", merchantItemId);
  }

  /**
   * Set the contents of the &lt;merchant-private-item-data&gt; tag as an array
   * of Elements. If the merchant-private-item-data tag already exists, it will
   * be replaced with the contents of elements. If the elements are not of type
   * item-data, a CheckoutException will be thrown.
   * 
   * @param elements The private data Elements.
   * 
   * @see Element
   */
  public void setMerchantPrivateItemData(Element[] elements) throws CheckoutException {   
    for (int i=0; i<elements.length; ++i) {
      if (!elements[i].getNodeName().equals("item-data")) {
        throw new CheckoutException("At least one of the nodes is not item-data");
      }
    }
    
    Element privateData =
      Utils.findElementOrContainer(document, element,
        "merchant-private-item-data");

    if (privateData != null) {
      element.removeChild(privateData);
    } 
    
    privateData = document.createElement("merchant-private-item-data");
    
    Utils.importElements(document, privateData, elements);
    element.appendChild(privateData);
  }

  /**
   * Set the contents of the &lt;quantity&gt; tag.
   * 
   * @param quantity The quantity as an int.
   */
  public void setQuantity(int quantity) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "quantity",
        quantity);
  }

  /**
   * Set the contents of the &lt;tax-table-selector&gt; tag.
   * 
   * @param taxTableSelector The tax table selector name.
   */
  public void setTaxTableSelector(String taxTableSelector) {
    Utils.findElementAndSetElseCreateAndSet(document, element,
        "tax-table-selector", taxTableSelector);
  }

  /**
   * Set the contents of the &lt;unit-price&gt; tag.
   * 
   * @param unitPriceAmount The unit price as a float.
   */
  public void setUnitPriceAmount(float unitPriceAmount) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "unit-price",
        unitPriceAmount);
  }

  /**
   * Set the contents of the currency attribute of the &lt;unit-price&gt; tag.
   * 
   * @param unitPriceCurrencyCode The currency code.
   */
  public void setUnitPriceCurrency(String unitPriceCurrencyCode) {
    Element unitPrice =
        Utils.findContainerElseCreate(document, element, "unit-price");
    unitPrice.setAttribute("currency", unitPriceCurrencyCode);
  }

  /**
   * Set the contents of the currency attribute of the &lt;item-weight&gt; tag.
   * 
   * @param itemWeight The item weight.
   */
  public void setItemWeight(float itemWeight) {
    Element unitPrice =
        Utils.findContainerElseCreate(document, element, "item-weight");
    unitPrice.setAttribute("value", Float.toString(itemWeight));
  }

  /**
   * Set the contents of the unit attribute of the &lt;item-weight&gt; tag.
   * 
   * @param itemWeightUnit The item weight unit.
   */
  public void setItemWeightUnit(String itemWeightUnit) {
    Element unitPrice =
        Utils.findContainerElseCreate(document, element, "item-weight");
    unitPrice.setAttribute("unit", itemWeightUnit);
  }

  /**
   * Retrieve the contents of the &lt;digital-content&gt; tag as a
   * DigitalContent object.
   * 
   * @return The DigitalContent object.
   * 
   * @see DigitalContent
   */
  public DigitalContent getDigitalContent() {
    Element digitalContent =
        Utils.findElementOrContainer(document, element, "digital-content");

    if (digitalContent == null) {
      return null;
    }

    return new DigitalContent(document, digitalContent);
  }

  /**
   * Set the contents of the &lt;digital-content&gt; tag as a DigitalContent
   * object.
   * 
   * @param digitalContent The DigitalContent object.
   * 
   * @see DigitalContent
   */
  public void setDigitalContent(DigitalContent digitalContent) {
    Element dcElement =
        Utils.findElementOrContainer(document, element, "digital-content");

    if (dcElement != null) {
      element.removeChild(dcElement);
    }

    Utils.importElements(document, element, new Element[] {digitalContent
        .getRootElement()});
  }

  /**
   * Get the root element, &lt;item&gt;
   * 
   * @return The root element, &lt;item&gt;.
   */
  public Element getRootElement() {
    return element;
  }
}
