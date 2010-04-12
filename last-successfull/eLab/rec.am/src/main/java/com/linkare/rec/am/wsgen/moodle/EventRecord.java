/**
 * EventRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class EventRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.lang.String name;

    private java.lang.String description;

    private java.math.BigInteger format;

    private java.math.BigInteger courseid;

    private java.math.BigInteger groupid;

    private java.math.BigInteger userid;

    private java.math.BigInteger repeatid;

    private java.lang.String modulename;

    private java.math.BigInteger instance;

    private java.lang.String eventtype;

    private java.math.BigInteger timestart;

    private java.math.BigInteger timeduration;

    private java.math.BigInteger visible;

    private java.lang.String uuid;

    private java.math.BigInteger sequence;

    private java.math.BigInteger timemodified;

    public EventRecord() {
    }

    public EventRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.lang.String name,
           java.lang.String description,
           java.math.BigInteger format,
           java.math.BigInteger courseid,
           java.math.BigInteger groupid,
           java.math.BigInteger userid,
           java.math.BigInteger repeatid,
           java.lang.String modulename,
           java.math.BigInteger instance,
           java.lang.String eventtype,
           java.math.BigInteger timestart,
           java.math.BigInteger timeduration,
           java.math.BigInteger visible,
           java.lang.String uuid,
           java.math.BigInteger sequence,
           java.math.BigInteger timemodified) {
           this.error = error;
           this.id = id;
           this.name = name;
           this.description = description;
           this.format = format;
           this.courseid = courseid;
           this.groupid = groupid;
           this.userid = userid;
           this.repeatid = repeatid;
           this.modulename = modulename;
           this.instance = instance;
           this.eventtype = eventtype;
           this.timestart = timestart;
           this.timeduration = timeduration;
           this.visible = visible;
           this.uuid = uuid;
           this.sequence = sequence;
           this.timemodified = timemodified;
    }


    /**
     * Gets the error value for this EventRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this EventRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this EventRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this EventRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the name value for this EventRecord.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this EventRecord.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the description value for this EventRecord.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this EventRecord.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the format value for this EventRecord.
     * 
     * @return format
     */
    public java.math.BigInteger getFormat() {
        return format;
    }


    /**
     * Sets the format value for this EventRecord.
     * 
     * @param format
     */
    public void setFormat(java.math.BigInteger format) {
        this.format = format;
    }


    /**
     * Gets the courseid value for this EventRecord.
     * 
     * @return courseid
     */
    public java.math.BigInteger getCourseid() {
        return courseid;
    }


    /**
     * Sets the courseid value for this EventRecord.
     * 
     * @param courseid
     */
    public void setCourseid(java.math.BigInteger courseid) {
        this.courseid = courseid;
    }


    /**
     * Gets the groupid value for this EventRecord.
     * 
     * @return groupid
     */
    public java.math.BigInteger getGroupid() {
        return groupid;
    }


    /**
     * Sets the groupid value for this EventRecord.
     * 
     * @param groupid
     */
    public void setGroupid(java.math.BigInteger groupid) {
        this.groupid = groupid;
    }


    /**
     * Gets the userid value for this EventRecord.
     * 
     * @return userid
     */
    public java.math.BigInteger getUserid() {
        return userid;
    }


    /**
     * Sets the userid value for this EventRecord.
     * 
     * @param userid
     */
    public void setUserid(java.math.BigInteger userid) {
        this.userid = userid;
    }


    /**
     * Gets the repeatid value for this EventRecord.
     * 
     * @return repeatid
     */
    public java.math.BigInteger getRepeatid() {
        return repeatid;
    }


    /**
     * Sets the repeatid value for this EventRecord.
     * 
     * @param repeatid
     */
    public void setRepeatid(java.math.BigInteger repeatid) {
        this.repeatid = repeatid;
    }


    /**
     * Gets the modulename value for this EventRecord.
     * 
     * @return modulename
     */
    public java.lang.String getModulename() {
        return modulename;
    }


    /**
     * Sets the modulename value for this EventRecord.
     * 
     * @param modulename
     */
    public void setModulename(java.lang.String modulename) {
        this.modulename = modulename;
    }


    /**
     * Gets the instance value for this EventRecord.
     * 
     * @return instance
     */
    public java.math.BigInteger getInstance() {
        return instance;
    }


    /**
     * Sets the instance value for this EventRecord.
     * 
     * @param instance
     */
    public void setInstance(java.math.BigInteger instance) {
        this.instance = instance;
    }


    /**
     * Gets the eventtype value for this EventRecord.
     * 
     * @return eventtype
     */
    public java.lang.String getEventtype() {
        return eventtype;
    }


    /**
     * Sets the eventtype value for this EventRecord.
     * 
     * @param eventtype
     */
    public void setEventtype(java.lang.String eventtype) {
        this.eventtype = eventtype;
    }


    /**
     * Gets the timestart value for this EventRecord.
     * 
     * @return timestart
     */
    public java.math.BigInteger getTimestart() {
        return timestart;
    }


    /**
     * Sets the timestart value for this EventRecord.
     * 
     * @param timestart
     */
    public void setTimestart(java.math.BigInteger timestart) {
        this.timestart = timestart;
    }


    /**
     * Gets the timeduration value for this EventRecord.
     * 
     * @return timeduration
     */
    public java.math.BigInteger getTimeduration() {
        return timeduration;
    }


    /**
     * Sets the timeduration value for this EventRecord.
     * 
     * @param timeduration
     */
    public void setTimeduration(java.math.BigInteger timeduration) {
        this.timeduration = timeduration;
    }


    /**
     * Gets the visible value for this EventRecord.
     * 
     * @return visible
     */
    public java.math.BigInteger getVisible() {
        return visible;
    }


    /**
     * Sets the visible value for this EventRecord.
     * 
     * @param visible
     */
    public void setVisible(java.math.BigInteger visible) {
        this.visible = visible;
    }


    /**
     * Gets the uuid value for this EventRecord.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this EventRecord.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }


    /**
     * Gets the sequence value for this EventRecord.
     * 
     * @return sequence
     */
    public java.math.BigInteger getSequence() {
        return sequence;
    }


    /**
     * Sets the sequence value for this EventRecord.
     * 
     * @param sequence
     */
    public void setSequence(java.math.BigInteger sequence) {
        this.sequence = sequence;
    }


    /**
     * Gets the timemodified value for this EventRecord.
     * 
     * @return timemodified
     */
    public java.math.BigInteger getTimemodified() {
        return timemodified;
    }


    /**
     * Sets the timemodified value for this EventRecord.
     * 
     * @param timemodified
     */
    public void setTimemodified(java.math.BigInteger timemodified) {
        this.timemodified = timemodified;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EventRecord)) return false;
        EventRecord other = (EventRecord) obj;
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
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.courseid==null && other.getCourseid()==null) || 
             (this.courseid!=null &&
              this.courseid.equals(other.getCourseid()))) &&
            ((this.groupid==null && other.getGroupid()==null) || 
             (this.groupid!=null &&
              this.groupid.equals(other.getGroupid()))) &&
            ((this.userid==null && other.getUserid()==null) || 
             (this.userid!=null &&
              this.userid.equals(other.getUserid()))) &&
            ((this.repeatid==null && other.getRepeatid()==null) || 
             (this.repeatid!=null &&
              this.repeatid.equals(other.getRepeatid()))) &&
            ((this.modulename==null && other.getModulename()==null) || 
             (this.modulename!=null &&
              this.modulename.equals(other.getModulename()))) &&
            ((this.instance==null && other.getInstance()==null) || 
             (this.instance!=null &&
              this.instance.equals(other.getInstance()))) &&
            ((this.eventtype==null && other.getEventtype()==null) || 
             (this.eventtype!=null &&
              this.eventtype.equals(other.getEventtype()))) &&
            ((this.timestart==null && other.getTimestart()==null) || 
             (this.timestart!=null &&
              this.timestart.equals(other.getTimestart()))) &&
            ((this.timeduration==null && other.getTimeduration()==null) || 
             (this.timeduration!=null &&
              this.timeduration.equals(other.getTimeduration()))) &&
            ((this.visible==null && other.getVisible()==null) || 
             (this.visible!=null &&
              this.visible.equals(other.getVisible()))) &&
            ((this.uuid==null && other.getUuid()==null) || 
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid()))) &&
            ((this.sequence==null && other.getSequence()==null) || 
             (this.sequence!=null &&
              this.sequence.equals(other.getSequence()))) &&
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getCourseid() != null) {
            _hashCode += getCourseid().hashCode();
        }
        if (getGroupid() != null) {
            _hashCode += getGroupid().hashCode();
        }
        if (getUserid() != null) {
            _hashCode += getUserid().hashCode();
        }
        if (getRepeatid() != null) {
            _hashCode += getRepeatid().hashCode();
        }
        if (getModulename() != null) {
            _hashCode += getModulename().hashCode();
        }
        if (getInstance() != null) {
            _hashCode += getInstance().hashCode();
        }
        if (getEventtype() != null) {
            _hashCode += getEventtype().hashCode();
        }
        if (getTimestart() != null) {
            _hashCode += getTimestart().hashCode();
        }
        if (getTimeduration() != null) {
            _hashCode += getTimeduration().hashCode();
        }
        if (getVisible() != null) {
            _hashCode += getVisible().hashCode();
        }
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        if (getSequence() != null) {
            _hashCode += getSequence().hashCode();
        }
        if (getTimemodified() != null) {
            _hashCode += getTimemodified().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EventRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "eventRecord"));
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
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("", "format"));
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
        elemField.setFieldName("groupid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repeatid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "repeatid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modulename");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modulename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "instance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventtype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventtype"));
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
        elemField.setFieldName("timeduration");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeduration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visible");
        elemField.setXmlName(new javax.xml.namespace.QName("", "visible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uuid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequence");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sequence"));
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
