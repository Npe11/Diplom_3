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
import static org.junit.Assert.*;
import java.time.Duration;
import clients.UserClient;

public class AccountTest extends BaseTest {
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
    }

    @Test
    @Description("Проверка перехода в личный кабинет: после входа через форму логина и клика на 'Личный кабинет' открывается страница профиля с информацией")
    public void testNavigateToProfile() {
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
        String currentUrl = driver.getCurrentUrl();
        assertEquals("После клика на 'Личный кабинет' ожидается открытие профиля",
                "https://stellarburgers.nomoreparties.site/account/profile", currentUrl);

        ProfilePage profilePage = new ProfilePage(driver);
        String profileInfo = profilePage.getProfileInfoText();
        assertTrue("Страница профиля должна содержать сообщение о возможности изменения персональных данных",
                profileInfo.contains("В этом разделе вы можете изменить свои персональные данные"));
    }
}
