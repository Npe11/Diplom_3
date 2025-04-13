import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.Browser;

/**
 * BaseTest – базовый класс для тестов.
 * Драйвер создается через фабрику Browser, которая выбирает нужный браузер по системному свойству "browser".
 * По умолчанию используется chrome.
 */
public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = new Browser().getWebDriver(browser);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
