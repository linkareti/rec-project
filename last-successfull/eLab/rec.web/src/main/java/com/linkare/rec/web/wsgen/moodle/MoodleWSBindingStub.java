/**
 * MoodleWSBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class MoodleWSBindingStub extends org.apache.axis.client.Stub implements com.linkare.rec.web.wsgen.moodle.MoodleWSPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[108];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
        _initOperationDesc5();
        _initOperationDesc6();
        _initOperationDesc7();
        _initOperationDesc8();
        _initOperationDesc9();
        _initOperationDesc10();
        _initOperationDesc11();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("login");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "loginReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.LoginReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("logout");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_users");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "users"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editUsersInput"), com.linkare.rec.web.wsgen.moodle.EditUsersInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editUsersOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_users");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_courses");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courses"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCoursesInput"), com.linkare.rec.web.wsgen.moodle.EditCoursesInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCoursesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_courses");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_courses_search");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_resources");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getResourcesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetResourcesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_version");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_sections");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getSectionsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetSectionsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_instances_bytype");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "type"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getResourcesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetResourcesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_grades");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseidfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGradesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGradesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_user_grades");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGradesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGradesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_course_grades");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGradesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGradesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enrol_students");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseidfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolStudentsInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useridfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolStudentsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unenrol_students");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseidfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolStudentsInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useridfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolStudentsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_last_changes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "limit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getLastChangesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetLastChangesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_events");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "eventtype"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ownerid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getEventsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetEventsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_course_byid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "info"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_course_byidnumber");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "info"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_user");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_roles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getRolesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetRolesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_role_byid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getRolesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetRolesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_role_byname");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getRolesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetRolesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_categories");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCategoriesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_category_byid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCategoriesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_category_byname");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCategoriesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_my_courses");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sort"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_my_courses_byusername");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uinfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sort"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_my_courses_byidnumber");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uinfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sort"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_user_byusername");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userinfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_user_byidnumber");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userinfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_user_byid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userinfo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_users_bycourse");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcourse"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idrole"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("count_users_bycourse");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcourse"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idrole"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        oper.setReturnClass(java.math.BigInteger.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_courses_bycategory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_groups_bycourse");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGroupsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[37] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_group_byid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "info"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGroupsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[38] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_groups_byname");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "info"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGroupsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[39] = oper;

    }

    private static void _initOperationDesc5(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_group_members");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[40] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_grouping_members");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[41] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_my_id");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        oper.setReturnClass(java.math.BigInteger.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[42] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_my_group");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGroupsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[43] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_my_groups");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGroupsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[44] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_teachers");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[45] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_students");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[46] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("has_role_incourse");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "iduser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "iduserfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcourse"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcoursefield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idrole"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[47] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_primaryrole_incourse");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "iduser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "iduserfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcourse"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcoursefield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        oper.setReturnClass(java.math.BigInteger.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[48] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_activities");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "iduser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "iduserfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcourse"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idcoursefield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idlimit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getActivitiesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetActivitiesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[49] = oper;

    }

    private static void _initOperationDesc6(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("count_activities");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        oper.setReturnClass(java.math.BigInteger.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[50] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_assignment_submissions");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "assignmentid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userids"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersInput"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useridfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "timemodified"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAssignmentSubmissionsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAssignmentSubmissionsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[51] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_user");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userDatum"), com.linkare.rec.web.wsgen.moodle.UserDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editUsersOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[52] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "course"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseDatum"), com.linkare.rec.web.wsgen.moodle.CourseDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCoursesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[53] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_group");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "group"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupDatum"), com.linkare.rec.web.wsgen.moodle.GroupDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[54] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_grouping");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "grouping"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupingDatum"), com.linkare.rec.web.wsgen.moodle.GroupingDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupingsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[55] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_section");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "section"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionDatum"), com.linkare.rec.web.wsgen.moodle.SectionDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editSectionsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditSectionsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[56] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_label");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "label"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelDatum"), com.linkare.rec.web.wsgen.moodle.LabelDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editLabelsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditLabelsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[57] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_forum");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "forum"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "forumDatum"), com.linkare.rec.web.wsgen.moodle.ForumDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editForumsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditForumsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[58] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_database");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "database"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseDatum"), com.linkare.rec.web.wsgen.moodle.DatabaseDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editDatabasesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[59] = oper;

    }

    private static void _initOperationDesc7(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_assignment");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "assignment"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentDatum"), com.linkare.rec.web.wsgen.moodle.AssignmentDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editAssignmentsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[60] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_wiki");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "wiki"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "wikiDatum"), com.linkare.rec.web.wsgen.moodle.WikiDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editWikisOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditWikisOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[61] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_pagewiki");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "page"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiDatum"), com.linkare.rec.web.wsgen.moodle.PageWikiDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editPagesWikiOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[62] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_user");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editUsersOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[63] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("add_category");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "category"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "categoryDatum"), com.linkare.rec.web.wsgen.moodle.CategoryDatum.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCategoriesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[64] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCoursesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[65] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_group");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[66] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_grouping");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupingsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[67] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_user");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userDatum"), com.linkare.rec.web.wsgen.moodle.UserDatum.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editUsersOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[68] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "course"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseDatum"), com.linkare.rec.web.wsgen.moodle.CourseDatum.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCoursesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[69] = oper;

    }

    private static void _initOperationDesc8(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_section");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "section"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionDatum"), com.linkare.rec.web.wsgen.moodle.SectionDatum.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editSectionsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditSectionsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[70] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_group");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "group"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupDatum"), com.linkare.rec.web.wsgen.moodle.GroupDatum.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[71] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_grouping");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "grouping"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupingDatum"), com.linkare.rec.web.wsgen.moodle.GroupingDatum.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupingsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[72] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_labels");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "labels"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editLabelsInput"), com.linkare.rec.web.wsgen.moodle.EditLabelsInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editLabelsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditLabelsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[73] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_groups");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groups"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupsInput"), com.linkare.rec.web.wsgen.moodle.EditGroupsInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[74] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_assignments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "assignments"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editAssignmentsInput"), com.linkare.rec.web.wsgen.moodle.EditAssignmentsInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editAssignmentsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[75] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_databases");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "databases"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editDatabasesInput"), com.linkare.rec.web.wsgen.moodle.EditDatabasesInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editDatabasesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[76] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_categories");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categories"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCategoriesInput"), com.linkare.rec.web.wsgen.moodle.EditCategoriesInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCategoriesOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[77] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_sections");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sections"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editSectionsInput"), com.linkare.rec.web.wsgen.moodle.EditSectionsInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editSectionsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditSectionsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[78] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_forums");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "forums"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editForumsInput"), com.linkare.rec.web.wsgen.moodle.EditForumsInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editForumsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditForumsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[79] = oper;

    }

    private static void _initOperationDesc9(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_wikis");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "wikis"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editWikisInput"), com.linkare.rec.web.wsgen.moodle.EditWikisInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editWikisOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditWikisOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[80] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_pagesWiki");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pagesWiki"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editPagesWikiInput"), com.linkare.rec.web.wsgen.moodle.EditPagesWikiInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editPagesWikiOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[81] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_course_to_category");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[82] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_label_to_section");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "labelid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sectionid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[83] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_forum_to_section");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "forumid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sectionid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupmode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[84] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_section_to_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sectionid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[85] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_user_to_group");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[86] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_group_to_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "coursid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[87] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_wiki_to_section");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "wikiid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sectionid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupmode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "visible"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[88] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_database_to_section");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "databaseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sectionid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[89] = oper;

    }

    private static void _initOperationDesc10(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_assignment_to_section");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "assignmentid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sectionid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupmode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[90] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_user_to_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "rolename"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[91] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_pageWiki_to_wiki");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "wikiid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[92] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("remove_user_from_course");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "courseid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "rolename"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[93] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_groups");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGroupsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[94] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_forums");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllForumsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAllForumsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[95] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_labels");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllLabelsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAllLabelsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[96] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_wikis");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllWikisReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAllWikisReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[97] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_pagesWiki");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllPagesWikiReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAllPagesWikiReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[98] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_assignments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllAssignmentsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAllAssignmentsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[99] = oper;

    }

    private static void _initOperationDesc11(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_databases");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllDatabasesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAllDatabasesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[100] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_all_groupings");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllGroupingsReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetAllGroupingsReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[101] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("remove_user_from_group");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[102] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("edit_groupings");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupings"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupingsInput"), com.linkare.rec.web.wsgen.moodle.EditGroupingsInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupingsOutput"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[103] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("remove_group_from_grouping");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupingid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[104] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("affect_group_to_grouping");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "groupingid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[105] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_user_profile_values");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useridfield"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "values"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "profileitemRecords"), com.linkare.rec.web.wsgen.moodle.ProfileitemRecord[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "setUserProfileValuesReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.SetUserProfileValuesReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[106] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_users_byprofile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "client"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sesskey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "profilefieldname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "profilefieldvalue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn"));
        oper.setReturnClass(com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[107] = oper;

    }

    public MoodleWSBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public MoodleWSBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public MoodleWSBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "activityRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ActivityRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "activityRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ActivityRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "activityRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affectRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.AffectRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.AssignmentDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.AssignmentDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.AssignmentRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.AssignmentRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentSubmissionRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.AssignmentSubmissionRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentSubmissionRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.AssignmentSubmissionRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "assignmentSubmissionRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "categoryData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CategoryDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "categoryDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "categoryDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CategoryDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "categoryRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CategoryRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "categoryRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CategoryRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "categoryRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "changeRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ChangeRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "changeRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ChangeRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "changeRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CourseDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CourseDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CourseRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.CourseRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "courseRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.DatabaseDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.DatabaseDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.DatabaseRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.DatabaseRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "databaseRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editAssignmentsInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditAssignmentsInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editAssignmentsOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCategoriesInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditCategoriesInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCategoriesOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCoursesInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditCoursesInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editCoursesOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editDatabasesInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditDatabasesInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editDatabasesOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editForumsInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditForumsInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editForumsOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditForumsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupingsInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditGroupingsInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupingsOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupsInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditGroupsInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editGroupsOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editLabelsInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditLabelsInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editLabelsOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditLabelsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editPagesWikiInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditPagesWikiInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editPagesWikiOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editSectionsInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditSectionsInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editSectionsOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditSectionsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editUsersInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditUsersInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editUsersOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editWikisInput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditWikisInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "editWikisOutput");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EditWikisOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EnrolRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EnrolRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolStudentsInput");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrolStudentsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "eventRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EventRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "eventRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.EventRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "eventRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "fileRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.FileRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "fileRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.FileRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "fileRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "forumData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ForumDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "forumDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "forumDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ForumDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "forumRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ForumRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "forumRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ForumRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "forumRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getActivitiesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetActivitiesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllAssignmentsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAllAssignmentsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllDatabasesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAllDatabasesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllForumsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAllForumsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllGroupingsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAllGroupingsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllLabelsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAllLabelsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllPagesWikiReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAllPagesWikiReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAllWikisReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAllWikisReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getAssignmentSubmissionsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetAssignmentSubmissionsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCategoriesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesInput");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getCoursesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getEventsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetEventsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGradesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetGradesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getGroupsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getLastChangesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetLastChangesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getResourcesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetResourcesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getRolesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetRolesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getSectionsReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetSectionsReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersInput");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "getUsersReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "gradeRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GradeRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "gradeRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GradeRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "gradeRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupingData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupingDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupingDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupingRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupingRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupingRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupingRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupingRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.GroupRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "groupRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.LabelDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.LabelDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.LabelRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.LabelRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "labelRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "loginReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.LoginReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.PageWikiDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.PageWikiDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.PageWikiRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.PageWikiRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "pageWikiRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "profileitemRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ProfileitemRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "profileitemRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ProfileitemRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "profileitemRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "resourceRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ResourceRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "resourceRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.ResourceRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "resourceRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "roleRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.RoleRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "roleRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.RoleRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "roleRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.SectionDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.SectionDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.SectionRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.SectionRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "sectionRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "setUserProfileValuesReturn");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.SetUserProfileValuesReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.UserDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.UserDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.UserRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.UserRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "wikiData");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.WikiDatum[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "wikiDatum");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "wikiDatum");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.WikiDatum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "wikiRecord");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.WikiRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "wikiRecords");
            cachedSerQNames.add(qName);
            cls = com.linkare.rec.web.wsgen.moodle.WikiRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "wikiRecord");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.linkare.rec.web.wsgen.moodle.LoginReturn login(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#login");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "login"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {username, password});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.LoginReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.LoginReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.LoginReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public boolean logout(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#logout");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "logout"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditUsersOutput edit_users(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditUsersInput users) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_users");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_users"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, users});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_users(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] userids, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_users");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_users"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userids, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditCoursesOutput edit_courses(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditCoursesInput courses) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_courses");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_courses"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courses});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_courses(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_courses");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_courses"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseids, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_courses_search(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_courses_search");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_courses_search"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetResourcesReturn get_resources(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_resources");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_resources"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseids, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetResourcesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetResourcesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetResourcesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_version(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_version");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_version"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetSectionsReturn get_sections(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_sections");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_sections"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseids, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetSectionsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetSectionsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetSectionsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetResourcesReturn get_instances_bytype(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield, java.lang.String type) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_instances_bytype");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_instances_bytype"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseids, idfield, type});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetResourcesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetResourcesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetResourcesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGradesReturn get_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userid, java.lang.String userfield, java.lang.String[] courseids, java.lang.String courseidfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_grades");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_grades"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userid, userfield, courseids, courseidfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGradesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGradesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGradesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGradesReturn get_user_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_user_grades");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_user_grades"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGradesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGradesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGradesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGradesReturn get_course_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_course_grades");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_course_grades"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGradesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGradesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGradesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn enrol_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String[] userids, java.lang.String useridfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#enrol_students");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "enrol_students"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseid, courseidfield, userids, useridfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn unenrol_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String[] userids, java.lang.String useridfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#unenrol_students");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "unenrol_students"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseid, courseidfield, userids, useridfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EnrolStudentsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetLastChangesReturn get_last_changes(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield, java.math.BigInteger limit) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_last_changes");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_last_changes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseid, idfield, limit});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetLastChangesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetLastChangesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetLastChangesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetEventsReturn get_events(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger eventtype, java.math.BigInteger ownerid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_events");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_events"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, eventtype, ownerid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetEventsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetEventsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetEventsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_course(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseid, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_course_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_course_byid");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_course_byid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, info});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_course_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_course_byidnumber");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_course_byidnumber"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, info});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_user(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userid, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_user");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_user"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userid, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetRolesReturn get_roles(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_roles");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_roles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetRolesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetRolesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetRolesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetRolesReturn get_role_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_role_byid");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_role_byid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetRolesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetRolesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetRolesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetRolesReturn get_role_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_role_byname");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_role_byname"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetRolesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetRolesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetRolesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn get_categories(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_categories");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_categories"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn get_category_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_category_byid");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_category_byid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn get_category_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_category_byname");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_category_byname"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCategoriesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_my_courses(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger uid, java.lang.String sort) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_my_courses");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_my_courses"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, uid, sort});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_my_courses_byusername(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uinfo, java.lang.String sort) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_my_courses_byusername");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_my_courses_byusername"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, uinfo, sort});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_my_courses_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uinfo, java.lang.String sort) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_my_courses_byidnumber");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_my_courses_byidnumber"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, uinfo, sort});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_user_byusername(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_user_byusername");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_user_byusername"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userinfo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_user_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_user_byidnumber");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_user_byidnumber"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userinfo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_user_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_user_byid");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_user_byid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userinfo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_users_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String idcourse, java.lang.String idfield, java.math.BigInteger idrole) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_users_bycourse");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_users_bycourse"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, idcourse, idfield, idrole});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.math.BigInteger count_users_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String idcourse, java.lang.String idfield, java.math.BigInteger idrole) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#count_users_bycourse");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "count_users_bycourse"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, idcourse, idfield, idrole});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.math.BigInteger) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.math.BigInteger) org.apache.axis.utils.JavaUtils.convert(_resp, java.math.BigInteger.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetCoursesReturn get_courses_bycategory(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger categoryid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_courses_bycategory");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_courses_bycategory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, categoryid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetCoursesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetCoursesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGroupsReturn get_groups_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_groups_bycourse");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_groups_bycourse"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseid, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGroupsReturn get_group_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info, java.math.BigInteger courseid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_group_byid");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_group_byid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, info, courseid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGroupsReturn get_groups_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info, java.math.BigInteger courseid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[39]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_groups_byname");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_groups_byname"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, info, courseid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_group_members(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[40]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_group_members");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_group_members"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, groupid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_grouping_members(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[41]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_grouping_members");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_grouping_members"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, groupid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.math.BigInteger get_my_id(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[42]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_my_id");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_my_id"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.math.BigInteger) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.math.BigInteger) org.apache.axis.utils.JavaUtils.convert(_resp, java.math.BigInteger.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGroupsReturn get_my_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger uid, java.math.BigInteger courseid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[43]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_my_group");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_my_group"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, uid, courseid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGroupsReturn get_my_groups(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uid, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[44]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_my_groups");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_my_groups"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, uid, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_teachers(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[45]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_teachers");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_teachers"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[46]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_students");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_students"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public boolean has_role_incourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield, java.math.BigInteger idrole) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[47]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#has_role_incourse");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "has_role_incourse"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, iduser, iduserfield, idcourse, idcoursefield, idrole});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.math.BigInteger get_primaryrole_incourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[48]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_primaryrole_incourse");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_primaryrole_incourse"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, iduser, iduserfield, idcourse, idcoursefield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.math.BigInteger) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.math.BigInteger) org.apache.axis.utils.JavaUtils.convert(_resp, java.math.BigInteger.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetActivitiesReturn get_activities(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield, java.math.BigInteger idlimit) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[49]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_activities");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_activities"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, iduser, iduserfield, idcourse, idcoursefield, idlimit});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetActivitiesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetActivitiesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetActivitiesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.math.BigInteger count_activities(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value1, java.lang.String id1, java.lang.String value2, java.lang.String id2) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[50]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#count_activities");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "count_activities"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value1, id1, value2, id2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.math.BigInteger) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.math.BigInteger) org.apache.axis.utils.JavaUtils.convert(_resp, java.math.BigInteger.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAssignmentSubmissionsReturn get_assignment_submissions(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger assignmentid, java.lang.String[] userids, java.lang.String useridfield, java.math.BigInteger timemodified) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[51]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_assignment_submissions");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_assignment_submissions"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, assignmentid, userids, useridfield, timemodified});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAssignmentSubmissionsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAssignmentSubmissionsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAssignmentSubmissionsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditUsersOutput add_user(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.UserDatum user) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[52]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_user");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_user"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, user});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditCoursesOutput add_course(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.CourseDatum course) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[53]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, course});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupsOutput add_group(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.GroupDatum group) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[54]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_group");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_group"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, group});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput add_grouping(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.GroupingDatum grouping) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[55]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_grouping");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_grouping"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, grouping});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditSectionsOutput add_section(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.SectionDatum section) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[56]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_section");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_section"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, section});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditSectionsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditSectionsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditSectionsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditLabelsOutput add_label(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.LabelDatum label) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[57]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_label");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_label"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, label});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditLabelsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditLabelsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditLabelsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditForumsOutput add_forum(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.ForumDatum forum) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[58]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_forum");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_forum"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, forum});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditForumsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditForumsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditForumsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput add_database(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.DatabaseDatum database) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[59]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_database");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_database"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, database});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput add_assignment(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.AssignmentDatum assignment) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[60]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_assignment");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_assignment"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, assignment});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditWikisOutput add_wiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.WikiDatum wiki) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[61]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_wiki");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_wiki"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, wiki});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditWikisOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditWikisOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditWikisOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput add_pagewiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.PageWikiDatum page) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[62]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_pagewiki");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_pagewiki"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, page});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditUsersOutput delete_user(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[63]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#delete_user");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "delete_user"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput add_category(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.CategoryDatum category) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[64]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#add_category");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "add_category"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, category});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditCoursesOutput delete_course(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[65]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#delete_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "delete_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupsOutput delete_group(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[66]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#delete_group");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "delete_group"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput delete_grouping(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[67]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#delete_grouping");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "delete_grouping"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, value, id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditUsersOutput update_user(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.UserDatum user, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[68]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#update_user");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "update_user"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, user, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditUsersOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditUsersOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditCoursesOutput update_course(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.CourseDatum course, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[69]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#update_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "update_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, course, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditCoursesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditCoursesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditSectionsOutput update_section(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.SectionDatum section, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[70]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#update_section");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "update_section"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, section, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditSectionsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditSectionsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditSectionsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupsOutput update_group(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.GroupDatum group, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[71]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#update_group");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "update_group"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, group, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput update_grouping(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.GroupingDatum grouping, java.lang.String idfield) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[72]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#update_grouping");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "update_grouping"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, grouping, idfield});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditLabelsOutput edit_labels(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditLabelsInput labels) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[73]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_labels");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_labels"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, labels});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditLabelsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditLabelsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditLabelsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupsOutput edit_groups(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditGroupsInput groups) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[74]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_groups");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_groups"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, groups});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput edit_assignments(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditAssignmentsInput assignments) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[75]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_assignments");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_assignments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, assignments});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditAssignmentsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput edit_databases(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditDatabasesInput databases) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[76]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_databases");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_databases"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, databases});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditDatabasesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput edit_categories(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditCategoriesInput categories) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[77]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_categories");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_categories"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, categories});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditCategoriesOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditSectionsOutput edit_sections(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditSectionsInput sections) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[78]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_sections");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_sections"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, sections});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditSectionsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditSectionsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditSectionsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditForumsOutput edit_forums(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditForumsInput forums) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[79]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_forums");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_forums"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, forums});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditForumsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditForumsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditForumsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditWikisOutput edit_wikis(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditWikisInput wikis) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[80]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_wikis");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_wikis"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, wikis});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditWikisOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditWikisOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditWikisOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput edit_pagesWiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditPagesWikiInput pagesWiki) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[81]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_pagesWiki");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_pagesWiki"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, pagesWiki});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditPagesWikiOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_course_to_category(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger courseid, java.math.BigInteger categoryid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[82]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_course_to_category");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_course_to_category"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, courseid, categoryid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_label_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger labelid, java.math.BigInteger sectionid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[83]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_label_to_section");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_label_to_section"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, labelid, sectionid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_forum_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger forumid, java.math.BigInteger sectionid, java.math.BigInteger groupmode) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[84]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_forum_to_section");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_forum_to_section"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, forumid, sectionid, groupmode});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_section_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger sectionid, java.math.BigInteger courseid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[85]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_section_to_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_section_to_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, sectionid, courseid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_user_to_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger groupid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[86]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_user_to_group");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_user_to_group"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userid, groupid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_group_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger coursid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[87]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_group_to_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_group_to_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, groupid, coursid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_wiki_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger wikiid, java.math.BigInteger sectionid, java.math.BigInteger groupmode, java.math.BigInteger visible) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[88]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_wiki_to_section");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_wiki_to_section"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, wikiid, sectionid, groupmode, visible});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_database_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger databaseid, java.math.BigInteger sectionid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[89]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_database_to_section");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_database_to_section"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, databaseid, sectionid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_assignment_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger assignmentid, java.math.BigInteger sectionid, java.math.BigInteger groupmode) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[90]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_assignment_to_section");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_assignment_to_section"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, assignmentid, sectionid, groupmode});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_user_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger courseid, java.lang.String rolename) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[91]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_user_to_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_user_to_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userid, courseid, rolename});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_pageWiki_to_wiki(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger pageid, java.math.BigInteger wikiid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[92]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_pageWiki_to_wiki");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_pageWiki_to_wiki"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, pageid, wikiid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord remove_user_from_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger courseid, java.lang.String rolename) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[93]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#remove_user_from_course");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "remove_user_from_course"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userid, courseid, rolename});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetGroupsReturn get_all_groups(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[94]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_groups");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_groups"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetGroupsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetGroupsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAllForumsReturn get_all_forums(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[95]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_forums");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_forums"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAllForumsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAllForumsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAllForumsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAllLabelsReturn get_all_labels(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[96]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_labels");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_labels"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAllLabelsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAllLabelsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAllLabelsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAllWikisReturn get_all_wikis(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[97]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_wikis");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_wikis"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAllWikisReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAllWikisReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAllWikisReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAllPagesWikiReturn get_all_pagesWiki(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[98]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_pagesWiki");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_pagesWiki"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAllPagesWikiReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAllPagesWikiReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAllPagesWikiReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAllAssignmentsReturn get_all_assignments(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[99]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_assignments");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_assignments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAllAssignmentsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAllAssignmentsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAllAssignmentsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAllDatabasesReturn get_all_databases(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[100]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_databases");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_databases"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAllDatabasesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAllDatabasesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAllDatabasesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetAllGroupingsReturn get_all_groupings(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[101]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_all_groupings");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_all_groupings"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, fieldname, fieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetAllGroupingsReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetAllGroupingsReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetAllGroupingsReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord remove_user_from_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger groupid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[102]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#remove_user_from_group");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "remove_user_from_group"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userid, groupid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput edit_groupings(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.web.wsgen.moodle.EditGroupingsInput groupings) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[103]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#edit_groupings");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "edit_groupings"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, groupings});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.EditGroupingsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord remove_group_from_grouping(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger groupingid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[104]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#remove_group_from_grouping");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "remove_group_from_grouping"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, groupid, groupingid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.AffectRecord affect_group_to_grouping(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger groupingid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[105]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#affect_group_to_grouping");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "affect_group_to_grouping"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, groupid, groupingid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.AffectRecord) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.AffectRecord.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.SetUserProfileValuesReturn set_user_profile_values(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userid, java.lang.String useridfield, com.linkare.rec.web.wsgen.moodle.ProfileitemRecord[] values) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[106]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#set_user_profile_values");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "set_user_profile_values"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, userid, useridfield, values});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.SetUserProfileValuesReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.SetUserProfileValuesReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.SetUserProfileValuesReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.linkare.rec.web.wsgen.moodle.GetUsersReturn get_users_byprofile(java.math.BigInteger client, java.lang.String sesskey, java.lang.String profilefieldname, java.lang.String profilefieldvalue) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[107]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://elab.ist.utl.pt/moodle/wspp/wsdl#get_users_byprofile");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "get_users_byprofile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {client, sesskey, profilefieldname, profilefieldvalue});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.linkare.rec.web.wsgen.moodle.GetUsersReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.linkare.rec.web.wsgen.moodle.GetUsersReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
