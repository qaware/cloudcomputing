package edu.qaware.cc.zwitscher;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import edu.qaware.cc.zwitscher.core.ZwitscherApplication;
import edu.qaware.cc.zwitscher.core.ZwitscherConfiguration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import static org.hamcrest.Matchers.*;
import org.junit.ClassRule;
import org.junit.Test;

public class TestApi {
    
    @ClassRule
    public static final DropwizardAppRule<ZwitscherConfiguration> RULE =
            new DropwizardAppRule<>(ZwitscherApplication.class, "./src/main/resources/zwitscher-config.yml");
    
    @Test
    public void testRandomMessage(){
        get("http://localhost:2890/messages/random")
                .then().body("message", equalTo("YO!"));
    }
    
}
