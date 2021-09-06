package ru.geekbrains.tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static geekbrains.base.Endpoints.GET_ACCOUNT;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class  AccountTests extends BaseTest{
    ResponseSpecification accountResponseSpec;

    @Test
    void getAccountPositiveTest() {
        accountResponseSpec = positiveResponseSpecification
                .expect()
                .body("data.url", equalTo(username));

      given(requestSpecification, accountResponseSpec)
                .get(GET_ACCOUNT, username)
                .prettyPeek();

    }

    @Test
    void getAccountSettingsTest() {
        Response response = given()
                .expect()
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .when()
                .get("https://api.imgur.com/3/account/testprogmath/settings")
                .prettyPeek();
        assertThat(response.jsonPath().get("data.active_emails[0]"), CoreMatchers.<Object>equalTo("anna.chemic@gmail.com"));
    }
}