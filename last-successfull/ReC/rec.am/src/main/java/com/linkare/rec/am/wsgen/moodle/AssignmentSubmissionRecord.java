/**
 * AssignmentSubmissionRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public class AssignmentSubmissionRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.math.BigInteger assignment;

    private java.lang.String assignmenttype;

    private java.math.BigInteger userid;

    private java.math.BigInteger timecreated;

    private java.math.BigInteger timemodified;

    private java.math.BigInteger numfiles;

    private java.lang.String data1;

    private java.lang.String data2;

    private java.math.BigInteger grade;

    private java.lang.String submissioncomment;

    private java.math.BigInteger format;

    private java.math.BigInteger teacher;

    private java.math.BigInteger timemarked;

    private java.math.BigInteger mailed;

    private java.lang.String useridnumber;

    private java.lang.String userusername;

    private java.lang.String useremail;

    private com.linkare.rec.am.wsgen.moodle.FileRecord[] files;

    public AssignmentSubmissionRecord() {
    }

    public AssignmentSubmissionRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.math.BigInteger assignment,
           java.lang.String assignmenttype,
           java.math.BigInteger userid,
           java.math.BigInteger timecreated,
           java.math.BigInteger timemodified,
           java.math.BigInteger numfiles,
           java.lang.String data1,
           java.lang.String data2,
           java.math.BigInteger grade,
           java.lang.String submissioncomment,
           java.math.BigInteger format,
           java.math.BigInteger teacher,
           java.math.BigInteger timemarked,
           java.math.BigInteger mailed,
           java.lang.String useridnumber,
           java.lang.String userusername,
           java.lang.String useremail,
           com.linkare.rec.am.wsgen.moodle.FileRecord[] files) {
           this.error = error;
           this.id = id;
           this.assignment = assignment;
           this.assignmenttype = assignmenttype;
           this.userid = userid;
           this.timecreated = timecreated;
           this.timemodified = timemodified;
           this.numfiles = numfiles;
           this.data1 = data1;
           this.data2 = data2;
           this.grade = grade;
           this.submissioncomment = submissioncomment;
           this.format = format;
           this.teacher = teacher;
           this.timemarked = timemarked;
           this.mailed = mailed;
           this.useridnumber = useridnumber;
           this.userusername = userusername;
           this.useremail = useremail;
           this.files = files;
    }


    /**
     * Gets the error value for this AssignmentSubmissionRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this AssignmentSubmissionRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this AssignmentSubmissionRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this AssignmentSubmissionRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the assignment value for this AssignmentSubmissionRecord.
     * 
     * @return assignment
     */
    public java.math.BigInteger getAssignment() {
        return assignment;
    }


    /**
     * Sets the assignment value for this AssignmentSubmissionRecord.
     * 
     * @param assignment
     */
    public void setAssignment(java.math.BigInteger assignment) {
        this.assignment = assignment;
    }


    /**
     * Gets the assignmenttype value for this AssignmentSubmissionRecord.
     * 
     * @return assignmenttype
     */
    public java.lang.String getAssignmenttype() {
        return assignmenttype;
    }


    /**
     * Sets the assignmenttype value for this AssignmentSubmissionRecord.
     * 
     * @param assignmenttype
     */
    public void setAssignmenttype(java.lang.String assignmenttype) {
        this.assignmenttype = assignmenttype;
    }


    /**
     * Gets the userid value for this AssignmentSubmissionRecord.
     * 
     * @return userid
     */
    public java.math.BigInteger getUserid() {
        return userid;
    }


    /**
     * Sets the userid value for this AssignmentSubmissionRecord.
     * 
     * @param userid
     */
    public void setUserid(java.math.BigInteger userid) {
        this.userid = userid;
    }


    /**
     * Gets the timecreated value for this AssignmentSubmissionRecord.
     * 
     * @return timecreated
     */
    public java.math.BigInteger getTimecreated() {
        return timecreated;
    }


    /**
     * Sets the timecreated value for this AssignmentSubmissionRecord.
     * 
     * @param timecreated
     */
    public void setTimecreated(java.math.BigInteger timecreated) {
        this.timecreated = timecreated;
    }


    /**
     * Gets the timemodified value for this AssignmentSubmissionRecord.
     * 
     * @return timemodified
     */
    public java.math.BigInteger getTimemodified() {
        return timemodified;
    }


    /**
     * Sets the timemodified value for this AssignmentSubmissionRecord.
     * 
     * @param timemodified
     */
    public void setTimemodified(java.math.BigInteger timemodified) {
        this.timemodified = timemodified;
    }


    /**
     * Gets the numfiles value for this AssignmentSubmissionRecord.
     * 
     * @return numfiles
     */
    public java.math.BigInteger getNumfiles() {
        return numfiles;
    }


    /**
     * Sets the numfiles value for this AssignmentSubmissionRecord.
     * 
     * @param numfiles
     */
    public void setNumfiles(java.math.BigInteger numfiles) {
        this.numfiles = numfiles;
    }


    /**
     * Gets the data1 value for this AssignmentSubmissionRecord.
     * 
     * @return data1
     */
    public java.lang.String getData1() {
        return data1;
    }


    /**
     * Sets the data1 value for this AssignmentSubmissionRecord.
     * 
     * @param data1
     */
    public void setData1(java.lang.String data1) {
        this.data1 = data1;
    }


    /**
     * Gets the data2 value for this AssignmentSubmissionRecord.
     * 
     * @return data2
     */
    public java.lang.String getData2() {
        return data2;
    }


    /**
     * Sets the data2 value for this AssignmentSubmissionRecord.
     * 
     * @param data2
     */
    public void setData2(java.lang.String data2) {
        this.data2 = data2;
    }


    /**
     * Gets the grade value for this AssignmentSubmissionRecord.
     * 
     * @return grade
     */
    public java.math.BigInteger getGrade() {
        return grade;
    }


    /**
     * Sets the grade value for this AssignmentSubmissionRecord.
     * 
     * @param grade
     */
    public void setGrade(java.math.BigInteger grade) {
        this.grade = grade;
    }


    /**
     * Gets the submissioncomment value for this AssignmentSubmissionRecord.
     * 
     * @return submissioncomment
     */
    public java.lang.String getSubmissioncomment() {
        return submissioncomment;
    }


    /**
     * Sets the submissioncomment value for this AssignmentSubmissionRecord.
     * 
     * @param submissioncomment
     */
    public void setSubmissioncomment(java.lang.String submissioncomment) {
        this.submissioncomment = submissioncomment;
    }


    /**
     * Gets the format value for this AssignmentSubmissionRecord.
     * 
     * @return format
     */
    public java.math.BigInteger getFormat() {
        return format;
    }


    /**
     * Sets the format value for this AssignmentSubmissionRecord.
     * 
     * @param format
     */
    public void setFormat(java.math.BigInteger format) {
        this.format = format;
    }


    /**
     * Gets the teacher value for this AssignmentSubmissionRecord.
     * 
     * @return teacher
     */
    public java.math.BigInteger getTeacher() {
        return teacher;
    }


    /**
     * Sets the teacher value for this AssignmentSubmissionRecord.
     * 
     * @param teacher
     */
    public void setTeacher(java.math.BigInteger teacher) {
        this.teacher = teacher;
    }


    /**
     * Gets the timemarked value for this AssignmentSubmissionRecord.
     * 
     * @return timemarked
     */
    public java.math.BigInteger getTimemarked() {
        return timemarked;
    }


    /**
     * Sets the timemarked value for this AssignmentSubmissionRecord.
     * 
     * @param timemarked
     */
    public void setTimemarked(java.math.BigInteger timemarked) {
        this.timemarked = timemarked;
    }


    /**
     * Gets the mailed value for this AssignmentSubmissionRecord.
     * 
     * @return mailed
     */
    public java.math.BigInteger getMailed() {
        return mailed;
    }


    /**
     * Sets the mailed value for this AssignmentSubmissionRecord.
     * 
     * @param mailed
     */
    public void setMailed(java.math.BigInteger mailed) {
        this.mailed = mailed;
    }


    /**
     * Gets the useridnumber value for this AssignmentSubmissionRecord.
     * 
     * @return useridnumber
     */
    public java.lang.String getUseridnumber() {
        return useridnumber;
    }


    /**
     * Sets the useridnumber value for this AssignmentSubmissionRecord.
     * 
     * @param useridnumber
     */
    public void setUseridnumber(java.lang.String useridnumber) {
        this.useridnumber = useridnumber;
    }


    /**
     * Gets the userusername value for this AssignmentSubmissionRecord.
     * 
     * @return userusername
     */
    public java.lang.String getUserusername() {
        return userusername;
    }


    /**
     * Sets the userusername value for this AssignmentSubmissionRecord.
     * 
     * @param userusername
     */
    public void setUserusername(java.lang.String userusername) {
        this.userusername = userusername;
    }


    /**
     * Gets the useremail value for this AssignmentSubmissionRecord.
     * 
     * @return useremail
     */
    public java.lang.String getUseremail() {
        return useremail;
    }


    /**
     * Sets the useremail value for this AssignmentSubmissionRecord.
     * 
     * @param useremail
     */
    public void setUseremail(java.lang.String useremail) {
        this.useremail = useremail;
    }


    /**
     * Gets the files value for this AssignmentSubmissionRecord.
     * 
     * @return files
     */
    public com.linkare.rec.am.wsgen.moodle.FileRecord[] getFiles() {
        return files;
    }


    /**
     * Sets the files value for this AssignmentSubmissionRecord.
     * 
     * @param files
     */
    public void setFiles(com.linkare.rec.am.wsgen.moodle.FileRecord[] files) {
        this.files = files;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AssignmentSubmissionRecord)) return false;
        AssignmentSubmissionRecord other = (AssignmentSubmissionRecord) obj;
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
            ((this.assignment==null && other.getAssignment()==null) || 
             (this.assignment!=null &&
              this.assignment.equals(other.getAssignment()))) &&
            ((this.assignmenttype==null && other.getAssignmenttype()==null) || 
             (this.assignmenttype!=null &&
              this.assignmenttype.equals(other.getAssignmenttype()))) &&
            ((this.userid==null && other.getUserid()==null) || 
             (this.userid!=null &&
              this.userid.equals(other.getUserid()))) &&
            ((this.timecreated==null && other.getTimecreated()==null) || 
             (this.timecreated!=null &&
              this.timecreated.equals(other.getTimecreated()))) &&
            ((this.timemodified==null && other.getTimemodified()==null) || 
             (this.timemodified!=null &&
              this.timemodified.equals(other.getTimemodified()))) &&
            ((this.numfiles==null && other.getNumfiles()==null) || 
             (this.numfiles!=null &&
              this.numfiles.equals(other.getNumfiles()))) &&
            ((this.data1==null && other.getData1()==null) || 
             (this.data1!=null &&
              this.data1.equals(other.getData1()))) &&
            ((this.data2==null && other.getData2()==null) || 
             (this.data2!=null &&
              this.data2.equals(other.getData2()))) &&
            ((this.grade==null && other.getGrade()==null) || 
             (this.grade!=null &&
              this.grade.equals(other.getGrade()))) &&
            ((this.submissioncomment==null && other.getSubmissioncomment()==null) || 
             (this.submissioncomment!=null &&
              this.submissioncomment.equals(other.getSubmissioncomment()))) &&
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.teacher==null && other.getTeacher()==null) || 
             (this.teacher!=null &&
              this.teacher.equals(other.getTeacher()))) &&
            ((this.timemarked==null && other.getTimemarked()==null) || 
             (this.timemarked!=null &&
              this.timemarked.equals(other.getTimemarked()))) &&
            ((this.mailed==null && other.getMailed()==null) || 
             (this.mailed!=null &&
              this.mailed.equals(other.getMailed()))) &&
            ((this.useridnumber==null && other.getUseridnumber()==null) || 
             (this.useridnumber!=null &&
              this.useridnumber.equals(other.getUseridnumber()))) &&
            ((this.userusername==null && other.getUserusername()==null) || 
             (this.userusername!=null &&
              this.userusername.equals(other.getUserusername()))) &&
            ((this.useremail==null && other.getUseremail()==null) || 
             (this.useremail!=null &&
              this.useremail.equals(other.getUseremail()))) &&
            ((this.files==null && other.getFiles()==null) || 
             (this.files!=null &&
              java.util.Arrays.equals(this.files, other.getFiles())));
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
        if (getAssignment() != null) {
            _hashCode += getAssignment().hashCode();
        }
        if (getAssignmenttype() != null) {
            _hashCode += getAssignmenttype().hashCode();
        }
        if (getUserid() != null) {
            _hashCode += getUserid().hashCode();
        }
        if (getTimecreated() != null) {
            _hashCode += getTimecreated().hashCode();
        }
        if (getTimemodified() != null) {
            _hashCode += getTimemodified().hashCode();
        }
        if (getNumfiles() != null) {
            _hashCode += getNumfiles().hashCode();
        }
        if (getData1() != null) {
            _hashCode += getData1().hashCode();
        }
        if (getData2() != null) {
            _hashCode += getData2().hashCode();
        }
        if (getGrade() != null) {
            _hashCode += getGrade().hashCode();
        }
        if (getSubmissioncomment() != null) {
            _hashCode += getSubmissioncomment().hashCode();
        }
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getTeacher() != null) {
            _hashCode += getTeacher().hashCode();
        }
        if (getTimemarked() != null) {
            _hashCode += getTimemarked().hashCode();
        }
        if (getMailed() != null) {
            _hashCode += getMailed().hashCode();
        }
        if (getUseridnumber() != null) {
            _hashCode += getUseridnumber().hashCode();
        }
        if (getUserusername() != null) {
            _hashCode += getUserusername().hashCode();
        }
        if (getUseremail() != null) {
            _hashCode += getUseremail().hashCode();
        }
        if (getFiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFiles(), i);
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
        new org.apache.axis.description.TypeDesc(AssignmentSubmissionRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "assignmentSubmissionRecord"));
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
        elemField.setFieldName("assignment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assignment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assignmenttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assignmenttype"));
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
        elemField.setFieldName("timecreated");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timecreated"));
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
        elemField.setFieldName("numfiles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numfiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "grade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submissioncomment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submissioncomment"));
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
        elemField.setFieldName("teacher");
        elemField.setXmlName(new javax.xml.namespace.QName("", "teacher"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timemarked");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timemarked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mailed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useridnumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "useridnumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userusername");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userusername"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useremail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "useremail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("files");
        elemField.setXmlName(new javax.xml.namespace.QName("", "files"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://localhost/moodle/wspp/wsdl", "fileRecord"));
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
