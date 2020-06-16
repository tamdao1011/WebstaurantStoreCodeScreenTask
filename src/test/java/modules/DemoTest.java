package modules;

import core.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static pages.CartPage.emptyCart;
import static pages.CartPage.verifyCartIsEmpty;
import static pages.HomePage.searchValue;
import static pages.SearchPage.*;

public class DemoTest extends TestBase {

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
    }

}
