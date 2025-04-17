package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loginButton = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");
    private By accountLink = By.xpath("//a[contains(@href, '/account')]");
    private By burgerAssemblyText = By.xpath("//h1[contains(text(), 'Соберите бургер')]");

    private By orderButton = By.xpath("//button[contains(text(), 'Оформить заказ')]");

    private By bunsTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Булки']]");
    private By saucesTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Соусы']]");
    private By fillingsTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Начинки']]");

    private By bunsHeader = By.xpath("//h2[text()='Булки']");
    private By saucesHeader = By.xpath("//h2[text()='Соусы']");
    private By fillingsHeader = By.xpath("//h2[text()='Начинки']");

    private String activeTabClass = "tab_tab_type_current__2BEPc";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Нажатие кнопки 'Войти в аккаунт' на главной странице")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Нажатие ссылки 'Личный Кабинет' на главной странице")
    public void clickAccountLink() {
        driver.findElement(accountLink).click();
    }

    @Step("Проверка, что кнопка 'Войти в аккаунт' отображается")
    public boolean isLoginButtonDisplayed() {
        return !driver.findElements(loginButton).isEmpty() && driver.findElement(loginButton).isDisplayed();
    }

    @Step("Проверка отображения текста 'Соберите бургер'")
    public boolean isBurgerAssemblyTextDisplayed() {
        return driver.findElement(burgerAssemblyText).isDisplayed();
    }

    @Step("Проверка, что кнопка 'Оформить заказ' отображается")
    public boolean isOrderButtonDisplayed() {
        return !driver.findElements(orderButton).isEmpty() && driver.findElement(orderButton).isDisplayed();
    }

    @Step("Выбор вкладки 'Булки'")
    public void clickBunsTab() {
        driver.findElement(bunsTab).click();
    }

    @Step("Выбор вкладки 'Соусы'")
    public void clickSaucesTab() {
        driver.findElement(saucesTab).click();
    }

    @Step("Выбор вкладки 'Начинки'")
    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    @Step("Проверка отображения раздела 'Булки'")
    public boolean isBunsSectionVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(bunsHeader)).isDisplayed();
    }

    @Step("Проверка отображения раздела 'Соусы'")
    public boolean isSaucesSectionVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(saucesHeader)).isDisplayed();
    }

    @Step("Проверка отображения раздела 'Начинки'")
    public boolean isFillingsSectionVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(fillingsHeader)).isDisplayed();
    }

    @Step("Проверка, что вкладка 'Булки' активна")
    public boolean isBunsTabActive() {
        return driver.findElement(bunsTab).getAttribute("class").contains(activeTabClass);
    }

    @Step("Проверка, что вкладка 'Соусы' активна")
    public boolean isSaucesTabActive() {
        return driver.findElement(saucesTab).getAttribute("class").contains(activeTabClass);
    }

    @Step("Проверка, что вкладка 'Начинки' активна")
    public boolean isFillingsTabActive() {
        return driver.findElement(fillingsTab).getAttribute("class").contains(activeTabClass);
    }

    public void waitUntilPageUrlLoads() {
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
    }

    public void waitUntilBunsTabClickable() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab));
    }
}
