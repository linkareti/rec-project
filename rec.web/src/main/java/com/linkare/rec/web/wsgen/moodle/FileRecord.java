/**
 * FileRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class FileRecord  implements java.io.Serializable {
    private java.lang.String fileurl;

    private java.lang.String filename;

    private java.lang.String filepath;

    private java.math.BigInteger filesize;

    private java.lang.String filecontent;

    public FileRecord() {
    }

    public FileRecord(
           java.lang.String fileurl,
           java.lang.String filename,
           java.lang.String filepath,
           java.math.BigInteger filesize,
           java.lang.String filecontent) {
           this.fileurl = fileurl;
           this.filename = filename;
           this.filepath = filepath;
           this.filesize = filesize;
           this.filecontent = filecontent;
    }


    /**
     * Gets the fileurl value for this FileRecord.
     * 
     * @return fileurl
     */
    public java.lang.String getFileurl() {
        return fileurl;
    }


    /**
     * Sets the fileurl value for this FileRecord.
     * 
     * @param fileurl
     */
    public void setFileurl(java.lang.String fileurl) {
        this.fileurl = fileurl;
    }


    /**
     * Gets the filename value for this FileRecord.
     * 
     * @return filename
     */
    public java.lang.String getFilename() {
        return filename;
    }


    /**
     * Sets the filename value for this FileRecord.
     * 
     * @param filename
     */
    public void setFilename(java.lang.String filename) {
        this.filename = filename;
    }


    /**
     * Gets the filepath value for this FileRecord.
     * 
     * @return filepath
     */
    public java.lang.String getFilepath() {
        return filepath;
    }


    /**
     * Sets the filepath value for this FileRecord.
     * 
     * @param filepath
     */
    public void setFilepath(java.lang.String filepath) {
        this.filepath = filepath;
    }


    /**
     * Gets the filesize value for this FileRecord.
     * 
     * @return filesize
     */
    public java.math.BigInteger getFilesize() {
        return filesize;
    }


    /**
     * Sets the filesize value for this FileRecord.
     * 
     * @param filesize
     */
    public void setFilesize(java.math.BigInteger filesize) {
        this.filesize = filesize;
    }


    /**
     * Gets the filecontent value for this FileRecord.
     * 
     * @return filecontent
     */
    public java.lang.String getFilecontent() {
        return filecontent;
    }


    /**
     * Sets the filecontent value for this FileRecord.
     * 
     * @param filecontent
     */
    public void setFilecontent(java.lang.String filecontent) {
        this.filecontent = filecontent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FileRecord)) return false;
        FileRecord other = (FileRecord) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fileurl==null && other.getFileurl()==null) || 
             (this.fileurl!=null &&
              this.fileurl.equals(other.getFileurl()))) &&
            ((this.filename==null && other.getFilename()==null) || 
             (this.filename!=null &&
              this.filename.equals(other.getFilename()))) &&
            ((this.filepath==null && other.getFilepath()==null) || 
             (this.filepath!=null &&
              this.filepath.equals(other.getFilepath()))) &&
            ((this.filesize==null && other.getFilesize()==null) || 
             (this.filesize!=null &&
              this.filesize.equals(other.getFilesize()))) &&
            ((this.filecontent==null && other.getFilecontent()==null) || 
             (this.filecontent!=null &&
              this.filecontent.equals(other.getFilecontent())));
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
        if (getFileurl() != null) {
            _hashCode += getFileurl().hashCode();
        }
        if (getFilename() != null) {
            _hashCode += getFilename().hashCode();
        }
        if (getFilepath() != null) {
            _hashCode += getFilepath().hashCode();
        }
        if (getFilesize() != null) {
            _hashCode += getFilesize().hashCode();
        }
        if (getFilecontent() != null) {
            _hashCode += getFilecontent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FileRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "fileRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileurl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileurl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filename");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filepath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filepath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filesize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filesize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filecontent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filecontent"));
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
