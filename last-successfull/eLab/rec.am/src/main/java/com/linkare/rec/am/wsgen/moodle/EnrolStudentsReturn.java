/**
 * EnrolStudentsReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class EnrolStudentsReturn  implements java.io.Serializable {
    private com.linkare.rec.am.wsgen.moodle.EnrolRecord[] students;

    public EnrolStudentsReturn() {
    }

    public EnrolStudentsReturn(
           com.linkare.rec.am.wsgen.moodle.EnrolRecord[] students) {
           this.students = students;
    }


    /**
     * Gets the students value for this EnrolStudentsReturn.
     * 
     * @return students
     */
    public com.linkare.rec.am.wsgen.moodle.EnrolRecord[] getStudents() {
        return students;
    }


    /**
     * Sets the students value for this EnrolStudentsReturn.
     * 
     * @param students
     */
    public void setStudents(com.linkare.rec.am.wsgen.moodle.EnrolRecord[] students) {
        this.students = students;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnrolStudentsReturn)) return false;
        EnrolStudentsReturn other = (EnrolStudentsReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.students==null && other.getStudents()==null) || 
             (this.students!=null &&
              java.util.Arrays.equals(this.students, other.getStudents())));
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
        if (getStudents() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudents());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudents(), i);
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
        new org.apache.axis.description.TypeDesc(EnrolStudentsReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "enrolStudentsReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("students");
        elemField.setXmlName(new javax.xml.namespace.QName("", "students"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "enrolRecord"));
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
