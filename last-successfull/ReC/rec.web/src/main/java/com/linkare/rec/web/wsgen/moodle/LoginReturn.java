/**
 * LoginReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class LoginReturn  implements java.io.Serializable {
    private java.math.BigInteger client;

    private java.lang.String sessionkey;

    public LoginReturn() {
    }

    public LoginReturn(
           java.math.BigInteger client,
           java.lang.String sessionkey) {
           this.client = client;
           this.sessionkey = sessionkey;
    }


    /**
     * Gets the client value for this LoginReturn.
     * 
     * @return client
     */
    public java.math.BigInteger getClient() {
        return client;
    }


    /**
     * Sets the client value for this LoginReturn.
     * 
     * @param client
     */
    public void setClient(java.math.BigInteger client) {
        this.client = client;
    }


    /**
     * Gets the sessionkey value for this LoginReturn.
     * 
     * @return sessionkey
     */
    public java.lang.String getSessionkey() {
        return sessionkey;
    }


    /**
     * Sets the sessionkey value for this LoginReturn.
     * 
     * @param sessionkey
     */
    public void setSessionkey(java.lang.String sessionkey) {
        this.sessionkey = sessionkey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginReturn)) return false;
        LoginReturn other = (LoginReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.client==null && other.getClient()==null) || 
             (this.client!=null &&
              this.client.equals(other.getClient()))) &&
            ((this.sessionkey==null && other.getSessionkey()==null) || 
             (this.sessionkey!=null &&
              this.sessionkey.equals(other.getSessionkey())));
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
        if (getClient() != null) {
            _hashCode += getClient().hashCode();
        }
        if (getSessionkey() != null) {
            _hashCode += getSessionkey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "loginReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("client");
        elemField.setXmlName(new javax.xml.namespace.QName("", "client"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionkey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionkey"));
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
