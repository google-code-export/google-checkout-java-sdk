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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for NotificationHistoryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificationHistoryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="serial-number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="start-time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="next-page-token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="end-time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="order-numbers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="google-order-number" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="notification-types" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="notification-type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationHistoryRequest", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class NotificationHistoryRequest {

    @XmlElement(name = "serial-number", namespace = "http://checkout.google.com/schema/2")
    protected String serialNumber;
    @XmlElement(name = "start-time", namespace = "http://checkout.google.com/schema/2")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "next-page-token", namespace = "http://checkout.google.com/schema/2")
    protected String nextPageToken;
    @XmlElement(name = "end-time", namespace = "http://checkout.google.com/schema/2")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "order-numbers", namespace = "http://checkout.google.com/schema/2")
    protected NotificationHistoryRequest.OrderNumbers orderNumbers;
    @XmlElement(name = "notification-types", namespace = "http://checkout.google.com/schema/2")
    protected NotificationHistoryRequest.NotificationTypes notificationTypes;

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

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the nextPageToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextPageToken() {
        return nextPageToken;
    }

    /**
     * Sets the value of the nextPageToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextPageToken(String value) {
        this.nextPageToken = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the orderNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationHistoryRequest.OrderNumbers }
     *     
     */
    public NotificationHistoryRequest.OrderNumbers getOrderNumbers() {
        return orderNumbers;
    }

    /**
     * Sets the value of the orderNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationHistoryRequest.OrderNumbers }
     *     
     */
    public void setOrderNumbers(NotificationHistoryRequest.OrderNumbers value) {
        this.orderNumbers = value;
    }

    /**
     * Gets the value of the notificationTypes property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationHistoryRequest.NotificationTypes }
     *     
     */
    public NotificationHistoryRequest.NotificationTypes getNotificationTypes() {
        return notificationTypes;
    }

    /**
     * Sets the value of the notificationTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationHistoryRequest.NotificationTypes }
     *     
     */
    public void setNotificationTypes(NotificationHistoryRequest.NotificationTypes value) {
        this.notificationTypes = value;
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
     *         &lt;element name="notification-type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "notificationType"
    })
    public static class NotificationTypes {

        @XmlElement(name = "notification-type", namespace = "http://checkout.google.com/schema/2")
        protected List<String> notificationType;

        /**
         * Gets the value of the notificationType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the notificationType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNotificationType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getNotificationType() {
            if (notificationType == null) {
                notificationType = new ArrayList<String>();
            }
            return this.notificationType;
        }

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
     *         &lt;element name="google-order-number" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded" minOccurs="0"/>
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
        "googleOrderNumber"
    })
    public static class OrderNumbers {

        @XmlElement(name = "google-order-number", namespace = "http://checkout.google.com/schema/2")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected List<String> googleOrderNumber;

        /**
         * Gets the value of the googleOrderNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the googleOrderNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGoogleOrderNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getGoogleOrderNumber() {
            if (googleOrderNumber == null) {
                googleOrderNumber = new ArrayList<String>();
            }
            return this.googleOrderNumber;
        }

    }


    public javax.xml.bind.JAXBElement<NotificationHistoryRequest> toJAXB() {
      return com.google.checkout.sdk.util.Utils.objectFactory().createNotificationHistoryRequest(this);
    }

    @Override
    public String toString() {
      return com.google.checkout.sdk.util.Utils.toXML(toJAXB());
    }
}
