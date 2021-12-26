package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void sendRequest(RegistrationDto user) {

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private static Faker faker = new Faker(new Locale("en"));

    public static String randomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String randomPassword() {
        String password = faker.internet().password();
        return password;
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }

    public static RegistrationDto createUser(String status) {
        RegistrationDto user = new RegistrationDto(randomLogin(), randomPassword(), status);
        return user;
    }

    public static RegistrationDto createRegisteredUser(String status) {
        RegistrationDto registeredUser = createUser(status);
        sendRequest(registeredUser);
        return registeredUser;
    }
}