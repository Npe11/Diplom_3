import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.RegistrationPage;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import clients.UserClient;
import utils.Endpoints;

public class RegistrationTest extends BaseTest {

    private Faker faker;
    private String email;
    private String password;
    private String name;
    private UserClient userClient = new UserClient();

    @Before
    public void setUpTest() {
        faker = new Faker();
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 12);
        name = faker.name().fullName();
        driver.get(Endpoints.REGISTER_PAGE);
    }

    @After
    public void tearDownTest() {
        if (email != null) {
            CourierModel courier = new CourierModel(email, password, null);
            Response loginResponse = userClient.loginUser(courier);
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

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilPageUrlLoads();

        String currentUrl = driver.getCurrentUrl();
        assertTrue("После успешной регистрации должен произойти переход на страницу логина",
                currentUrl.contains("/login"));
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

        assertEquals("Некорректный пароль", regPage.getErrorMessage());
    }
}
