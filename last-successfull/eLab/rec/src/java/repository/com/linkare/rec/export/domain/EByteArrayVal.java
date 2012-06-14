package com.linkare.rec.export.domain;

import com.linkare.rec.data.acquisition.ByteArrayValue;
import com.linkare.rec.export.domain.embeddable.EmbByteArrayValue;
import com.linkare.rec.export.ejbinterfaces.ConsultarReCLocal;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author artur
 */
@Entity
public class EByteArrayVal extends EPhysicsVal {
    
    private static final Log log = LogFactory.getLog(EByteArrayVal.class);
    
    private static final long serialVersionUID = 1L;
    private EmbByteArrayValue value;

    public EByteArrayVal() {
    }

    public EByteArrayVal(ByteArrayValue byteArrayValue) {
        this.setValue(new EmbByteArrayValue(byteArrayValue));
    }

    public EmbByteArrayValue getValue() {
        return value;
    }

    public void setValue(EmbByteArrayValue value) {
        this.value = value;
    }


    /* @Override
    public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
    }
    @Override
    public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EByteArrayValue)) {
    return false;
    }
    EByteArrayValue other = (EByteArrayValue) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
    return false;
    }
    return true;
    } */
    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + this.getId() + "]";
    }
    
    
    @PrePersist
    public void preInsert() {
        try {
            InitialContext ic = new InitialContext();
            ConsultarReCLocal beanLocal = (ConsultarReCLocal) 
                    ic.lookup("java:comp/env/ejb/com.linkare.rec.ejb.ConsultarReCBeanBean");
            beanLocal.sendMessage(this.getValue().getVirtualPath(), this.getValue().getData());
        } catch (NamingException ex) {
            log.error("NamingException when trying to invoke ConsultarReCLocal", ex);
        }

    }
}
