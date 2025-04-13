import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.RegistrationPage;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.time.Duration;
import clients.UserClient;

public class RegistrationTest extends BaseTest {

    private String email;
    private final String password = "password123";
    private final String name = "TestUser";
    private UserClient userClient = new UserClient();

    @Before
    public void setUpTest() {
        email = "testuser" + System.currentTimeMillis() + "@example.com";
        driver.get("https://stellarburgers.nomoreparties.site/register");
    }

    @After
    public void tearDownTest() {
        if (email != null) {
            Response loginResponse = userClient.loginUser(email, password);
            String token = loginResponse.jsonPath().getString("accessToken");
            if (token != null && !token.isEmpty()) {
                userClient.deleteUser(token);
            }
        }
    }

    @Test
    @Description("Проверка успешной регистрации с корректными данными")
    public void testSuccessfulRegistration() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.enterName(name);
        regPage.enterEmail(email);
        regPage.enterPassword(password);
        regPage.clickRegister();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/login"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue("После успешной регистрации должен произойти переход на страницу логина", currentUrl.contains("/login"));
    }

    @Test
    @Description("Проверка ошибки регистрации с некорректным паролем (менее 6 символов)")
    public void testRegistrationWithInvalidPassword() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.enterName(name);
        regPage.enterEmail(email);
        String invalidPassword = "12345"; // меньше 6 символов
        regPage.enterPassword(invalidPassword);
        regPage.clickRegister();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(regPage.getErrorMessageLocator()));

        assertEquals("Некорректный пароль", regPage.getErrorMessage());
    }
}
