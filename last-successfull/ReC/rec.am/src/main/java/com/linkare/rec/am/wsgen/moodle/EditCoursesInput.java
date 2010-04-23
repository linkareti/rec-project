/**
 * EditCoursesInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class EditCoursesInput  implements java.io.Serializable {
    private com.linkare.rec.am.wsgen.moodle.CourseDatum[] courses;

    public EditCoursesInput() {
    }

    public EditCoursesInput(
           com.linkare.rec.am.wsgen.moodle.CourseDatum[] courses) {
           this.courses = courses;
    }


    /**
     * Gets the courses value for this EditCoursesInput.
     * 
     * @return courses
     */
    public com.linkare.rec.am.wsgen.moodle.CourseDatum[] getCourses() {
        return courses;
    }


    /**
     * Sets the courses value for this EditCoursesInput.
     * 
     * @param courses
     */
    public void setCourses(com.linkare.rec.am.wsgen.moodle.CourseDatum[] courses) {
        this.courses = courses;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EditCoursesInput)) return false;
        EditCoursesInput other = (EditCoursesInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.courses==null && other.getCourses()==null) || 
             (this.courses!=null &&
              java.util.Arrays.equals(this.courses, other.getCourses())));
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
        if (getCourses() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCourses());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCourses(), i);
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
        new org.apache.axis.description.TypeDesc(EditCoursesInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "editCoursesInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("courses");
        elemField.setXmlName(new javax.xml.namespace.QName("", "courses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "courseDatum"));
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
