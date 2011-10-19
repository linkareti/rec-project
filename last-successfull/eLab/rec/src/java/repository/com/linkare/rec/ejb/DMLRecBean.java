/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.ejb;

import com.linkare.rec.export.ejbinterfaces.DMLRecLocal;
import com.linkare.rec.export.ejbinterfaces.DMLRecRemote;
import com.linkare.rec.export.domain.EReCMultiCastDataProducer;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author artur
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DMLRecBean implements DMLRecRemote, DMLRecLocal {
    
    private static final Log log = LogFactory.getLog(DMLRecBean.class);

    @PersistenceContext(name = "RecEJBComponentPU")
    EntityManager entMan;

    public void persistRecDataProducer(final EReCMultiCastDataProducer parameter) {
        try {
            entMan.persist(parameter);
        } catch (Exception e) {
            log.error("Error persisting EReCMultiCastDataProducer",e);
        }
    }

    public String testBusinessMethod(String name) {
        return "Hello " + name;
    }
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
}
