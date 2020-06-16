package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static common.CommonFunctions.*;

public class HomePage {

    private static final WebElement SEARCH_TXTBOX = findElement(By.id("searchval"));
    private static final WebElement SEARCH_BTN = findElement(By.cssSelector(".banner-search-btn"));
    private static final String title = "WebstaurantStore: Restaurant Supplies & Foodservice Equipment";

    @Step("Search for item")
    public static void searchValue(String value){
        if (!checkHomePageExist())
            navigateToHomePage();
        fill(SEARCH_TXTBOX,value);
        click(SEARCH_BTN);
    }

    private static boolean checkHomePageExist() {
        return verifyPageTitle(title);
    }
}
