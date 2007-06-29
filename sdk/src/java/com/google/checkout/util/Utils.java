/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.google.checkout.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Various XML utilities.
 * 
 * @author simonjsmith, ksim
 * @version 1.1 - ksim - March 6th, 2007 - Added functions regarding streaming
 * @version 1.2 - ksim - March 10th, 2007 - Added functions regarding DOM
 *          manipulation
 */

public class Utils {

	public static Document newEmptyDocument() {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		ret = builder.newDocument();

		return ret;
	}

	public static Element findElementOrContainer(Document document,
			Element parent, String element) {
		NodeList nl = parent.getElementsByTagName(element);
		if (nl.getLength() == 0) {
			return null;
		}
		return (Element) nl.item(0);
	}

	public static Element findContainerElseCreate(Document document,
			Element parent, String child) {
		NodeList nl = parent.getElementsByTagName(child);
		if (nl.getLength() == 0) {
			parent.appendChild(document.createElement(child));
		}
		return (Element) parent.getElementsByTagName(child).item(0);
	}

	public static Element createNewContainer(Document document, Element parent,
			String childElement) {
		Element child = (Element) document.createElement(childElement);
		parent.appendChild(child);
		return child;
	}

	public static Element findElementElseCreateAndSet(Document document,
			Element parent, String child, String value) {
		Element ret = null;
		NodeList nl = parent.getElementsByTagName(child);
		if (nl.getLength() == 0) {
			parent.appendChild(document.createElement(child));
			ret = (Element) parent.getElementsByTagName(child).item(0);
			ret.appendChild(document.createTextNode(value));
		}
		return ret;
	}

	public static Element findElementElseCreateAndSet(Document document,
			Element parent, String child, boolean value) {
		return findElementElseCreateAndSet(document, parent, child, value + "");
	}

	public static Element findElementAndSetElseCreateAndSet(Document document,
			Element parent, String child, String value) {
		NodeList nl = parent.getElementsByTagName(child);
		if (nl.getLength() == 0) {
			parent.appendChild(document.createElement(child));
		}
		Element ret = (Element) parent.getElementsByTagName(child).item(0);
		if (ret.getFirstChild() != null) {
			ret.removeChild(ret.getFirstChild());
		}
		ret.appendChild(document.createTextNode(value));
		return ret;
	}

	public static Element findElementAndSetElseCreateAndSet(Document document,
			Element parent, String child, boolean value) {
		return findElementAndSetElseCreateAndSet(document, parent, child, ""
				+ value);
	}

	public static Element findElementAndSetElseCreateAndSet(Document document,
			Element parent, String child, float value) {
		return findElementAndSetElseCreateAndSet(document, parent, child, ""
				+ value);
	}

	public static Element createNewElementAndSet(Document document,
			Element parent, String childElement, String childValue) {
		Element child = (Element) document.createElement(childElement);
		parent.appendChild(child);
		child.setNodeValue(childValue);
		child.appendChild(document.createTextNode(childValue));
		return child;
	}

	public static Element createNewElementAndSetAndAttribute(Document document,
			Element parent, String childElement, String childValue,
			String attributeName, String attributeValue) {
		Element child = createNewElementAndSet(document, parent, childElement,
				childValue);
		child.setAttribute(attributeName, attributeValue);
		return child;
	}

	public static Element createNewElementAndSet(Document document,
			Element parent, String childElement, float childValue) {
		return createNewElementAndSet(document, parent, childElement, ""
				+ childValue);
	}

	public static Element createNewElementAndSet(Document document,
			Element parent, String childElement, int childValue) {
		return createNewElementAndSet(document, parent, childElement, ""
				+ childValue);
	}

	public static Element createNewElementAndSet(Document document,
			Element parent, String childElement, boolean childValue) {
		return createNewElementAndSet(document, parent, childElement, ""
				+ childValue);
	}

	public static Element createNewElementAndSet(Document document,
			Element parent, String childElement, double childValue) {
		return createNewElementAndSet(document, parent, childElement, ""
				+ childValue);
	}

	public static String getElementStringValue(Document document,
			Element parent, String element) {
		NodeList nl = parent.getElementsByTagName(element);
		if (nl.getLength() == 0) {
			return "";
		}

		Node n = nl.item(0).getFirstChild();
		if (n == null) {
			return "";
		}

		return n.getNodeValue();
	}

