package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import properties.BrowserProperties;

import java.util.List;


public class MainPageSettings extends BaseSettings {
    private final By currencyButton = By.cssSelector(".expand-more._gray-darker");
    private final By eurCurrency = By.xpath("//a[.='EUR €']");
    private final By uahCurrency = By.xpath("//a[.='UAH ₴']");
    private final By usdCurrency = By.xpath("//a[.='USD $']");
    private final By productPrice = By.className("price");
    private final By searchField = By.xpath("//div[@id='search_widget']/form[@action='http://prestashop-automation.qatestlab.com.ua/ru/search']/input[@name='s']");
    private final By searchButtonSubmit = By.xpath("//div[@id='search_widget']/form[@action='http://prestashop-automation.qatestlab.com.ua/ru/search']/button[@type='submit']");


    /**
     * Метод для выбора различных валют на сайте.
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
     * Метод для проверки совпадения выбраной валюты в шапке и значка данной валюты на сайте.
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
     * Нажатие на кнопку поиска и ввод определенной строки для поиска.
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
