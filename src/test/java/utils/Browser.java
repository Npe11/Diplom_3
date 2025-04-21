package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {
    public WebDriver getWebDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                return new ChromeDriver();
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "/Users/npnp/Downloads/yandexdriver");
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                return new ChromeDriver(yandexOptions);
            default:
                throw new IllegalArgumentException("Поддерживаемые браузеры: chrome, yandex. Получен: " + browserName);
        }
    }
}
