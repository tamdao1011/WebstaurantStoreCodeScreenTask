package core;

import io.qameta.allure.Step;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static common.BrowserFactory.*;
import static common.CommonFunctions.*;

public class TestBase {

    @Parameters({"browser", "url"})
    @BeforeTest
    @Step("Launch the browser and navigate to the Home page")
    public void setup(String browser, String url) {
        setGlobalVariables(browser, url);
        launchBrowser(browser);
        navigateToHomePage();
    }

    @AfterMethod
    public void clear(ITestResult result) {
        if (result.isSuccess()) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @AfterTest
    @Step("Close the browser")
    public void tearDown() {
        quitDriver();
    }

}
