package edu.qaware.cc.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import edu.qaware.cc.amqp.dts.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AmqpConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AmqpConsumer.class);

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //Faire Verteilung aktivieren. Neue Nachricht wird erst zugeordnet,
        //wenn ACK der vorhergehenden Nachricht erfolgt ist.
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
        LOG.info(" [*] Waiting for messages.");

        ObjectMapper mapper = new ObjectMapper();

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            LOG.info(" [x] Received '{}'", message);
            Order order = mapper.readValue(message, Order.class);
            LOG.info("     - Processing order of {} pieces of {} for {}",
                    new Object[]{order.getAmount(), order.getItem(), order.getCustomer()});
        }
    }
}
