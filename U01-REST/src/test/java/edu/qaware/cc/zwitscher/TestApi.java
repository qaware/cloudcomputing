package edu.qaware.cc.zwitscher;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class TestApi {
    
    @Test
    public void testRandomMessage(){
        get("http://localhost:2890/messages/random")
                .then().body("message", equalTo("YO!"));
    }
    
}
