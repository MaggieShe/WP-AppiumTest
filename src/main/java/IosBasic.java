package main.java;

import com.google.common.io.Files;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class IosBasic {

    public IOSDriver driver;
    public String validEmail = "maggie.she@wetech.io";
    public String validPassword = "WPAdmin1";
    public String invalidEmail1 = "invalid@wetech.io";
    public String invalidEmail2 = "invalid";
    public String randomPassword = "123alkjogEn";
    public String outputDirectory = "./outputs/";
    public DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    //    private ScreenRecorder screenRecorder;
    public String proxy = "intercept"; // false for no proxy, or specify your own with http://proxy-example.com:port

    @Before
    public void setUp() throws IOException, InterruptedException {
        new File(outputDirectory).mkdir();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 7");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "/Users/maggie/git/WP-AppiumTest/demoApp/WhizzyPay.app");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");

        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        System.out.println("session started");

        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("ending session");
        driver.quit();
    }

    @Test
    public void testA_positiveFlow() throws IOException, InterruptedException {
        logValidEmail();
        nav_TranList_TranDetail();
        nav_TranDetail_TranList();
        openDrawer();
        open_About();
        about_To_TranList();
        logOut();
    }

    public void logValidEmail() throws IOException, InterruptedException {

        /*  Given I am a new app user
            And I am a merchant admin/merchant user of only one merchant
            When I enter the correct email and password combination on the [login] page
            Then I expect to login to the system
            And the transaction history page is displayed */

        System.out.println("tapping login element");

        WebElement emailTextField = (new WebDriverWait(driver, 60))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("Email")));
        emailTextField.sendKeys(validEmail);
        WebElement passwordTextField = driver.findElement(By.id("Password"));
        passwordTextField.sendKeys(validPassword);

        Thread.sleep(2000);
        takeScreenshot(driver);

        WebElement loginButton = driver.findElement(By.id("LOGIN"));
        loginButton.click();

        System.out.println("Successfully Login");
        Thread.sleep(2000);
        takeScreenshot(driver);

    }

    public void nav_TranList_TranDetail() throws IOException, InterruptedException {
        /*  Given I am on the [Transaction History] page
            When I click a payment
            Then I expect to navigate to the [Transaction Details] page */

        System.out.println("tapping a payment");
        WebElement transaction = driver.findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'T'"));
        transaction.click();
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

             /*  Given I am on the [Transaction Details] page
            When I pull down the [Transaction Details] page
            Then I expect to see the Refreshing loading spinner on the top of the page */

//    public void refresh_TranDetail() throws IOException, InterruptedException {
//        System.out.println("Pull to refresh");
//        WebElement pull_to_refresh = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"\\"])[2]"));
//        TouchAction pull_down = new TouchAction(driver);
//        pull_down.tap(pull_to_refresh,10, 50).perform();
//        Thread.sleep(2000);
//        takeScreenshot(driver);
//    }
        /*  Given I am on the [Transaction Details] page
            When I click the back arrow button on the top-left corner of the page
            Then I expect to navigate to the [Transaction History] page */

        public void nav_TranDetail_TranList() throws IOException, InterruptedException {
        System.out.println("tapping a backArrow on Transaction Detail page");
        WebElement transactionList = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"\uF060\"])[2]"));
        transactionList.click();
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    public void openDrawer() throws IOException, InterruptedException {

        /*  Give I have logged on to the App
            When I click the hamburger menu on the top left corner
            Then I expect to see the [Menu] List */

        System.out.println("tapping the hamburger menu");
//        TouchAction hamburger = new TouchAction(driver);
//        hamburger.tap(10, 50).perform();
        WebElement drawer = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"\uF0C9\"])[2]"));
        drawer.click();
