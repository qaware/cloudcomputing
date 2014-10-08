package edu.qaware.cc.jaxws;

import edu.qaware.cc.jaxws.helloworld.HelloWorld;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

public class HelloWorldTest {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldTest.class);

    @Test
    public void testHelloWorld() throws MalformedURLException {
        URL url = new URL("http://localhost:2899/webservices/sayhello?wsdl");
        QName qname = new QName("http://helloworld.jaxws.cc.qaware.edu/", "HelloWorldImplService");

        Service service = Service.create(url, qname);
        HelloWorld hello = service.getPort(HelloWorld.class);
        String response = hello.sayHello("Cloud Computing");
        LOG.info(response);
        assertEquals("Hallo Cloud Computing", response);
    }

}
