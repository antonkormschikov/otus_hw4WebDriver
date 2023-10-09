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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class WebDriverTests {
    Logger logger= (Logger) LogManager.getLogger();
    WebDriver driver;

    @BeforeAll
    public static void setup() {

        //WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Disabled
    @BeforeEach
    public void start() {


       /* FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver=new FirefoxDriver(options);*/
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
        String actual =(driver.findElement(new By.ByXPath("//ol/li[1]/article//a[@href='https://otus.ru/' and @data-testid='result-title-a']/span"))).getText();
        String expected="Онлайн‑курсы для профессионалов, дистанционное обучение современным ...";
        Assertions.assertEquals(expected,actual);
    }


    @Test
    public void kioskTest() throws NoSuchElementException{

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--kiosk");
        driver=new FirefoxDriver(options);
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(new By.ByXPath("//li[@data-id='id-2' and @class='portfolio-item2 content']")).click();
        WebElement modalWindow = driver.findElement(new By.ByXPath("//div[@class='pp_content_container']"));

        Assertions.assertEquals(true,modalWindow.isDisplayed());

    }



    @Test
    public void cookesTest() throws NoSuchElementException{
        driver = new FirefoxDriver();
        driver.manage().window().fullscreen();
        driver.get("https://otus.ru");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().deleteAllCookies();
        driver.findElement(new By.ByXPath("//button[@class='sc-mrx253-0 enxKCy sc-945rct-0 iOoJwQ']")).click();
        WebElement loginInput = driver.findElement(new By.ByXPath("//input[@name='email']"));
        loginInput.clear();loginInput.sendKeys("oxilqrxobfqlrd@hldrive.com");
        WebElement passInput = driver.findElement(new By.ByXPath("//input[@type='password']"));
        passInput.clear(); passInput.sendKeys("Opera-324");
        driver.findElement(new By.ByXPath("//button[@class='sc-9a4spb-0 gYNtqF sc-11ptd2v-2-Component cElCrZ']")).click();
        logger.info(String.format("Выводим в логи Cookies с otus.ru %s", driver.manage().getCookies().toString()));
    }

}
