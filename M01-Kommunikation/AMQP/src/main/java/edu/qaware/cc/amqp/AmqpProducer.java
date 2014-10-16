package edu.qaware.cc.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import edu.qaware.cc.amqp.dts.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AmqpProducer {

    private static final Logger LOG = LoggerFactory.getLogger(AmqpProducer.class);

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //default port: 5672
        Connection connection = factory.newConnection();
        //Ein Channel ist eine logische Verbindung zu einem Broker.
        //Mehrere Channels teilen sich eine TCP/IP-Connection.
        Channel channel = connection.createChannel();
        ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < 100; i++) {
            Order order = new Order("iPad", i, "Cloud Computing Vorlesung");
            String message = mapper.writeValueAsString(order);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            LOG.info(" [x] Sent '{}'", message);
            Thread.sleep(100);
        }

        channel.close();
        connection.close();
    }


}
