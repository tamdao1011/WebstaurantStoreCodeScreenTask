package modules;

import core.TestBase;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SearchPage;

import java.util.List;

import static common.CommonFunctions.*;
import static common.CommonFunctions.findElements;
import static pages.CartPage.*;
import static pages.HomePage.searchValue;
import static pages.SearchPage.*;

public class DemoTest extends TestBase {

    private static SoftAssert softAssert = new SoftAssert();
    private static int currentPage = 1;

    @Severity(SeverityLevel.NORMAL)
    @Story("WebstaurantStore Code Screen Task")
    @Description("Verify the product title has the word 'Table' with 'stainless work table' search value and add/empty the cart")
    @Test
    public void Test() {
        searchValue("stainless work table");
        verifyProductTitleValue("Table");
        addTheLastItem();
        emptyCart();
        verifyCartIsEmpty();
        softAssert.assertAll();
    }

    @Step("Check the search result ensuring every product item has the correct value")
    public void verifyProductTitleValue(String value) {

        //The number of total result page is more than one
        if (checkElementPresent(next_btn)) {
            int totalPage = getTotalPage();
            while (currentPage <= totalPage) {
                checkSearchResultValue(value);
                //Check if it is not the last page, click the next page
                if (currentPage == totalPage)
                    click(findElement(next_btn));
                currentPage++;
            }
        }
        //The number of total result page equals one or no search result
        else checkSearchResultValue(value);
    }

    private void checkSearchResultValue(String value) {
        try {
            //Check the search result ensuring every product item has the value in its title.
            List<WebElement> PRODUCT_LISTING = findElements(SearchPage.product_listing);
            for (WebElement webElement : PRODUCT_LISTING) {
                String actualTitle = webElement.getAttribute("data-description");
                softAssert.assertTrue(actualTitle.matches(String.format("(.*)%s(.*)", value))
                        , String.format("'%s' value does not display in the '%s' product title on page %d", value, actualTitle, currentPage));
            }
        } catch (NullPointerException e) {
            //If PRODUCT_LISTING element does not exist, no search result
            System.out.println("No search result");
            isNoResult = true;
        }
    }

    @Step("Check the cart is empty")
    public static void verifyCartIsEmpty() {
        //Wait for the empty cart text displays
        WebElement EMPTY_CART_TXT = findElement(empty_cart_txt);
        waitForElementExist(EMPTY_CART_TXT);
        //Check the value
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("Your cart is empty.", EMPTY_CART_TXT.getText());
        softAssert.assertAll();
    }

}
