import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.ForgotPasswordPage;
import static org.junit.Assert.*;
import utils.Endpoints;

import clients.UserClient;

public class LoginTest extends BaseTest {

    private Faker faker;
    private String email;
    private String password;
    private String name;
    private String accessToken;
    private UserClient userClient = new UserClient();

    @Before
    public void setUpUser() {
        faker = new Faker();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().fullName();

        CourierModel courier = new CourierModel(email, password, name);
        Response response = userClient.createUser(courier);
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
        driver.get(Endpoints.MAIN_PAGE);
        HomePage home = new HomePage(driver);
        home.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        HomePage homeAfter = new HomePage(driver);
        homeAfter.waitUntilPageUrlLoads();

        assertFalse("Кнопка 'Войти в аккаунт' не должна отображаться после входа",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("Кнопка 'Оформить заказ' должна отображаться после входа",
                homeAfter.isOrderButtonDisplayed());
    }

    @Test
    @Description("Вход через ссылку 'Личный Кабинет' на главной странице")
    public void testLoginFromAccountLink() {
        driver.get(Endpoints.MAIN_PAGE);
        HomePage home = new HomePage(driver);
        home.clickAccountLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        HomePage homeAfter = new HomePage(driver);
        homeAfter.waitUntilPageUrlLoads();

        assertFalse("После входа не должна отображаться кнопка 'Войти в аккаунт'",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                homeAfter.isOrderButtonDisplayed());
    }

    @Test
    @Description("Вход через ссылку 'Войти' на странице регистрации")
    public void testLoginFromRegistrationForm() {
        driver.get(Endpoints.REGISTER_PAGE);
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        HomePage homeAfter = new HomePage(driver);
        homeAfter.waitUntilPageUrlLoads();

        assertFalse("После входа в систему через форму регистрации не должна отображаться кнопка 'Войти в аккаунт'",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("После входа в систему должна отображаться кнопка 'Оформить заказ'",
                homeAfter.isOrderButtonDisplayed());
    }

    @Test
    @Description("Вход через ссылку 'Войти' на странице восстановления пароля")
    public void testLoginFromForgotPasswordForm() {
        driver.get(Endpoints.FORGOT_PASSWORD_PAGE);
        ForgotPasswordPage forgotPage = new ForgotPasswordPage(driver);
        forgotPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        HomePage homeAfter = new HomePage(driver);
        homeAfter.waitUntilPageUrlLoads();

        assertFalse("После входа через восстановление пароля не должна отображаться кнопка 'Войти в аккаунт'",
                homeAfter.isLoginButtonDisplayed());
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                homeAfter.isOrderButtonDisplayed());
    }
}
