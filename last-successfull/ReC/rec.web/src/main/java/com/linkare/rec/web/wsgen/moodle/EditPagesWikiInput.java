/**
 * EditPagesWikiInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class EditPagesWikiInput  implements java.io.Serializable {
    private com.linkare.rec.web.wsgen.moodle.PageWikiDatum[] pagesWiki;

    public EditPagesWikiInput() {
    }

    public EditPagesWikiInput(
           com.linkare.rec.web.wsgen.moodle.PageWikiDatum[] pagesWiki) {
           this.pagesWiki = pagesWiki;
    }


    /**
     * Gets the pagesWiki value for this EditPagesWikiInput.
     * 
     * @return pagesWiki
     */
    public com.linkare.rec.web.wsgen.moodle.PageWikiDatum[] getPagesWiki() {
        return pagesWiki;
    }


    /**
     * Sets the pagesWiki value for this EditPagesWikiInput.
     * 
     * @param pagesWiki
     */
    public void setPagesWiki(com.linkare.rec.web.wsgen.moodle.PageWikiDatum[] pagesWiki) {
        this.pagesWiki = pagesWiki;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EditPagesWikiInput)) return false;
        EditPagesWikiInput other = (EditPagesWikiInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pagesWiki==null && other.getPagesWiki()==null) || 
             (this.pagesWiki!=null &&
              java.util.Arrays.equals(this.pagesWiki, other.getPagesWiki())));
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
        if (getPagesWiki() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPagesWiki());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPagesWiki(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EditPagesWikiInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editPagesWikiInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagesWiki");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pagesWiki"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiDatum"));
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
