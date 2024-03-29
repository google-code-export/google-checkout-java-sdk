//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.09.23 at 12:14:48 PM PDT 
//


package com.google.checkout.sdk.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxTables complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxTables">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="default-tax-table" type="{http://checkout.google.com/schema/2}DefaultTaxTable" minOccurs="0"/>
 *         &lt;element name="alternate-tax-tables" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="alternate-tax-table" type="{http://checkout.google.com/schema/2}AlternateTaxTable" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *       &lt;attribute name="merchant-calculated" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxTables", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class TaxTables {

    @XmlElement(name = "default-tax-table", namespace = "http://checkout.google.com/schema/2")
    protected DefaultTaxTable defaultTaxTable;
    @XmlElement(name = "alternate-tax-tables", namespace = "http://checkout.google.com/schema/2")
    protected TaxTables.AlternateTaxTables alternateTaxTables;
    @XmlAttribute(name = "merchant-calculated")
    protected Boolean merchantCalculated;

    /**
     * Gets the value of the defaultTaxTable property.
     * 
     * @return
     *     possible object is
     *     {@link DefaultTaxTable }
     *     
     */
    public DefaultTaxTable getDefaultTaxTable() {
        return defaultTaxTable;
    }

    /**
     * Sets the value of the defaultTaxTable property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefaultTaxTable }
     *     
     */
    public void setDefaultTaxTable(DefaultTaxTable value) {
        this.defaultTaxTable = value;
    }

    /**
     * Gets the value of the alternateTaxTables property.
     * 
     * @return
     *     possible object is
     *     {@link TaxTables.AlternateTaxTables }
     *     
     */
    public TaxTables.AlternateTaxTables getAlternateTaxTables() {
        return alternateTaxTables;
    }

    /**
     * Sets the value of the alternateTaxTables property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxTables.AlternateTaxTables }
     *     
     */
    public void setAlternateTaxTables(TaxTables.AlternateTaxTables value) {
        this.alternateTaxTables = value;
    }

    /**
     * Gets the value of the merchantCalculated property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMerchantCalculated() {
        return merchantCalculated;
    }

    /**
     * Sets the value of the merchantCalculated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMerchantCalculated(Boolean value) {
        this.merchantCalculated = value;
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
     *         &lt;element name="alternate-tax-table" type="{http://checkout.google.com/schema/2}AlternateTaxTable" maxOccurs="unbounded" minOccurs="0"/>
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
        "alternateTaxTable"
    })
    public static class AlternateTaxTables {

        @XmlElement(name = "alternate-tax-table", namespace = "http://checkout.google.com/schema/2")
        protected List<AlternateTaxTable> alternateTaxTable;

        /**
         * Gets the value of the alternateTaxTable property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the alternateTaxTable property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAlternateTaxTable().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AlternateTaxTable }
         * 
         * 
         */
        public List<AlternateTaxTable> getAlternateTaxTable() {
            if (alternateTaxTable == null) {
                alternateTaxTable = new ArrayList<AlternateTaxTable>();
            }
            return this.alternateTaxTable;
        }

    }

}
