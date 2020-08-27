package pages;

import common.Retry;
import common.Wait;
import common.WebElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public class EditorPage {
    WebDriver driver;

    @FindBy(xpath = "//ws-background-container")
    WebElement ele_backgroundContainer;

    @FindBy(xpath = "//div[text()='Add block']/preceding-sibling::button")
    WebElement btn_addBlock;

    @FindBy(id = "iframe")
    WebElement btn_youtubeVidEditData;

    @FindBy(xpath = "//button[.='Embed URL']")
    WebElement tab_embedURL;

    @FindBy(xpath = "//span[.='Embed URL']/following-sibling::div//input")
    WebElement txt_EmbedURL;

    @FindBy(xpath = "//button[.='Submit']")
    WebElement btn_SubmitYoutubeVidData;

    @FindBy(xpath = "//button[.='Save']")
    WebElement btn_Save;

    @FindBy(xpath = "//ws-media-container/iframe[contains(@src, 'youtube')]")
    WebElement ele_youtubeVideoContainer;

    By lbl_SavedLoc = By.xpath("//*[@class='ws-navbar__right']//button//*[text()='Saved']");

    public EditorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void switchToEditorFrame() {
        this.driver.switchTo().defaultContent();

        Wait.waitAndSwitchToFrame(driver, By.cssSelector("#editor > iframe"));
        Wait.waitUntilPageLoad(this.driver, 30000);
        System.out.println("switched to editor frame");

    }

    public void switchToPreviewFrame() {
        switchToEditorFrame();

        Wait.waitAndSwitchToFrame(driver, By.cssSelector("[id^='syncy-frame-instance']"));
        Wait.waitUntilPageLoad(this.driver, 30000);
        System.out.println("switched to preview frame");
    }

    public void switchToBlockFrame() {
        switchToEditorFrame();

        Wait.waitAndSwitchToFrame(driver, By.id("block-sanbox"));
        Wait.waitUntilPageLoad(this.driver, 30000);
        System.out.println("switched to block frame");

    }

    public void switchToYoutubeVideoFrame() {
        switchToPreviewFrame();

        Wait.waitAndSwitchToFrame(driver, By.xpath("//iframe[contains(@src, 'youtube')]"), 60);
        Wait.waitUntilPageLoad(this.driver, 30000);
        System.out.println("switched to youtube video frame");

    }

    public void addContentToPage(String contentType, int nthTemplate, Map<String, String> data) throws InterruptedException {
        CommonPage commonPage = new CommonPage(driver);

        switchToEditorFrame();
        Wait.waitUntilVisibilityOfLocator(driver, lbl_SavedLoc, 120);

        switchToPreviewFrame();
        // mouse move to get the add block button
        Actions builder = new Actions(driver);
        builder
                .moveToElement(this.ele_backgroundContainer, 50, 50)
                .pause(1000)
                .moveToElement(this.ele_backgroundContainer)
                .build()
                .perform();

        switchToEditorFrame();
        // click on add block button
        By contentTypeLoc = WebElements.resolveX("//ul[@class='ws-tag-list__list']/li[text()='%s']", contentType);
        By templateLoc = WebElements.resolveX("//span[text()='Video']/following-sibling::div/div[%d]", nthTemplate);

        Retry.handleWebDriverException(() -> {
            this.btn_addBlock.click();
            return null;
        });
        driver.findElement(contentTypeLoc).click();

        switchToBlockFrame();
        Wait.waitUntilVisibilityOfLocator(driver, templateLoc).click();

        switchToEditorFrame();
        commonPage.waitUntilLoadingAnimationToDisappear(60);

        switchToPreviewFrame();
        builder = new Actions(driver);
        builder
                .moveToElement(ele_youtubeVideoContainer, 50, 50)
                .pause(1000)
                .moveToElement(this.ele_backgroundContainer)
                .build()
                .perform();

        switchToEditorFrame();
        this.btn_youtubeVidEditData.click();
        this.tab_embedURL.click();
        this.txt_EmbedURL.clear();
        this.txt_EmbedURL.sendKeys(data.get("url"));
        this.btn_SubmitYoutubeVidData.click();
        this.btn_Save.click();
    }
}
