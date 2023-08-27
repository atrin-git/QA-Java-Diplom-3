package praktikum;

import handlers.ApiClient;
import handlers.Parameters;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobjects.AuthPage;
import pageobjects.ForgotPasswordPage;
import pageobjects.MainPage;
import pageobjects.RegisterPage;

import java.util.UUID;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Авторизация пользователя")
@RunWith(Parameterized.class)
public class AuthTests {
    private WebDriver webDriver;
    private String browserName;
    private AuthPage authPage;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
    private String name, email, password;
    private ApiClient apiClient;
    @Parameterized.Parameters(name="Browser {0}")
    public static Object[][] initParams() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }
    public AuthTests(String browserName) {
        this.browserName = browserName;
    }

    @Before
    @Step("Запуск браузера, подготовка тестовых данных")
    public void startUp() {
        webDriver = getWebDriver(browserName);
        webDriver.get(Parameters.URL_MAIN_PAGE);

        authPage = new AuthPage(webDriver);
        mainPage = new MainPage(webDriver);
        registerPage = new RegisterPage(webDriver);
        forgotPasswordPage = new ForgotPasswordPage(webDriver);

        name = "name";
        email = "email_" + UUID.randomUUID() + "@gmail.com";
        password = "pass_" + UUID.randomUUID();

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);

        apiClient = new ApiClient();
        apiClient.createUser(name, email,password);
    }
    @After
    @Step("Закрытие браузера и очистка данных")
    public void tearDown() {
        webDriver.quit();
        apiClient.deleteTestUser(email, password);
    }

    @Step("Процесс авторизации")
    private void authUser() {
        authPage.setEmail(email);
        authPage.setPassword(password);

        authPage.clickAuthButton();

        authPage.waitFormSubmitted();
    }
    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void authFromMainIsSuccess() {
        Allure.parameter("Браузер", browserName);

        mainPage.clickAuthButton();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }
    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void authFromLinkToProfileIsSuccess() {
        Allure.parameter("Браузер", browserName);

        mainPage.clickLinkToProfile();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }
    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void authLinkFromRegFormIsSuccess() {
        Allure.parameter("Браузер", browserName);

        webDriver.get(Parameters.URL_REGISTER_PAGE);

        registerPage.clickAuthLink();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }
    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void authLinkFromForgotPasswordFormIsSuccess() {
        Allure.parameter("Браузер", browserName);

        webDriver.get(Parameters.URL_FORGOT_PASSWORD_PAGE);

        forgotPasswordPage.clickAuthLink();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }
}
