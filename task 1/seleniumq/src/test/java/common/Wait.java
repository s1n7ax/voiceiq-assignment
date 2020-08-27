package common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {
    public static void waitUntilPageLoad(WebDriver driver, long timeout) {
        System.out.println(String.format("waiting until page is loaded", timeout));
        new WebDriverWait(driver, timeout).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        System.out.println(String.format("waiting until page is loaded - completed", timeout));
    }

    public static void sleep(long timeout) throws InterruptedException {
        Thread.sleep(timeout);
    }

    public static WebElement waitUntilVisibilityOfLocator(WebDriver driver, By locator, long timeInSec) {
        System.out.println(String.format("waiting until visibility of locator %s", locator.toString()));
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println(String.format("waiting until visibility of locator %s - completed", locator.toString()));
        return ele;
    }

    public static WebElement waitUntilVisibilityOfLocator(WebDriver driver, By locator) {
        return waitUntilVisibilityOfLocator(driver, locator, 10);
    }

    public static void waitAndSwitchToFrame(WebDriver driver, By locator) {
        waitAndSwitchToFrame(driver, locator, 10);
    }

    public static void waitAndSwitchToFrame(WebDriver driver, By locator, long timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }
}
