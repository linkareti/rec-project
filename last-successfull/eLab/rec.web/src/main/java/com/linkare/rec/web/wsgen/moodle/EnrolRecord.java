/**
 * EnrolRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class EnrolRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.lang.String userid;

    private java.lang.String course;

    private java.math.BigInteger timestart;

    private java.math.BigInteger timeend;

    private java.math.BigInteger timeaccess;

    private java.lang.String enrol;

    public EnrolRecord() {
    }

    public EnrolRecord(
           java.lang.String error,
           java.lang.String userid,
           java.lang.String course,
           java.math.BigInteger timestart,
           java.math.BigInteger timeend,
           java.math.BigInteger timeaccess,
           java.lang.String enrol) {
           this.error = error;
           this.userid = userid;
           this.course = course;
           this.timestart = timestart;
           this.timeend = timeend;
           this.timeaccess = timeaccess;
           this.enrol = enrol;
    }


    /**
     * Gets the error value for this EnrolRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this EnrolRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the userid value for this EnrolRecord.
     * 
     * @return userid
     */
    public java.lang.String getUserid() {
        return userid;
    }


    /**
     * Sets the userid value for this EnrolRecord.
     * 
     * @param userid
     */
    public void setUserid(java.lang.String userid) {
        this.userid = userid;
    }


    /**
     * Gets the course value for this EnrolRecord.
     * 
     * @return course
     */
    public java.lang.String getCourse() {
        return course;
    }


    /**
     * Sets the course value for this EnrolRecord.
     * 
     * @param course
     */
    public void setCourse(java.lang.String course) {
        this.course = course;
    }


    /**
     * Gets the timestart value for this EnrolRecord.
     * 
     * @return timestart
     */
    public java.math.BigInteger getTimestart() {
        return timestart;
    }


    /**
     * Sets the timestart value for this EnrolRecord.
     * 
     * @param timestart
     */
    public void setTimestart(java.math.BigInteger timestart) {
        this.timestart = timestart;
    }


    /**
     * Gets the timeend value for this EnrolRecord.
     * 
     * @return timeend
     */
    public java.math.BigInteger getTimeend() {
        return timeend;
    }


    /**
     * Sets the timeend value for this EnrolRecord.
     * 
     * @param timeend
     */
    public void setTimeend(java.math.BigInteger timeend) {
        this.timeend = timeend;
    }


    /**
     * Gets the timeaccess value for this EnrolRecord.
     * 
     * @return timeaccess
     */
    public java.math.BigInteger getTimeaccess() {
        return timeaccess;
    }


    /**
     * Sets the timeaccess value for this EnrolRecord.
     * 
     * @param timeaccess
     */
    public void setTimeaccess(java.math.BigInteger timeaccess) {
        this.timeaccess = timeaccess;
    }


    /**
     * Gets the enrol value for this EnrolRecord.
     * 
     * @return enrol
     */
    public java.lang.String getEnrol() {
        return enrol;
    }


    /**
     * Sets the enrol value for this EnrolRecord.
     * 
     * @param enrol
     */
    public void setEnrol(java.lang.String enrol) {
        this.enrol = enrol;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnrolRecord)) return false;
        EnrolRecord other = (EnrolRecord) obj;
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
            ((this.userid==null && other.getUserid()==null) || 
             (this.userid!=null &&
              this.userid.equals(other.getUserid()))) &&
            ((this.course==null && other.getCourse()==null) || 
             (this.course!=null &&
              this.course.equals(other.getCourse()))) &&
            ((this.timestart==null && other.getTimestart()==null) || 
             (this.timestart!=null &&
              this.timestart.equals(other.getTimestart()))) &&
            ((this.timeend==null && other.getTimeend()==null) || 
             (this.timeend!=null &&
              this.timeend.equals(other.getTimeend()))) &&
            ((this.timeaccess==null && other.getTimeaccess()==null) || 
             (this.timeaccess!=null &&
              this.timeaccess.equals(other.getTimeaccess()))) &&
            ((this.enrol==null && other.getEnrol()==null) || 
             (this.enrol!=null &&
              this.enrol.equals(other.getEnrol())));
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
        if (getUserid() != null) {
            _hashCode += getUserid().hashCode();
        }
        if (getCourse() != null) {
            _hashCode += getCourse().hashCode();
        }
        if (getTimestart() != null) {
            _hashCode += getTimestart().hashCode();
        }
        if (getTimeend() != null) {
            _hashCode += getTimeend().hashCode();
        }
        if (getTimeaccess() != null) {
            _hashCode += getTimeaccess().hashCode();
        }
        if (getEnrol() != null) {
            _hashCode += getEnrol().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnrolRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("course");
        elemField.setXmlName(new javax.xml.namespace.QName("", "course"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestart");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timestart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeend");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeend"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeaccess");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeaccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enrol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enrol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
