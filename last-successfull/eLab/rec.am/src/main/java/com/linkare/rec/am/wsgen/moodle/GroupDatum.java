/**
 * GroupDatum.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class GroupDatum  implements java.io.Serializable {
    private java.lang.String action;

    private java.math.BigInteger id;

    private java.math.BigInteger courseid;

    private java.lang.String name;

    private java.lang.String description;

    private java.lang.String enrolmentkey;

    private java.math.BigInteger picture;

    private java.math.BigInteger hidepicture;

    public GroupDatum() {
    }

    public GroupDatum(
           java.lang.String action,
           java.math.BigInteger id,
           java.math.BigInteger courseid,
           java.lang.String name,
           java.lang.String description,
           java.lang.String enrolmentkey,
           java.math.BigInteger picture,
           java.math.BigInteger hidepicture) {
           this.action = action;
           this.id = id;
           this.courseid = courseid;
           this.name = name;
           this.description = description;
           this.enrolmentkey = enrolmentkey;
           this.picture = picture;
           this.hidepicture = hidepicture;
    }


    /**
     * Gets the action value for this GroupDatum.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this GroupDatum.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the id value for this GroupDatum.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this GroupDatum.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the courseid value for this GroupDatum.
     * 
     * @return courseid
     */
    public java.math.BigInteger getCourseid() {
        return courseid;
    }


    /**
     * Sets the courseid value for this GroupDatum.
     * 
     * @param courseid
     */
    public void setCourseid(java.math.BigInteger courseid) {
        this.courseid = courseid;
    }


    /**
     * Gets the name value for this GroupDatum.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GroupDatum.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the description value for this GroupDatum.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this GroupDatum.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the enrolmentkey value for this GroupDatum.
     * 
     * @return enrolmentkey
     */
    public java.lang.String getEnrolmentkey() {
        return enrolmentkey;
    }


    /**
     * Sets the enrolmentkey value for this GroupDatum.
     * 
     * @param enrolmentkey
     */
    public void setEnrolmentkey(java.lang.String enrolmentkey) {
        this.enrolmentkey = enrolmentkey;
    }


    /**
     * Gets the picture value for this GroupDatum.
     * 
     * @return picture
     */
    public java.math.BigInteger getPicture() {
        return picture;
    }


    /**
     * Sets the picture value for this GroupDatum.
     * 
     * @param picture
     */
    public void setPicture(java.math.BigInteger picture) {
        this.picture = picture;
    }


    /**
     * Gets the hidepicture value for this GroupDatum.
     * 
     * @return hidepicture
     */
    public java.math.BigInteger getHidepicture() {
        return hidepicture;
    }


    /**
     * Sets the hidepicture value for this GroupDatum.
     * 
     * @param hidepicture
     */
    public void setHidepicture(java.math.BigInteger hidepicture) {
        this.hidepicture = hidepicture;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GroupDatum)) return false;
        GroupDatum other = (GroupDatum) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
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
            ((this.enrolmentkey==null && other.getEnrolmentkey()==null) || 
             (this.enrolmentkey!=null &&
              this.enrolmentkey.equals(other.getEnrolmentkey()))) &&
            ((this.picture==null && other.getPicture()==null) || 
             (this.picture!=null &&
              this.picture.equals(other.getPicture()))) &&
            ((this.hidepicture==null && other.getHidepicture()==null) || 
             (this.hidepicture!=null &&
              this.hidepicture.equals(other.getHidepicture())));
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
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
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
        if (getEnrolmentkey() != null) {
            _hashCode += getEnrolmentkey().hashCode();
        }
        if (getPicture() != null) {
            _hashCode += getPicture().hashCode();
        }
        if (getHidepicture() != null) {
            _hashCode += getHidepicture().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GroupDatum.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "groupDatum"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
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
        elemField.setFieldName("enrolmentkey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enrolmentkey"));
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
