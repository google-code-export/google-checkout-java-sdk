//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.28 at 11:58:56 AM PDT 
//


package com.google.checkout.sdk.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ReturnItemsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnItemsRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="item-ids">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="item-id" type="{http://checkout.google.com/schema/2}ItemId" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="send-email" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="google-order-number" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnItemsRequest", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class ReturnItemsRequest {

    @XmlElement(name = "item-ids", namespace = "http://checkout.google.com/schema/2", required = true)
    protected ReturnItemsRequest.ItemIds itemIds;
    @XmlElement(name = "send-email", namespace = "http://checkout.google.com/schema/2")
    protected Boolean sendEmail;
    @XmlAttribute(name = "google-order-number", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String googleOrderNumber;

    /**
     * Gets the value of the itemIds property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnItemsRequest.ItemIds }
     *     
     */
    public ReturnItemsRequest.ItemIds getItemIds() {
        return itemIds;
    }

    /**
     * Sets the value of the itemIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnItemsRequest.ItemIds }
     *     
     */
    public void setItemIds(ReturnItemsRequest.ItemIds value) {
        this.itemIds = value;
    }

    /**
     * Gets the value of the sendEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSendEmail() {
        return sendEmail;
    }

    /**
     * Sets the value of the sendEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSendEmail(Boolean value) {
        this.sendEmail = value;
    }

    /**
     * Gets the value of the googleOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleOrderNumber() {
        return googleOrderNumber;
    }

    /**
     * Sets the value of the googleOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleOrderNumber(String value) {
        this.googleOrderNumber = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="item-id" type="{http://checkout.google.com/schema/2}ItemId" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "itemId"
    })
    public static class ItemIds {

        @XmlElement(name = "item-id", namespace = "http://checkout.google.com/schema/2")
        protected List<ItemId> itemId;

        /**
         * Gets the value of the itemId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the itemId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItemId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ItemId }
         * 
         * 
         */
        public List<ItemId> getItemId() {
            if (itemId == null) {
                itemId = new ArrayList<ItemId>();
            }
            return this.itemId;
        }

    }


    public javax.xml.bind.JAXBElement<ReturnItemsRequest> toJAXB() {
      return com.google.checkout.sdk.util.Utils.objectFactory().createReturnItems(this);
    }

    @Override
    public String toString() {
      return com.google.checkout.sdk.util.Utils.toXML(toJAXB());
    }
}
