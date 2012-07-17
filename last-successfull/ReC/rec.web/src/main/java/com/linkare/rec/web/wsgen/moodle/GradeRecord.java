/**
 * GradeRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class GradeRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.lang.String itemid;

    private float grade;

    private java.lang.String str_grade;

    private java.lang.String feedback;

    public GradeRecord() {
    }

    public GradeRecord(
           java.lang.String error,
           java.lang.String itemid,
           float grade,
           java.lang.String str_grade,
           java.lang.String feedback) {
           this.error = error;
           this.itemid = itemid;
           this.grade = grade;
           this.str_grade = str_grade;
           this.feedback = feedback;
    }


    /**
     * Gets the error value for this GradeRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this GradeRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the itemid value for this GradeRecord.
     * 
     * @return itemid
     */
    public java.lang.String getItemid() {
        return itemid;
    }


    /**
     * Sets the itemid value for this GradeRecord.
     * 
     * @param itemid
     */
    public void setItemid(java.lang.String itemid) {
        this.itemid = itemid;
    }


    /**
     * Gets the grade value for this GradeRecord.
     * 
     * @return grade
     */
    public float getGrade() {
        return grade;
    }


    /**
     * Sets the grade value for this GradeRecord.
     * 
     * @param grade
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }


    /**
     * Gets the str_grade value for this GradeRecord.
     * 
     * @return str_grade
     */
    public java.lang.String getStr_grade() {
        return str_grade;
    }


    /**
     * Sets the str_grade value for this GradeRecord.
     * 
     * @param str_grade
     */
    public void setStr_grade(java.lang.String str_grade) {
        this.str_grade = str_grade;
    }


    /**
     * Gets the feedback value for this GradeRecord.
     * 
     * @return feedback
     */
    public java.lang.String getFeedback() {
        return feedback;
    }


    /**
     * Sets the feedback value for this GradeRecord.
     * 
     * @param feedback
     */
    public void setFeedback(java.lang.String feedback) {
        this.feedback = feedback;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GradeRecord)) return false;
        GradeRecord other = (GradeRecord) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            ((this.itemid==null && other.getItemid()==null) || 
             (this.itemid!=null &&
              this.itemid.equals(other.getItemid()))) &&
            this.grade == other.getGrade() &&
            ((this.str_grade==null && other.getStr_grade()==null) || 
             (this.str_grade!=null &&
              this.str_grade.equals(other.getStr_grade()))) &&
            ((this.feedback==null && other.getFeedback()==null) || 
             (this.feedback!=null &&
              this.feedback.equals(other.getFeedback())));
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
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        if (getItemid() != null) {
            _hashCode += getItemid().hashCode();
        }
        _hashCode += new Float(getGrade()).hashCode();
        if (getStr_grade() != null) {
            _hashCode += getStr_grade().hashCode();
        }
        if (getFeedback() != null) {
            _hashCode += getFeedback().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GradeRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "gradeRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itemid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "grade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("str_grade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "str_grade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feedback");
        elemField.setXmlName(new javax.xml.namespace.QName("", "feedback"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
