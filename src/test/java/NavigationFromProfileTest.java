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

import static org.junit.Assert.assertTrue;

public class NavigationFromProfileTest extends BaseTest {

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
    @Description("Переход из личного кабинета в конструктор через кнопку 'Конструктор'")
    public void testNavigationToConstructorViaButton() {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructorButton();

        HomePage home = new HomePage(driver);
        home.waitUntilPageUrlLoads();

        assertTrue("На главной странице должен отображаться текст 'Соберите бургер'",
                home.isBurgerAssemblyTextDisplayed());
        assertTrue("На главной странице должна отображаться кнопка 'Оформить заказ'",
                home.isOrderButtonDisplayed());
    }

    @Test
    @Description("Переход из личного кабинета в конструктор через логотип Stellar Burgers")
    public void testNavigationToConstructorViaLogo() {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogo();

        HomePage home = new HomePage(driver);
        home.waitUntilPageUrlLoads();

        assertTrue("На главной странице должен отображаться текст 'Соберите бургер'",
                home.isBurgerAssemblyTextDisplayed());
        assertTrue("На главной странице должна отображаться кнопка 'Оформить заказ'",
                home.isOrderButtonDisplayed());
    }
}
