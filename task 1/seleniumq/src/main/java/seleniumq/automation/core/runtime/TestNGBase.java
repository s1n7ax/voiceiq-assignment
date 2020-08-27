package seleniumq.automation.core.runtime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import seleniumq.automation.core.runtime.helper.DriverHandler;
import seleniumq.automation.core.runtime.helper.PropertyReader;
import seleniumq.automation.core.runtime.helper.exceptions.InvalideBrowserType;
import seleniumq.automation.core.runtime.helper.logger.TestCaseSummaryModel;

/**
 * this class will defines common TestNg base excluding @Test which will be
 * defined the QA
 * 
 * @author srinesh
 *
 */
public abstract class TestNGBase {
	private String url;
	private int implecitTimeout;
	private String defaultBrowser;

	// stores all the test case summary
	private List<TestCaseSummaryModel> summary = Collections.synchronizedList(new ArrayList<TestCaseSummaryModel>());

	// drivers
	private Map<String, String> driverPaths = new HashMap<String, String>();

	// WebDriver driver thread local;
	private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();

	public TestNGBase() throws IOException {
		// get runtime properties
		PropertyReader prop = new PropertyReader("runtime.properties");

		// get runtime properties
		implecitTimeout = Integer.parseInt(prop.getProperty("IMPLECIT_WAIT_TIME"));
		url = prop.getProperty("APPLICATION_URL");
		defaultBrowser = prop.getProperty("DEFAULT_BROWSER");

		// read driver paths
		String chromeDriverPath = prop.getProperty("CHROME_DRIVER_PATH");
		String firefoxDriverPath = prop.getProperty("FIREFOX_DRIVER_PATH");
		String ieDriverPath = prop.getProperty("IE_DRIVER_PATH");

		// setting driver paths
		driverPaths.put("chrome", chromeDriverPath);
		driverPaths.put("firefox", firefoxDriverPath);
		driverPaths.put("ie", ieDriverPath);
	}

	public static ThreadLocal<WebDriver> getThreadLocalDriver() {
		return threadLocalDriver;
	}

	/*
	 * before all tests in the suite
	 */
	@BeforeSuite()
	public void beforeSuite() {
	}

	/*
	 * before the first test method
	 */
	@BeforeClass()
	public void beforeClass() {
	}

	/*
	 * before first test test method even before @BeforeClass
	 */
	@BeforeTest()
	public void beforeTest() {
	}

	
	/*
	 * before each and every test
	 */
	@Parameters("browser")
	@BeforeMethod()
	public synchronized void beforeMethod(@Optional("null") String browser) throws IOException, InvalideBrowserType {
		/*
		 *  this is used to provide the browser type from the runtime properties even 
		 * if the user doesn't use testng xml 
		 */
		if(browser == null)
			browser = defaultBrowser;
		
		initializeDriver(browser);
	}
	

	@AfterSuite()
	public void afterSuite() {
		System.out.println();
	}

	@AfterTest()
	public void afterTest() {
	}

	@AfterMethod()
	public synchronized void afterMethod(ITestResult testResult) {
		// closing the created driver
		getThreadLocalDriver().get().close();

		Capabilities cap = ((RemoteWebDriver) getThreadLocalDriver().get()).getCapabilities();
		String browser = cap.getBrowserName().toLowerCase();
		System.out.println(browser + " session closed");

		// store test case details
		TestCaseSummaryModel sumModel = new TestCaseSummaryModel(testResult, browser);
		summary.add(sumModel);
	}
	
	
	/*
	 * helper methods
	 */
	private void initializeDriver(String browser) throws InvalideBrowserType, IOException {
		// creates a new driver for the test suite in the thread
		getThreadLocalDriver().set(DriverHandler.getNewDriver(browser, driverPaths));

		// setting driver configuration
		getThreadLocalDriver().get().manage().timeouts().implicitlyWait(implecitTimeout, TimeUnit.SECONDS);
		getThreadLocalDriver().get().get(url);

		System.out.println(browser + " session created");
	}
}
