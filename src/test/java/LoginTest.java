import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.ForgotPasswordPage;
import static org.junit.Assert.*;

import java.time.Duration;
import clients.UserClient;

public class LoginTest extends BaseTest {

    private String email;
    private final String password = "password123";
    private final String name = "TestUser";
    private String accessToken;
    private UserClient userClient = new UserClient();

    @Before
    public void setUpUser() {
        email = "testuser" + System.currentTimeMillis() + "@example.com";
        Response response = userClient.createUser(email, password, name);
        accessToken = response.jsonPath().getString("accessToken");
    }

    @After
    public void tearDownUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userClient.deleteUser(accessToken);
        }

        accessToken = null;
        userClient = null;
    }

    @Test
    @Description("Вход через кнопку 'Войти в аккаунт' на главной странице")
    public void testLoginFromMainPageButton() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        HomePage home = new HomePage(driver);
        home.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        HomePage homeAfter = new HomePage(driver);
        assertFalse("Кнопка 'Войти в аккаунт' не должна отображаться после входа",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("Кнопка 'Оформить заказ' должна отображаться после входа",
                homeAfter.isOrderButtonDisplayed());
    }

    @Test
    @Description("Вход через ссылку 'Личный Кабинет' на главной странице")
    public void testLoginFromAccountLink() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        HomePage home = new HomePage(driver);
        home.clickAccountLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        HomePage homeAfter = new HomePage(driver);
        assertFalse("После входа не должна отображаться кнопка 'Войти в аккаунт'",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                homeAfter.isOrderButtonDisplayed());
    }

    @Test
    @Description("Вход через ссылку 'Войти' на странице регистрации")
    public void testLoginFromRegistrationForm() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        HomePage homeAfter = new HomePage(driver);
        assertFalse("После входа в систему через форму регистрации не должна отображаться кнопка 'Войти в аккаунт'",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("После входа в систему должна отображаться кнопка 'Оформить заказ'",
                homeAfter.isOrderButtonDisplayed());
    }

    @Test
    @Description("Вход через ссылку 'Войти' на странице восстановления пароля")
    public void testLoginFromForgotPasswordForm() {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
        ForgotPasswordPage forgotPage = new ForgotPasswordPage(driver);
        forgotPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        HomePage homeAfter = new HomePage(driver);
        assertFalse("После входа через восстановление пароля не должна отображаться кнопка 'Войти в аккаунт'",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                homeAfter.isOrderButtonDisplayed());
    }
}
