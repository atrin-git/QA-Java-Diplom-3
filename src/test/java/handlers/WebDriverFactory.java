package handlers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver getWebDriver(String browserName) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver-114");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        switch (browserName) {
            case "chrome":
                return new ChromeDriver(options);

            case "yandex":
                return new ChromeDriver(options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex"));

            default:
                throw new RuntimeException("Incorrect browser name");
        }
    }
}
