/**
 * EditLabelsInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class EditLabelsInput  implements java.io.Serializable {
    private com.linkare.rec.web.wsgen.moodle.LabelDatum[] labels;

    public EditLabelsInput() {
    }

    public EditLabelsInput(
           com.linkare.rec.web.wsgen.moodle.LabelDatum[] labels) {
           this.labels = labels;
    }


    /**
     * Gets the labels value for this EditLabelsInput.
     * 
     * @return labels
     */
    public com.linkare.rec.web.wsgen.moodle.LabelDatum[] getLabels() {
        return labels;
    }


    /**
     * Sets the labels value for this EditLabelsInput.
     * 
     * @param labels
     */
    public void setLabels(com.linkare.rec.web.wsgen.moodle.LabelDatum[] labels) {
        this.labels = labels;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EditLabelsInput)) return false;
        EditLabelsInput other = (EditLabelsInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.labels==null && other.getLabels()==null) || 
             (this.labels!=null &&
              java.util.Arrays.equals(this.labels, other.getLabels())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLabels() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLabels());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLabels(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EditLabelsInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editLabelsInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labels");
        elemField.setXmlName(new javax.xml.namespace.QName("", "labels"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelDatum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
