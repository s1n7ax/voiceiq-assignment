package seleniumq.automation.core.runtime.helper;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import seleniumq.automation.core.runtime.helper.exceptions.InvalideBrowserType;

public class DriverHandler {
	
	public static WebDriver getNewDriver(String browser, Map<String, String> driverPath) throws InvalideBrowserType, IOException {
		WebDriver driver;

		if ("chrome".equals(browser.toLowerCase())) {
			System.setProperty("webdriver.chrome.driver", driverPath.get("chrome"));
			driver = new ChromeDriver();
		} else if ("firefox".equals(browser.toLowerCase())) {
			System.setProperty("webdriver.gecko.driver", driverPath.get("firefox"));
			driver = new FirefoxDriver();
		} else if ("ie".equals(browser.toLowerCase())) {
			System.setProperty("webdriver.ie.driver", driverPath.get("ie"));
			driver = new InternetExplorerDriver();
		} else {
			throw new InvalideBrowserType("Invalid browser type " + browser);
		}

		driver.manage().window().maximize();
		return driver;
	}
}
