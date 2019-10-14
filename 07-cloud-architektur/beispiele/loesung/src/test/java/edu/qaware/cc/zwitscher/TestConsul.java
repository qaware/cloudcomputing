/*
 * Copyright (C) 2016 QAware GmbH
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package edu.qaware.cc.zwitscher;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.model.health.ServiceHealth;
import org.junit.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * https://github.com/OrbitzWorldwide/consul-client
 */
public class TestConsul {

    @Test
    public void testConfiguration() {
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        KeyValueClient kvClient = consul.keyValueClient();
        kvClient.putValue("Servus", "Josef");
        String value = kvClient.getValueAsString("Servus").get(); // bar
        assertThat(value, is("Josef"));
    }

    public static final String SERVICE = "zwitscher";

    @Test
    public void testServiceDiscovery() {
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        HealthClient healthClient = consul.healthClient();

        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances(SERVICE).getResponse(); // discover only "passing" nodes
        System.out.println("Services:");
        for (ServiceHealth node: nodes) {
            System.out.println("********************************");
            System.out.println( node.getNode().getAddress() );
            System.out.println( node.getService().getAddress() );
            System.out.println( node.getService().getService() );
            System.out.println( node.getService().getId() );
            System.out.println( node.getService().getPort() );
            System.out.println("--------------------------------");
            get("http://" + node.getNode().getAddress() + ":" + node.getService().getPort() + "/messages/random")
                    .then().body("message", equalTo("YO!"));
        }
    }
}
