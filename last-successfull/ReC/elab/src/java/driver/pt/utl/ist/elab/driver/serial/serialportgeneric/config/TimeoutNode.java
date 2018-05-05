//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.02 at 10:54:22 AM BST 
//

package pt.utl.ist.elab.driver.serial.serialportgeneric.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for TimeoutNode complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="TimeoutNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="default_timeout" type="{}OneTimeoutNode"/>
 *         &lt;element name="id" type="{}OneTimeoutNode"/>
 *         &lt;element name="cfg" type="{}OneTimeoutNode"/>
 *         &lt;element name="cur" type="{}OneTimeoutNode"/>
 *         &lt;element name="str" type="{}OneTimeoutNode"/>
 *         &lt;element name="dat_bin" type="{}OneTimeoutNode"/>
 *         &lt;element name="no_data" type="{}OneTimeoutNode"/>
 *         &lt;element name="stp" type="{}OneTimeoutNode"/>
 *         &lt;element name="rst" type="{}OneTimeoutNode"/>
 *         &lt;element name="hardware_died" type="{}OneTimeoutNode"/>
 *         &lt;element name="listen_to_port" type="{}OneTimeoutNode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeoutNode", propOrder = { "defaultTimeout", "id", "cfg", "cur", "str", "datBin", "noData", "end",
		"stp", "rst", "hardwareDied", "portListen" })
public class TimeoutNode {

	@XmlElement(name = "default_timeout", required = true)
	protected OneTimeoutNode defaultTimeout;
	@XmlElement(required = true)
	protected OneTimeoutNode id;
	@XmlElement(required = true)
	protected OneTimeoutNode cfg;
	@XmlElement(required = true)
	protected OneTimeoutNode cur;
	@XmlElement(required = true)
	protected OneTimeoutNode str;
	@XmlElement(name = "dat_bin", required = true)
	protected OneTimeoutNode datBin;
	@XmlElement(name = "no_data", required = true)
	protected OneTimeoutNode noData;
	@XmlElement(required = true)
	protected OneTimeoutNode end;
	@XmlElement(required = true)
	protected OneTimeoutNode stp;
	@XmlElement(required = true)
	protected OneTimeoutNode rst;
	@XmlElement(name = "hardware_died", required = true)
	protected OneTimeoutNode hardwareDied;
	@XmlElement(name = "listen_to_port", required = true)
	protected OneTimeoutNode portListen;

	/**
	 * Gets the value of the defaultTimeout property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getDefaultTimeout() {
		return defaultTimeout;
	}

	/**
	 * Sets the value of the defaultTimeout property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setDefaultTimeout(final OneTimeoutNode value) {
		defaultTimeout = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setId(final OneTimeoutNode value) {
		id = value;
	}

	/**
	 * Gets the value of the cfg property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getCfg() {
		return cfg;
	}

	/**
	 * Sets the value of the cfg property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setCfg(final OneTimeoutNode value) {
		cfg = value;
	}

	/**
	 * Gets the value of the cur property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getCur() {
		return cur;
	}

	/**
	 * Sets the value of the cur property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setCur(final OneTimeoutNode value) {
		cur = value;
	}

	/**
	 * Gets the value of the str property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getStr() {
		return str;
	}

	/**
	 * Sets the value of the str property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setStr(final OneTimeoutNode value) {
		str = value;
	}

	/**
	 * Gets the value of the datBin property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getDatBin() {
		return datBin;
	}

	/**
	 * Sets the value of the datBin property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setDatBin(final OneTimeoutNode value) {
		datBin = value;
	}

	/**
	 * Gets the value of the datNoData property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getNoData() {
		return noData;
	}

	/**
	 * Sets the value of the datNoData property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setNoData(final OneTimeoutNode value) {
		noData = value;
	}

	/**
	 * Gets the value of the end property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getEnd() {
		return end;
	}

	/**
	 * Sets the value of the end property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setEnd(final OneTimeoutNode value) {
		end = value;
	}

	/**
	 * Gets the value of the stp property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getStp() {
		return stp;
	}

	/**
	 * Sets the value of the stp property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setStp(final OneTimeoutNode value) {
		stp = value;
	}

	/**
	 * Gets the value of the rst property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getRst() {
		return rst;
	}

	/**
	 * Sets the value of the rst property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setRst(final OneTimeoutNode value) {
		rst = value;
	}

	/**
	 * Gets the value of the hardwareDied property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getHardwareDied() {
		return hardwareDied;
	}

	/**
	 * Sets the value of the hardwareDied property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setHardwareDied(final OneTimeoutNode value) {
		hardwareDied = value;
	}

	/**
	 * Gets the value of the portListen property.
	 * 
	 * @return possible object is {@link OneTimeoutNode }
	 * 
	 */
	public OneTimeoutNode getPortListen() {
		return portListen;
	}

	/**
	 * Sets the value of the portListen property.
	 * 
	 * @param value allowed object is {@link OneTimeoutNode }
	 * 
	 */
	public void setPortListen(final OneTimeoutNode value) {
		portListen = value;
	}

}
