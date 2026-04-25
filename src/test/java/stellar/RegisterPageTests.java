package stellar;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.Assert;
import stellar.poclasses.MainPage;
import stellar.poclasses.RegisterPage;

import java.util.Objects;

@Story("Press Enter button in registration form")
public class RegisterPageTests extends BaseTest {

    @Test
    @Description("Переход к форме входа из формы регистрации")
    @Step("Тест: переход к форме входа из формы регистрации")
    public void pressEnterButtonFromRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = new RegisterPage(driver, wait);

        mainPage.openPage();
        mainPage.clickEnterAccountButton();

        registerPage.clickRegisterButton();
        registerPage.clickEnterFromRegisterForm();

        Assert.assertTrue(
                "Не отобразилась форма входа после перехода из формы регистрации",
                registerPage.isLoginFormVisible()
        );

        Assert.assertTrue(
                "URL не соответствует ожидаемому для страницы входа",
                Objects.requireNonNull(driver.getCurrentUrl()).contains("/login")
        );
    }
}
