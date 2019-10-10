package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import properties.BrowserProperties;


public abstract class BaseSettings {
    final WebDriver driver;
    final WebDriverWait wait;

    BaseSettings() {
        driver = BrowserProperties.getDriver();
        wait = new WebDriverWait(driver, 10);
    }

    /**
     * Ожидание кликабельности.
     * @param by
     */
    void waitElementToBeClickable(By by){
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * Ожидание видимости.
     * @param by
     */
    void waitElementToBeVisible(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
