package stellar;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Assert;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import stellar.poclasses.MainPage;
import stellar.poclasses.RegisterPage;

@RunWith(Parameterized.class)
@Epic("UI Tests")
@Feature("Registration")
public class ParametrizedRegistrationErrorTests extends BaseTest {

    private final String name;
    private final String email;
    private final String password;

    public ParametrizedRegistrationErrorTests(String name, String email, String password, String expectedError) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Ошибка регистрации: {3}")
    public static Object[][] getErrorData() {
        return new Object[][]{
                {"Корнеплод", "valid@email.com", "123", "Некорректный пароль"},
                {"Корнеплод", "valid@email.com", "12345", "Некорректный пароль"},
                {"Корнеплод", "valid@email.com", "1", "Некорректный пароль"}
        };
    }

    @Test
    @Story("Error handling in registration")
    @Description("Проверка обработки ошибок при регистрации с некорректными данными")
    public void registrationWithInvalidDataTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = new RegisterPage(driver, wait);

        mainPage.openPage();
        mainPage.clickEnterAccountButton();
        registerPage.clickRegisterButton();
        registerPage.fillRegistrationForm(name, email, password);
        registerPage.submitRegistration();

        Assert.assertTrue("Не отображается ошибка для некорректного пароля", registerPage.isPasswordErrorVisible());
    }
}
