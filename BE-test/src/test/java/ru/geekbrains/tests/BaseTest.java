package ru.geekbrains.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;

public abstract class BaseTest {
    static Properties properties;
    static String host;
    static String username;
    static String token;
    static Integer userId;

    static ResponseSpecification positiveResponseSpecification;

    static RequestSpecification requestSpecification;


    @BeforeAll
    static void beforeAll() throws IOException {

        positiveResponseSpecification = new ResponseSpecBuilder()

                //.expectResponseTime(lessThan(10000L))
                .expectBody("success", CoreMatchers.is(true))
                .expectBody("status", equalTo(200))
                .build();



        properties = new Properties();
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        host = properties.getProperty("host");
        username = properties.getProperty("username", "testprogmath");
        token = properties.getProperty("auth.token");
        //userId = Integer.valueOf(properties.getProperty("user.id"));

        RestAssured.baseURI = host;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();


        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                //.setContentType("application/json")
                .build();




        RestAssured.responseSpecification = positiveResponseSpecification;
        //RestAssured.requestSpecification = requestSpecification;

    }
}
