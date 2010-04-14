/**
 * GroupingRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class GroupingRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.math.BigInteger courseid;

    private java.lang.String name;

    private java.lang.String description;

    private java.lang.String configdata;

    private java.math.BigInteger timecreated;

    private java.math.BigInteger timemodified;

    public GroupingRecord() {
    }

    public GroupingRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.math.BigInteger courseid,
           java.lang.String name,
           java.lang.String description,
           java.lang.String configdata,
           java.math.BigInteger timecreated,
           java.math.BigInteger timemodified) {
           this.error = error;
           this.id = id;
           this.courseid = courseid;
           this.name = name;
           this.description = description;
           this.configdata = configdata;
           this.timecreated = timecreated;
           this.timemodified = timemodified;
    }


    /**
     * Gets the error value for this GroupingRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this GroupingRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this GroupingRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this GroupingRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the courseid value for this GroupingRecord.
     * 
     * @return courseid
     */
    public java.math.BigInteger getCourseid() {
        return courseid;
    }


    /**
     * Sets the courseid value for this GroupingRecord.
     * 
     * @param courseid
     */
    public void setCourseid(java.math.BigInteger courseid) {
        this.courseid = courseid;
    }


    /**
     * Gets the name value for this GroupingRecord.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GroupingRecord.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the description value for this GroupingRecord.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this GroupingRecord.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the configdata value for this GroupingRecord.
     * 
     * @return configdata
     */
    public java.lang.String getConfigdata() {
        return configdata;
    }


    /**
     * Sets the configdata value for this GroupingRecord.
     * 
     * @param configdata
     */
    public void setConfigdata(java.lang.String configdata) {
        this.configdata = configdata;
    }


    /**
     * Gets the timecreated value for this GroupingRecord.
     * 
     * @return timecreated
     */
    public java.math.BigInteger getTimecreated() {
        return timecreated;
    }


    /**
     * Sets the timecreated value for this GroupingRecord.
     * 
     * @param timecreated
     */
    public void setTimecreated(java.math.BigInteger timecreated) {
        this.timecreated = timecreated;
    }


    /**
     * Gets the timemodified value for this GroupingRecord.
     * 
     * @return timemodified
     */
    public java.math.BigInteger getTimemodified() {
        return timemodified;
    }


    /**
     * Sets the timemodified value for this GroupingRecord.
     * 
     * @param timemodified
     */
    public void setTimemodified(java.math.BigInteger timemodified) {
        this.timemodified = timemodified;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GroupingRecord)) return false;
        GroupingRecord other = (GroupingRecord) obj;
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
            ((this.configdata==null && other.getConfigdata()==null) || 
             (this.configdata!=null &&
              this.configdata.equals(other.getConfigdata()))) &&
            ((this.timecreated==null && other.getTimecreated()==null) || 
             (this.timecreated!=null &&
              this.timecreated.equals(other.getTimecreated()))) &&
            ((this.timemodified==null && other.getTimemodified()==null) || 
             (this.timemodified!=null &&
              this.timemodified.equals(other.getTimemodified())));
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
        if (getConfigdata() != null) {
            _hashCode += getConfigdata().hashCode();
        }
        if (getTimecreated() != null) {
            _hashCode += getTimecreated().hashCode();
        }
        if (getTimemodified() != null) {
            _hashCode += getTimemodified().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GroupingRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "groupingRecord"));
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
        elemField.setFieldName("configdata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "configdata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
