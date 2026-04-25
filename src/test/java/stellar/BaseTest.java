package stellar;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");

        switch (browser) {
            case "chrome":
                launchChrome();
                break;
            case "yandex":
                launchYandexBrowser();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private void launchChrome() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    private void launchYandexBrowser() {
        WebDriverManager.chromedriver()
                .driverVersion("144.0.7559.110")
                .setup();

        ChromeOptions options = new ChromeOptions();
        String yandexPath = "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe";
        options.setBinary(yandexPath);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String userAgent = (String) js.executeScript("return navigator.userAgent;");
        Assert.assertNotNull(userAgent);
        String browserVersion = extractVersionFromUserAgent(userAgent);

        System.out.println("Яндекс Браузер успешно запущен (версия " + browserVersion + ")");
    }

    private String extractVersionFromUserAgent(String userAgent) {
        int start = userAgent.indexOf("YaBrowser/");
        if (start != -1) {
            start += "YaBrowser/".length();
            int end = userAgent.indexOf(" ", start);
            if (end == -1) end = userAgent.length();
            return userAgent.substring(start, end);
        }
        return "неизвестная версия";
    }


    @After
    public void quit() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Ошибка при закрытии драйвера: " + e.getMessage());
            }
        }
    }
}
