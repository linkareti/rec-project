/**
 * DatabaseRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class DatabaseRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.math.BigInteger course;

    private java.lang.String name;

    private java.lang.String intro;

    private java.math.BigInteger comments;

    private java.math.BigInteger timeavailablefrom;

    private java.math.BigInteger timeavailableto;

    private java.math.BigInteger timeviewfrom;

    private java.math.BigInteger timeviewto;

    private java.math.BigInteger requiredentries;

    private java.math.BigInteger requiredentriestoview;

    private java.math.BigInteger maxentries;

    private java.math.BigInteger ressarticles;

    private java.lang.String singletemplate;

    private java.lang.String listtemplate;

    private java.lang.String listtemplateheader;

    private java.lang.String listtemplatefooter;

    private java.lang.String addtemplatee;

    private java.lang.String rsstemplate;

    private java.lang.String rsstitletemplate;

    private java.lang.String csstemplate;

    private java.lang.String jstemplate;

    private java.lang.String asearchtemplate;

    private java.math.BigInteger approval;

    private java.math.BigInteger scale;

    private java.math.BigInteger assessed;

    private java.math.BigInteger defaultsort;

    private java.math.BigInteger defaultsortdir;

    private java.math.BigInteger editany;

    private java.math.BigInteger notification;

    public DatabaseRecord() {
    }

    public DatabaseRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.math.BigInteger course,
           java.lang.String name,
           java.lang.String intro,
           java.math.BigInteger comments,
           java.math.BigInteger timeavailablefrom,
           java.math.BigInteger timeavailableto,
           java.math.BigInteger timeviewfrom,
           java.math.BigInteger timeviewto,
           java.math.BigInteger requiredentries,
           java.math.BigInteger requiredentriestoview,
           java.math.BigInteger maxentries,
           java.math.BigInteger ressarticles,
           java.lang.String singletemplate,
           java.lang.String listtemplate,
           java.lang.String listtemplateheader,
           java.lang.String listtemplatefooter,
           java.lang.String addtemplatee,
           java.lang.String rsstemplate,
           java.lang.String rsstitletemplate,
           java.lang.String csstemplate,
           java.lang.String jstemplate,
           java.lang.String asearchtemplate,
           java.math.BigInteger approval,
           java.math.BigInteger scale,
           java.math.BigInteger assessed,
           java.math.BigInteger defaultsort,
           java.math.BigInteger defaultsortdir,
           java.math.BigInteger editany,
           java.math.BigInteger notification) {
           this.error = error;
           this.id = id;
           this.course = course;
           this.name = name;
           this.intro = intro;
           this.comments = comments;
           this.timeavailablefrom = timeavailablefrom;
           this.timeavailableto = timeavailableto;
           this.timeviewfrom = timeviewfrom;
           this.timeviewto = timeviewto;
           this.requiredentries = requiredentries;
           this.requiredentriestoview = requiredentriestoview;
           this.maxentries = maxentries;
           this.ressarticles = ressarticles;
           this.singletemplate = singletemplate;
           this.listtemplate = listtemplate;
           this.listtemplateheader = listtemplateheader;
           this.listtemplatefooter = listtemplatefooter;
           this.addtemplatee = addtemplatee;
           this.rsstemplate = rsstemplate;
           this.rsstitletemplate = rsstitletemplate;
           this.csstemplate = csstemplate;
           this.jstemplate = jstemplate;
           this.asearchtemplate = asearchtemplate;
           this.approval = approval;
           this.scale = scale;
           this.assessed = assessed;
           this.defaultsort = defaultsort;
           this.defaultsortdir = defaultsortdir;
           this.editany = editany;
           this.notification = notification;
    }


    /**
     * Gets the error value for this DatabaseRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this DatabaseRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this DatabaseRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this DatabaseRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the course value for this DatabaseRecord.
     * 
     * @return course
     */
    public java.math.BigInteger getCourse() {
        return course;
    }


    /**
     * Sets the course value for this DatabaseRecord.
     * 
     * @param course
     */
    public void setCourse(java.math.BigInteger course) {
        this.course = course;
    }


    /**
     * Gets the name value for this DatabaseRecord.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this DatabaseRecord.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the intro value for this DatabaseRecord.
     * 
     * @return intro
     */
    public java.lang.String getIntro() {
        return intro;
    }


    /**
     * Sets the intro value for this DatabaseRecord.
     * 
     * @param intro
     */
    public void setIntro(java.lang.String intro) {
        this.intro = intro;
    }


    /**
     * Gets the comments value for this DatabaseRecord.
     * 
     * @return comments
     */
    public java.math.BigInteger getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this DatabaseRecord.
     * 
     * @param comments
     */
    public void setComments(java.math.BigInteger comments) {
        this.comments = comments;
    }


    /**
     * Gets the timeavailablefrom value for this DatabaseRecord.
     * 
     * @return timeavailablefrom
     */
    public java.math.BigInteger getTimeavailablefrom() {
        return timeavailablefrom;
    }


    /**
     * Sets the timeavailablefrom value for this DatabaseRecord.
     * 
     * @param timeavailablefrom
     */
    public void setTimeavailablefrom(java.math.BigInteger timeavailablefrom) {
        this.timeavailablefrom = timeavailablefrom;
    }


    /**
     * Gets the timeavailableto value for this DatabaseRecord.
     * 
     * @return timeavailableto
     */
    public java.math.BigInteger getTimeavailableto() {
        return timeavailableto;
    }


    /**
     * Sets the timeavailableto value for this DatabaseRecord.
     * 
     * @param timeavailableto
     */
    public void setTimeavailableto(java.math.BigInteger timeavailableto) {
        this.timeavailableto = timeavailableto;
    }


    /**
     * Gets the timeviewfrom value for this DatabaseRecord.
     * 
     * @return timeviewfrom
     */
    public java.math.BigInteger getTimeviewfrom() {
        return timeviewfrom;
    }


    /**
     * Sets the timeviewfrom value for this DatabaseRecord.
     * 
     * @param timeviewfrom
     */
    public void setTimeviewfrom(java.math.BigInteger timeviewfrom) {
        this.timeviewfrom = timeviewfrom;
    }


    /**
     * Gets the timeviewto value for this DatabaseRecord.
     * 
     * @return timeviewto
     */
    public java.math.BigInteger getTimeviewto() {
        return timeviewto;
    }


    /**
     * Sets the timeviewto value for this DatabaseRecord.
     * 
     * @param timeviewto
     */
    public void setTimeviewto(java.math.BigInteger timeviewto) {
        this.timeviewto = timeviewto;
    }


    /**
     * Gets the requiredentries value for this DatabaseRecord.
     * 
     * @return requiredentries
     */
    public java.math.BigInteger getRequiredentries() {
        return requiredentries;
    }


    /**
     * Sets the requiredentries value for this DatabaseRecord.
     * 
     * @param requiredentries
     */
    public void setRequiredentries(java.math.BigInteger requiredentries) {
        this.requiredentries = requiredentries;
    }


    /**
     * Gets the requiredentriestoview value for this DatabaseRecord.
     * 
     * @return requiredentriestoview
     */
    public java.math.BigInteger getRequiredentriestoview() {
        return requiredentriestoview;
    }


    /**
     * Sets the requiredentriestoview value for this DatabaseRecord.
     * 
     * @param requiredentriestoview
     */
    public void setRequiredentriestoview(java.math.BigInteger requiredentriestoview) {
        this.requiredentriestoview = requiredentriestoview;
    }


    /**
     * Gets the maxentries value for this DatabaseRecord.
     * 
     * @return maxentries
     */
    public java.math.BigInteger getMaxentries() {
        return maxentries;
    }


    /**
     * Sets the maxentries value for this DatabaseRecord.
     * 
     * @param maxentries
     */
    public void setMaxentries(java.math.BigInteger maxentries) {
        this.maxentries = maxentries;
    }


    /**
     * Gets the ressarticles value for this DatabaseRecord.
     * 
     * @return ressarticles
     */
    public java.math.BigInteger getRessarticles() {
        return ressarticles;
    }


    /**
     * Sets the ressarticles value for this DatabaseRecord.
     * 
     * @param ressarticles
     */
    public void setRessarticles(java.math.BigInteger ressarticles) {
        this.ressarticles = ressarticles;
    }


    /**
     * Gets the singletemplate value for this DatabaseRecord.
     * 
     * @return singletemplate
     */
    public java.lang.String getSingletemplate() {
        return singletemplate;
    }


    /**
     * Sets the singletemplate value for this DatabaseRecord.
     * 
     * @param singletemplate
     */
    public void setSingletemplate(java.lang.String singletemplate) {
        this.singletemplate = singletemplate;
    }


    /**
     * Gets the listtemplate value for this DatabaseRecord.
     * 
     * @return listtemplate
     */
    public java.lang.String getListtemplate() {
        return listtemplate;
    }


    /**
     * Sets the listtemplate value for this DatabaseRecord.
     * 
     * @param listtemplate
     */
    public void setListtemplate(java.lang.String listtemplate) {
        this.listtemplate = listtemplate;
    }


    /**
     * Gets the listtemplateheader value for this DatabaseRecord.
     * 
     * @return listtemplateheader
     */
    public java.lang.String getListtemplateheader() {
        return listtemplateheader;
    }


    /**
     * Sets the listtemplateheader value for this DatabaseRecord.
     * 
     * @param listtemplateheader
     */
    public void setListtemplateheader(java.lang.String listtemplateheader) {
        this.listtemplateheader = listtemplateheader;
    }


    /**
     * Gets the listtemplatefooter value for this DatabaseRecord.
     * 
     * @return listtemplatefooter
     */
    public java.lang.String getListtemplatefooter() {
        return listtemplatefooter;
    }


    /**
     * Sets the listtemplatefooter value for this DatabaseRecord.
     * 
     * @param listtemplatefooter
     */
    public void setListtemplatefooter(java.lang.String listtemplatefooter) {
        this.listtemplatefooter = listtemplatefooter;
    }


    /**
     * Gets the addtemplatee value for this DatabaseRecord.
     * 
     * @return addtemplatee
     */
    public java.lang.String getAddtemplatee() {
        return addtemplatee;
    }


    /**
     * Sets the addtemplatee value for this DatabaseRecord.
     * 
     * @param addtemplatee
     */
    public void setAddtemplatee(java.lang.String addtemplatee) {
        this.addtemplatee = addtemplatee;
    }


    /**
     * Gets the rsstemplate value for this DatabaseRecord.
     * 
     * @return rsstemplate
     */
    public java.lang.String getRsstemplate() {
        return rsstemplate;
    }


    /**
     * Sets the rsstemplate value for this DatabaseRecord.
     * 
     * @param rsstemplate
     */
    public void setRsstemplate(java.lang.String rsstemplate) {
        this.rsstemplate = rsstemplate;
    }


    /**
     * Gets the rsstitletemplate value for this DatabaseRecord.
     * 
     * @return rsstitletemplate
     */
    public java.lang.String getRsstitletemplate() {
        return rsstitletemplate;
    }


    /**
     * Sets the rsstitletemplate value for this DatabaseRecord.
     * 
     * @param rsstitletemplate
     */
    public void setRsstitletemplate(java.lang.String rsstitletemplate) {
        this.rsstitletemplate = rsstitletemplate;
    }


    /**
     * Gets the csstemplate value for this DatabaseRecord.
     * 
     * @return csstemplate
     */
    public java.lang.String getCsstemplate() {
        return csstemplate;
    }


    /**
     * Sets the csstemplate value for this DatabaseRecord.
     * 
     * @param csstemplate
     */
    public void setCsstemplate(java.lang.String csstemplate) {
        this.csstemplate = csstemplate;
    }


    /**
     * Gets the jstemplate value for this DatabaseRecord.
     * 
     * @return jstemplate
     */
    public java.lang.String getJstemplate() {
        return jstemplate;
    }


    /**
     * Sets the jstemplate value for this DatabaseRecord.
     * 
     * @param jstemplate
     */
    public void setJstemplate(java.lang.String jstemplate) {
        this.jstemplate = jstemplate;
    }


    /**
     * Gets the asearchtemplate value for this DatabaseRecord.
     * 
     * @return asearchtemplate
     */
    public java.lang.String getAsearchtemplate() {
        return asearchtemplate;
    }


    /**
     * Sets the asearchtemplate value for this DatabaseRecord.
     * 
     * @param asearchtemplate
     */
    public void setAsearchtemplate(java.lang.String asearchtemplate) {
        this.asearchtemplate = asearchtemplate;
    }


    /**
     * Gets the approval value for this DatabaseRecord.
     * 
     * @return approval
     */
    public java.math.BigInteger getApproval() {
        return approval;
    }


    /**
     * Sets the approval value for this DatabaseRecord.
     * 
     * @param approval
     */
    public void setApproval(java.math.BigInteger approval) {
        this.approval = approval;
    }


    /**
     * Gets the scale value for this DatabaseRecord.
     * 
     * @return scale
     */
    public java.math.BigInteger getScale() {
        return scale;
    }


    /**
     * Sets the scale value for this DatabaseRecord.
     * 
     * @param scale
     */
    public void setScale(java.math.BigInteger scale) {
        this.scale = scale;
    }


    /**
     * Gets the assessed value for this DatabaseRecord.
     * 
     * @return assessed
     */
    public java.math.BigInteger getAssessed() {
        return assessed;
    }


    /**
     * Sets the assessed value for this DatabaseRecord.
     * 
     * @param assessed
     */
    public void setAssessed(java.math.BigInteger assessed) {
        this.assessed = assessed;
    }


    /**
     * Gets the defaultsort value for this DatabaseRecord.
     * 
     * @return defaultsort
     */
    public java.math.BigInteger getDefaultsort() {
        return defaultsort;
    }


    /**
     * Sets the defaultsort value for this DatabaseRecord.
     * 
     * @param defaultsort
     */
    public void setDefaultsort(java.math.BigInteger defaultsort) {
        this.defaultsort = defaultsort;
    }


    /**
     * Gets the defaultsortdir value for this DatabaseRecord.
     * 
     * @return defaultsortdir
     */
    public java.math.BigInteger getDefaultsortdir() {
        return defaultsortdir;
    }


    /**
     * Sets the defaultsortdir value for this DatabaseRecord.
     * 
     * @param defaultsortdir
     */
    public void setDefaultsortdir(java.math.BigInteger defaultsortdir) {
        this.defaultsortdir = defaultsortdir;
    }


    /**
     * Gets the editany value for this DatabaseRecord.
     * 
     * @return editany
     */
    public java.math.BigInteger getEditany() {
        return editany;
    }


    /**
     * Sets the editany value for this DatabaseRecord.
     * 
     * @param editany
     */
    public void setEditany(java.math.BigInteger editany) {
        this.editany = editany;
    }


    /**
     * Gets the notification value for this DatabaseRecord.
     * 
     * @return notification
     */
    public java.math.BigInteger getNotification() {
        return notification;
    }


    /**
     * Sets the notification value for this DatabaseRecord.
     * 
     * @param notification
     */
    public void setNotification(java.math.BigInteger notification) {
        this.notification = notification;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatabaseRecord)) return false;
        DatabaseRecord other = (DatabaseRecord) obj;
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
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.intro==null && other.getIntro()==null) || 
             (this.intro!=null &&
              this.intro.equals(other.getIntro()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.timeavailablefrom==null && other.getTimeavailablefrom()==null) || 
             (this.timeavailablefrom!=null &&
              this.timeavailablefrom.equals(other.getTimeavailablefrom()))) &&
            ((this.timeavailableto==null && other.getTimeavailableto()==null) || 
             (this.timeavailableto!=null &&
              this.timeavailableto.equals(other.getTimeavailableto()))) &&
            ((this.timeviewfrom==null && other.getTimeviewfrom()==null) || 
             (this.timeviewfrom!=null &&
              this.timeviewfrom.equals(other.getTimeviewfrom()))) &&
            ((this.timeviewto==null && other.getTimeviewto()==null) || 
             (this.timeviewto!=null &&
              this.timeviewto.equals(other.getTimeviewto()))) &&
            ((this.requiredentries==null && other.getRequiredentries()==null) || 
             (this.requiredentries!=null &&
              this.requiredentries.equals(other.getRequiredentries()))) &&
            ((this.requiredentriestoview==null && other.getRequiredentriestoview()==null) || 
             (this.requiredentriestoview!=null &&
              this.requiredentriestoview.equals(other.getRequiredentriestoview()))) &&
            ((this.maxentries==null && other.getMaxentries()==null) || 
             (this.maxentries!=null &&
              this.maxentries.equals(other.getMaxentries()))) &&
            ((this.ressarticles==null && other.getRessarticles()==null) || 
             (this.ressarticles!=null &&
              this.ressarticles.equals(other.getRessarticles()))) &&
            ((this.singletemplate==null && other.getSingletemplate()==null) || 
             (this.singletemplate!=null &&
              this.singletemplate.equals(other.getSingletemplate()))) &&
            ((this.listtemplate==null && other.getListtemplate()==null) || 
             (this.listtemplate!=null &&
              this.listtemplate.equals(other.getListtemplate()))) &&
            ((this.listtemplateheader==null && other.getListtemplateheader()==null) || 
             (this.listtemplateheader!=null &&
              this.listtemplateheader.equals(other.getListtemplateheader()))) &&
            ((this.listtemplatefooter==null && other.getListtemplatefooter()==null) || 
             (this.listtemplatefooter!=null &&
              this.listtemplatefooter.equals(other.getListtemplatefooter()))) &&
            ((this.addtemplatee==null && other.getAddtemplatee()==null) || 
             (this.addtemplatee!=null &&
              this.addtemplatee.equals(other.getAddtemplatee()))) &&
            ((this.rsstemplate==null && other.getRsstemplate()==null) || 
             (this.rsstemplate!=null &&
              this.rsstemplate.equals(other.getRsstemplate()))) &&
            ((this.rsstitletemplate==null && other.getRsstitletemplate()==null) || 
             (this.rsstitletemplate!=null &&
              this.rsstitletemplate.equals(other.getRsstitletemplate()))) &&
            ((this.csstemplate==null && other.getCsstemplate()==null) || 
             (this.csstemplate!=null &&
              this.csstemplate.equals(other.getCsstemplate()))) &&
            ((this.jstemplate==null && other.getJstemplate()==null) || 
             (this.jstemplate!=null &&
              this.jstemplate.equals(other.getJstemplate()))) &&
            ((this.asearchtemplate==null && other.getAsearchtemplate()==null) || 
             (this.asearchtemplate!=null &&
              this.asearchtemplate.equals(other.getAsearchtemplate()))) &&
            ((this.approval==null && other.getApproval()==null) || 
             (this.approval!=null &&
              this.approval.equals(other.getApproval()))) &&
            ((this.scale==null && other.getScale()==null) || 
             (this.scale!=null &&
              this.scale.equals(other.getScale()))) &&
            ((this.assessed==null && other.getAssessed()==null) || 
             (this.assessed!=null &&
              this.assessed.equals(other.getAssessed()))) &&
            ((this.defaultsort==null && other.getDefaultsort()==null) || 
             (this.defaultsort!=null &&
              this.defaultsort.equals(other.getDefaultsort()))) &&
            ((this.defaultsortdir==null && other.getDefaultsortdir()==null) || 
             (this.defaultsortdir!=null &&
              this.defaultsortdir.equals(other.getDefaultsortdir()))) &&
            ((this.editany==null && other.getEditany()==null) || 
             (this.editany!=null &&
              this.editany.equals(other.getEditany()))) &&
            ((this.notification==null && other.getNotification()==null) || 
             (this.notification!=null &&
              this.notification.equals(other.getNotification())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getIntro() != null) {
            _hashCode += getIntro().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getTimeavailablefrom() != null) {
            _hashCode += getTimeavailablefrom().hashCode();
        }
        if (getTimeavailableto() != null) {
            _hashCode += getTimeavailableto().hashCode();
        }
        if (getTimeviewfrom() != null) {
            _hashCode += getTimeviewfrom().hashCode();
        }
        if (getTimeviewto() != null) {
            _hashCode += getTimeviewto().hashCode();
        }
        if (getRequiredentries() != null) {
            _hashCode += getRequiredentries().hashCode();
        }
        if (getRequiredentriestoview() != null) {
            _hashCode += getRequiredentriestoview().hashCode();
        }
        if (getMaxentries() != null) {
            _hashCode += getMaxentries().hashCode();
        }
        if (getRessarticles() != null) {
            _hashCode += getRessarticles().hashCode();
        }
        if (getSingletemplate() != null) {
            _hashCode += getSingletemplate().hashCode();
        }
        if (getListtemplate() != null) {
            _hashCode += getListtemplate().hashCode();
        }
        if (getListtemplateheader() != null) {
            _hashCode += getListtemplateheader().hashCode();
        }
        if (getListtemplatefooter() != null) {
            _hashCode += getListtemplatefooter().hashCode();
        }
        if (getAddtemplatee() != null) {
            _hashCode += getAddtemplatee().hashCode();
        }
        if (getRsstemplate() != null) {
            _hashCode += getRsstemplate().hashCode();
        }
        if (getRsstitletemplate() != null) {
            _hashCode += getRsstitletemplate().hashCode();
        }
        if (getCsstemplate() != null) {
            _hashCode += getCsstemplate().hashCode();
        }
        if (getJstemplate() != null) {
            _hashCode += getJstemplate().hashCode();
        }
        if (getAsearchtemplate() != null) {
            _hashCode += getAsearchtemplate().hashCode();
        }
        if (getApproval() != null) {
            _hashCode += getApproval().hashCode();
        }
        if (getScale() != null) {
            _hashCode += getScale().hashCode();
        }
        if (getAssessed() != null) {
            _hashCode += getAssessed().hashCode();
        }
        if (getDefaultsort() != null) {
            _hashCode += getDefaultsort().hashCode();
        }
        if (getDefaultsortdir() != null) {
            _hashCode += getDefaultsortdir().hashCode();
        }
        if (getEditany() != null) {
            _hashCode += getEditany().hashCode();
        }
        if (getNotification() != null) {
            _hashCode += getNotification().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatabaseRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseRecord"));
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
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeavailablefrom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeavailablefrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeavailableto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeavailableto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeviewfrom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeviewfrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeviewto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeviewto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requiredentries");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requiredentries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requiredentriestoview");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requiredentriestoview"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxentries");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxentries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ressarticles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ressarticles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("singletemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "singletemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listtemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listtemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listtemplateheader");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listtemplateheader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listtemplatefooter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listtemplatefooter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addtemplatee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addtemplatee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rsstemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rsstemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rsstitletemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rsstitletemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("csstemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "csstemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jstemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jstemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asearchtemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asearchtemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approval"));
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
        elemField.setFieldName("assessed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assessed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultsort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "defaultsort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultsortdir");
        elemField.setXmlName(new javax.xml.namespace.QName("", "defaultsortdir"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("editany");
        elemField.setXmlName(new javax.xml.namespace.QName("", "editany"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notification");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notification"));
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
