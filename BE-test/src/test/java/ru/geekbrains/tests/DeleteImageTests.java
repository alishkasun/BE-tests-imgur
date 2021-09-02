package ru.geekbrains.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest {

    String imageDeleteHash;


    @ParameterizedTest
    @ValueSource(strings = {"img.png", "forgif.gif", "before10Mb.jpg", "image.jpg", "imagefor.bmp", "img.png", "OnetoOne.jpg"})
    void deleteExistentImageTest(String imageName) {
        imageDeleteHash = uploadImage(imageName);

        given()
                .header("Authorization", token)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    private String uploadImage(String imageName) {
        return given()
                .header("Authorization", token)
                .body(new File("src/test/resources/" + imageName))
                .when()
                .post("/image")
                .jsonPath()
                .get("data.deletehash");
    }
}
