package com.heiti.artemis;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ArtemisListener {

	private static Session session0 = null;

	public static void main(String[] args) throws JMSException {

		Connection conn0 = null;
		ConnectionFactory cf0 = null;

		try {
			Queue queue = ActiveMQJMSClient.createQueue("msp");
			cf0 = new ActiveMQConnectionFactory("tcp://localhost:61616");
			conn0 = cf0.createConnection();
			session0 = conn0.createSession(false, Session.AUTO_ACKNOWLEDGE);

			conn0.start();

			MessageConsumer consumer = session0.createConsumer(queue);
			
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					
				}
			});
			


		} finally {

			if (conn0 != null)
				conn0.close();
		}

	}

}