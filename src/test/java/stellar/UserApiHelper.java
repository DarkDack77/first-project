package stellar;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import io.qameta.allure.Step;

import java.util.Random;

public class UserApiHelper {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";
    private static final String REGISTER_USER = "/auth/register";
    private static final String LOGIN_USER = "/auth/login";
    private static final String DELETE_USER = "/auth/user";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Step("Создание тестового пользователя через API")
    public static UserData createUniqueUser() throws Exception {
        UserData userData = generateUniqueUser();
        String accessToken = registerUser(userData);
        userData.setAccessToken(accessToken);
        return userData;
    }

    @Step("Генерация данных уникального пользователя")
    public static UserData generateUniqueUser() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "user_" + timestamp + "@test.com";
        String password = "password_" + new Random().nextInt(10000);
        String name = "TestUser_" + timestamp;

        UserData userData = new UserData();
        userData.setEmail(email);
        userData.setPassword(password);
        userData.setName(name);
        return userData;
    }

    private static String registerUser(UserData userData) throws Exception {
        UserRegistrationRequest userRequest = new UserRegistrationRequest(
                userData.getEmail(),
                userData.getPassword(),
                userData.getName()
        );

        return postAuthRequest(REGISTER_USER, userRequest);
    }

    @Step("Авторизация пользователя через API")
    public static String loginUser(UserData userData) throws Exception {
        UserRegistrationRequest userRequest = new UserRegistrationRequest(
                userData.getEmail(),
                userData.getPassword(),
                userData.getName()
        );
        return postAuthRequest(LOGIN_USER, userRequest);
    }

    private static String postAuthRequest(String endpoint, UserRegistrationRequest userRequest) throws Exception {
        String userJson = objectMapper.writeValueAsString(userRequest);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(BASE_URL + endpoint);
            postRequest.addHeader("Content-Type", "application/json");

            StringEntity entity = new StringEntity(userJson);
            postRequest.setEntity(entity);

            HttpResponse response = httpClient.execute(postRequest);
            String responseBody = EntityUtils.toString(response.getEntity());

            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getString("accessToken");
        }
    }

    @Step("Удаление тестового пользователя через API")
    public static void deleteUser(String accessToken) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            org.apache.http.client.methods.HttpDelete deleteRequest =
                    new org.apache.http.client.methods.HttpDelete(BASE_URL + DELETE_USER);
            deleteRequest.addHeader("Authorization", accessToken);
            httpClient.execute(deleteRequest);
        }
    }
}
