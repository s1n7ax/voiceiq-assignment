package common;

import org.openqa.selenium.*;

public class Retry {
    public static WebElement HandleClickInterceptedException(WebElementRetryFunction func, int retryCount, int interval) throws InterruptedException {
        int i = 0;

        while (i < retryCount) {
            try {
                return func.run();
            } catch (ElementClickInterceptedException ex) {
                i++;
                Thread.sleep(interval);
            }
        }

        throw new TimeoutException();
    }

    public static WebElement HandleClickInterceptedException(WebElementRetryFunction func) throws InterruptedException {
        return HandleClickInterceptedException(func, 10, 1000);
    }

    public static WebElement handleWebDriverException(WebElementRetryFunction func) throws InterruptedException {
        return handleWebDriverException(func, 10, 1000);
    }

    public static WebElement handleWebDriverException(WebElementRetryFunction func, int retryCount, int interval) throws InterruptedException {
        int i = 0;

        while (i < retryCount) {
            try {
                return func.run();
            } catch (WebDriverException ex) {
                System.out.println("retrying due to " + ex.getClass().getSimpleName());
                Thread.sleep(interval);
                i++;
            }
        }

        throw new TimeoutException();
    }
}
