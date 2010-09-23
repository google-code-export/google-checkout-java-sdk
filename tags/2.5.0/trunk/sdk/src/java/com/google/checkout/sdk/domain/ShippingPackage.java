//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.28 at 11:58:56 AM PDT 
//


package com.google.checkout.sdk.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ShippingPackage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShippingPackage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="length" type="{http://checkout.google.com/schema/2}Dimension" minOccurs="0"/>
 *         &lt;element name="width" type="{http://checkout.google.com/schema/2}Dimension" minOccurs="0"/>
 *         &lt;element name="height" type="{http://checkout.google.com/schema/2}Dimension" minOccurs="0"/>
 *         &lt;element name="ship-from" type="{http://checkout.google.com/schema/2}AnonymousAddress"/>
 *         &lt;element name="delivery-address-category" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShippingPackage", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class ShippingPackage {

    @XmlElement(namespace = "http://checkout.google.com/schema/2")
    protected Dimension length;
    @XmlElement(namespace = "http://checkout.google.com/schema/2")
    protected Dimension width;
    @XmlElement(namespace = "http://checkout.google.com/schema/2")
    protected Dimension height;
    @XmlElement(name = "ship-from", namespace = "http://checkout.google.com/schema/2", required = true)
    protected AnonymousAddress shipFrom;
    @XmlElement(name = "delivery-address-category", namespace = "http://checkout.google.com/schema/2")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String deliveryAddressCategory;

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link Dimension }
     *     
     */
    public Dimension getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dimension }
     *     
     */
    public void setLength(Dimension value) {
        this.length = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Dimension }
     *     
     */
    public Dimension getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dimension }
     *     
     */
    public void setWidth(Dimension value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Dimension }
     *     
     */
    public Dimension getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dimension }
     *     
     */
    public void setHeight(Dimension value) {
        this.height = value;
    }

    /**
     * Gets the value of the shipFrom property.
     * 
     * @return
     *     possible object is
     *     {@link AnonymousAddress }
     *     
     */
    public AnonymousAddress getShipFrom() {
        return shipFrom;
    }

    /**
     * Sets the value of the shipFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnonymousAddress }
     *     
     */
    public void setShipFrom(AnonymousAddress value) {
        this.shipFrom = value;
    }

    /**
     * Gets the value of the deliveryAddressCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryAddressCategory() {
        return deliveryAddressCategory;
    }

    /**
     * Sets the value of the deliveryAddressCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryAddressCategory(String value) {
        this.deliveryAddressCategory = value;
    }

}