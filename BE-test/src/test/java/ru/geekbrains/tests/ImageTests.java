package ru.geekbrains.tests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static geekbrains.base.Endpoints.UPLOAD_IMAGE;
import static geekbrains.base.Images.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.lessThan;

public class ImageTests extends BaseTest {
    static final String IMAGE_FILE = "src/test/resources/img.png";
    String imageDeleteHash;
    String base64Image;
    static ResponseSpecification FofBigFileResponseSpecification;
    static ResponseSpecification TextFileResponseSpecification;

    static final String MORE_10Mb = "src/test/resources/More10Mb.jpg";
    static final String ONE_TO_ONE = "src/test/resources/OnetoOne.jpg";
    static final String TEST_JPG = "src/test/resources/image.jpg";
    static final String TEST_BMP = "src/test/resources/imagefor.bmp";
    static final String TEST_GIF = "src/test/resources/forgif.gif";
    static final String BEFORE_10Mb = "src/test/resources/before10Mb.jpg";
    static final String TEXT = "src/test/resources/text.txt";



    @BeforeEach
    void setUp() throws IOException {
        byte[] imageBytesArray = FileUtils.readFileToByteArray(new File(IMAGE_FILE));
        base64Image = Base64.getEncoder().encodeToString(imageBytesArray);
    }

    // PNG
    @Test
    void uploadImageFileTestPng() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(IMAGE_FILE))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.id", is(notNullValue()))
                .body("data.in_most_viral", CoreMatchers.is(false))
                .body("data.type", equalTo("image/png"))
                .body("data.height", equalTo(1200))
                .body("data.vote", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    //JPG
    @Test
    void uploadImageFileTestJpg() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(TEST_JPG))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.id", is(notNullValue()))
                .body("data.animated", CoreMatchers.is(false))
                .body("data.type", equalTo("image/jpeg"))
                .body("data.width", equalTo(800))
                .body("data.vote", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    //BMP
    @Test
    void uploadImageFileTestBmp() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(TEST_BMP))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.datetime", is(notNullValue()))
                .body("data.favorite", CoreMatchers.is(false))
                .body("data.type", equalTo("image/bmp"))
                .body("data.width", equalTo(1280))
                .body("data.description", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    //GIF
    @Test
    void uploadImageFileTestForGif() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(TEST_GIF))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.account_id", is(notNullValue()))
                .body("data.has_sound", CoreMatchers.is(false))
                .body("data.type", equalTo("image/gif"))
                .body("data.width", equalTo(1280))
                .body("data.account_url", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    // до 10 мб.
    @Test
    void uploadImageFileTestBefore10Mb() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(BEFORE_10Mb))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.account_id", is(notNullValue()))
                .body("data.has_sound", CoreMatchers.is(false))
                .body("data.type", equalTo("image/jpeg"))
                .body("data.height", equalTo(600))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    //  image 1x1 px
    @Test
    void uploadImageFileTest1x1() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(ONE_TO_ONE))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.account_id", is(notNullValue()))
                .body("data.type", equalTo("image/jpeg"))
                .body("data.size", equalTo(1139))
                .body("data.width", equalTo(1))
                .body("data.height", equalTo(1))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    @Test
    void uploadImageFileTestUrlGif() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", IMAGE_URL_GIF.getPath())
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.account_id", is(notNullValue()))
                .body("data.type", equalTo("image/gif"))
                .body("data.size", equalTo(885272))
                .body("data.width", equalTo(380))
                .body("data.height", equalTo(262))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }


    @Test
    void uploadImageFileTestUrlPng() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", IMAGE_URL_PNG.getPath())
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.account_id", is(notNullValue()))
                .body("data.type", equalTo("image/png"))
                .body("data.size", equalTo(19990))
                .body("data.width", equalTo(640))
                .body("data.height", equalTo(640))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }

    @Test
    void uploadImageFileTestUrlJpeg() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", IMAGE_URL_JPG.getPath())
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.account_id", is(notNullValue()))
                .body("data.type", equalTo("image/jpeg"))
                .body("data.account_id", equalTo(145270851))
                .body("data.width", equalTo(1920))
                .body("data.height", equalTo(1200))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }
    // больше 10 мб = работает, если отключить  @BeforeAll в BaseTest positiveResponseSpecification (выносить в до=ругой класс?)
    @Test
    void uploadImageFileTestMore10Mb() {

        FofBigFileResponseSpecification = new ResponseSpecBuilder()

                .expectResponseTime(lessThan(10000L))
                .expectBody("success", CoreMatchers.is(false))
                .expectBody("status", equalTo(400))
                .expectBody("data.error", equalTo("File is over the size limit"))
                .build();

        given()
                .spec(requestSpecification)
                .multiPart("image", new File(MORE_10Mb))
                .expect()
                .spec(FofBigFileResponseSpecification)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek();

    }

//    работает, если отключить  @BeforeAll в BaseTest positiveResponseSpecification (выносить в до=ругой класс?)
    @Test
    void uploadImageFileTestText() {

        TextFileResponseSpecification = new ResponseSpecBuilder()

                .expectResponseTime(lessThan(10000L))
                .expectBody("success", CoreMatchers.is(false))
                .expectBody("status", equalTo(400))
                .build();

        given()
                .spec(requestSpecification)
                .multiPart("image", new File(TEXT))
                .expect()
                .spec(TextFileResponseSpecification)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek();


    }
    //не работает, говорит "message": "File type invalid (1)", не пойму как подгрузить
    @Test
    void uploadImageFileTestVideo() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File("src/test/resources/video.mp4"))
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");

    }
    @Test
    void uploadBase64FileTest() {
        imageDeleteHash=  given()
                .spec(requestSpecification)
                .multiPart("image", base64Image)
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.account_id", is(notNullValue()))
                .body("data.type", equalTo("image/png"))
                .body("data.account_id", equalTo(145270851))
                .body("data.width", equalTo(1332))
                .body("data.height", equalTo(1200))
                .body("data.nsfw", is(nullValue()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .jsonPath()
                .getString("data.deletehash");
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
