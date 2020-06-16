package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import static common.CommonFunctions.*;
import static pages.SearchPage.isNoResult;

public class CartPage {

    private static final By view_cart_btn_popup = By.xpath("//a[.='View Cart']");
    private static final By cart_btn = By.cssSelector("span.btn-primary");
    private static final By empty_cart_btn = By.cssSelector(".emptyCartButton");
    private static final By empty_cart_btn_popup = By.cssSelector(".btn-primary[type='button']");
    public static final By empty_cart_txt = By.cssSelector(".empty-cart__text p");

    @Step("Empty Cart")
    public static void emptyCart() {
        navigateToCart();
        if (!isNoResult && checkElementPresent(empty_cart_btn)) {
            click(findElement(empty_cart_btn));
            click(findElement(empty_cart_btn_popup));
        }
    }

    private static void navigateToCart() {
        if (checkElementPresent(view_cart_btn_popup))
            click(findElement(view_cart_btn_popup));
        else click(findElement(cart_btn));
    }

}
