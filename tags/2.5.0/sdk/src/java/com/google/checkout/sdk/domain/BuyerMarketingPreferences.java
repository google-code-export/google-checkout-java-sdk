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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BuyerMarketingPreferences complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BuyerMarketingPreferences">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="email-allowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BuyerMarketingPreferences", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class BuyerMarketingPreferences {

    @XmlElement(name = "email-allowed", namespace = "http://checkout.google.com/schema/2")
    protected boolean emailAllowed;

    /**
     * Gets the value of the emailAllowed property.
     * 
     */
    public boolean isEmailAllowed() {
        return emailAllowed;
    }

    /**
     * Sets the value of the emailAllowed property.
     * 
     */
    public void setEmailAllowed(boolean value) {
        this.emailAllowed = value;
    }


    public javax.xml.bind.JAXBElement<BuyerMarketingPreferences> toJAXB() {
      return com.google.checkout.sdk.util.Utils.objectFactory().createBuyerMarketingPreferences(this);
    }

    @Override
    public String toString() {
      return com.google.checkout.sdk.util.Utils.toXML(toJAXB());
    }
}