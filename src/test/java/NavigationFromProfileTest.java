import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import clients.UserClient;
import java.time.Duration;
import static org.junit.Assert.*;

public class NavigationFromProfileTest extends BaseTest {

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

        driver.get("https://stellarburgers.nomoreparties.site/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        HomePage home = new HomePage(driver);
        home.clickAccountLink();

        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/account/profile"));
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        HomePage home = new HomePage(driver);
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        HomePage home = new HomePage(driver);
        assertTrue("На главной странице должен отображаться текст 'Соберите бургер'",
                home.isBurgerAssemblyTextDisplayed());
        assertTrue("На главной странице должна отображаться кнопка 'Оформить заказ'",
                home.isOrderButtonDisplayed());
    }
}
