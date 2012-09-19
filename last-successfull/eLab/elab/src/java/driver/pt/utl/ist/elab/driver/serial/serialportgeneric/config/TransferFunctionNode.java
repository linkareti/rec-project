//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.02 at 10:54:22 AM BST 
//

package pt.utl.ist.elab.driver.serial.serialportgeneric.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for TransferFunctionNode complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="TransferFunctionNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="linear" type="{}LinearFunctionNode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="power" type="{}PowerFunctionNode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="exponential" type="{}ExpFunctionNode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="logarithm" type="{}LogFunctionNode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sin" type="{}SinFunctionNode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tg" type="{}TgFunctionNode" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferFunctionNode", propOrder = { "linear", "power", "exponential", "logarithm", "sin", "tg" })
public class TransferFunctionNode {

	protected List<LinearFunctionNode> linear;
	protected List<PowerFunctionNode> power;
	protected List<ExpFunctionNode> exponential;
	protected List<LogFunctionNode> logarithm;
	protected List<SinFunctionNode> sin;
	protected List<TgFunctionNode> tg;
	@XmlAttribute(required = true)
	protected String type;

	/**
	 * Gets the value of the linear property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the linear property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLinear().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link LinearFunctionNode }
	 * 
	 * @return
	 * 
	 * 
	 */
	public List<LinearFunctionNode> getLinear() {
		if (linear == null) {
			linear = new ArrayList<LinearFunctionNode>();
		}
		return linear;
	}

	/**
	 * Gets the value of the power property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the power property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPower().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link PowerFunctionNode }
	 * 
	 * @return
	 * 
	 * 
	 */
	public List<PowerFunctionNode> getPower() {
		if (power == null) {
			power = new ArrayList<PowerFunctionNode>();
		}
		return power;
	}

	/**
	 * Gets the value of the exponential property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the exponential property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getExponential().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ExpFunctionNode }
	 * 
	 * @return
	 * 
	 * 
	 */
	public List<ExpFunctionNode> getExponential() {
		if (exponential == null) {
			exponential = new ArrayList<ExpFunctionNode>();
		}
		return exponential;
	}

	/**
	 * Gets the value of the logarithm property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the logarithm property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLogarithm().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link LogFunctionNode }
	 * 
	 * @return
	 * 
	 * 
	 */
	public List<LogFunctionNode> getLogarithm() {
		if (logarithm == null) {
			logarithm = new ArrayList<LogFunctionNode>();
		}
		return logarithm;
	}

	/**
	 * Gets the value of the sin property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the sin property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getSin().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link SinFunctionNode }
	 * 
	 * @return
	 * 
	 * 
	 */
	public List<SinFunctionNode> getSin() {
		if (sin == null) {
			sin = new ArrayList<SinFunctionNode>();
		}
		return sin;
	}

	/**
	 * Gets the value of the tg property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the tg property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getTg().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link TgFunctionNode }
	 * 
	 * @return
	 * 
	 * 
	 */
	public List<TgFunctionNode> getTg() {
		if (tg == null) {
			tg = new ArrayList<TgFunctionNode>();
		}
		return tg;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setType(final String value) {
		type = value;
	}

}
