package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver ;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements (driver,this);
    }

    @FindBy(xpath = "//input[@id=':r1:']")
    WebElement userNameField;
    //*[@id=":r1:"]

    @FindBy(xpath = "//input[@type='password']")
    WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btn;

    public void setUserNameField(String userName){
        userNameField.sendKeys(userName);
    }

    public void setPassword(String password){
        passwordField.sendKeys(password);
    }
    public void clickBtn(){
        btn.click();
    }

}
