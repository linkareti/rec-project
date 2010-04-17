/**
 * GroupRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class GroupRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.math.BigInteger courseid;

    private java.lang.String name;

    private java.lang.String description;

    private java.math.BigInteger picture;

    private java.math.BigInteger hidepicture;

    private java.math.BigInteger timecreated;

    private java.math.BigInteger timemodified;

    private java.lang.String enrolmentkey;

    public GroupRecord() {
    }

    public GroupRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.math.BigInteger courseid,
           java.lang.String name,
           java.lang.String description,
           java.math.BigInteger picture,
           java.math.BigInteger hidepicture,
           java.math.BigInteger timecreated,
           java.math.BigInteger timemodified,
           java.lang.String enrolmentkey) {
           this.error = error;
           this.id = id;
           this.courseid = courseid;
           this.name = name;
           this.description = description;
           this.picture = picture;
           this.hidepicture = hidepicture;
           this.timecreated = timecreated;
           this.timemodified = timemodified;
           this.enrolmentkey = enrolmentkey;
    }


    /**
     * Gets the error value for this GroupRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this GroupRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this GroupRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this GroupRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the courseid value for this GroupRecord.
     * 
     * @return courseid
     */
    public java.math.BigInteger getCourseid() {
        return courseid;
    }


    /**
     * Sets the courseid value for this GroupRecord.
     * 
     * @param courseid
     */
    public void setCourseid(java.math.BigInteger courseid) {
        this.courseid = courseid;
    }


    /**
     * Gets the name value for this GroupRecord.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GroupRecord.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the description value for this GroupRecord.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this GroupRecord.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the picture value for this GroupRecord.
     * 
     * @return picture
     */
    public java.math.BigInteger getPicture() {
        return picture;
    }


    /**
     * Sets the picture value for this GroupRecord.
     * 
     * @param picture
     */
    public void setPicture(java.math.BigInteger picture) {
        this.picture = picture;
    }


    /**
     * Gets the hidepicture value for this GroupRecord.
     * 
     * @return hidepicture
     */
    public java.math.BigInteger getHidepicture() {
        return hidepicture;
    }


    /**
     * Sets the hidepicture value for this GroupRecord.
     * 
     * @param hidepicture
     */
    public void setHidepicture(java.math.BigInteger hidepicture) {
        this.hidepicture = hidepicture;
    }


    /**
     * Gets the timecreated value for this GroupRecord.
     * 
     * @return timecreated
     */
    public java.math.BigInteger getTimecreated() {
        return timecreated;
    }


    /**
     * Sets the timecreated value for this GroupRecord.
     * 
     * @param timecreated
     */
    public void setTimecreated(java.math.BigInteger timecreated) {
        this.timecreated = timecreated;
    }


    /**
     * Gets the timemodified value for this GroupRecord.
     * 
     * @return timemodified
     */
    public java.math.BigInteger getTimemodified() {
        return timemodified;
    }


    /**
     * Sets the timemodified value for this GroupRecord.
     * 
     * @param timemodified
     */
    public void setTimemodified(java.math.BigInteger timemodified) {
        this.timemodified = timemodified;
    }


    /**
     * Gets the enrolmentkey value for this GroupRecord.
     * 
     * @return enrolmentkey
     */
    public java.lang.String getEnrolmentkey() {
        return enrolmentkey;
    }


    /**
     * Sets the enrolmentkey value for this GroupRecord.
     * 
     * @param enrolmentkey
     */
    public void setEnrolmentkey(java.lang.String enrolmentkey) {
        this.enrolmentkey = enrolmentkey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GroupRecord)) return false;
        GroupRecord other = (GroupRecord) obj;
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
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.courseid==null && other.getCourseid()==null) || 
             (this.courseid!=null &&
              this.courseid.equals(other.getCourseid()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.picture==null && other.getPicture()==null) || 
             (this.picture!=null &&
              this.picture.equals(other.getPicture()))) &&
            ((this.hidepicture==null && other.getHidepicture()==null) || 
             (this.hidepicture!=null &&
              this.hidepicture.equals(other.getHidepicture()))) &&
            ((this.timecreated==null && other.getTimecreated()==null) || 
             (this.timecreated!=null &&
              this.timecreated.equals(other.getTimecreated()))) &&
            ((this.timemodified==null && other.getTimemodified()==null) || 
             (this.timemodified!=null &&
              this.timemodified.equals(other.getTimemodified()))) &&
            ((this.enrolmentkey==null && other.getEnrolmentkey()==null) || 
             (this.enrolmentkey!=null &&
              this.enrolmentkey.equals(other.getEnrolmentkey())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getCourseid() != null) {
            _hashCode += getCourseid().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getPicture() != null) {
            _hashCode += getPicture().hashCode();
        }
        if (getHidepicture() != null) {
            _hashCode += getHidepicture().hashCode();
        }
        if (getTimecreated() != null) {
            _hashCode += getTimecreated().hashCode();
        }
        if (getTimemodified() != null) {
            _hashCode += getTimemodified().hashCode();
        }
        if (getEnrolmentkey() != null) {
            _hashCode += getEnrolmentkey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GroupRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "groupRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("courseid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "courseid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("picture");
        elemField.setXmlName(new javax.xml.namespace.QName("", "picture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hidepicture");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hidepicture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timecreated");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timecreated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timemodified");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timemodified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enrolmentkey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enrolmentkey"));
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
