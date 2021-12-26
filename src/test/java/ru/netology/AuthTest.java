package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.*;


public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }


    @Test
    void shouldLoginNotRegisteredUser() {
        var notRegisteredUser = createUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $(".button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldLoginActiveUser() {
        var activeUser = createRegisteredUser("active");
        $("[data-test-id='login']  input").setValue(activeUser.getLogin());
        $("[data-test-id='password']  input").setValue(activeUser.getPassword());
        $(".button").click();
        $("h2").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
    }

    @Test
    void shouldLoginBlockedUser() {
        var blockedUser = createRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $(".button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }


    @Test
    void shouldLoginEmptyLogin() {
        var registeredUser = createRegisteredUser("active");
        $("[data-test-id='login'] input").setValue("");
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $("[data-test-id='login'] .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldLoginEmptyPassword() {
        var registeredUser = createRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue("");
        $(".button").click();
        $("[data-test-id='password'] .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
}
