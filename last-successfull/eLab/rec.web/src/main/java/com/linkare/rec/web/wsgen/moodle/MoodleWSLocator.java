/**
 * MoodleWSLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class MoodleWSLocator extends org.apache.axis.client.Service implements com.linkare.rec.web.wsgen.moodle.MoodleWS {

    public MoodleWSLocator() {
    }


    public MoodleWSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MoodleWSLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MoodleWSPort
    private java.lang.String MoodleWSPort_address = "http://elab.ist.utl.pt/moodle/wspp/service_pp.php";

    public java.lang.String getMoodleWSPortAddress() {
        return MoodleWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MoodleWSPortWSDDServiceName = "MoodleWSPort";

    public java.lang.String getMoodleWSPortWSDDServiceName() {
        return MoodleWSPortWSDDServiceName;
    }

    public void setMoodleWSPortWSDDServiceName(java.lang.String name) {
        MoodleWSPortWSDDServiceName = name;
    }

    public com.linkare.rec.web.wsgen.moodle.MoodleWSPortType getMoodleWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MoodleWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMoodleWSPort(endpoint);
    }

    public com.linkare.rec.web.wsgen.moodle.MoodleWSPortType getMoodleWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.linkare.rec.web.wsgen.moodle.MoodleWSBindingStub _stub = new com.linkare.rec.web.wsgen.moodle.MoodleWSBindingStub(portAddress, this);
            _stub.setPortName(getMoodleWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMoodleWSPortEndpointAddress(java.lang.String address) {
        MoodleWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.linkare.rec.web.wsgen.moodle.MoodleWSPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.linkare.rec.web.wsgen.moodle.MoodleWSBindingStub _stub = new com.linkare.rec.web.wsgen.moodle.MoodleWSBindingStub(new java.net.URL(MoodleWSPort_address), this);
                _stub.setPortName(getMoodleWSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MoodleWSPort".equals(inputPortName)) {
            return getMoodleWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "MoodleWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "MoodleWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MoodleWSPort".equals(portName)) {
            setMoodleWSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
