package pages;

import common.Retry;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    @FindBy(linkText = "Login")
    WebElement lnk_login;

    @FindBy(css = "[placeholder='Email address']")
    WebElement txt_email;

    @FindBy(css = "[placeholder='Password']")
    WebElement txt_password;

    @FindBy(css = "[type='Submit']")
    WebElement btn_submit;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) throws InterruptedException {
        Retry.handleWebDriverException(() -> {
            this.lnk_login.click();
            return null;
        });

        this.txt_email.sendKeys(email);
        this.txt_password.sendKeys(password);
        this.btn_submit.click();
    }
}
