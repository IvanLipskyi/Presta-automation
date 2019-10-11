package properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.Arrays;

/**
 * Class for handling different events.
 */
class EventHandler implements WebDriverEventListener {
    static StringBuilder sb = new StringBuilder();

    /**
     * Method for writing correct log for events
     * @param message
     */
    private void eventLogs(String message) {
        System.out.println(message);
        sb.append(message).append("\n");
    }

    /**
     * Getting the name of web element.
      * @param webElement
     * @return
     */
    private String getElement(WebElement webElement) {
        if (webElement != null) {
            if (webElement.getText() != null) {
                return webElement.getText();
            } else if (webElement.getTagName() != null) {
                return webElement.getTagName();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {
        eventLogs("Trying to accept alert.");
    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {
        eventLogs("Alert is accepted successfully.");
    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {
        eventLogs("Dismiss is canceled successfully.");
    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {
        eventLogs("Trying to cancel alert.");
    }

    @Override
    public void beforeNavigateTo(String s, WebDriver webDriver) {
        eventLogs("Trying to navigate to " + s);

    }

    @Override
    public void afterNavigateTo(String s, WebDriver webDriver) {
        eventLogs("Navigated to " + s + " successfully");

    }

    @Override
    public void beforeNavigateBack(WebDriver webDriver) {
        eventLogs("Trying to navigate back.");
    }

    @Override
    public void afterNavigateBack(WebDriver webDriver) {
        eventLogs("Navigated back successfully.");
    }

    @Override
    public void beforeNavigateForward(WebDriver webDriver) {
        eventLogs("Trying to navigate forward.");
    }

    @Override
    public void afterNavigateForward(WebDriver webDriver) {
        eventLogs("Navigated forward successfully.");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {
        eventLogs("Trying to refresh.");
    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {
        eventLogs("The page is refreshed successfully");
    }

    @Override
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        eventLogs("Trying to find: " + getElement(webElement) + " by: " + by.toString());
    }

    @Override
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
        eventLogs("Founds successfully");
    }

    @Override
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
        eventLogs("Trying to click on " + getElement(webElement));
    }

    @Override
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        eventLogs("Clicked successfully");
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        eventLogs("Trying to change value of " + getElement(webElement) + " on " + Arrays.toString(charSequences));
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        eventLogs(getElement(webElement) + " changed on " + Arrays.toString(charSequences) + " successfully");
    }

    @Override
    public void beforeScript(String s, WebDriver webDriver) {
        eventLogs("Trying to execute script " + s);
    }

    @Override
    public void afterScript(String s, WebDriver webDriver) {
        eventLogs(s + " executed successfully");
    }

    @Override
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {
        eventLogs("Trying to switch to window " + s);
    }

    @Override
    public void afterSwitchToWindow(String s, WebDriver webDriver) {
        eventLogs(s + " switched successfully");
    }

    @Override
    public void onException(Throwable throwable, WebDriver webDriver) {
        eventLogs("Next exception has been caused: " + throwable);
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {

    }

    @Override
    public void beforeGetText(WebElement webElement, WebDriver webDriver) {
        eventLogs("Trying to get text of " + getElement(webElement));
    }

    @Override
    public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {
        eventLogs("Text: " + s + " of " + getElement(webElement) + " got successfully");
    }
}
