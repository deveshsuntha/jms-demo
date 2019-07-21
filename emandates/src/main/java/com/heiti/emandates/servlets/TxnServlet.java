package com.heiti.emandates.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TxnServlet")
public class TxnServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4623920671980696267L;
	
	@Inject
	private JMSContext context;

	@Resource(lookup = "java:/jms/queue/myQueue")
	private Queue queue;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		JMSProducer jmsProducer = context.createProducer();
		Message message = context.createMessage();
		try {
			message.setStringProperty("app", "msp");
			message.setStringProperty("msg", "Hello from Emandates");
			Destination destination = queue;
			jmsProducer.send(destination , message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.sendRedirect("./index.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
	
	

}
