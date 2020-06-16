package common;

import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

import static common.BrowserFactory.*;

public class CommonFunctions {

    public static final int timeInSeconds = 10;

    //Declare and set value for global variables
    public static String g_browserName;
    public static String g_url;

    public static void setGlobalVariables(String browser, String url) {
        g_browserName = browser;
        g_url = url;
    }

    //Navigate to the Home page
    public static void navigateToHomePage() {
        //Check the exist of the driver, if no, start a new one
        if (driver == null)
            launchBrowser(g_browserName);
        visit(g_url);
    }

    //Create basic Web Element functions
    public static WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public static List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public static void click(WebElement ele) {
        waitForElementExist(ele);
        Actions actions = new Actions(driver);
        actions.click(ele).perform();
    }

    public static void waitForElementExist(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    public static void fill(WebElement ele, String text) {
        Actions actions = new Actions(driver);
        actions.sendKeys(ele, text).perform();
    }

    public static boolean checkElementPresent(By by) {
        try {
            return driver.findElements(by).size() > 0;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    //Create basic verify functions
    @Step("Verify the page title")
    public static boolean verifyPageTitle(String expectedTitle) {
        return driver.getTitle().matches(expectedTitle);
    }

    public static void takeScreenShot(String filePath) throws Exception {
        File screenFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = System.currentTimeMillis() / 1000 + "";
        File destFile = new File("./src/test/java/reports/screenshot/" + filePath + "-" + timeStamp + ".png");
        FileUtils.copyFile(screenFile, destFile);
    }
}
