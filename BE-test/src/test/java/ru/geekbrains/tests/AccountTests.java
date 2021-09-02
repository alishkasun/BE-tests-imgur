package ru.geekbrains.tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class  AccountTests extends BaseTest{

    @Test
    void getAccountPositiveTest() {
        given()
                .header("Authorization", token)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get( "account/{username}", username)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.url", equalTo(username));
    }
    //todo: refactor at home
    @Test
    void getAccountSettingsTest() {
        Response response = given()
                .header("Authorization", token)
                .expect()
                .body("success", CoreMatchers.is(true))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .when()
                .get("https://api.imgur.com/3/account/testprogmath/settings")
                .prettyPeek();
        assertThat(response.jsonPath().get("data.active_emails[0]"), CoreMatchers.<Object>equalTo("anna.chemic@gmail.com"));
    }
}