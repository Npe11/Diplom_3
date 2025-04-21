package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private WebDriver driver;

    private By nameInput = By.xpath("(//input[@name='name'])[1]");
    private By emailInput = By.xpath("(//input[@name='name'])[2]");
    private By passwordInput = By.name("Пароль");
    private By registerButton = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");
    private By errorMessage = By.cssSelector(".input__error");
    private By loginLink = By.cssSelector("a.Auth_link__1fOlj");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввод имени: {0}")
    public void enterName(String name) {
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);
    }

    @Step("Ввод email: {0}")
    public void enterEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввод пароля: {0}")
    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажатие кнопки 'Зарегистрироваться'")
    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    @Step("Получение текста сообщения об ошибке")
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public By getErrorMessageLocator() {
        return errorMessage;
    }

    @Step("Переход по ссылке 'Войти' в форме регистрации")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }
}
