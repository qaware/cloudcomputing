package edu.qaware.cc.jaxws;

import edu.qaware.cc.jaxws.helloworld.HelloWorldImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;

public class HelloWorldMain {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldMain.class);
    public static final String SERVICE_URL = "http://localhost:2899/webservices/sayhello";

    public static void main(String[] args) {
        Endpoint.publish(SERVICE_URL, new HelloWorldImpl());
        LOG.info(" [*] Waiting for calls. See WSDL on '{}?WSDL'", SERVICE_URL);
    }

}
