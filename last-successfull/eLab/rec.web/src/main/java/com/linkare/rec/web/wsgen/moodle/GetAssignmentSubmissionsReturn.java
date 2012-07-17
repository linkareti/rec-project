/**
 * GetAssignmentSubmissionsReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class GetAssignmentSubmissionsReturn  implements java.io.Serializable {
    private com.linkare.rec.web.wsgen.moodle.AssignmentSubmissionRecord[] submissions;

    public GetAssignmentSubmissionsReturn() {
    }

    public GetAssignmentSubmissionsReturn(
           com.linkare.rec.web.wsgen.moodle.AssignmentSubmissionRecord[] submissions) {
           this.submissions = submissions;
    }


    /**
     * Gets the submissions value for this GetAssignmentSubmissionsReturn.
     * 
     * @return submissions
     */
    public com.linkare.rec.web.wsgen.moodle.AssignmentSubmissionRecord[] getSubmissions() {
        return submissions;
    }


    /**
     * Sets the submissions value for this GetAssignmentSubmissionsReturn.
     * 
     * @param submissions
     */
    public void setSubmissions(com.linkare.rec.web.wsgen.moodle.AssignmentSubmissionRecord[] submissions) {
        this.submissions = submissions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAssignmentSubmissionsReturn)) return false;
        GetAssignmentSubmissionsReturn other = (GetAssignmentSubmissionsReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.submissions==null && other.getSubmissions()==null) || 
             (this.submissions!=null &&
              java.util.Arrays.equals(this.submissions, other.getSubmissions())));
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
        if (getSubmissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubmissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubmissions(), i);
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
        new org.apache.axis.description.TypeDesc(GetAssignmentSubmissionsReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAssignmentSubmissionsReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentSubmissionRecord"));
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
