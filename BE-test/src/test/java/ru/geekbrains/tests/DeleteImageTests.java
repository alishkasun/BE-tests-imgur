package ru.geekbrains.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static geekbrains.base.Endpoints.UPLOAD_IMAGE;
import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest {

    String imageDeleteHash;


    @ParameterizedTest
    @ValueSource(strings = {"img.png", "forgif.gif", "before10Mb.jpg", "image.jpg", "imagefor.bmp", "img.png", "OnetoOne.jpg"})
    void deleteExistentImageTest(String imageName) {
        imageDeleteHash = uploadImage(imageName);

        given()
                .spec(requestSpecification)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .prettyPeek();

    }

    private String uploadImage(String imageName) {
        return given()
                .spec(requestSpecification)
                .body(new File("src/test/resources/" + imageName))
                .when()
                .post(UPLOAD_IMAGE)
                .jsonPath()
                .get("data.deletehash");
    }
}
