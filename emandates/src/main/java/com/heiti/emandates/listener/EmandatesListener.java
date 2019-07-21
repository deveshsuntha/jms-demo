package com.heiti.emandates.listener;

import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "app= 'Emandates'"),

		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/myQueue"),

		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class EmandatesListener implements MessageListener {

	private static final Logger log = Logger.getLogger("EMandates");

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message rcvMessage) {
		
		if(rcvMessage!=null) {
			
			String message;
			try {
				message = rcvMessage.getStringProperty("msg");
				log.info("Received msg : " + message);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
