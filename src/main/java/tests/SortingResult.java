package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.BrowserProperties;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class SortingResult extends MainPageSettings {
    private final By productTitle = By.className("product-title");
    private final By foundTotalProductsHeadline = By.className("total-products");
    private final By productPrice = By.className("price");
    private final By sortingType = By.xpath("/html//div[@id='js-product-list-top']//a[@class='select-title']/i[@class='material-icons pull-xs-right']");
    private final By sortingText = By.linkText("Цене: от высокой к низкой");
    private final String sortingURL = "http://prestashop-automation.qatestlab.com.ua/ru/search?order=product.price.desc&s=dress";
    private final By productRegularPrice = By.className("product-price-and-shipping");
    private final By productsWithDiscount = By.className("discount");
    private final By discountPercentageSign = By.className("discount-percentage");
    private final By actualPriceOfDiscountProduct = By.xpath("//span[@class='discount-percentage']/following-sibling::span");
    private final By regularPriceOfDiscountProduct = By.className("regular-price");
    private final int substringNum1 = 0;
    private final int substringNum2 = 4;
    private final By chosenProduct = By.cssSelector("[data-id-product='5']");
    private final By sizeSelector = By.cssSelector("select[name='group[1]']");
    private final By sizeL = By.cssSelector("[value='3']");
    private final By colorBlue = By.cssSelector(".product-variants ul .input-container:nth-of-type(3)");
    private final By increaseQuantity = By.cssSelector(".bootstrap-touchspin-up.btn.btn-touchspin.js-touchspin");
    private final By addToCart = By.cssSelector(".add-to-cart.btn.btn-primary");

    /**
     * Get the real size of all found products after searching procedure.
     * @return
     */
    public int getAllFoundResultsQuantity() {
        waitElementToBeClickable(productTitle);
        return driver.findElements(productTitle).size();
    }

    /**
     * Get the headline of found elements with definite quantity of founds products.
     * @return
     */
    public String getAllFoundProductsHeadline() {
        waitElementToBeVisible(foundTotalProductsHeadline);
        return driver.findElement(foundTotalProductsHeadline).getText();
    }

    /**
     * Check that all the products contain USD currency symbol.
     * Проверка правильности отображения валюты после фильтра по USD.
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
     * Click on sorting button and choose a criteria of sorting.
     */
    public void sortProducts() {
        waitElementToBeClickable(sortingType);
        driver.findElement(sortingType).click();
        waitElementToBeClickable(sortingText);
        driver.findElement(sortingText).click();
    }

    /**
     * This method checks that the products are sorted via criteria "Prices: From high to low".
     * In this method we get the prices of each product and compare among themselves.
     * Comparing strategy: the price of the first product bigger than the price of the second product, etc.
     * @return
     */
    public boolean isSortedFromHighToLow() {
        wait.until(ExpectedConditions.urlContains(sortingURL));
        List<WebElement> regularPrice = driver.findElements(productRegularPrice);
        BrowserProperties.log("Verifying sorting method \"prices: from high to low\"");
        for (int i = 0; i + 1  < regularPrice.size(); i++) {
            Double firstPrice = Double.parseDouble(regularPrice.get(i).getText().substring(substringNum1, substringNum2).replace(",", "."));
            Double secondPrice = Double.parseDouble(regularPrice.get(i + 1).getText().substring(substringNum1, substringNum2).replace(",", "."));
            if (firstPrice < secondPrice) {
                BrowserProperties.log("The price of the first element is less than the price of the second element!");
                return false;
            }
            BrowserProperties.log("Prices are sorted correctly!");
        }
        return true;
    }

    /**
     * In this method we find all products with discount price.
     * We check that the products have a discount sign.
     * Next step we check that these products have two prices: actual and regular.
     * @return
     */
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

    /**
     * Check the correct discount calculation.
     * We get the discount percentage from discount signs.
     * Next step we get the clean price of all discount products
     * Next step we check that the discount amount was caused correctly on the website.
     * @return
     */
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
            float regularPrice = Float.parseFloat(regularPriceOfDiscountProduct.get(i).getText().substring(substringNum1, substringNum2).replace(",", "."));
            float actualPrice = Float.parseFloat(actualPriceOfDiscountProduct.get(i).getText().substring(substringNum1, substringNum2).replace(",", "."));
            float discountValue = Float.parseFloat(decimalFormat.format((regularPrice * (Math.abs(discPerc))) / 100).replace(",","."));
            if ((regularPrice - discountValue) != actualPrice) {
                BrowserProperties.log("The regular price: " + regularPrice + "$" + " minus discount value:" + discountValue + "$" + " doesn't match the actual price:" + actualPrice + "$");
                return false;
            }
                BrowserProperties.log("The regular price: " + regularPrice + "$" + " minus discount value:" + discountValue + "$" + " matches the actual price:" + actualPrice + "$");
            }
        return true;
    }

    public void chooseProduct(){
        waitElementToBeClickable(chosenProduct);
        driver.findElement(chosenProduct).click();
        waitElementToBeClickable(sizeSelector);
        driver.findElement(sizeSelector).click();
        waitElementToBeClickable(sizeL);
        driver.findElement(sizeL).click();
        waitElementToBeClickable(colorBlue);
        driver.findElement(colorBlue).click();
        waitElementToBeClickable(increaseQuantity);
        driver.findElement(increaseQuantity).click();
        waitElementToBeClickable(addToCart);
        driver.findElement(addToCart).click();
    }
}
