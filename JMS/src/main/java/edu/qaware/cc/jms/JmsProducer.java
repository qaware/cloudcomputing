//______________________________________________________________________________
//
//          Project: Vorlesung Cloudcomputing (Hochschule M�nchen)
//______________________________________________________________________________
//
//       created by: Adersberger / Weigend
//    creation date: 12.04.2012
//       changed by: $Author: $
//      change date: $LastChangedDate: $
//      description: 
//______________________________________________________________________________
//
//        Copyright: QAware GmbH 2012
//______________________________________________________________________________
package edu.qaware.cc.jms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmsProducer {

    private static final Logger LOG = LoggerFactory.getLogger(JmsProducer.class);

    private static String url = "tcp://localhost:61616";
    private static String subject = "ZWITSCHER";

    public static void main(String[] args) throws JMSException, InterruptedException, IOException {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Connection initialisiert - Twitscher:");
        try{
            while(true){
                String msg = in.readLine();
                TextMessage message = session.createTextMessage(msg);
                producer.send(message);
                LOG.info("Sent message '{}'",message.getText());
            }
        } finally {
            connection.close();
        }
    }
}