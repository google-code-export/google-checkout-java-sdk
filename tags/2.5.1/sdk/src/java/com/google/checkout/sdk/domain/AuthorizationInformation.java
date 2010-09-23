//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.09.23 at 12:14:48 PM PDT 
//


package com.google.checkout.sdk.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AuthorizationInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorizationInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="authorization-amount" type="{http://checkout.google.com/schema/2}Money"/>
 *         &lt;element name="authorization-expiration-date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorizationInformation", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class AuthorizationInformation {

    @XmlElement(name = "authorization-amount", namespace = "http://checkout.google.com/schema/2", required = true)
    protected Money authorizationAmount;
    @XmlElement(name = "authorization-expiration-date", namespace = "http://checkout.google.com/schema/2", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar authorizationExpirationDate;

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

}
