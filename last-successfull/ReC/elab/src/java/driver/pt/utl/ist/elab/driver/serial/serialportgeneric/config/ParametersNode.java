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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ParametersNode complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ParametersNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{}OneParameterNode" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="input" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="maxvalue" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="minvalue" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="order" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="output" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametersNode", propOrder = { "parameter" })
public class ParametersNode {

	protected List<OneParameterNode> parameter;

	/**
	 * Gets the value of the parameter property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the parameter property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getParameter().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link OneParameterNode }
	 * 
	 * @return List<OneParameterNode>
	 * 
	 * 
	 */
	public List<OneParameterNode> getParameter() {
		if (parameter == null) {
			parameter = new ArrayList<OneParameterNode>();
		}
		return parameter;
	}

	public OneParameterNode getParameterToOrder(final int order) {
		if (parameter == null) {
			return new OneParameterNode();
		}
		for (final OneParameterNode oneParameterNode : parameter) {
			if (oneParameterNode.getOrder().intValue() == order) {
				return oneParameterNode;
			}
		}
		return new OneParameterNode();
	}

}
