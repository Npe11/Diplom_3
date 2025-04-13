package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By emailField = By.xpath("//input[@type='text' and contains(@class, 'input__textfield')]");
    private By passwordField = By.xpath("//input[@type='password' and contains(@class, 'input__textfield')]");
    private By loginButton = By.xpath("//button[contains(text(), 'Войти')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввод email: {0}")
    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажатие кнопки 'Войти'")
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    @Step("Проверка отображения кнопки 'Войти'")
    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }
}
