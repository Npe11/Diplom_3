package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ForgotPasswordPage {
    private WebDriver driver;

    private By loginLink = By.cssSelector("a.Auth_link__1fOlj");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие ссылки 'Войти' на странице восстановления пароля")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }
}
