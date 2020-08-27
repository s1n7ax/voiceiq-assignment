package pages;

import common.WebElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ThemePage {
    WebDriver driver;

    @FindBy(xpath = "//*[text()='Use this template']")
    WebElement btn_useThisTemplate;

    @FindBy(css = "[id^='prompt-field-id']")
    WebElement txt_websiteName;

    @FindBy(xpath = "//*[text()='Continue']")
    WebElement btn_continue;

    public ThemePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectTheme(String browseBy, String themeName, String websiteName) {
        By browseByLoc = WebElements.resolveX("//*[text()='Browse by']/following-sibling::div[text()='%s']", browseBy);
        By themeLoc = WebElements.resolveX("//*[text()='%s']/parent::div/preceding-sibling::a", themeName);

        this.driver.findElement(browseByLoc).click();
        this.driver.findElement(themeLoc).click();
        this.btn_useThisTemplate.click();
        this.txt_websiteName.sendKeys(websiteName);
        this.btn_continue.click();
    }
}
