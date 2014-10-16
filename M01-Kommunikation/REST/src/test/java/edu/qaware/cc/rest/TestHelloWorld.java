package edu.qaware.cc.rest;

import com.jayway.restassured.RestAssured;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestHelloWorld {

    @Test
    public void testSayHello(){
        RestAssured.baseURI = "http://localhost:9999";
        given().
                queryParam("salutation", "Hallo").
                pathParam("name","Josef Adersberger").
        expect().
                contentType("application/json").
                body("message", equalTo("Hallo Josef Adersberger")).
        when().
                get("/hello/{name}");

    }

}
