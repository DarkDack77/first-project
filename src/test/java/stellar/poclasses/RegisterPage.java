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
@Feature("Registration functionality")
@Story("User registration")
public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By REGISTER_BUTTON = By.xpath("//a[contains(text(), 'Зарегистрироваться')]");
    private final By REGISTER_NAME_FIELD = By.name("name");
    private final By REGISTER_SUBMIT_BUTTON = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");
    private final By REGISTER_ENTER_BUTTON = By.xpath("//a[contains(text(), 'Войти')]");
    private final By PASSWORD_ERROR = By.xpath("//p[contains(text(), 'Некорректный пароль')]");

    public RegisterPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Нажатие кнопки регистрации")
    public void clickRegisterButton() {  // Убран модификатор static
        UsefullMethods.clickElement(driver, REGISTER_BUTTON);
    }

    @Step("Заполнение формы регистрации")
    public void fillRegistrationForm(String name, String email, String password) {
        fillField(REGISTER_NAME_FIELD, name);
        UsefullMethods.fillFieldUseful(driver, email);
        UsefullMethods.fillFieldUseful(driver, password);
    }

    @Step("Отправка формы регистрации")
    public void submitRegistration() {
        UsefullMethods.clickElement(driver, REGISTER_SUBMIT_BUTTON);
    }

    @Step("Переход к форме входа из формы регистрации")
    public void clickEnterFromRegisterForm() {
        UsefullMethods.clickElement(driver, REGISTER_ENTER_BUTTON);
    }

    private void fillField(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
        field.clear();
        field.sendKeys(value);
    }


    @Step("Проверка видимости формы входа")
    public boolean isLoginFormVisible() {
        try {
            By loginFormLocator = By.xpath("//h2[contains(text(), 'Вход') or contains(text(), 'Авторизация')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginFormLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка ошибки некорректного пароля")
    public boolean isPasswordErrorVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
