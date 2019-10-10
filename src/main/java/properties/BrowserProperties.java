package properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public abstract class BrowserProperties {

    private static EventFiringWebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = getConfiguredDriver();
        String homePageUrl = "http://prestashop-automation.qatestlab.com.ua/ru/";
        driver.get(homePageUrl);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        writeLogs();
    }

    public static void log(String message) {
        Reporter.log(message);
        System.out.println(message);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private void writeLogs() {
        File logsFile = new File("Logs.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logsFile))) {
            bw.write(EventHandler.sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventHandler.sb = new StringBuilder();
    }

    private static EventFiringWebDriver getConfiguredDriver() {
        WebDriver driver = getNewDriver();
        driver.manage().window().maximize();
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
        eventFiringWebDriver.register(new EventHandler());
        eventFiringWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return eventFiringWebDriver;
    }

    private static WebDriver getNewDriver() {
                System.setProperty("webdriver.chrome.driver", new File(BrowserProperties.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
    }
}
