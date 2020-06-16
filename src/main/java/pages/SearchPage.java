package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static common.CommonFunctions.*;

public class SearchPage {

    public static final By product_listing = By.xpath("//div[@id='product_listing']/div");
    private static final By pagination = By.cssSelector(".pagination ul li");
    public static final By next_btn = By.xpath("//i[@class='icon-right-open']");
    private static final By add_to_cart_btn_popup = By.cssSelector("button[name='addToCartButton']");
    private static final By required_field = By.cssSelector("select[data-required='Y']");

    public static boolean isNoResult= false;

    public static int getTotalPage() {
        //Return the number of total page on the Search result
        //Find the pagination
        List<WebElement> PAGINATION = findElements(pagination);
        int size = PAGINATION.size() - 1;
        //Get the number of the second-last item which next to the ">" icon
        WebElement lastPage = findElement(By.xpath(String.format("//div[contains(@class,'pagination')]//li[%s]/a", size)));
        return Integer.parseInt(lastPage.getText());
    }

    @Step("Add the last of found items to the Cart")
    public static void addTheLastItem() {
        SearchPage searchPage = new SearchPage();
        //Run only the result is not null
        if (!isNoResult) {
            //Check if the last page is currently displayed
            if (!searchPage.isLastPage())
                searchPage.moveToLastPage();
            //Add the last item by clicking at the last item on the product list
            List<WebElement> PRODUCT_LISTING = findElements(product_listing);
            int size = PRODUCT_LISTING.size();
            WebElement lastProduct = findElement(By.xpath(String.format("//div[@id='product_listing']/div[%s]//input[@type='submit']", size)));
            click(lastProduct);
            //Click Add to Cart button on the Product Accessories popup
            searchPage.fillAndAddToCart_productAccessories();
        }
    }

    private boolean isLastPage() {
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

    private void moveToLastPage() {
        //Click Next button until the last page displays
        while (!isLastPage()) {
            click(findElement(next_btn));
        }
    }

    private void fillAndAddToCart_productAccessories() {
        if (!checkElementPresent(required_field))
            click(findElement(add_to_cart_btn_popup));
        //To be defined for Required case
    }

}
