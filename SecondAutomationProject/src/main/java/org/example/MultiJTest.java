package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MultiJTest {
    static WebDriver driver;

    public static void clickOnElement(By by){
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text){
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by){
        return driver.findElement(by).getText();
    }

    public static String getCurrentTimeStamp(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmss");
        return sdf.format(date);
    }

//    public static void waitForClickable(By by, int timeInSeconds){
//        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
//        wait.until(ExpectedConditions.elementToBeClickable(by));
//    }
//
//    public static void waitForVisible(By by, int timeInSeconds){
//        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
//    }

    @BeforeMethod
    public static void openBrowser(){
        System.setProperty("webdriver.chrome.driver","src/test/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

//    @AfterMethod
    public static void  closeBrowser(){
        driver.close();
    }
    //Hovering on the menu and click on submenu item
    public static void hoverOnElement(By by){
        //Locating the parent menu
        WebElement element = driver.findElement(by);
        Actions actions = new Actions(driver);
        //Hovering on main menu
        actions.moveToElement(element);

    }

    // To verify your is able to register successfully
    @Test
    public static void verifyUserShouldBeAbleToRegisterSuccessfully(){
        //Click on register link
        clickOnElement(By.linkText("Register"));
        //Check if the user is in register page
        Assert.assertTrue(driver.getCurrentUrl().contains("register"));

        //Click on male radio button
        clickOnElement(By.id("gender-male"));
        //Enter first name
        typeText(By.id("FirstName"), "aaaaa");
        //Enter last name
        typeText(By.id("LastName"),"bbb");
        //Select Day of birth
        Select selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
        selectDay.selectByValue("5");
        //Select Month of birth
        Select selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        selectMonth.selectByVisibleText("March");
        //Select year
        Select selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
        selectYear.selectByVisibleText("2000");
        //Enter email
        String email = "aaaa+" + getCurrentTimeStamp() + "@gmail.com";
        typeText(By.id("Email"),email);
        //Deselect newsletter checkbox
        clickOnElement(By.id("Newsletter"));
        //Enter password
        typeText(By.id("Password"), "aaaa1111");
        //Enter confirm password
        typeText(By.id("ConfirmPassword"),"aaaa1111");
        //Click on register button
//        waitForClickable(By.id("register-button"),20);
        clickOnElement(By.id("register-button"));

        //Check if the test case passed
        String expectedResult = "Your registration completed";
//        waitForVisible(By.xpath("//div[@class=\"result\"]"),20);
        String actualResult = getTextFromElement(By.xpath("//div[@class=\"result\"]"));

        Assert.assertEquals(actualResult, expectedResult, "Registration Error: Registration failed!!!");

    }

    //To verify user is able navigate to desktop page
//    @Test
//    public  static void verifyUserIsAbleToNavigateToTheDesktopPage(){
////        Locating the  main menu "Desktop"
//        hoverOnElement(By.xpath("//ul[@class=\"top-menu notmobile\"]/li[1]/a[@href=\"/computers\"]"));
////        Location the sub-menu item
//        hoverOnElement(By.xpath(""));
//    }

    @Test
    public static void verifyUserIsAbleToPostAComment(){
        //Click on Details button
        clickOnElement(By.xpath("//div[@class=\"buttons\"]/a[@href=\"/nopcommerce-new-release\"]"));
        //Enter Title
        typeText(By.id("AddNewComment_CommentTitle"),"New Comment");
        //Enter Comment
        typeText(By.id("AddNewComment_CommentText"), "Comment Text");
        //Click on button
        clickOnElement(By.name("add-comment"));

        String actualResult = getTextFromElement(By.xpath("//div[@class=\"result\"]")) ;
        String expectedResult = "News comment is successfully added.";

        Assert.assertEquals(actualResult,expectedResult, "Comment error: error in posting the comment");
    }

}
