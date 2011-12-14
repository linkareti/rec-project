/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.ejb;


import com.linkare.rec.utils.IoHelper;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author artur
 */
@MessageDriven(mappedName = "jms/recQueue", activationConfig = {
@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RecMDBBean implements MessageListener {
    
    private static final Log log = LogFactory.getLog(RecMDBBean.class);

    public RecMDBBean() {
    }

    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            IoHelper ioHelper = new IoHelper();
            ioHelper.writeToFS(mapMessage.getString("virtualPath"), mapMessage.getBytes("bytes"));
        } catch (JMSException ex) {
            log.error("JMSException reading the message", ex);
        }
    }
}
