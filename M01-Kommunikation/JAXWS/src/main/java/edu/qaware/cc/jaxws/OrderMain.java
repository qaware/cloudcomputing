package edu.qaware.cc.jaxws;

import edu.qaware.cc.jaxws.ordermanager.OrderManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;

public class OrderMain {

    private static final Logger LOG = LoggerFactory.getLogger(OrderMain.class);
    public static final String SERVICE_URL = "http://localhost:2900/webservices/ordermanager";

    public static void main(String[] args) {
        Endpoint.publish(SERVICE_URL, new OrderManagerImpl());
        LOG.info(" [*] Waiting for calls. See WSDL on '{}?WSDL'", SERVICE_URL);
    }

}
