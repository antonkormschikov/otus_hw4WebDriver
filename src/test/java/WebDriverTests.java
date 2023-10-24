import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverTests {
    private static final Logger logger= (Logger) LogManager.getLogger();
    private WebDriver driver;

    @BeforeAll
    public static void setup() {

           WebDriverManager.firefoxdriver().setup();
    }

    @AfterEach
    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }

     @Test
    public void headlessTest() throws NoSuchElementException  {
        //Инициализация драйвера вынесена в тест т.к., в разных тестах браузер запускается в разных режимах
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        //driver = new ChromeDriver(options);

        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver=new FirefoxDriver(options);

        driver.get("https://duckduckgo.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement searchString = driver.findElement(new By.ByXPath("//input[@name='q']"));
        searchString.clear();
        searchString.sendKeys("ОТУС");
        driver.findElement(new By.ByXPath("//button[@aria-label='Search']")).click();
        Assertions.assertTrue(driver.findElement(new By.ByXPath("//ol/li[1]/article//a[@href='https://otus.ru/']/span[text()='Онлайн‑курсы для профессионалов, дистанционное обучение современным ...']")).isDisplayed());
    }

    @Test
    public void kioskTest() throws NoSuchElementException{

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--kiosk");
        driver=new FirefoxDriver(options);
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(new By.ByXPath("//li/span/a[@href='assets/images/p3.jpg']")).click();
        WebElement modalWindow = driver.findElement(new By.ByXPath("//div[@class='pp_content_container']"));
        Assertions.assertTrue(modalWindow.isDisplayed());

    }

    @Test
    public void cookesTest() throws NoSuchElementException{
        driver = new FirefoxDriver();
        driver.manage().window().fullscreen();
        driver.get("https://otus.ru");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().deleteAllCookies();
        driver.findElement(new By.ByXPath("//button[text()='Войти']")).click();
        WebElement loginInput = driver.findElement(new By.ByXPath("//input[@name='email']"));
        loginInput.clear();
        loginInput.sendKeys("oxilqrxobfqlrd@hldrive.com");
        WebElement passInput = driver.findElement(new By.ByXPath("//input[@type='password']"));
        passInput.clear();
        passInput.sendKeys("Opera-324");
        driver.findElement(new By.ByXPath("//button/div[text()='Войти']")).click();
        logger.info(String.format("Выводим в логи Cookies с otus.ru %s", driver.manage().getCookies().toString()));
    }

}
