package TestCases;

import Pages.LoginPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;


    @BeforeMethod
    public void openPage(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:3000/");
    }

    @DataProvider(name = "loginData")
    public String[][] loginDataProvider (){
        String[][] data = {
                {"user@gmail.com","user123","valid"},
                {"abc@gmail.com","abc","Invalid"},
                {"user@gmail.com","test","Invalid"},
                {"thandu@gmail.com","user","Invalid"}
        };

        return data;
    }

    @Test(dataProvider = "loginData")
    public void login(String uname,String pwd,String expValidation) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserNameField(uname);
        loginPage.setPassword(pwd);
        loginPage.clickBtn();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


        if (expValidation.equals("valid")) {
            // Check for alert presence (if any)
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Unexpected Alert Message: " + alertText);
                alert.accept(); // Close the unexpected alert
                Assert.fail("Unexpected alert appeared for valid login.");
            } catch (Exception e) {
                // No alert, continue
            }

            // check URL after handling the alert
            boolean urlVerification = driver.getCurrentUrl().contains("dashboard");
            Assert.assertTrue(urlVerification, "Expected Login Success but was not redirected to dashboard.");
        } else {
            try {
                // Wait for the alert to appear
                wait.until(ExpectedConditions.alertIsPresent());

                // Handle Alert
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert Message: " + alertText);

                // Validate Alert Message
                Assert.assertTrue(alertText.toLowerCase().contains("invalid"), "Alert message does not match expected text.");

                alert.accept(); // Close the alert
            } catch (Exception e) {
                Assert.fail("Expected alert for invalid login, but no alert appeared.");
            }
        }
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}



