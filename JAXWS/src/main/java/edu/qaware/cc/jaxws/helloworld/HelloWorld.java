package edu.qaware.cc.jaxws.helloworld;

import javax.jws.WebService;

@WebService
public interface HelloWorld {

    public String sayHello(String name);

}
