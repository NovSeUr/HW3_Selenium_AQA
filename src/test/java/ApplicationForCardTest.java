import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationForCardTest {
    WebDriver driver;
    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    //Фамилия в одно слово
    @Test
    void testShouldFillOutTheForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Чехов Антон"); // Двойная фамилия через тире
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79969969601");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    //Имя без фамилии
    @Test
    void testShouldFillOutTheFormJustName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Боб"); // Только имя без фамилии
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79651234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    //Двойная фамилия через дефис
    @Test
    void testShouldFillOutTheFormWithDash() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Мамин-Сибиряк Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79651234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    //Двойная фамилия через пробел
    @Test
    void testShouldFillOutTheFormSpace() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Мамин Сибиряк Дмитрий"); // Двойная фамилия через пробел
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79651234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }
}

