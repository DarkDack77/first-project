package stellar.poclasses;

import Utils.UsefullMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;

@Epic("UI Tests")
@Feature("Login functionality")
@Story("User authentication")
public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;


    private static final By LOGIN_EMAIL_FIELD = By.name("name");
    private static final By LOGIN_PASSWORD_FIELD = By.name("Пароль");
    private static final By LOGIN_ENTER_BUTTON = By.xpath("//button[text()='Войти']");
    private static final By RESET_PASSWORD_BUTTON = By.xpath("//a[text()='Восстановить пароль']");

    private static final By REGISTER_BUTTON = By.xpath("//a[contains(text(), 'Зарегистрироваться')]");
    private static final By RESET_PASSWORD_PAGE_ENTER_BUTTON = By.xpath("//a[text()='Войти']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Заполнение поля Email")
    public void fillEmailField(String email) {
        fillField(LOGIN_EMAIL_FIELD, email);
    }

    @Step("Заполнение поля Пароль")
    public void fillPasswordField(String password) {
        fillField(LOGIN_PASSWORD_FIELD, password);
    }

    @Step("Нажатие кнопки «Войти»")
    public void clickLoginButton() {
        UsefullMethods.clickElement(driver, LOGIN_ENTER_BUTTON);
    }

    @Step("Переход к форме восстановления пароля")
    public void clickResetPasswordButton() {
        UsefullMethods.clickElement(driver, RESET_PASSWORD_BUTTON);
    }

    @Step("Нажатие кнопки «Зарегистрироваться»")
    public void clickRegisterButton() {
        UsefullMethods.clickElement(driver, REGISTER_BUTTON);
    }

    @Step("Нажатие кнопки «Войти» на странице восстановления пароля")
    public void clickLoginFromResetPasswordPage() {
        UsefullMethods.clickElement(driver, RESET_PASSWORD_PAGE_ENTER_BUTTON);
    }

    private void fillField(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
        field.clear();
        field.sendKeys(value);
    }
}
