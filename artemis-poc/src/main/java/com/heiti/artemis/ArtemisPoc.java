package com.heiti.artemis;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ArtemisPoc {

	private static Session session0 = null;

	public static void main(String[] args) throws JMSException {
		
		Connection conn0 = null;
		ConnectionFactory cf0 = null;
		
		try {
			Queue queue = ActiveMQJMSClient.createQueue("msp");
			cf0 = new ActiveMQConnectionFactory("tcp://localhost:61616");
			conn0 = cf0.createConnection();
			session0  = conn0.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			conn0.start();
			
			
			MessageProducer producer = session0.createProducer(queue);
			
			for(int i=0;i<10;i++)
				producer.send(createMessage(i));
			
			
		} finally {
			
			if(conn0!=null)
				conn0.close();
		}
		

		
		
	}

	private static Message createMessage(int i) throws JMSException {
		
		TextMessage message = null;
		if(i%2==0) {
			
			message = session0.createTextMessage("Even message" + i);
			message.setStringProperty("app", "even");
			System.out.println("Sent even message : " + i);
		}
			
		else {
			
			message = session0.createTextMessage("Odd message" + i);
			message.setStringProperty("app", "odd");
			System.out.println("Sent odd message : " + i);
		}
			
		return message;
	}

}
