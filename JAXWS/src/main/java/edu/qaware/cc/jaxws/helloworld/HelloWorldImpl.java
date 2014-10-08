package edu.qaware.cc.jaxws.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "edu.qaware.cc.jaxws.helloworld.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

    @WebMethod
    public String sayHello(String name) {
        String message = "Hallo " + name;
        LOG.info("Sending message '{}'", message);
        return message;
    }

}
