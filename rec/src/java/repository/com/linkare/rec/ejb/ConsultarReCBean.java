/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.ejb;

import com.linkare.rec.export.ejbinterfaces.ConsultarReCLocal;
import com.linkare.rec.export.ejbinterfaces.ConsultarReCRemote;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 *
 * @author artur
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ConsultarReCBean implements ConsultarReCRemote, ConsultarReCLocal {

    @Resource(mappedName = "jms/recQueueFactory")
    private ConnectionFactory factory;
    @Resource(mappedName = "jms/recQueue")
    private Queue queue;

    public void sendMessage(final String virtualPath, byte[] data) {
        Connection conection = null;
        try {
            conection = factory.createConnection();
            Session session = conection.createSession(true, 0);
            MessageProducer messageProducer = session.createProducer(queue);
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setBytes("bytes", data);
            mapMessage.setString("virtualPath", virtualPath);
            messageProducer.send(mapMessage);
        } catch (JMSException jmsex) {

        //handle of exception
        } catch (Exception e) {

        } finally {
            try {
                conection.close();
            } catch (JMSException jms) {
            //logar erro:
            }
        }

    }
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
}
