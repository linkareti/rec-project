/**
 * PageWikiRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class PageWikiRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.lang.String pagename;

    private java.math.BigInteger version;

    private java.math.BigInteger flags;

    private java.lang.String content;

    private java.lang.String author;

    private java.math.BigInteger userid;

    private java.math.BigInteger created;

    private java.math.BigInteger lastmodified;

    private java.lang.String refs;

    private java.lang.String meta;

    private java.math.BigInteger hits;

    private java.math.BigInteger wiki;

    public PageWikiRecord() {
    }

    public PageWikiRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.lang.String pagename,
           java.math.BigInteger version,
           java.math.BigInteger flags,
           java.lang.String content,
           java.lang.String author,
           java.math.BigInteger userid,
           java.math.BigInteger created,
           java.math.BigInteger lastmodified,
           java.lang.String refs,
           java.lang.String meta,
           java.math.BigInteger hits,
           java.math.BigInteger wiki) {
           this.error = error;
           this.id = id;
           this.pagename = pagename;
           this.version = version;
           this.flags = flags;
           this.content = content;
           this.author = author;
           this.userid = userid;
           this.created = created;
           this.lastmodified = lastmodified;
           this.refs = refs;
           this.meta = meta;
           this.hits = hits;
           this.wiki = wiki;
    }


    /**
     * Gets the error value for this PageWikiRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this PageWikiRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this PageWikiRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this PageWikiRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the pagename value for this PageWikiRecord.
     * 
     * @return pagename
     */
    public java.lang.String getPagename() {
        return pagename;
    }


    /**
     * Sets the pagename value for this PageWikiRecord.
     * 
     * @param pagename
     */
    public void setPagename(java.lang.String pagename) {
        this.pagename = pagename;
    }


    /**
     * Gets the version value for this PageWikiRecord.
     * 
     * @return version
     */
    public java.math.BigInteger getVersion() {
        return version;
    }


    /**
     * Sets the version value for this PageWikiRecord.
     * 
     * @param version
     */
    public void setVersion(java.math.BigInteger version) {
        this.version = version;
    }


    /**
     * Gets the flags value for this PageWikiRecord.
     * 
     * @return flags
     */
    public java.math.BigInteger getFlags() {
        return flags;
    }


    /**
     * Sets the flags value for this PageWikiRecord.
     * 
     * @param flags
     */
    public void setFlags(java.math.BigInteger flags) {
        this.flags = flags;
    }


    /**
     * Gets the content value for this PageWikiRecord.
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this PageWikiRecord.
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the author value for this PageWikiRecord.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this PageWikiRecord.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the userid value for this PageWikiRecord.
     * 
     * @return userid
     */
    public java.math.BigInteger getUserid() {
        return userid;
    }


    /**
     * Sets the userid value for this PageWikiRecord.
     * 
     * @param userid
     */
    public void setUserid(java.math.BigInteger userid) {
        this.userid = userid;
    }


    /**
     * Gets the created value for this PageWikiRecord.
     * 
     * @return created
     */
    public java.math.BigInteger getCreated() {
        return created;
    }


    /**
     * Sets the created value for this PageWikiRecord.
     * 
     * @param created
     */
    public void setCreated(java.math.BigInteger created) {
        this.created = created;
    }


    /**
     * Gets the lastmodified value for this PageWikiRecord.
     * 
     * @return lastmodified
     */
    public java.math.BigInteger getLastmodified() {
        return lastmodified;
    }


    /**
     * Sets the lastmodified value for this PageWikiRecord.
     * 
     * @param lastmodified
     */
    public void setLastmodified(java.math.BigInteger lastmodified) {
        this.lastmodified = lastmodified;
    }


    /**
     * Gets the refs value for this PageWikiRecord.
     * 
     * @return refs
     */
    public java.lang.String getRefs() {
        return refs;
    }


    /**
     * Sets the refs value for this PageWikiRecord.
     * 
     * @param refs
     */
    public void setRefs(java.lang.String refs) {
        this.refs = refs;
    }


    /**
     * Gets the meta value for this PageWikiRecord.
     * 
     * @return meta
     */
    public java.lang.String getMeta() {
        return meta;
    }


    /**
     * Sets the meta value for this PageWikiRecord.
     * 
     * @param meta
     */
    public void setMeta(java.lang.String meta) {
        this.meta = meta;
    }


    /**
     * Gets the hits value for this PageWikiRecord.
     * 
     * @return hits
     */
    public java.math.BigInteger getHits() {
        return hits;
    }


    /**
     * Sets the hits value for this PageWikiRecord.
     * 
     * @param hits
     */
    public void setHits(java.math.BigInteger hits) {
        this.hits = hits;
    }


    /**
     * Gets the wiki value for this PageWikiRecord.
     * 
     * @return wiki
     */
    public java.math.BigInteger getWiki() {
        return wiki;
    }


    /**
     * Sets the wiki value for this PageWikiRecord.
     * 
     * @param wiki
     */
    public void setWiki(java.math.BigInteger wiki) {
        this.wiki = wiki;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PageWikiRecord)) return false;
        PageWikiRecord other = (PageWikiRecord) obj;
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
            ((this.pagename==null && other.getPagename()==null) || 
             (this.pagename!=null &&
              this.pagename.equals(other.getPagename()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.flags==null && other.getFlags()==null) || 
             (this.flags!=null &&
              this.flags.equals(other.getFlags()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            ((this.author==null && other.getAuthor()==null) || 
             (this.author!=null &&
              this.author.equals(other.getAuthor()))) &&
            ((this.userid==null && other.getUserid()==null) || 
             (this.userid!=null &&
              this.userid.equals(other.getUserid()))) &&
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            ((this.lastmodified==null && other.getLastmodified()==null) || 
             (this.lastmodified!=null &&
              this.lastmodified.equals(other.getLastmodified()))) &&
            ((this.refs==null && other.getRefs()==null) || 
             (this.refs!=null &&
              this.refs.equals(other.getRefs()))) &&
            ((this.meta==null && other.getMeta()==null) || 
             (this.meta!=null &&
              this.meta.equals(other.getMeta()))) &&
            ((this.hits==null && other.getHits()==null) || 
             (this.hits!=null &&
              this.hits.equals(other.getHits()))) &&
            ((this.wiki==null && other.getWiki()==null) || 
             (this.wiki!=null &&
              this.wiki.equals(other.getWiki())));
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
        if (getPagename() != null) {
            _hashCode += getPagename().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getFlags() != null) {
            _hashCode += getFlags().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        if (getAuthor() != null) {
            _hashCode += getAuthor().hashCode();
        }
        if (getUserid() != null) {
            _hashCode += getUserid().hashCode();
        }
        if (getCreated() != null) {
            _hashCode += getCreated().hashCode();
        }
        if (getLastmodified() != null) {
            _hashCode += getLastmodified().hashCode();
        }
        if (getRefs() != null) {
            _hashCode += getRefs().hashCode();
        }
        if (getMeta() != null) {
            _hashCode += getMeta().hashCode();
        }
        if (getHits() != null) {
            _hashCode += getHits().hashCode();
        }
        if (getWiki() != null) {
            _hashCode += getWiki().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PageWikiRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "pageWikiRecord"));
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
        elemField.setFieldName("pagename");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pagename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flags");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flags"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("", "content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author");
        elemField.setXmlName(new javax.xml.namespace.QName("", "author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("", "created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastmodified");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastmodified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("meta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "meta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hits");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wiki");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wiki"));
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
