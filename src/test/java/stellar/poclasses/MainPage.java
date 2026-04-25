package stellar.poclasses;

import Utils.UsefullMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

@Epic("UI Tests")
@Feature("Main page functionality")
public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By ENTER_ACCOUNT_BUTTON = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");
    private final By PERSONAL_OFFICE_BUTTON = By.xpath("//p[text()='Личный Кабинет']");
    private final By FILLINGS_BUTTON = By.xpath("//span[contains(text(), 'Начинки')]");
    private final By SAUCES_BUTTON = By.xpath("//span[contains(text(), 'Соусы')]");
    private final By BUNS_BUTTON = By.xpath("//span[contains(text(), 'Булки')]");


    private final String BASE_URL = "https://stellarburgers.education-services.ru/";

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
    }

    @Step("Открытие главной страницы")
    public void openPage() {
        driver.get(BASE_URL);
    }

    @Step("Нажатие кнопки «Войти в аккаунт»")
    public void clickEnterAccountButton() {
        UsefullMethods.clickElement(driver, ENTER_ACCOUNT_BUTTON);
    }

    @Step("Нажатие кнопки «Личный кабинет»")
    public void clickPersonalOfficeButton() {
        UsefullMethods.clickElement(driver, PERSONAL_OFFICE_BUTTON);
    }

    @Step("Переход в раздел «Начинки»")
    public void clickFillingsButton() {
        UsefullMethods.clickElement(driver, FILLINGS_BUTTON);
    }

    @Step("Переход в раздел «Соусы»")
    public void clickSaucesButton() {
        UsefullMethods.clickElement(driver, SAUCES_BUTTON);
    }

    @Step("Переход в раздел «Булки»")
    public void clickBunsButton() {
        UsefullMethods.clickElement(driver, BUNS_BUTTON);
    }

    public boolean isSaucesSectionVisible() {
        return isSectionActive("Соусы");
    }

    public boolean isFillingsSectionVisible() {
        return isSectionActive("Начинки");
    }

    public boolean isBunsSectionVisible() {
        return isSectionActive("Булки");
    }


    private boolean isSectionActive(String sectionName) {
        try {
            By sectionLocator = By.xpath(String.format(
                    "//div[contains(@class, 'tab_tab_type_current')]/span[contains(text(), '%s')]",
                    sectionName)
            );
            return wait.until(ExpectedConditions.visibilityOfElementLocated(sectionLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
