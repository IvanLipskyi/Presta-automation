package executive;

import org.testng.annotations.Test;
import properties.BrowserProperties;
import tests.Currency;
import tests.MainPageSettings;
import tests.SortingResult;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExecutiveTest extends BrowserProperties {

    /**
     * Main method to run our tests.
     */
    @Test
    public void TrialTest(){

        MainPageSettings mainPageSettings = new MainPageSettings();

        mainPageSettings.changeAndCheckCurrencyType(Currency.EUR);
        assertTrue(mainPageSettings.checkCurrencyOnPage(Currency.EUR), "Some of the goods are not displayed in EUR currency!");

        mainPageSettings.changeAndCheckCurrencyType(Currency.USD);
        assertTrue(mainPageSettings.checkCurrencyOnPage(Currency.USD), "Some of the goods are not displayed in USD currency!");

        mainPageSettings.changeAndCheckCurrencyType(Currency.UAH);
        assertTrue(mainPageSettings.checkCurrencyOnPage(Currency.UAH), "Some of the goods are not displayed in UAH currency!");

        mainPageSettings.changeAndCheckCurrencyType(Currency.USD);

        SortingResult sortingResult = mainPageSettings.searchProduct("dress");
        BrowserProperties.log("Verifying that the headline of all found products with definite number matches the real quantity of found products");
        assertEquals(sortingResult.getAllFoundProductsHeadline(), "Товаров: " + sortingResult.getAllFoundResultsQuantity() + ".");

        assertTrue(sortingResult.checkCurrencyOfFoundProducts(Currency.USD), "One or some of the goods on the page don't match the currency type in the header!");
        sortingResult.sortProducts();

        assertTrue(sortingResult.isSortedFromHighToLow(), "Some of the goods are sorted incorrectly!");
        assertTrue(sortingResult.checkDiscountProductPrice(), "Some of the products don't have a discount sign, actual or regular price");
        assertTrue(sortingResult.checkCorrectDiscountCalculation(), "Some of the goods have incorrect discount value");
    }
}
