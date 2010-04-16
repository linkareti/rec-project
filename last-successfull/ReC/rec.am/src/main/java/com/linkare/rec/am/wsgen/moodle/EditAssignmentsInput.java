/**
 * EditAssignmentsInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class EditAssignmentsInput  implements java.io.Serializable {
    private com.linkare.rec.am.wsgen.moodle.AssignmentDatum[] assignments;

    public EditAssignmentsInput() {
    }

    public EditAssignmentsInput(
           com.linkare.rec.am.wsgen.moodle.AssignmentDatum[] assignments) {
           this.assignments = assignments;
    }


    /**
     * Gets the assignments value for this EditAssignmentsInput.
     * 
     * @return assignments
     */
    public com.linkare.rec.am.wsgen.moodle.AssignmentDatum[] getAssignments() {
        return assignments;
    }


    /**
     * Sets the assignments value for this EditAssignmentsInput.
     * 
     * @param assignments
     */
    public void setAssignments(com.linkare.rec.am.wsgen.moodle.AssignmentDatum[] assignments) {
        this.assignments = assignments;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EditAssignmentsInput)) return false;
        EditAssignmentsInput other = (EditAssignmentsInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.assignments==null && other.getAssignments()==null) || 
             (this.assignments!=null &&
              java.util.Arrays.equals(this.assignments, other.getAssignments())));
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
        if (getAssignments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAssignments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAssignments(), i);
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
        new org.apache.axis.description.TypeDesc(EditAssignmentsInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "editAssignmentsInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assignments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assignments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "assignmentDatum"));
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