//        Thread.sleep(2000);
        takeScreenshot(driver);

    }

    public void open_About() throws IOException, InterruptedException {

        /*  Given I am on the [Menu List] page
            When I click the About button on the [Menu List]
            Then I expect to navigate to the [About] page */

        System.out.println("Navigate to About page");
        WebElement about = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"\uF311 About\"])[2]"));
        about.click();
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    public void about_To_TranList() throws IOException, InterruptedException {

        /*  Give I am on the [About] page
            When I click the hamburger menu on the top left corner
            And I click the Transactions button on the [Menu List]
            Then I expect to navigate to the [Transaction History] page */

        System.out.println("Navigate to Transaction History");
        TouchAction hamburger = new TouchAction(driver);
        hamburger.tap(10, 50).perform();
        Thread.sleep(2000);
        takeScreenshot(driver);
        WebElement transactionMenu = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"\uF389 Transactions\"])[1]"));
        transactionMenu.click();
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    public void logOut() throws IOException, InterruptedException {

         /* Give I am on the [Transaction History] page
            When I click the hamburger menu on the top left corner
            And I click the Log out button on the [Menu List]
            Then I expect to see a log out confirm popup modal*/

        openDrawer();
        System.out.println("Log out");
        WebElement logOut = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"\uF359 Log Out\"])[1]"));
        logOut.click();
        Thread.sleep(2000);
        takeScreenshot(driver);

         /* Give I am on the logOut confirm popup modal
            When I click the <button> on the popup modal
            Then I expect to see the <result>
            |button|result|
            |cancel|dismiss the popup modal|
            |ok|log out from the app| */

        System.out.println("cancel log out ");
        TouchAction logoutPopup = new TouchAction(driver);
        logoutPopup.tap(118, 375).perform();
        Thread.sleep(2000);
        takeScreenshot(driver);
        logOut.click();
        System.out.println("confirm log out ");
        logoutPopup.tap(257, 375).perform();
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    @Test
    public void testB_exception_Flow() throws IOException, InterruptedException {
        login_invalidEmail();
        login_invalidEmail2();
        login_emptyEmail();
    }

    public void login_invalidEmail() throws IOException, InterruptedException {

        /*  Given I am a merchant admin/merchant user of only one merchant
            When I enter the incorrect email and password on the [login] page
            Then I expect to see a popup modal with the following message
            And the transaction history page is displayed
            And I click the ok button to dismiss the popup */

        System.out.println("Login with invalid email1");
        WebElement emailTextField = (new WebDriverWait(driver, 60))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("Email")));
        emailTextField.sendKeys(invalidEmail1);
        WebElement passwordTextField = driver.findElement(By.id("Password"));
        passwordTextField.sendKeys(randomPassword);
        Thread.sleep(2000);
        takeScreenshot(driver);

        WebElement loginButton = driver.findElement(By.id("LOGIN"));
        loginButton.click();

        System.out.println("Login Failed");
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    public void login_invalidEmail2() throws IOException, InterruptedException {

        System.out.println("Login with invalid email2");
        WebElement emailTextField = (new WebDriverWait(driver, 60))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("Email")));
        emailTextField.sendKeys(invalidEmail2);
        WebElement passwordTextField = driver.findElement(By.id("Password"));
        passwordTextField.sendKeys(randomPassword);
        Thread.sleep(2000);
        takeScreenshot(driver);
        WebElement loginButton = driver.findElement(By.id("LOGIN"));
        loginButton.click();
        System.out.println("Login Failed");
        Thread.sleep(2000);
        takeScreenshot(driver);
        TouchAction confirmLoginFail = new TouchAction(driver);
        confirmLoginFail.tap(380, 606).perform();
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    public void login_emptyEmail() throws IOException, InterruptedException {
        /*Enter empty email*/
        System.out.println("Login with empty email");
        WebElement emailTextField = (new WebDriverWait(driver, 60))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("Email")));
        emailTextField.sendKeys("");
        WebElement passwordTextField = driver.findElement(By.id("Password"));
        passwordTextField.sendKeys("");
        Thread.sleep(2000);
        takeScreenshot(driver);
        WebElement loginButton = driver.findElement(By.id("LOGIN"));
        loginButton.click();
        System.out.println("Login Failed");
        Thread.sleep(2000);
        takeScreenshot(driver);
        TouchAction confirmLoginFail = new TouchAction(driver);
        confirmLoginFail.tap(380, 606).perform();
        Thread.sleep(2000);
        takeScreenshot(driver);
    }

    @Test
    public void testC_HorizontalScreen() throws IOException, InterruptedException {
        /* Rotate the screen to horizontal*/
        driver.rotate(ScreenOrientation.LANDSCAPE);
        takeScreenshot(driver);
        logValidEmail();
        nav_TranList_TranDetail();
        openDrawer();
        open_About();
        about_To_TranList();
        logOut();
    }

    public void takeScreenshot(IOSDriver driver) throws IOException {
        System.out.println("take screenshot");
        String fileName = "WhizzyPay-" + MobileCapabilityType.PLATFORM_NAME + "-" + MobileCapabilityType.DEVICE_NAME +
                "-screenshot-" + System.currentTimeMillis() + ".png";
        File destination = new File(outputDirectory, fileName);
        File screenShot = driver.getScreenshotAs(OutputType.FILE);
        Files.move(screenShot, destination);
    }
}
