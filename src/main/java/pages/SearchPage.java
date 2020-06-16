package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static common.CommonFunctions.*;

public class SearchPage {

    private static final By product_listing = By.xpath("//div[@id='product_listing']/div");
    private static final By pagination = By.cssSelector(".pagination ul li");
    private static final By next_btn = By.xpath("//i[@class='icon-right-open']");
    private static final By add_to_cart_btn_popup = By.cssSelector("button[name='addToCartButton']");
    private static final By required_field = By.cssSelector("select[data-required='Y']");

    private static SoftAssert sortAssertion = new SoftAssert();
    public static boolean isNoResult= false;
    private static int totalPage = 0;
    private static int currentPage = 1;

    @Step("Check the search result ensuring every product item has the correct value")
    public static void verifyProductTitleValue(String value) {
        //The number of total result page is more than one
        if (checkElementPresent(next_btn)) {
            getTotalPage();
            while (currentPage <= totalPage) {
                checkSearchResultValue(value);
                //Check if it is not the last page, click the next page
                if (currentPage == totalPage)
                    click(find(next_btn));
                currentPage++;
            }
        }
        //The number of total result page equals one or no search result
        else checkSearchResultValue(value);
        sortAssertion.assertAll();
    }

    private static void getTotalPage() {
        //Return the number of total page on the Search result
        //Find the pagination
        List<WebElement> PAGINATION = findElements(pagination);
        int size = PAGINATION.size() - 1;
        //Get the number of the second-last item which next to the ">" icon
        WebElement lastPage = find(By.xpath(String.format("//div[@class='pagination pagination--unified centered']/ul/li[%s]/a", size)));
        totalPage = Integer.parseInt(lastPage.getText());
    }

    private static void checkSearchResultValue(String value) {
        try {
            //Check the search result ensuring every product item has the value in its title.
            List<WebElement> PRODUCT_LISTING = findElements(product_listing);
            for (WebElement webElement : PRODUCT_LISTING) {
                String actualTitle = webElement.getAttribute("data-description");
                sortAssertion.assertTrue(actualTitle.matches(String.format("(.*)%s(.*)", value))
                        , String.format("'%s' value does not display in the '%s' product title on page %d", value, actualTitle, currentPage));
            }
        } catch (NullPointerException e) {
            //If PRODUCT_LISTING element does not exist, no search result
            System.out.println("No search result");
            isNoResult = true;
        }
    }

    @Step("Add the last of found items to the Cart")
    public static void addTheLastItem() {
        //Run only the result is not null
        if (!isNoResult) {
            //Check if the last page is currently displayed
            if (!isLastPage())
                moveToLastPage();
            //Add the last item by clicking at the last item on the product list
            List<WebElement> PRODUCT_LISTING = findElements(product_listing);
            int size = PRODUCT_LISTING.size();
            WebElement lastProduct = find(By.xpath(String.format("//div[@id='product_listing']/div[%s]//input[@type='submit']", size)));
            click(lastProduct);
            //Click Add to Cart button on the Product Accessories popup
            fillAndAddToCart_productAccessories();
        }
    }

    private static boolean isLastPage() {
        try {
            //Check the ">" icon is disabled
            List<WebElement> PAGINATION = findElements(pagination);
            int size = PAGINATION.size();
            if (size == 0)
                return true;
            return PAGINATION.get(size-1).getAttribute("class").matches("disabled");
        } catch (NoSuchElementException e) {
            //Only one page result
            return false;
        }
    }

    private static void moveToLastPage() {
        //Click Next button until the last page displays
        while (!isLastPage()) {
            click(find(next_btn));
        }
    }

    private static void fillAndAddToCart_productAccessories() {
        if (!checkElementPresent(required_field))
            click(find(add_to_cart_btn_popup));
        //To be defined for Required case
    }

}
