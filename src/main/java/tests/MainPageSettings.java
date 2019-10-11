package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import properties.BrowserProperties;

import java.util.List;


public class MainPageSettings extends BaseSettings {
    private final By currencyButton = By.cssSelector("._gray-darker.expand-more.hidden-sm-down");
    private final By eurCurrency = By.xpath("//a[.='EUR €']");
    private final By uahCurrency = By.xpath("//a[.='UAH ₴']");
    private final By usdCurrency = By.xpath("//a[.='USD $']");
    private final By productPrice = By.className("price");
    private final By searchField = By.xpath("//div[@id='search_widget']/form[@action='http://prestashop-automation.qatestlab.com.ua/ru/search']/input[@name='s']");
    private final By searchButtonSubmit = By.xpath("//div[@id='search_widget']/form[@action='http://prestashop-automation.qatestlab.com.ua/ru/search']/button[@type='submit']");
    private final By languageButton = By.cssSelector(".language-selector span");
    private final By languageButtonUkrainian = By.xpath("//a[.='Українська']");
    private final By languageButtonRussian = By.xpath("//a[.='Русский']");


    public void checkLanguageSwitch(Language language){
        waitElementToBeClickable(languageButton);
        driver.findElement(languageButton).click();
        switch (language){
            case RUSSIAN:
                waitElementToBeClickable(languageButtonRussian);
                driver.findElement(languageButtonRussian).click();
                break;
            case UKRAINIAN:
                waitElementToBeClickable(languageButtonUkrainian);
                driver.findElement(languageButtonUkrainian).click();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Method for clicking the different currency from the drop-down menu.
     * @param currency
     */
    public void changeAndCheckCurrencyType(Currency currency){
        waitElementToBeClickable(currencyButton);
        driver.findElement(currencyButton).click();
        switch (currency){
            case EUR:
                waitElementToBeClickable(eurCurrency);
                driver.findElement(eurCurrency).click();
                break;
            case USD:
                waitElementToBeClickable(this.usdCurrency);
                driver.findElement(usdCurrency).click();
                break;
            case UAH:
                waitElementToBeClickable(this.uahCurrency);
                driver.findElement(uahCurrency).click();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Method for checking the match between a chosen currency in header and this currency symbol for products, displayed on the site.
     * @param currency
     * @return
     */
    public boolean checkCurrencyOnPage(Currency currency){
        waitElementToBeClickable(productPrice);
        List<WebElement> prices = driver.findElements(productPrice);
        BrowserProperties.log("Verifying that products contain the correct currency type");
        switch(currency){
            case EUR:
                for(WebElement we: prices){
                    if(we.getText().contains("€")){
                        BrowserProperties.log(we.getText() + " contains correct € currency symbol. Verified!");
                    }else{
                        return false;
                    }
                }
                return true;
            case UAH:
                for(WebElement we:prices){
                    if(we.getText().contains("₴")){
                        BrowserProperties.log(we.getText() + " contains correct ₴ currency symbol. Verified!");
                    }else{
                        return false;
                    }
                }
                return true;
            case USD:
                for(WebElement we:prices){
                    if(we.getText().contains("$")){
                        BrowserProperties.log(we.getText() + " contains correct $ currency symbol. Verified!");
                    }else{
                        return false;
                    }
                }
                return true;
            default:
                throw new IllegalArgumentException("You have to enter the correct currency name: EUR, UAH or USD!");
        }
    }

    /**
     * Click the search-box menu and find any string.
     * @param str
     */
    public SortingResult searchProduct(String str){
        waitElementToBeClickable(searchField);
        WebElement searchInputField = driver.findElement(searchField);
        searchInputField.click();
        searchInputField.sendKeys(str);
        WebElement submitButton = driver.findElement(searchButtonSubmit);
        submitButton.click();
        return new SortingResult();
    }
}
