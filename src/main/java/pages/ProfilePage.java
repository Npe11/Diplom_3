package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
    private WebDriver driver;

    private By profileInfoText = By.cssSelector(".Account_text__fZAIn");
    private By logoutButton = By.xpath("//button[text()='Выход']");

    private final By constructorButton = By.cssSelector(".AppHeader_header__link__3D_hX");
    private final By logoLink = By.cssSelector(".AppHeader_header__logo__2D0X2 a");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получение текста информации профиля")
    public String getProfileInfoText() {
        return driver.findElement(profileInfoText).getText();
    }


    @Step("Нажатие на кнопку 'Конструктор' в личном кабинете")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Нажатие на логотип Stellar Burgers в личном кабинете")
    public void clickLogo() {
        driver.findElement(logoLink).click();
    }

    @Step("Нажатие кнопки 'Выход' в личном кабинете")
    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }
}
