import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import utils.Endpoints;

import static org.junit.Assert.assertTrue;

public class ConstructorSectionTest extends BaseTest {
    private HomePage home;

    @Before
    public void testSetUp() {
        driver.get(Endpoints.MAIN_PAGE);
        home = new HomePage(driver);
    }

    @Test
    @Description("Проверка перехода на вкладку 'Булки': отображается раздел 'Булки' и вкладка активна")
    public void testBunsSection() {
        home.clickSaucesTab();
        home.waitUntilBunsTabClickable();
        home.clickBunsTab();

        assertTrue("Раздел 'Булки' должен отображаться", home.isBunsSectionVisible());
    }

    @Test
    @Description("Проверка перехода на вкладку 'Соусы': отображается раздел 'Соусы' и вкладка активна")
    public void testSaucesSection() {
        home.clickSaucesTab();

        assertTrue("Раздел 'Соусы' должен отображаться", home.isSaucesSectionVisible());
    }

    @Test
    @Description("Проверка перехода на вкладку 'Начинки': отображается раздел 'Начинки' и вкладка активна")
    public void testFillingsSection() {
        home.clickFillingsTab();

        assertTrue("Раздел 'Начинки' должен отображаться", home.isFillingsSectionVisible());
    }
}
