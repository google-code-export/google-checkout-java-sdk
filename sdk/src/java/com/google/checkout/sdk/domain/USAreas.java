//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.09.23 at 12:14:48 PM PDT 
//


package com.google.checkout.sdk.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for USAreas.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="USAreas">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="CONTINENTAL_48"/>
 *     &lt;enumeration value="FULL_50_STATES"/>
 *     &lt;enumeration value="ALL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "USAreas", namespace = "http://checkout.google.com/schema/2")
@XmlEnum
public enum USAreas {

    CONTINENTAL_48,
    FULL_50_STATES,
    ALL;

    public String value() {
        return name();
    }

    public static USAreas fromValue(String v) {
        return valueOf(v);
    }

}
