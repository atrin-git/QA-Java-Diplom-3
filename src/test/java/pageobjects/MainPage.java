package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class MainPage {
    private WebDriver driver;
    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    private final By basketButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");
    private final By ingredientsButtons = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div");
    private final By ingredientsTitles = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]/h2");
    private final By header = By.xpath(".//main//h1");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAuthButton() {
        waitButtonIsClickable();
        driver.findElement(basketButton).click();
    }
    public void waitButtonIsClickable() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }
    public void waitHeaderIsVisible() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(header));
    }
    private void waitIngredientsScrolled(int navNumber) {
        new WebDriverWait(driver, 30)
                .until(driver -> {
                            return driver.findElements(ingredientsTitles).get(navNumber).getLocation().getY() == 243;
                        }
                );
    }
    public String getBasketButtonText() {
        return driver.findElement(basketButton).getText();
    }
    public void clickLinkToProfile() {
        waitButtonIsClickable();
        driver.findElements(headerLinks).get(2).click();
    }
    public int getIngredientTitleExpectedLocation() {
        return Integer.valueOf(driver.findElements(ingredientsButtons).get(0).getLocation().getY()
                + driver.findElements(ingredientsButtons).get(0).getSize().getHeight()
        );
    }
    public void clickBunsButton() {
        waitButtonIsClickable();
        driver.findElements(ingredientsButtons).get(0).click();
        waitIngredientsScrolled(0);
    }
    public void clickToppingsButton() {
        waitButtonIsClickable();
        driver.findElements(ingredientsButtons).get(1).click();
        waitIngredientsScrolled(1);
    }
    public void clickFillingsButton() {
        waitButtonIsClickable();
        driver.findElements(ingredientsButtons).get(2).click();
        waitIngredientsScrolled(2);
    }

    public int getBunsLocation() {
        return Integer.valueOf(driver.findElements(ingredientsTitles).get(0).getLocation().getY());
    }

    public int getToppingsLocation() {
        return Integer.valueOf(driver.findElements(ingredientsTitles).get(1).getLocation().getY());
    }
    public int getFillingsLocation() {
        return Integer.valueOf(driver.findElements(ingredientsTitles).get(2).getLocation().getY());
    }
}
