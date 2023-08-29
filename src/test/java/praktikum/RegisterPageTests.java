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
import pageobjects.RegisterPage;

import java.util.UUID;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Регистрация пользователя")
@RunWith(Parameterized.class)
public class RegisterPageTests {
    private WebDriver webDriver;
    private String browserName;
    private RegisterPage registerPage;
    private String email, name, password;

    @Parameterized.Parameters(name="Browser {0}")
    public static Object[][] initParams() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }
    public RegisterPageTests(String browserName) {
        this.browserName = browserName;
    }
    @Before
    @Step("Запуск браузера, подготовка тестовых данных")
    public void startUp() {
        webDriver = getWebDriver(browserName);
        webDriver.get(Parameters.URL_REGISTER_PAGE);
        registerPage = new RegisterPage(webDriver);

        email = "email_" + UUID.randomUUID() + "@gmail.com";
        name = "name";
        password = "pass_" + UUID.randomUUID();

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);
    }

    @After
    @Step("Закрытие браузера и очистка данных")
    public void tearDown() {
        webDriver.quit();
        new ApiClient().deleteTestUser(email, password);
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void registerNewUserIsSuccess() {
        Allure.parameter("Браузер", browserName);

        registerPage.setEmail(email);
        registerPage.setName(name);
        registerPage.setPassword(password);

        registerPage.clickRegisterButton();

        registerPage.waitFormSubmitted("Вход");

        checkFormReload();
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    public void registerNewUserLowPasswordIsFailed() {
        Allure.parameter("Браузер", browserName);

        registerPage.setEmail(email);
        registerPage.setName(name);
        registerPage.setPassword(password.substring(0, 3));

        registerPage.clickRegisterButton();

        registerPage.waitErrorIsVisible();

        checkErrorMessage();
    }

    @Step("Проверка перезагрузки формы регистрации")
    private void checkFormReload() {
        MatcherAssert.assertThat(
                "Форма регистрации не перезагрузилась",
                webDriver.getCurrentUrl(),
                containsString("/login")
        );
    }

    @Step("Проверка появления сообщения об ошибке")
    private void checkErrorMessage() {
        MatcherAssert.assertThat(
                "Некорректное сообщение об ошибке",
                registerPage.getErrorMessage(),
                equalTo("Некорректный пароль")
        );
    }
}