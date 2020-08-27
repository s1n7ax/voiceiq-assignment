package pages;

import common.Retry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonPage {
    WebDriver driver;

    By loadingAnimation = By.cssSelector(".ws-loader .ws-loader__inner");

    public CommonPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitUntilLoadingAnimationToDisappear(long timeInSec) throws InterruptedException {
        new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeInSec))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(WebDriverException.class)
                .withMessage(() -> {
                    int count = driver.findElements(loadingAnimation).size();
                    return String.format("retrying! there are %d elements in the DOM that match %d locator", loadingAnimation.toString());
                })
                .until((ele) -> {
                    return driver.findElements(loadingAnimation).size() == 0;
                });
    }

    public void waitUntilLoadingAnimationIsCompleted(long timeInSec) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        Retry.handleWebDriverException(() -> {
            wait.until(ExpectedConditions.presenceOfElementLocated(loadingAnimation));
            return null;
        });

        waitUntilLoadingAnimationToDisappear(timeInSec);
    }
}
