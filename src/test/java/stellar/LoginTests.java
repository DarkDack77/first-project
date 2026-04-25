package stellar;

import io.qameta.allure.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Assert;
import stellar.poclasses.LoginPage;
import stellar.poclasses.MainPage;
import stellar.poclasses.RegisterPage;

import java.util.Objects;

@RunWith(Parameterized.class)
@Epic("UI Tests")
@Feature("Authentication")
public class LoginTests extends BaseTest {

    private UserData testUser;
    private final String loginMethod;

    public LoginTests(String loginMethod) {
        this.loginMethod = loginMethod;
    }

    @Parameterized.Parameters(name = "Способ авторизации: {0}")
    public static Object[][] getLoginData() {
        return new Object[][]{
                {"Через кнопку «Войти в аккаунт» на главной"},
                {"Через кнопку «Личный кабинет»"},
                {"Через кнопку в форме регистрации"},
                {"Через кнопку в форме восстановления пароля"}
        };
    }

    @Before
    public void setUpUser() throws Exception {
        testUser = stellar.UserApiHelper.createUniqueUser();
    }

    @After
    public void tearDownUser() throws Exception {
        if (testUser != null) {
            stellar.UserApiHelper.deleteUser(testUser.getAccessToken());
        }
    }

    @Test
    @Story("Successful login via different methods")
    public void successfulLoginTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver, wait);
        RegisterPage registerPage = new RegisterPage(driver, wait);

        mainPage.openPage();

        switch (loginMethod) {
            case "Через кнопку «Войти в аккаунт» на главной":
                mainPage.clickEnterAccountButton();
                break;
            case "Через кнопку «Личный кабинет»":
                mainPage.clickPersonalOfficeButton();
                break;
            case "Через кнопку в форме регистрации":
                mainPage.clickEnterAccountButton();
                loginPage.clickRegisterButton();
                registerPage.clickEnterFromRegisterForm();
                break;
            case "Через кнопку в форме восстановления пароля":
                mainPage.clickEnterAccountButton();
                loginPage.clickResetPasswordButton();
                loginPage.clickLoginFromResetPasswordPage();
                break;
        }

        loginPage.fillEmailField(testUser.getEmail());
        loginPage.fillPasswordField(testUser.getPassword());
        loginPage.clickLoginButton();

        Assert.assertTrue(
                "Авторизация не удалась для способа: " + loginMethod,
                Objects.requireNonNull(driver.getCurrentUrl()).contains("/profile")
        );
    }
}
