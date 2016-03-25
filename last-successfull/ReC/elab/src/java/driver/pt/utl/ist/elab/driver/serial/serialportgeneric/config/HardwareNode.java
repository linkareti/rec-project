//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.02 at 10:54:22 AM BST 
//

package pt.utl.ist.elab.driver.serial.serialportgeneric.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for HardwareNode complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="HardwareNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rs232" type="{}Rs232Node"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="maxfrequency" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="minfrequency" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="num_channels" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HardwareNode", propOrder = { "rs232" })
public class HardwareNode {

	protected Rs232Node rs232;
	@XmlAttribute(required = true)
	protected String id;
	@XmlAttribute(required = true)
	protected String maxfrequency;
	@XmlAttribute(required = true)
	protected String minfrequency;
	@XmlAttribute(name = "num_channels", required = true)
	protected String numChannels;

	/**
	 * Gets the value of the rs232 property.
	 * 
	 * @return possible object is {@link Rs232Node }
	 * 
	 */
	public Rs232Node getRs232() {
		return rs232;
	}

	/**
	 * Sets the value of the rs232 property.
	 * 
	 * @param value allowed object is {@link Rs232Node }
	 * 
	 */
	public void setRs232(final Rs232Node value) {
		rs232 = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setId(final String value) {
		id = value;
	}

	/**
	 * Gets the value of the maxfrequency property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMaxfrequency() {
		return maxfrequency;
	}

	/**
	 * Sets the value of the maxfrequency property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setMaxfrequency(final String value) {
		maxfrequency = value;
	}

	/**
	 * Gets the value of the minfrequency property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMinfrequency() {
		return minfrequency;
	}

	/**
	 * Sets the value of the minfrequency property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setMinfrequency(final String value) {
		minfrequency = value;
	}

	/**
	 * Gets the value of the numChannels property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNumChannels() {
		return numChannels;
	}

	/**
	 * Sets the value of the numChannels property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNumChannels(final String value) {
		numChannels = value;
	}

}