	public static boolean getElementBooleanValue(Document document,
			Element parent, String element) {
		return Boolean
				.valueOf(getElementStringValue(document, parent, element))
				.booleanValue();
	}

	public static float getElementFloatValue(Document document, Element parent,
			String element) {
		return Float
				.parseFloat(getElementStringValue(document, parent, element));
	}

	public static void importElements(Document document, Element parent,
			Element[] children) {
		for (int i = 0; i < children.length; i++) {
			parent.appendChild(document.importNode(children[i], true));
		}
	}

	public static Date getElementDateValue(Document document, Element parent,
			String string) {
		return parseDate(getElementStringValue(document, parent, string));
	}

	public static long getElementLongValue(Document document, Element parent,
			String string) {
		return Long.parseLong(getElementStringValue(document, parent, string));
	}

	public static int getElementIntValue(Document document, Element parent,
			String string) {
		return Integer
				.parseInt(getElementStringValue(document, parent, string));
	}

	public static Element findElementAndSetElseCreateAndSet(Document document,
			Element parent, String element, Date date) {
		return findElementAndSetElseCreateAndSet(document, parent, element,
				getDateString(date));
	}

	public static Element[] getElements(Document document, Element parent) {
		if (parent == null) {
			return new Element[] {};
		}

		NodeList nl = parent.getChildNodes();
		ArrayList al = new ArrayList();

		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);

			if (n instanceof Element) {
				al.add((Element) nl.item(i));
			}
		}

		Element[] ret = new Element[al.size()];
		Iterator it = al.iterator();
		int i = 0;
		while (it.hasNext()) {
			ret[i] = (Element) it.next();
			i++;
		}

		return ret;
	}

	public static Element findContainerWithAttributeValueElseCreate(
			Document document, Element parent, String element,
			String attributeName, String attributeValue) {

		NodeList nl = parent.getElementsByTagName(element);
		Element e;
		for (int i = 0; i < nl.getLength(); i++) {
			e = (Element) nl.item(i);
			if (e.getAttribute(attributeName).equals(attributeValue)) {
				return e;
			}
		}

		e = document.createElement(element);
		parent.appendChild(e);
		e.setAttribute(attributeName, attributeValue);

		return e;
	}

	public static Element findContainerWithAttributeValueElseCreateAndSet(
			Document document, Element parent, String element, String value,
			String attributeName, String attributeValue) {

		Element e = findContainerWithAttributeValueElseCreate(document, parent,
				element, attributeName, attributeValue);
		e.appendChild(document.createTextNode(value));

		return e;
	}

	public static Element findElementElseCreateAndAttribute(Document document,
			Element parent, String element, String attributeName,
			String attributeValue) {
		NodeList nl = parent.getElementsByTagName(element);
		Element e = null;

		if (nl.getLength() == 0) {
			parent.appendChild(document.createElement(element));
			e = (Element) parent.getElementsByTagName(element).item(0);
			e.setAttribute(attributeName, attributeValue);
		}

		return e;
	}

	public static Element findElementElseCreateAndSetAndAttribute(
			Document document, Element parent, String element, String value,
			String attributeName, String attributeValue) {

		Element e = findElementElseCreateAndAttribute(document, parent,
				element, attributeName, attributeValue);
		if (e != null)
			e.appendChild(document.createTextNode(value));

		return e;
	}

	public static String documentToString(Document document) {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			StringWriter sw = new StringWriter();
			trans.transform(new DOMSource(document), new StreamResult(sw));
			return sw.toString();
		} catch (TransformerException tEx) {
			tEx.printStackTrace();
		}
		return null;
	}

	public static String documentToStringPretty(Document document) {
		  
	  try {
		StreamSource stylesource = new StreamSource(Utils.class.getResourceAsStream("indent.xsl"));
	    
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer trans = tf.newTransformer(stylesource);
	                       
		StringWriter sw = new StringWriter();
		trans.transform (new DOMSource(document), new StreamResult(sw));
		
		return sw.toString();		
		
      } catch (TransformerException tEx) {
  	    tEx.printStackTrace();
  	  }
  	  return null;
    }

	public static Document newDocumentFromString(String xmlString) {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret = null;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			ret = builder.parse(new InputSource(new StringReader(xmlString)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static Document newDocumentFromInputStream(InputStream in) {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret = null;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			ret = builder.parse(new InputSource(in));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

	public static Date parseDate(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDateString(Date date) {
		if (date == null) {
			return "null";
		}
		return sdf.format(date);
	}
}
