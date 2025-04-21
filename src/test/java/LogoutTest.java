import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import clients.UserClient;
import utils.Endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogoutTest extends BaseTest {

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

        driver.get(Endpoints.LOGIN_PAGE);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        HomePage home = new HomePage(driver);
        home.waitUntilPageUrlLoads();
        home.clickAccountLink();

        ProfilePage profile = new ProfilePage(driver);
        profile.waitUntilPageUrlLoads();
    }

    @After
    public void tearDownUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @Description("Выход из аккаунта: при клике на кнопку 'Выйти' в личном кабинете происходит переход на страницу входа и отображается кнопка 'Войти'")
    public void testLogout() {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilPageUrlLoads();

        String currentUrl = driver.getCurrentUrl();

        assertEquals("После выхода должна открыться страница логина",
                Endpoints.LOGIN_PAGE, currentUrl);
        assertTrue("На странице логина должна быть отображена кнопка 'Войти'",
                loginPage.isLoginButtonDisplayed());
    }
}
