package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.time.Duration;


public class VWO  {

    // 2 Test Cases
    // Negative -> Invalid username & password -> Error Message
    // Positive -> valid username & password -> Dashboard Page
    ChromeOptions options;
    WebDriver driver;

    @BeforeSuite
    public void setup(){
        options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);

    }

    @Test(enabled = true,priority = 1,groups = {"positive","sanity"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify with Invalid username & password , Login is not successful!!")
    public void testInvalidLogin() throws InterruptedException {

        driver.get("https://app.vwo.com/#/login");
        driver.findElement(By.id("login-username")).sendKeys("seemen5113@gmail.com");
        driver.findElement(By.id("login-password")).sendKeys("Ragul@7");
        driver.findElement(By.id("js-login-btn")).click();


// Temporary Wait Making JVM to Wait
        //   Thread.sleep(2000);

// Fluent Wait

//        Wait<WebDriver> wait = new FluentWait<>(driver)
//                .withTimeout(Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofSeconds(5))
//                .ignoring(NoSuchElementException.class);
//
//        WebElement errorMessage = wait.until(new Function<WebDriver, WebElement>() {
//            @Override
//            public WebElement apply(WebDriver webDriver) {
//                WebElement element = driver.findElement(By.className("notification-box-description"));
//                return element.getText().isEmpty() ? null : element;
//            }
//        });

// Explicit Wait
        WebElement errorMessage1 = driver.findElement(By.className("notification-box-description"));
        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(errorMessage1));


// Assertion (Actual result = Expected result)
        String errorstring = driver.findElement(By.className("notification-box-description")).getText();
        Assert.assertEquals(errorstring,"Your email, password, IP address or location did not match");

        driver.navigate().refresh();

    }

    @Test(enabled = true,priority = 2,groups = {"positive","sanity"})
    @Description("Verify with valid username & password , Login is  successful!!")
    public void testValidLogin() throws InterruptedException {


        driver.get("https://app.vwo.com/#/login");
        driver.findElement(By.id("login-username")).sendKeys("seemen511@gmail.com");
        driver.findElement(By.id("login-password")).sendKeys("Ragul@123");
        driver.findElement(By.id("js-login-btn")).click();

// Temporary Wait
        //     Thread.sleep(4000);
// Explicit Wait is Better
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.titleContains("VWO Setup"));

        // Below also we can try
//new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.page-heading")));


//Assert
        Assert.assertEquals(driver.getCurrentUrl(),"https://app.vwo.com/#/setup/account-setup"); // Check Using URL
        Assert.assertEquals(driver.getTitle(),"VWO Setup"); // Check Using Title
    }


    @AfterSuite
    public void TearDown(){
        driver.quit();

    }
}

