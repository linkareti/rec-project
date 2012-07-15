/**
 * UserRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.web.wsgen.moodle;

public class UserRecord  implements java.io.Serializable {
    private java.lang.String error;

    private java.math.BigInteger id;

    private java.lang.String auth;

    private java.math.BigInteger confirmed;

    private java.math.BigInteger policyagreed;

    private java.math.BigInteger deleted;

    private java.lang.String username;

    private java.lang.String idnumber;

    private java.lang.String firstname;

    private java.lang.String lastname;

    private java.lang.String email;

    private java.lang.String icq;

    private java.math.BigInteger emailstop;

    private java.lang.String skype;

    private java.lang.String yahoo;

    private java.lang.String aim;

    private java.lang.String msn;

    private java.lang.String phone1;

    private java.lang.String phone2;

    private java.lang.String institution;

    private java.lang.String department;

    private java.lang.String address;

    private java.lang.String city;

    private java.lang.String country;

    private java.lang.String lang;

    private java.math.BigInteger timezone;

    private java.math.BigInteger mnethostid;

    private java.lang.String lastip;

    private java.lang.String theme;

    private java.lang.String description;

    private java.math.BigInteger role;

    private com.linkare.rec.web.wsgen.moodle.ProfileitemRecord[] profile;

    public UserRecord() {
    }

    public UserRecord(
           java.lang.String error,
           java.math.BigInteger id,
           java.lang.String auth,
           java.math.BigInteger confirmed,
           java.math.BigInteger policyagreed,
           java.math.BigInteger deleted,
           java.lang.String username,
           java.lang.String idnumber,
           java.lang.String firstname,
           java.lang.String lastname,
           java.lang.String email,
           java.lang.String icq,
           java.math.BigInteger emailstop,
           java.lang.String skype,
           java.lang.String yahoo,
           java.lang.String aim,
           java.lang.String msn,
           java.lang.String phone1,
           java.lang.String phone2,
           java.lang.String institution,
           java.lang.String department,
           java.lang.String address,
           java.lang.String city,
           java.lang.String country,
           java.lang.String lang,
           java.math.BigInteger timezone,
           java.math.BigInteger mnethostid,
           java.lang.String lastip,
           java.lang.String theme,
           java.lang.String description,
           java.math.BigInteger role,
           com.linkare.rec.web.wsgen.moodle.ProfileitemRecord[] profile) {
           this.error = error;
           this.id = id;
           this.auth = auth;
           this.confirmed = confirmed;
           this.policyagreed = policyagreed;
           this.deleted = deleted;
           this.username = username;
           this.idnumber = idnumber;
           this.firstname = firstname;
           this.lastname = lastname;
           this.email = email;
           this.icq = icq;
           this.emailstop = emailstop;
           this.skype = skype;
           this.yahoo = yahoo;
           this.aim = aim;
           this.msn = msn;
           this.phone1 = phone1;
           this.phone2 = phone2;
           this.institution = institution;
           this.department = department;
           this.address = address;
           this.city = city;
           this.country = country;
           this.lang = lang;
           this.timezone = timezone;
           this.mnethostid = mnethostid;
           this.lastip = lastip;
           this.theme = theme;
           this.description = description;
           this.role = role;
           this.profile = profile;
    }


    /**
     * Gets the error value for this UserRecord.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this UserRecord.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the id value for this UserRecord.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this UserRecord.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the auth value for this UserRecord.
     * 
     * @return auth
     */
    public java.lang.String getAuth() {
        return auth;
    }


    /**
     * Sets the auth value for this UserRecord.
     * 
     * @param auth
     */
    public void setAuth(java.lang.String auth) {
        this.auth = auth;
    }


    /**
     * Gets the confirmed value for this UserRecord.
     * 
     * @return confirmed
     */
    public java.math.BigInteger getConfirmed() {
        return confirmed;
    }


    /**
     * Sets the confirmed value for this UserRecord.
     * 
     * @param confirmed
     */
    public void setConfirmed(java.math.BigInteger confirmed) {
        this.confirmed = confirmed;
    }


    /**
     * Gets the policyagreed value for this UserRecord.
     * 
     * @return policyagreed
     */
    public java.math.BigInteger getPolicyagreed() {
        return policyagreed;
    }


    /**
     * Sets the policyagreed value for this UserRecord.
     * 
     * @param policyagreed
     */
    public void setPolicyagreed(java.math.BigInteger policyagreed) {
        this.policyagreed = policyagreed;
    }


    /**
     * Gets the deleted value for this UserRecord.
     * 
     * @return deleted
     */
    public java.math.BigInteger getDeleted() {
        return deleted;
    }


    /**
     * Sets the deleted value for this UserRecord.
     * 
     * @param deleted
     */
    public void setDeleted(java.math.BigInteger deleted) {
        this.deleted = deleted;
    }


    /**
     * Gets the username value for this UserRecord.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this UserRecord.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the idnumber value for this UserRecord.
     * 
     * @return idnumber
     */
    public java.lang.String getIdnumber() {
        return idnumber;
    }


    /**
     * Sets the idnumber value for this UserRecord.
     * 
     * @param idnumber
     */
    public void setIdnumber(java.lang.String idnumber) {
        this.idnumber = idnumber;
    }


    /**
     * Gets the firstname value for this UserRecord.
     * 
     * @return firstname
     */
    public java.lang.String getFirstname() {
        return firstname;
    }


    /**
     * Sets the firstname value for this UserRecord.
     * 
     * @param firstname
     */
    public void setFirstname(java.lang.String firstname) {
        this.firstname = firstname;
    }


    /**
     * Gets the lastname value for this UserRecord.
     * 
     * @return lastname
     */
    public java.lang.String getLastname() {
        return lastname;
    }


    /**
     * Sets the lastname value for this UserRecord.
     * 
     * @param lastname
     */
    public void setLastname(java.lang.String lastname) {
        this.lastname = lastname;
    }


    /**
     * Gets the email value for this UserRecord.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this UserRecord.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the icq value for this UserRecord.
     * 
     * @return icq
     */
    public java.lang.String getIcq() {
        return icq;
    }


    /**
     * Sets the icq value for this UserRecord.
     * 
     * @param icq
     */
    public void setIcq(java.lang.String icq) {
        this.icq = icq;
    }


    /**
     * Gets the emailstop value for this UserRecord.
     * 
     * @return emailstop
     */
    public java.math.BigInteger getEmailstop() {
        return emailstop;
    }


    /**
     * Sets the emailstop value for this UserRecord.
     * 
     * @param emailstop
     */
    public void setEmailstop(java.math.BigInteger emailstop) {
        this.emailstop = emailstop;
    }


    /**
     * Gets the skype value for this UserRecord.
     * 
     * @return skype
     */
    public java.lang.String getSkype() {
        return skype;
    }


    /**
     * Sets the skype value for this UserRecord.
     * 
     * @param skype
     */
    public void setSkype(java.lang.String skype) {
        this.skype = skype;
    }


    /**
     * Gets the yahoo value for this UserRecord.
     * 
     * @return yahoo
     */
    public java.lang.String getYahoo() {
        return yahoo;
    }


    /**
     * Sets the yahoo value for this UserRecord.
     * 
     * @param yahoo
     */
    public void setYahoo(java.lang.String yahoo) {
        this.yahoo = yahoo;
    }


    /**
     * Gets the aim value for this UserRecord.
     * 
     * @return aim
     */
    public java.lang.String getAim() {
        return aim;
    }


    /**
     * Sets the aim value for this UserRecord.
     * 
     * @param aim
     */
    public void setAim(java.lang.String aim) {
        this.aim = aim;
    }


    /**
     * Gets the msn value for this UserRecord.
     * 
     * @return msn
     */
    public java.lang.String getMsn() {
        return msn;
    }


    /**
     * Sets the msn value for this UserRecord.
     * 
     * @param msn
     */
    public void setMsn(java.lang.String msn) {
        this.msn = msn;
    }


    /**
     * Gets the phone1 value for this UserRecord.
     * 
     * @return phone1
     */
    public java.lang.String getPhone1() {
        return phone1;
    }


    /**
     * Sets the phone1 value for this UserRecord.
     * 
     * @param phone1
     */
    public void setPhone1(java.lang.String phone1) {
        this.phone1 = phone1;
    }


    /**
     * Gets the phone2 value for this UserRecord.
     * 
     * @return phone2
     */
    public java.lang.String getPhone2() {
        return phone2;
    }


    /**
     * Sets the phone2 value for this UserRecord.
     * 
     * @param phone2
     */
    public void setPhone2(java.lang.String phone2) {
        this.phone2 = phone2;
    }


    /**
     * Gets the institution value for this UserRecord.
     * 
     * @return institution
     */
    public java.lang.String getInstitution() {
        return institution;
    }


    /**
     * Sets the institution value for this UserRecord.
     * 
     * @param institution
     */
    public void setInstitution(java.lang.String institution) {
        this.institution = institution;
    }


    /**
     * Gets the department value for this UserRecord.
     * 
     * @return department
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this UserRecord.
     * 
     * @param department
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the address value for this UserRecord.
     * 
     * @return address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this UserRecord.
     * 
     * @param address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the city value for this UserRecord.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this UserRecord.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the country value for this UserRecord.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this UserRecord.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the lang value for this UserRecord.
     * 
     * @return lang
     */
    public java.lang.String getLang() {
        return lang;
    }


    /**
     * Sets the lang value for this UserRecord.
     * 
     * @param lang
     */
    public void setLang(java.lang.String lang) {
        this.lang = lang;
    }


    /**
     * Gets the timezone value for this UserRecord.
     * 
     * @return timezone
     */
    public java.math.BigInteger getTimezone() {
        return timezone;
    }


    /**
     * Sets the timezone value for this UserRecord.
     * 
     * @param timezone
     */
    public void setTimezone(java.math.BigInteger timezone) {
        this.timezone = timezone;
    }


    /**
     * Gets the mnethostid value for this UserRecord.
     * 
     * @return mnethostid
     */
    public java.math.BigInteger getMnethostid() {
        return mnethostid;
    }


    /**
     * Sets the mnethostid value for this UserRecord.
     * 
     * @param mnethostid
     */
    public void setMnethostid(java.math.BigInteger mnethostid) {
        this.mnethostid = mnethostid;
    }


    /**
     * Gets the lastip value for this UserRecord.
     * 
     * @return lastip
     */
    public java.lang.String getLastip() {
        return lastip;
    }


    /**
     * Sets the lastip value for this UserRecord.
     * 
     * @param lastip
     */
    public void setLastip(java.lang.String lastip) {
        this.lastip = lastip;
    }


    /**
     * Gets the theme value for this UserRecord.
     * 
     * @return theme
     */
    public java.lang.String getTheme() {
        return theme;
    }


    /**
     * Sets the theme value for this UserRecord.
     * 
     * @param theme
     */
    public void setTheme(java.lang.String theme) {
        this.theme = theme;
    }


    /**
     * Gets the description value for this UserRecord.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this UserRecord.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the role value for this UserRecord.
     * 
     * @return role
     */
    public java.math.BigInteger getRole() {
        return role;
    }


    /**
     * Sets the role value for this UserRecord.
     * 
     * @param role
     */
    public void setRole(java.math.BigInteger role) {
        this.role = role;
    }


    /**
     * Gets the profile value for this UserRecord.
     * 
     * @return profile
     */
    public com.linkare.rec.web.wsgen.moodle.ProfileitemRecord[] getProfile() {
        return profile;
    }


    /**
     * Sets the profile value for this UserRecord.
     * 
     * @param profile
     */
    public void setProfile(com.linkare.rec.web.wsgen.moodle.ProfileitemRecord[] profile) {
        this.profile = profile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserRecord)) return false;
        UserRecord other = (UserRecord) obj;
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
            ((this.auth==null && other.getAuth()==null) || 
             (this.auth!=null &&
              this.auth.equals(other.getAuth()))) &&
            ((this.confirmed==null && other.getConfirmed()==null) || 
             (this.confirmed!=null &&
              this.confirmed.equals(other.getConfirmed()))) &&
            ((this.policyagreed==null && other.getPolicyagreed()==null) || 
             (this.policyagreed!=null &&
              this.policyagreed.equals(other.getPolicyagreed()))) &&
            ((this.deleted==null && other.getDeleted()==null) || 
             (this.deleted!=null &&
              this.deleted.equals(other.getDeleted()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.idnumber==null && other.getIdnumber()==null) || 
             (this.idnumber!=null &&
              this.idnumber.equals(other.getIdnumber()))) &&
            ((this.firstname==null && other.getFirstname()==null) || 
             (this.firstname!=null &&
              this.firstname.equals(other.getFirstname()))) &&
            ((this.lastname==null && other.getLastname()==null) || 
             (this.lastname!=null &&
              this.lastname.equals(other.getLastname()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.icq==null && other.getIcq()==null) || 
             (this.icq!=null &&
              this.icq.equals(other.getIcq()))) &&
            ((this.emailstop==null && other.getEmailstop()==null) || 
             (this.emailstop!=null &&
              this.emailstop.equals(other.getEmailstop()))) &&
            ((this.skype==null && other.getSkype()==null) || 
             (this.skype!=null &&
              this.skype.equals(other.getSkype()))) &&
            ((this.yahoo==null && other.getYahoo()==null) || 
             (this.yahoo!=null &&
              this.yahoo.equals(other.getYahoo()))) &&
            ((this.aim==null && other.getAim()==null) || 
             (this.aim!=null &&
              this.aim.equals(other.getAim()))) &&
            ((this.msn==null && other.getMsn()==null) || 
             (this.msn!=null &&
              this.msn.equals(other.getMsn()))) &&
            ((this.phone1==null && other.getPhone1()==null) || 
             (this.phone1!=null &&
              this.phone1.equals(other.getPhone1()))) &&
            ((this.phone2==null && other.getPhone2()==null) || 
             (this.phone2!=null &&
              this.phone2.equals(other.getPhone2()))) &&
            ((this.institution==null && other.getInstitution()==null) || 
             (this.institution!=null &&
              this.institution.equals(other.getInstitution()))) &&
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.lang==null && other.getLang()==null) || 
             (this.lang!=null &&
              this.lang.equals(other.getLang()))) &&
            ((this.timezone==null && other.getTimezone()==null) || 
             (this.timezone!=null &&
              this.timezone.equals(other.getTimezone()))) &&
            ((this.mnethostid==null && other.getMnethostid()==null) || 
             (this.mnethostid!=null &&
              this.mnethostid.equals(other.getMnethostid()))) &&
            ((this.lastip==null && other.getLastip()==null) || 
             (this.lastip!=null &&
              this.lastip.equals(other.getLastip()))) &&
            ((this.theme==null && other.getTheme()==null) || 
             (this.theme!=null &&
              this.theme.equals(other.getTheme()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.role==null && other.getRole()==null) || 
             (this.role!=null &&
              this.role.equals(other.getRole()))) &&
            ((this.profile==null && other.getProfile()==null) || 
             (this.profile!=null &&
              java.util.Arrays.equals(this.profile, other.getProfile())));
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
        if (getAuth() != null) {
            _hashCode += getAuth().hashCode();
        }
        if (getConfirmed() != null) {
            _hashCode += getConfirmed().hashCode();
        }
        if (getPolicyagreed() != null) {
            _hashCode += getPolicyagreed().hashCode();
        }
        if (getDeleted() != null) {
            _hashCode += getDeleted().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getIdnumber() != null) {
            _hashCode += getIdnumber().hashCode();
        }
        if (getFirstname() != null) {
            _hashCode += getFirstname().hashCode();
        }
        if (getLastname() != null) {
            _hashCode += getLastname().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getIcq() != null) {
            _hashCode += getIcq().hashCode();
        }
        if (getEmailstop() != null) {
            _hashCode += getEmailstop().hashCode();
        }
        if (getSkype() != null) {
            _hashCode += getSkype().hashCode();
        }
        if (getYahoo() != null) {
            _hashCode += getYahoo().hashCode();
        }
        if (getAim() != null) {
            _hashCode += getAim().hashCode();
        }
        if (getMsn() != null) {
            _hashCode += getMsn().hashCode();
        }
        if (getPhone1() != null) {
            _hashCode += getPhone1().hashCode();
        }
        if (getPhone2() != null) {
            _hashCode += getPhone2().hashCode();
        }
        if (getInstitution() != null) {
            _hashCode += getInstitution().hashCode();
        }
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getLang() != null) {
            _hashCode += getLang().hashCode();
        }
        if (getTimezone() != null) {
            _hashCode += getTimezone().hashCode();
        }
        if (getMnethostid() != null) {
            _hashCode += getMnethostid().hashCode();
        }
        if (getLastip() != null) {
            _hashCode += getLastip().hashCode();
        }
        if (getTheme() != null) {
            _hashCode += getTheme().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getRole() != null) {
            _hashCode += getRole().hashCode();
        }
        if (getProfile() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProfile());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProfile(), i);
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
        new org.apache.axis.description.TypeDesc(UserRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "userRecord"));
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
        elemField.setFieldName("auth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "auth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("confirmed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "confirmed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyagreed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "policyagreed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleted");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idnumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idnumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("icq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "icq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailstop");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailstop"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("skype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "skype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yahoo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yahoo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aim");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "msn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("institution");
        elemField.setXmlName(new javax.xml.namespace.QName("", "institution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("", "department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("", "city"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lang");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lang"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timezone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timezone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mnethostid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mnethostid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("theme");
        elemField.setXmlName(new javax.xml.namespace.QName("", "theme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("role");
        elemField.setXmlName(new javax.xml.namespace.QName("", "role"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://elab.ist.utl.pt/moodle/wspp/wsdl", "profileitemRecord"));
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
