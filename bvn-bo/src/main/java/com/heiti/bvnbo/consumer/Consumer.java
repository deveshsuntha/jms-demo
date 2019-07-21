package com.heiti.bvnbo.consumer;

import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "messageSelector",propertyValue = "app= 'msp'"),

		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/myQueue"),

		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class Consumer implements MessageListener {

	private static final Logger log = Logger.getLogger("BVN-BO");

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
