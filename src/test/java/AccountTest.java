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

import static org.junit.Assert.*;

public class AccountTest extends BaseTest {
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
    }

    @Test
    @Description("Проверка перехода в личный кабинет: после входа через форму логина и клика на 'Личный кабинет' открывается страница профиля с информацией")
    public void testNavigateToProfile() {
        driver.get("https://stellarburgers.nomoreparties.site/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        HomePage home = new HomePage(driver);
        home.waitUntilPageUrlLoads();
        home.clickAccountLink();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitUntilPageUrlLoads();

        assertEquals("После клика на 'Личный кабинет' ожидается открытие профиля",
                "https://stellarburgers.nomoreparties.site/account/profile",
                driver.getCurrentUrl());

        String profileInfo = profilePage.getProfileInfoText();
        assertTrue("Страница профиля должна содержать сообщение о возможности изменения персональных данных",
                profileInfo.contains("В этом разделе вы можете изменить свои персональные данные"));
    }
}
