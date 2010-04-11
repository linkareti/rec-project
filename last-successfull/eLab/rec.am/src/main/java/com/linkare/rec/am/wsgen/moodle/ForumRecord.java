/**
 * ForumRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class ForumRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.math.BigInteger course;

    private java.lang.String type;

    private java.lang.String name;

    private java.lang.String intro;

    private java.math.BigInteger assessed;

    private java.math.BigInteger assesstimestart;

    private java.math.BigInteger assesstimefinish;

    private java.math.BigInteger scale;

    private java.math.BigInteger maxbytes;

    private java.math.BigInteger forcesubscribe;

    private java.math.BigInteger trackingtype;

    private java.math.BigInteger rsstype;

    private java.math.BigInteger rssarticles;

    private java.math.BigInteger timemodified;

    private java.math.BigInteger warnafter;

    private java.math.BigInteger blockafter;

    private java.math.BigInteger blockperiod;

    public ForumRecord() {
    }

    public ForumRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.math.BigInteger course,
           java.lang.String type,
           java.lang.String name,
           java.lang.String intro,
           java.math.BigInteger assessed,
           java.math.BigInteger assesstimestart,
           java.math.BigInteger assesstimefinish,
           java.math.BigInteger scale,
           java.math.BigInteger maxbytes,
           java.math.BigInteger forcesubscribe,
           java.math.BigInteger trackingtype,
           java.math.BigInteger rsstype,
           java.math.BigInteger rssarticles,
           java.math.BigInteger timemodified,
           java.math.BigInteger warnafter,
           java.math.BigInteger blockafter,
           java.math.BigInteger blockperiod) {
           this.error = error;
           this.id = id;
           this.course = course;
           this.type = type;
           this.name = name;
           this.intro = intro;
           this.assessed = assessed;
           this.assesstimestart = assesstimestart;
           this.assesstimefinish = assesstimefinish;
           this.scale = scale;
           this.maxbytes = maxbytes;
           this.forcesubscribe = forcesubscribe;
           this.trackingtype = trackingtype;
           this.rsstype = rsstype;
           this.rssarticles = rssarticles;
           this.timemodified = timemodified;
           this.warnafter = warnafter;
           this.blockafter = blockafter;
           this.blockperiod = blockperiod;
    }


    /**
     * Gets the error value for this ForumRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this ForumRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this ForumRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this ForumRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the course value for this ForumRecord.
     * 
     * @return course
     */
    public java.math.BigInteger getCourse() {
        return course;
    }


    /**
     * Sets the course value for this ForumRecord.
     * 
     * @param course
     */
    public void setCourse(java.math.BigInteger course) {
        this.course = course;
    }


    /**
     * Gets the type value for this ForumRecord.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this ForumRecord.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the name value for this ForumRecord.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ForumRecord.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the intro value for this ForumRecord.
     * 
     * @return intro
     */
    public java.lang.String getIntro() {
        return intro;
    }


    /**
     * Sets the intro value for this ForumRecord.
     * 
     * @param intro
     */
    public void setIntro(java.lang.String intro) {
        this.intro = intro;
    }


    /**
     * Gets the assessed value for this ForumRecord.
     * 
     * @return assessed
     */
    public java.math.BigInteger getAssessed() {
        return assessed;
    }


    /**
     * Sets the assessed value for this ForumRecord.
     * 
     * @param assessed
     */
    public void setAssessed(java.math.BigInteger assessed) {
        this.assessed = assessed;
    }


    /**
     * Gets the assesstimestart value for this ForumRecord.
     * 
     * @return assesstimestart
     */
    public java.math.BigInteger getAssesstimestart() {
        return assesstimestart;
    }


    /**
     * Sets the assesstimestart value for this ForumRecord.
     * 
     * @param assesstimestart
     */
    public void setAssesstimestart(java.math.BigInteger assesstimestart) {
        this.assesstimestart = assesstimestart;
    }


    /**
     * Gets the assesstimefinish value for this ForumRecord.
     * 
     * @return assesstimefinish
     */
    public java.math.BigInteger getAssesstimefinish() {
        return assesstimefinish;
    }


    /**
     * Sets the assesstimefinish value for this ForumRecord.
     * 
     * @param assesstimefinish
     */
    public void setAssesstimefinish(java.math.BigInteger assesstimefinish) {
        this.assesstimefinish = assesstimefinish;
    }


    /**
     * Gets the scale value for this ForumRecord.
     * 
     * @return scale
     */
    public java.math.BigInteger getScale() {
        return scale;
    }


    /**
     * Sets the scale value for this ForumRecord.
     * 
     * @param scale
     */
    public void setScale(java.math.BigInteger scale) {
        this.scale = scale;
    }


    /**
     * Gets the maxbytes value for this ForumRecord.
     * 
     * @return maxbytes
     */
    public java.math.BigInteger getMaxbytes() {
        return maxbytes;
    }


    /**
     * Sets the maxbytes value for this ForumRecord.
     * 
     * @param maxbytes
     */
    public void setMaxbytes(java.math.BigInteger maxbytes) {
        this.maxbytes = maxbytes;
    }


    /**
     * Gets the forcesubscribe value for this ForumRecord.
     * 
     * @return forcesubscribe
     */
    public java.math.BigInteger getForcesubscribe() {
        return forcesubscribe;
    }


    /**
     * Sets the forcesubscribe value for this ForumRecord.
     * 
     * @param forcesubscribe
     */
    public void setForcesubscribe(java.math.BigInteger forcesubscribe) {
        this.forcesubscribe = forcesubscribe;
    }


    /**
     * Gets the trackingtype value for this ForumRecord.
     * 
     * @return trackingtype
     */
    public java.math.BigInteger getTrackingtype() {
        return trackingtype;
    }


    /**
     * Sets the trackingtype value for this ForumRecord.
     * 
     * @param trackingtype
     */
    public void setTrackingtype(java.math.BigInteger trackingtype) {
        this.trackingtype = trackingtype;
    }


    /**
     * Gets the rsstype value for this ForumRecord.
     * 
     * @return rsstype
     */
    public java.math.BigInteger getRsstype() {
        return rsstype;
    }


    /**
     * Sets the rsstype value for this ForumRecord.
     * 
     * @param rsstype
     */
    public void setRsstype(java.math.BigInteger rsstype) {
        this.rsstype = rsstype;
    }


    /**
     * Gets the rssarticles value for this ForumRecord.
     * 
     * @return rssarticles
     */
    public java.math.BigInteger getRssarticles() {
        return rssarticles;
    }


    /**
     * Sets the rssarticles value for this ForumRecord.
     * 
     * @param rssarticles
     */
    public void setRssarticles(java.math.BigInteger rssarticles) {
        this.rssarticles = rssarticles;
    }


    /**
     * Gets the timemodified value for this ForumRecord.
     * 
     * @return timemodified
     */
    public java.math.BigInteger getTimemodified() {
        return timemodified;
    }


    /**
     * Sets the timemodified value for this ForumRecord.
     * 
     * @param timemodified
     */
    public void setTimemodified(java.math.BigInteger timemodified) {
        this.timemodified = timemodified;
    }


    /**
     * Gets the warnafter value for this ForumRecord.
     * 
     * @return warnafter
     */
    public java.math.BigInteger getWarnafter() {
        return warnafter;
    }


    /**
     * Sets the warnafter value for this ForumRecord.
     * 
     * @param warnafter
     */
    public void setWarnafter(java.math.BigInteger warnafter) {
        this.warnafter = warnafter;
    }


    /**
     * Gets the blockafter value for this ForumRecord.
     * 
     * @return blockafter
     */
    public java.math.BigInteger getBlockafter() {
        return blockafter;
    }


    /**
     * Sets the blockafter value for this ForumRecord.
     * 
     * @param blockafter
     */
    public void setBlockafter(java.math.BigInteger blockafter) {
        this.blockafter = blockafter;
    }


    /**
     * Gets the blockperiod value for this ForumRecord.
     * 
     * @return blockperiod
     */
    public java.math.BigInteger getBlockperiod() {
        return blockperiod;
    }


    /**
     * Sets the blockperiod value for this ForumRecord.
     * 
     * @param blockperiod
     */
    public void setBlockperiod(java.math.BigInteger blockperiod) {
        this.blockperiod = blockperiod;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ForumRecord)) return false;
        ForumRecord other = (ForumRecord) obj;
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
            ((this.course==null && other.getCourse()==null) || 
             (this.course!=null &&
              this.course.equals(other.getCourse()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.intro==null && other.getIntro()==null) || 
             (this.intro!=null &&
              this.intro.equals(other.getIntro()))) &&
            ((this.assessed==null && other.getAssessed()==null) || 
             (this.assessed!=null &&
              this.assessed.equals(other.getAssessed()))) &&
            ((this.assesstimestart==null && other.getAssesstimestart()==null) || 
             (this.assesstimestart!=null &&
              this.assesstimestart.equals(other.getAssesstimestart()))) &&
            ((this.assesstimefinish==null && other.getAssesstimefinish()==null) || 
             (this.assesstimefinish!=null &&
              this.assesstimefinish.equals(other.getAssesstimefinish()))) &&
            ((this.scale==null && other.getScale()==null) || 
             (this.scale!=null &&
              this.scale.equals(other.getScale()))) &&
            ((this.maxbytes==null && other.getMaxbytes()==null) || 
             (this.maxbytes!=null &&
              this.maxbytes.equals(other.getMaxbytes()))) &&
            ((this.forcesubscribe==null && other.getForcesubscribe()==null) || 
             (this.forcesubscribe!=null &&
              this.forcesubscribe.equals(other.getForcesubscribe()))) &&
            ((this.trackingtype==null && other.getTrackingtype()==null) || 
             (this.trackingtype!=null &&
              this.trackingtype.equals(other.getTrackingtype()))) &&
            ((this.rsstype==null && other.getRsstype()==null) || 
             (this.rsstype!=null &&
              this.rsstype.equals(other.getRsstype()))) &&
            ((this.rssarticles==null && other.getRssarticles()==null) || 
             (this.rssarticles!=null &&
              this.rssarticles.equals(other.getRssarticles()))) &&
            ((this.timemodified==null && other.getTimemodified()==null) || 
             (this.timemodified!=null &&
              this.timemodified.equals(other.getTimemodified()))) &&
            ((this.warnafter==null && other.getWarnafter()==null) || 
             (this.warnafter!=null &&
              this.warnafter.equals(other.getWarnafter()))) &&
            ((this.blockafter==null && other.getBlockafter()==null) || 
             (this.blockafter!=null &&
              this.blockafter.equals(other.getBlockafter()))) &&
            ((this.blockperiod==null && other.getBlockperiod()==null) || 
             (this.blockperiod!=null &&
              this.blockperiod.equals(other.getBlockperiod())));
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
        if (getCourse() != null) {
            _hashCode += getCourse().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getIntro() != null) {
            _hashCode += getIntro().hashCode();
        }
        if (getAssessed() != null) {
            _hashCode += getAssessed().hashCode();
        }
        if (getAssesstimestart() != null) {
            _hashCode += getAssesstimestart().hashCode();
        }
        if (getAssesstimefinish() != null) {
            _hashCode += getAssesstimefinish().hashCode();
        }
        if (getScale() != null) {
            _hashCode += getScale().hashCode();
        }
        if (getMaxbytes() != null) {
            _hashCode += getMaxbytes().hashCode();
        }
        if (getForcesubscribe() != null) {
            _hashCode += getForcesubscribe().hashCode();
        }
        if (getTrackingtype() != null) {
            _hashCode += getTrackingtype().hashCode();
        }
        if (getRsstype() != null) {
            _hashCode += getRsstype().hashCode();
        }
        if (getRssarticles() != null) {
            _hashCode += getRssarticles().hashCode();
        }
        if (getTimemodified() != null) {
            _hashCode += getTimemodified().hashCode();
        }
        if (getWarnafter() != null) {
            _hashCode += getWarnafter().hashCode();
        }
        if (getBlockafter() != null) {
            _hashCode += getBlockafter().hashCode();
        }
        if (getBlockperiod() != null) {
            _hashCode += getBlockperiod().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ForumRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "forumRecord"));
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
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "intro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assessed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assessed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assesstimestart");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assesstimestart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assesstimefinish");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assesstimefinish"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxbytes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxbytes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("forcesubscribe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "forcesubscribe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingtype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "trackingtype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rsstype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rsstype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rssarticles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rssarticles"));
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
        elemField.setFieldName("warnafter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "warnafter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockafter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blockafter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockperiod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blockperiod"));
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
