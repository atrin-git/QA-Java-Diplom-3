package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private WebDriver driver;
    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    private final By logoLink = By.xpath(".//div[starts-with(@class,'AppHeader_header__logo')]/a");
    private final By profileNavLink = By.xpath(".//a[contains(@class, 'Account_link_active')]");
    private final By logOutLink = By.xpath(".//nav[starts-with(@class, 'Account_nav')]/ul/li/button");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }
    public void waitAuthFormVisible() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(profileNavLink));
    }
    public void waitButtonIsClickable() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }
    public void clickLinkToConstructor() {
        waitButtonIsClickable();
        driver.findElements(headerLinks).get(0).click();
    }
    public void clickLinkOnLogo() {
        waitButtonIsClickable();
        driver.findElement(logoLink).click();
    }
    public void clickLogoutLink() {
        waitButtonIsClickable();
        driver.findElement(logOutLink).click();
    }
}
