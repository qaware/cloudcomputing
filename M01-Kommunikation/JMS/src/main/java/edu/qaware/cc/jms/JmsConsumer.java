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

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmsConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(JmsConsumer.class);

    private static String url = "tcp://localhost:61616";
    private static String subject = "ZWITSCHER";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory
                = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(subject);

        MessageConsumer consumer = session.createConsumer(destination);
        try {
            while(true){
                // By default this call is blocking
                Message message = consumer.receive();

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    LOG.info("Received message '{}'", textMessage.getText());
                }
            }
        } finally {
            connection.close();
        }
    }
}
