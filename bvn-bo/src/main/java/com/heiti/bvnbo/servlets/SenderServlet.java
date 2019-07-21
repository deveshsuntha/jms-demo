package com.heiti.bvnbo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SenderServlet")
public class SenderServlet extends HttpServlet {

	private static final long serialVersionUID = -8314035702649252239L;
	private Logger log = Logger.getLogger(SenderServlet.class.getName());

	private static final String EMANDATES = "Send MetaData Emandates";
	private static final String iDIN = "Send MetaData iDIN";
	private static final String iDEAL = "Send MetaData iDEAL";

	@Inject
	private JMSContext context;

	@Resource(lookup = "java:/jms/queue/myQueue")
	private Queue queue;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	log.info("param map " + req.getParameterMap());
    	
    	Map<String, String[]> paraMap = req.getParameterMap();
    	
    	
    	try {
    	
    	if(paraMap.containsKey(EMANDATES)) {
    		
    		 Message jmsMsg = context.createMessage();
             jmsMsg.setStringProperty("app", "Emandates");
             jmsMsg.setStringProperty("msg", "Hello Emandates");
             sendMetaData(jmsMsg, resp);
    	}
    		
    	else if(paraMap.containsKey(iDIN)){
   		 Message jmsMsg = context.createMessage();
            jmsMsg.setStringProperty("app", "iDIN");
            jmsMsg.setStringProperty("msg", "Hello iDIN");
            sendMetaData(jmsMsg, resp);
   	}
    	else if(paraMap.containsKey(iDEAL)){
    		
   		 Message jmsMsg = context.createMessage();
            jmsMsg.setStringProperty("app", "iDEAL");
            jmsMsg.setStringProperty("msg", "Hello iDEAL");
            sendMetaData(jmsMsg, resp);
   	}
    	else
    		log.warning("Not a valid action");
    	
    	} catch(Exception e) {
    		log.log(Level.SEVERE, "Unable to create jms meg", e);
    	}
    }

	private void sendMetaData(Message message, HttpServletResponse resp) {
		
		PrintWriter out = null;
		
		try {
			
			resp.setContentType("text/html");
	         out = resp.getWriter();
            final Destination destination = queue;

                JMSProducer producer = context.createProducer();
                producer.send(destination, message);
                log.log(Level.INFO, "Message Sent " + message.getStringProperty("app"));
                out.write("<h3>Message Sent</h3><br>");
                out.write("<a href='./index.jsp'>HOME</a>");
            } catch(Exception e) {
                log.log(Level.SEVERE, "Unable to send message");
            }
            finally {
            	
            	if (out != null) {
                    out.close();
                }
            	
            }
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
