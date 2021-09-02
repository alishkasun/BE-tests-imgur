package ru.geekbrains.tests;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class ImageTests extends BaseTest {

    String imageDeleteHash;

    // PNG
    @Test
    void uploadImageFileTestPng() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/img.png"))
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("data.id", is(notNullValue()))
                .body("data.in_most_viral", CoreMatchers.is(false))
                .body("data.type", equalTo("image/png"))
                .body("data.height", equalTo(1200))
                .body("data.vote", is(nullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    //JPG
    @Test
    void uploadImageFileTestJpg() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/image.jpg"))
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("data.id", is(notNullValue()))
                .body("data.animated", CoreMatchers.is(false))
                .body("data.type", equalTo("image/jpeg"))
                .body("data.width", equalTo(800))
                .body("data.vote", is(nullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    //BMP
    @Test
    void uploadImageFileTestBmp() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/imagefor.bmp"))
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("data.datetime", is(notNullValue()))
                .body("data.favorite", CoreMatchers.is(false))
                .body("data.type", equalTo("image/bmp"))
                .body("data.width", equalTo(1280))
                .body("data.description", is(nullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    //GIF
    @Test
    void uploadImageFileTestForGif() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/forgif.gif"))
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("data.account_id", is(notNullValue()))
                .body("data.has_sound", CoreMatchers.is(false))
                .body("data.type", equalTo("image/gif"))
                .body("data.width", equalTo(1280))
                .body("data.account_url", is(nullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    // до 10 мб.
    @Test
    void uploadImageFileTestBefore10Mb() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/before10Mb.jpg"))
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("data.account_id", is(notNullValue()))
                .body("data.has_sound", CoreMatchers.is(false))
                .body("data.type", equalTo("image/jpeg"))
                .body("data.height", equalTo(600))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    //  image 1x1 px
    @Test
    void uploadImageFileTest1x1() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/OnetoOne.jpg"))
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("data.account_id", is(notNullValue()))
                .body("success", CoreMatchers.is(true))
                .body("data.type", equalTo("image/jpeg"))
                .body("data.size", equalTo(1139))
                .body("data.width", equalTo(1))
                .body("data.height", equalTo(1))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }


    // больше 10 мб
    @Test
    void uploadImageFileTestMore10Mb() {
        given()
                .header("Authorization", token)
                .body(new File("src/test/resources/More10Mb.jpg"))
                .expect()
                .statusCode(400)
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .body("data.method", equalTo("POST"))
                .body("data.error", equalTo("File is over the size limit"));


    }
//тест падает загрузка по url Png
    @Test
    void uploadImageFileTestUrlPng() throws IOException {

        try (var in = new URL( "https://wonder-day.com/wp-content/uploads/2020/10/wonder-day-among-us-png-48.png" ).openStream()) {
            imageDeleteHash = given()
                    .header("Authorization", token)
                    .body(in)
                    .expect()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("data.id", is(notNullValue()))
                    .body("data.animated", CoreMatchers.is(false))
                    .body("data.type", equalTo("image/png"))
                    .body("data.size", equalTo(19990))
                    .when()
                    .post("/image")
                    .prettyPeek()
                    .jsonPath()
                    .get("data.deletehash");
        }
    }


    @AfterEach
    void tearDown() {
        if (imageDeleteHash != null) {
            given()
                    .header("Authorization", token)
                    .when()
                    .delete("image/{imageHash}", imageDeleteHash)
                    .then()
                    .statusCode(200);
        }
    }
}
