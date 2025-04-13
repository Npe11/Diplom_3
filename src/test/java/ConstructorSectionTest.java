import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;

import static org.junit.Assert.assertTrue;

public class ConstructorSectionTest extends BaseTest {
    private HomePage home;

    @Before
    public void testSetUp() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        home = new HomePage(driver);
    }

    @Test
    @Description("Проверка перехода на вкладку 'Булки': отображается раздел 'Булки' и вкладка активна")
    public void testBunsSection() throws InterruptedException {
        home.clickSaucesTab();
        Thread.sleep(1000); // Ждем 1 секунду

        home.clickBunsTab();
        Thread.sleep(1000); // Ждем 1 секунду

        assertTrue("Вкладка 'Булки' должна быть активной", home.isBunsTabActive());
        assertTrue("Раздел 'Булки' должен отображаться", home.isBunsSectionVisible());
    }

    @Test
    @Description("Проверка перехода на вкладку 'Соусы': отображается раздел 'Соусы' и вкладка активна")
    public void testSaucesSection() throws InterruptedException {
        home.clickSaucesTab();
        Thread.sleep(1000); // Ждем 1 секунду

        assertTrue("Вкладка 'Соусы' должна быть активной", home.isSaucesTabActive());
        assertTrue("Раздел 'Соусы' должен отображаться", home.isSaucesSectionVisible());
    }

    @Test
    @Description("Проверка перехода на вкладку 'Начинки': отображается раздел 'Начинки' и вкладка активна")
    public void testFillingsSection() throws InterruptedException {
        home.clickFillingsTab();
        Thread.sleep(1000); // Ждем 1 секунду

        assertTrue("Вкладка 'Начинки' должна быть активной", home.isFillingsTabActive());
        assertTrue("Раздел 'Начинки' должен отображаться", home.isFillingsSectionVisible());
    }
}
