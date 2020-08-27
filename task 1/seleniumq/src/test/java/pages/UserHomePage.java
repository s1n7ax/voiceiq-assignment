package pages;

import common.Retry;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UserHomePage {
    WebDriver driver;

    @FindBy(css = ".sites-list > div .deleteSiteBtn")
    List<WebElement> btn_deleteSiteIconList;

    @FindBy(css = ".sites-list > div .deleteSiteBtn")
    WebElement btn_deleteSiteIcon;

    @FindBy(id = "delete-input")
    WebElement txt_deleteConformation;

    @FindBy(id = "delete-button")
    WebElement btn_deleteSite;

    @FindBy(linkText = "+ Add New Site")
    WebElement btn_addNewSite;

    public UserHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void deleteExistingProjects() throws InterruptedException {
        int len = this.btn_deleteSiteIconList.size();

        for(int i = 0; i < len; i++) {
            Retry.HandleClickInterceptedException(() -> {
                this.btn_deleteSiteIcon.click();
                return null;
            });

            this.txt_deleteConformation.sendKeys("DELETE");
            this.btn_deleteSite.click();
        }
    }

    public void addNewSite() throws InterruptedException {
        Retry.handleWebDriverException(() -> {
            this.btn_addNewSite.click();
            return null;
        });
    }
}
