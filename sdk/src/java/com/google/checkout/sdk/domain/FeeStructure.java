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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeeStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FeeStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="total" type="{http://checkout.google.com/schema/2}Money"/>
 *         &lt;element name="percentage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="flat" type="{http://checkout.google.com/schema/2}Money"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeeStructure", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class FeeStructure {

    @XmlElement(namespace = "http://checkout.google.com/schema/2", required = true)
    protected Money total;
    @XmlElement(namespace = "http://checkout.google.com/schema/2")
    protected double percentage;
    @XmlElement(namespace = "http://checkout.google.com/schema/2", required = true)
    protected Money flat;

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setTotal(Money value) {
        this.total = value;
    }

    /**
     * Gets the value of the percentage property.
     * 
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Sets the value of the percentage property.
     * 
     */
    public void setPercentage(double value) {
        this.percentage = value;
    }

    /**
     * Gets the value of the flat property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getFlat() {
        return flat;
    }

    /**
     * Sets the value of the flat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setFlat(Money value) {
        this.flat = value;
    }

}
