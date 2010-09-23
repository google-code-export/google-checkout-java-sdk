//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.09.23 at 12:14:48 PM PDT 
//


package com.google.checkout.sdk.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AuthorizationAmountNotification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorizationAmountNotification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="avs-response" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cvn-response" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authorization-amount" type="{http://checkout.google.com/schema/2}Money"/>
 *         &lt;element name="authorization-expiration-date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="google-order-number" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="order-summary" type="{http://checkout.google.com/schema/2}OrderSummary" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="serial-number" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorizationAmountNotification", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class AuthorizationAmountNotification implements com.google.checkout.sdk.notifications.Notification {

    @XmlElement(name = "avs-response", namespace = "http://checkout.google.com/schema/2", required = true)
    protected String avsResponse;
    @XmlElement(name = "cvn-response", namespace = "http://checkout.google.com/schema/2", required = true)
    protected String cvnResponse;
    @XmlElement(name = "authorization-amount", namespace = "http://checkout.google.com/schema/2", required = true)
    protected Money authorizationAmount;
    @XmlElement(name = "authorization-expiration-date", namespace = "http://checkout.google.com/schema/2", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar authorizationExpirationDate;
    @XmlElement(namespace = "http://checkout.google.com/schema/2", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "google-order-number", namespace = "http://checkout.google.com/schema/2", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String googleOrderNumber;
    @XmlElement(name = "order-summary", namespace = "http://checkout.google.com/schema/2")
    protected OrderSummary orderSummary;
    @XmlAttribute(name = "serial-number", required = true)
    protected String serialNumber;

    /**
     * Gets the value of the avsResponse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvsResponse() {
        return avsResponse;
    }

    /**
     * Sets the value of the avsResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvsResponse(String value) {
        this.avsResponse = value;
    }

    /**
     * Gets the value of the cvnResponse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCvnResponse() {
        return cvnResponse;
    }

    /**
     * Sets the value of the cvnResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCvnResponse(String value) {
        this.cvnResponse = value;
    }

    /**
     * Gets the value of the authorizationAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getAuthorizationAmount() {
        return authorizationAmount;
    }

    /**
     * Sets the value of the authorizationAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setAuthorizationAmount(Money value) {
        this.authorizationAmount = value;
    }

    /**
     * Gets the value of the authorizationExpirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuthorizationExpirationDate() {
        return authorizationExpirationDate;
    }

    /**
     * Sets the value of the authorizationExpirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuthorizationExpirationDate(XMLGregorianCalendar value) {
        this.authorizationExpirationDate = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
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
     * Gets the value of the orderSummary property.
     * 
     * @return
     *     possible object is
     *     {@link OrderSummary }
     *     
     */
    public OrderSummary getOrderSummary() {
        return orderSummary;
    }

    /**
     * Sets the value of the orderSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderSummary }
     *     
     */
    public void setOrderSummary(OrderSummary value) {
        this.orderSummary = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }


    public javax.xml.bind.JAXBElement<AuthorizationAmountNotification> toJAXB() {
      return com.google.checkout.sdk.util.Utils.objectFactory().createAuthorizationAmountNotification(this);
    }

    @Override
    public String toString() {
      return com.google.checkout.sdk.util.Utils.toXML(toJAXB());
    }
}
