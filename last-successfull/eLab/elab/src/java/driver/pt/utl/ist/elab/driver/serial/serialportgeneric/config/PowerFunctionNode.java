//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.02 at 10:54:22 AM BST 
//

package pt.utl.ist.elab.driver.serial.serialportgeneric.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for PowerFunctionNode complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PowerFunctionNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="param" type="{}ParamValuesNode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PowerFunctionNode", propOrder = { "param" })
public class PowerFunctionNode {

	protected ParamValuesNode param;

	/**
	 * Gets the value of the param property.
	 * 
	 * @return possible object is {@link ParamValuesNode }
	 * 
	 */
	public ParamValuesNode getParam() {
		return param;
	}

	/**
	 * Sets the value of the param property.
	 * 
	 * @param value allowed object is {@link ParamValuesNode }
	 * 
	 */
	public void setParam(ParamValuesNode value) {
		this.param = value;
	}

}
