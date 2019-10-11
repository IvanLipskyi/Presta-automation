package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import properties.BrowserProperties;


public abstract class BaseSettings {
    final WebDriver driver;
    final WebDriverWait wait;

    /**
     * Initialization of WebDriver and WebDriverWait
     */
    BaseSettings() {
        driver = BrowserProperties.getDriver();
        wait = new WebDriverWait(driver, 10);
    }

    /**
     * Waiting of an element to be successfully clicked.
     * @param by
     */
    void waitElementToBeClickable(By by){
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * Waiting for an element to be visible.
     * @param by
     */
    void waitElementToBeVisible(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
