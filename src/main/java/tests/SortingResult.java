package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.BrowserProperties;

import java.text.DecimalFormat;
import java.util.List;

public class SortingResult extends MainPageSettings {
    private final By productTitle = By.className("product-title");
    private final By foundTotalProductsHeadline = By.className("total-products");
    private final By productPrice = By.className("price");
    private final By sortingType = By.xpath("/html//div[@id='js-product-list-top']//a[@class='select-title']/i[@class='material-icons pull-xs-right']");
    private final By sortingText = By.linkText("Цене: от высокой к низкой");
    private final String sortingURL = "http://prestashop-automation.qatestlab.com.ua/ru/search?controller=search&order=product.price.desc&s=dress";
    private final By productRegularPrice = By.className("product-price-and-shipping");
    private final By productsWithDiscount = By.className("discount");
    private final By discountPercentageSign = By.className("discount-percentage");
    private final By actualPriceOfDiscountProduct = By.xpath("//span[@class='discount-percentage']/following-sibling::span");
    private final By regularPriceOfDiscountProduct = By.className("regular-price");

    public int getAllFoundResultsQuantity() {
        waitElementToBeClickable(productTitle);
        return driver.findElements(productTitle).size();
    }

    public String getAllFoundProductsHeadline() {
        waitElementToBeVisible(foundTotalProductsHeadline);
        return driver.findElement(foundTotalProductsHeadline).getText();
    }

    /**
     * Проверка правильности отображения валюты после фильтра по USD.
     *
     * @param currency
     * @return
     */
    public boolean checkCurrencyOfFoundProducts(Currency currency) {
        waitElementToBeClickable(productPrice);
        List<WebElement> prices = driver.findElements(productPrice);
        BrowserProperties.log("Verifying that all the products contain $ currency symbol");
        for (WebElement we : prices) {
            if (!we.getText().contains("$")) {
                BrowserProperties.log("Some of the products are not in USD currency");
                return false;
            }
        }
        BrowserProperties.log("All the products are in USD currency");
        return true;
    }

    /**
     * Поиск кнопки сортировки, нажатие на неё по выбранному критерию.
     */
    public void sortProducts() {
        waitElementToBeClickable(sortingType);
        driver.findElement(sortingType).click();
        waitElementToBeClickable(sortingText);
        driver.findElement(sortingText).click();
    }

    /**
     * Проверяем правильность сортировки товаров от большего к меньшему.
     * Получаем цену наших продуктов и
     * @return
     */
    public boolean isSortedFromHighToLow() {
        wait.until(ExpectedConditions.urlContains(sortingURL));
        List<WebElement> regularPrice = driver.findElements(productRegularPrice);
        BrowserProperties.log("Verifying sorting method \"prices: from high to low\"");
        for (int i = 0; i + 1  < regularPrice.size(); i++) {
            Double firstPrice = Double.parseDouble(regularPrice.get(i).getText().substring(0, 4).replace(",", "."));
            Double secondPrice = Double.parseDouble(regularPrice.get(i + 1).getText().substring(0, 4).replace(",", "."));
            if (firstPrice < secondPrice) {
                BrowserProperties.log("The price of the first element is less than the price of the second element!");
                return false;
            }
            BrowserProperties.log("Prices are sorted correctly!");
        }
        return true;
    }

    public boolean checkDiscountProductPrice(){
        waitElementToBeClickable(productTitle);
        List<WebElement>productsWithDiscount = driver.findElements(this.productsWithDiscount);
        List<WebElement>discountPercentageSign = driver.findElements(this.discountPercentageSign);
        List<WebElement>regularPriceOfDiscountProduct = driver.findElements(this.regularPriceOfDiscountProduct);
        List<WebElement>actualPriceOfDiscountProduct = driver.findElements(this.actualPriceOfDiscountProduct);
        BrowserProperties.log("Verifying each product on-sale to has a label with discount, actual and regular price");
        BrowserProperties.log("Found: " + productsWithDiscount.size() + " products with discount");
        BrowserProperties.log("Found: " + discountPercentageSign.size() + " product signs of discount");
        BrowserProperties.log("Found: " + regularPriceOfDiscountProduct.size() + " regular prices of discount products");
        BrowserProperties.log("Found: " + actualPriceOfDiscountProduct.size() + " actual prices of discount products");
        for(WebElement element:discountPercentageSign){
            if(element.getText().contains("%")){
                BrowserProperties.log("Element: " + element.getText() + " contains discount!");
            }else{
                BrowserProperties.log("Element: " + element.getText() + " doesn't contain discount!");
                return false;
            }
        }
        if (productsWithDiscount.size() == discountPercentageSign.size() && productsWithDiscount.size() == regularPriceOfDiscountProduct.size() && productsWithDiscount.size() == actualPriceOfDiscountProduct.size()){
            BrowserProperties.log("Quantity of products with discount: " + productsWithDiscount.size() + " equals to quantity of discount signs: " + discountPercentageSign.size());
            BrowserProperties.log("Quantity of products with discount: " + productsWithDiscount.size() + " equals to quantity of regular prices: " + regularPriceOfDiscountProduct.size());
            BrowserProperties.log("Quantity of products with discount: " + productsWithDiscount.size() + " equals to quantity of actual prices: " + actualPriceOfDiscountProduct.size());
            return true;
        } else{
            BrowserProperties.log("Quantity of products with discount doesn't to one of parameters");
            return false;
        }
    }
    public boolean checkCorrectDiscountCalculation(){
        waitElementToBeClickable(productTitle);
        List<WebElement>productsWithDiscount = driver.findElements(this.productsWithDiscount);
        List<WebElement>discountPercentageSign = driver.findElements(this.discountPercentageSign);
        List<WebElement>regularPriceOfDiscountProduct = driver.findElements(this.regularPriceOfDiscountProduct);
        List<WebElement>actualPriceOfDiscountProduct = driver.findElements(this.actualPriceOfDiscountProduct);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        BrowserProperties.log("Verifying the correct calculation of discount percentage");
        for (int i = 0; i < productsWithDiscount.size(); i++){
            int discPerc = Integer.parseInt(discountPercentageSign.get(i).getText().replace("%", ""));
            float regularPrice = Float.parseFloat(regularPriceOfDiscountProduct.get(i).getText().substring(0, 4).replace(",", "."));
            float actualPrice = Float.parseFloat(actualPriceOfDiscountProduct.get(i).getText().substring(0, 4).replace(",", "."));
            float discountValue = Float.parseFloat(decimalFormat.format((regularPrice * (Math.abs(discPerc))) / 100).replace(",","."));
            if ((regularPrice - discountValue) != actualPrice) {
                BrowserProperties.log("The regular price: " + regularPrice + "$" + " minus discount value:" + discountValue + "$" + " doesn't match the actual price:" + actualPrice + "$");
                return false;
            }
                BrowserProperties.log("The regular price: " + regularPrice + "$" + " minus discount value:" + discountValue + "$" + " matches the actual price:" + actualPrice + "$");
            }
        return true;
    }
}
