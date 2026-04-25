package stellar;

import io.qameta.allure.*;
import org.junit.*;
import stellar.poclasses.MainPage;
import stellar.poclasses.RegisterPage;

@Epic("UI Tests")
@Feature("Registration")
public class RegisterTests extends BaseTest {

    private UserData testUser;

    @Before
    @Step("Подготовка: генерация тестового пользователя")
    public void setUpUser() {
        testUser = UserApiHelper.generateUniqueUser();
    }

    @After
    @Step("Очистка: удаление тестового пользователя через API")
    public void tearDownUser() throws Exception {
        if (testUser != null) {
            String accessToken = testUser.getAccessToken();
            if (accessToken == null) {
                accessToken = UserApiHelper.loginUser(testUser);
            }
            UserApiHelper.deleteUser(accessToken);
        }
    }

    @Test
    @Story("Successful registration")
    @Description("Проверка успешной регистрации нового пользователя")
    public void successfulRegistrationTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = new RegisterPage(driver, wait);

        mainPage.openPage();
        mainPage.clickEnterAccountButton();

        registerPage.clickRegisterButton();
        registerPage.fillRegistrationForm(testUser.getName(), testUser.getEmail(), testUser.getPassword());
        registerPage.submitRegistration();

        Assert.assertTrue(
                "После успешной регистрации не открылась форма входа",
                registerPage.isLoginFormVisible()
        );
    }
}
