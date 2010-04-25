/**
 * ResourceRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class ResourceRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.lang.String name;

    private java.math.BigInteger course;

    private java.lang.String type;

    private java.lang.String reference;

    private java.lang.String summary;

    private java.lang.String alltext;

    private java.lang.String popup;

    private java.lang.String options;

    private java.math.BigInteger timemodified;

    private java.math.BigInteger section;

    private java.math.BigInteger visible;

    private java.math.BigInteger groupmode;

    private java.math.BigInteger coursemodule;

    private java.lang.String url;

    private java.lang.String timemodified_ut;

    public ResourceRecord() {
    }

    public ResourceRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.lang.String name,
           java.math.BigInteger course,
           java.lang.String type,
           java.lang.String reference,
           java.lang.String summary,
           java.lang.String alltext,
           java.lang.String popup,
           java.lang.String options,
           java.math.BigInteger timemodified,
           java.math.BigInteger section,
           java.math.BigInteger visible,
           java.math.BigInteger groupmode,
           java.math.BigInteger coursemodule,
           java.lang.String url,
           java.lang.String timemodified_ut) {
           this.error = error;
           this.id = id;
           this.name = name;
           this.course = course;
           this.type = type;
           this.reference = reference;
           this.summary = summary;
           this.alltext = alltext;
           this.popup = popup;
           this.options = options;
           this.timemodified = timemodified;
           this.section = section;
           this.visible = visible;
           this.groupmode = groupmode;
           this.coursemodule = coursemodule;
           this.url = url;
           this.timemodified_ut = timemodified_ut;
    }


    /**
     * Gets the error value for this ResourceRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this ResourceRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this ResourceRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this ResourceRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the name value for this ResourceRecord.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ResourceRecord.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the course value for this ResourceRecord.
     * 
     * @return course
     */
    public java.math.BigInteger getCourse() {
        return course;
    }


    /**
     * Sets the course value for this ResourceRecord.
     * 
     * @param course
     */
    public void setCourse(java.math.BigInteger course) {
        this.course = course;
    }


    /**
     * Gets the type value for this ResourceRecord.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this ResourceRecord.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the reference value for this ResourceRecord.
     * 
     * @return reference
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this ResourceRecord.
     * 
     * @param reference
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the summary value for this ResourceRecord.
     * 
     * @return summary
     */
    public java.lang.String getSummary() {
        return summary;
    }


    /**
     * Sets the summary value for this ResourceRecord.
     * 
     * @param summary
     */
    public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }


    /**
     * Gets the alltext value for this ResourceRecord.
     * 
     * @return alltext
     */
    public java.lang.String getAlltext() {
        return alltext;
    }


    /**
     * Sets the alltext value for this ResourceRecord.
     * 
     * @param alltext
     */
    public void setAlltext(java.lang.String alltext) {
        this.alltext = alltext;
    }


    /**
     * Gets the popup value for this ResourceRecord.
     * 
     * @return popup
     */
    public java.lang.String getPopup() {
        return popup;
    }


    /**
     * Sets the popup value for this ResourceRecord.
     * 
     * @param popup
     */
    public void setPopup(java.lang.String popup) {
        this.popup = popup;
    }


    /**
     * Gets the options value for this ResourceRecord.
     * 
     * @return options
     */
    public java.lang.String getOptions() {
        return options;
    }


    /**
     * Sets the options value for this ResourceRecord.
     * 
     * @param options
     */
    public void setOptions(java.lang.String options) {
        this.options = options;
    }


    /**
     * Gets the timemodified value for this ResourceRecord.
     * 
     * @return timemodified
     */
    public java.math.BigInteger getTimemodified() {
        return timemodified;
    }


    /**
     * Sets the timemodified value for this ResourceRecord.
     * 
     * @param timemodified
     */
    public void setTimemodified(java.math.BigInteger timemodified) {
        this.timemodified = timemodified;
    }


    /**
     * Gets the section value for this ResourceRecord.
     * 
     * @return section
     */
    public java.math.BigInteger getSection() {
        return section;
    }


    /**
     * Sets the section value for this ResourceRecord.
     * 
     * @param section
     */
    public void setSection(java.math.BigInteger section) {
        this.section = section;
    }


    /**
     * Gets the visible value for this ResourceRecord.
     * 
     * @return visible
     */
    public java.math.BigInteger getVisible() {
        return visible;
    }


    /**
     * Sets the visible value for this ResourceRecord.
     * 
     * @param visible
     */
    public void setVisible(java.math.BigInteger visible) {
        this.visible = visible;
    }


    /**
     * Gets the groupmode value for this ResourceRecord.
     * 
     * @return groupmode
     */
    public java.math.BigInteger getGroupmode() {
        return groupmode;
    }


    /**
     * Sets the groupmode value for this ResourceRecord.
     * 
     * @param groupmode
     */
    public void setGroupmode(java.math.BigInteger groupmode) {
        this.groupmode = groupmode;
    }


    /**
     * Gets the coursemodule value for this ResourceRecord.
     * 
     * @return coursemodule
     */
    public java.math.BigInteger getCoursemodule() {
        return coursemodule;
    }


    /**
     * Sets the coursemodule value for this ResourceRecord.
     * 
     * @param coursemodule
     */
    public void setCoursemodule(java.math.BigInteger coursemodule) {
        this.coursemodule = coursemodule;
    }


    /**
     * Gets the url value for this ResourceRecord.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this ResourceRecord.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }


    /**
     * Gets the timemodified_ut value for this ResourceRecord.
     * 
     * @return timemodified_ut
     */
    public java.lang.String getTimemodified_ut() {
        return timemodified_ut;
    }


    /**
     * Sets the timemodified_ut value for this ResourceRecord.
     * 
     * @param timemodified_ut
     */
    public void setTimemodified_ut(java.lang.String timemodified_ut) {
        this.timemodified_ut = timemodified_ut;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResourceRecord)) return false;
        ResourceRecord other = (ResourceRecord) obj;
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
            ((this.course==null && other.getCourse()==null) || 
             (this.course!=null &&
              this.course.equals(other.getCourse()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.summary==null && other.getSummary()==null) || 
             (this.summary!=null &&
              this.summary.equals(other.getSummary()))) &&
            ((this.alltext==null && other.getAlltext()==null) || 
             (this.alltext!=null &&
              this.alltext.equals(other.getAlltext()))) &&
            ((this.popup==null && other.getPopup()==null) || 
             (this.popup!=null &&
              this.popup.equals(other.getPopup()))) &&
            ((this.options==null && other.getOptions()==null) || 
             (this.options!=null &&
              this.options.equals(other.getOptions()))) &&
            ((this.timemodified==null && other.getTimemodified()==null) || 
             (this.timemodified!=null &&
              this.timemodified.equals(other.getTimemodified()))) &&
            ((this.section==null && other.getSection()==null) || 
             (this.section!=null &&
              this.section.equals(other.getSection()))) &&
            ((this.visible==null && other.getVisible()==null) || 
             (this.visible!=null &&
              this.visible.equals(other.getVisible()))) &&
            ((this.groupmode==null && other.getGroupmode()==null) || 
             (this.groupmode!=null &&
              this.groupmode.equals(other.getGroupmode()))) &&
            ((this.coursemodule==null && other.getCoursemodule()==null) || 
             (this.coursemodule!=null &&
              this.coursemodule.equals(other.getCoursemodule()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl()))) &&
            ((this.timemodified_ut==null && other.getTimemodified_ut()==null) || 
             (this.timemodified_ut!=null &&
              this.timemodified_ut.equals(other.getTimemodified_ut())));
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
        if (getCourse() != null) {
            _hashCode += getCourse().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getSummary() != null) {
            _hashCode += getSummary().hashCode();
        }
        if (getAlltext() != null) {
            _hashCode += getAlltext().hashCode();
        }
        if (getPopup() != null) {
            _hashCode += getPopup().hashCode();
        }
        if (getOptions() != null) {
            _hashCode += getOptions().hashCode();
        }
        if (getTimemodified() != null) {
            _hashCode += getTimemodified().hashCode();
        }
        if (getSection() != null) {
            _hashCode += getSection().hashCode();
        }
        if (getVisible() != null) {
            _hashCode += getVisible().hashCode();
        }
        if (getGroupmode() != null) {
            _hashCode += getGroupmode().hashCode();
        }
        if (getCoursemodule() != null) {
            _hashCode += getCoursemodule().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        if (getTimemodified_ut() != null) {
            _hashCode += getTimemodified_ut().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResourceRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "resourceRecord"));
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
        elemField.setFieldName("course");
        elemField.setXmlName(new javax.xml.namespace.QName("", "course"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summary");
        elemField.setXmlName(new javax.xml.namespace.QName("", "summary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alltext");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alltext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("popup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "popup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("options");
        elemField.setXmlName(new javax.xml.namespace.QName("", "options"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timemodified");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timemodified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("section");
        elemField.setXmlName(new javax.xml.namespace.QName("", "section"));
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
        elemField.setFieldName("groupmode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupmode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coursemodule");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coursemodule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timemodified_ut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timemodified_ut"));
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
