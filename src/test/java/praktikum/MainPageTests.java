package praktikum;

import handlers.Parameters;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Проверки конструктора (главной страницы)")
@RunWith(Parameterized.class)
public class MainPageTests {
    private WebDriver driver;
    private String browserName;
    private MainPage mainPage;
    @Parameterized.Parameters(name="Browser {0}")
    public static Object[][] initParams() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }
    public MainPageTests(String browserName) {
        this.browserName = browserName;
    }
    @Before
    @Step("Запуск браузера")
    public void startUp() {
        driver = getWebDriver(browserName);
        driver.get(Parameters.URL_MAIN_PAGE);
        mainPage = new MainPage(driver);
    }
    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        driver.quit();
    }
    @Test
    @DisplayName("Проверка работы вкладок для разделов с ингредиентами")
    public void checkConstructorNavIsSuccess() {
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();

        checkToppingsTab(expectedLocation);
        checkFillingsTab(expectedLocation);
        checkBunsTab(expectedLocation);
    }
    @Step("Нажатие на Соусы")
    private void checkToppingsTab(int expectedLocation) {
        mainPage.clickToppingsButton();

        MatcherAssert.assertThat(
                "Ингредиенты не проскроллились до соусов",
                mainPage.getToppingsLocation(),
                equalTo(expectedLocation)
        );
    }
    @Step("Нажатие на Начинки")
    private void checkFillingsTab(int expectedLocation) {
        mainPage.clickFillingsButton();

        MatcherAssert.assertThat(
                "Ингредиенты не проскроллились до начинок",
                mainPage.getFillingsLocation(),
                equalTo(expectedLocation)
        );
    }
    @Step("Нажатие на Начинки")
    private void checkBunsTab(int expectedLocation) {
        mainPage.clickBunsButton();

        MatcherAssert.assertThat(
                "Ингредиенты не проскроллились до начинок",
                mainPage.getBunsLocation(),
                equalTo(expectedLocation)
        );
    }
}
