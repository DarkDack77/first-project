package stellar;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import stellar.poclasses.MainPage;

import static org.junit.Assert.assertTrue;

@Epic("UI Tests")
@Feature("Navigation")
public class NavigationTests extends BaseTest {

    private MainPage mainPage;

    @Before
    @Step("Подготовка теста: открытие главной страницы")
    public void setUpTest() {
        mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    @Test
    @Story("Navigation To Sauces section")
    @Description("Проверка навигации в раздел «Соусы»")
    @Step("Переход в раздел «Соусы»")
    public void navigationToSaucesTest() {
        mainPage.clickSaucesButton();
        assertTrue("Раздел «Соусы» не отображается после перехода", mainPage.isSaucesSectionVisible());
    }

    @Test
    @Story("Navigation To Fillings section")
    @Description("Проверка навигации в раздел «Начинки»")
    @Step("Переход в раздел «Начинки»")
    public void navigationToFillingsTest() {
        mainPage.clickFillingsButton();
        assertTrue("Раздел «Начинки» не отображается после перехода", mainPage.isFillingsSectionVisible());
    }

    @Test
    @Story("Navigation To Buns section")
    @Description("Проверка навигации в раздел «Булки»")
    @Step("Переход в раздел «Булки»")
    public void navigationToBunsTest() {
        mainPage.clickSaucesButton();
        assertTrue("Раздел «Соусы» не отобразился перед переходом к разделу «Булки»", mainPage.isSaucesSectionVisible());

        mainPage.clickBunsButton();
        assertTrue("Раздел «Булки» не отображается после перехода", mainPage.isBunsSectionVisible());
    }
}
