package seleniumq.automation.core.runtime.helper.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.ITestResult;

public class TestCaseSummaryModel {
	private String name;
	private int status;
	private String browser;
	private long startTime;
	private long endTime;
	
	private static String dateFormatString = "dd, MMM, yyyy hh:mm:ss a";

	public TestCaseSummaryModel(String name, int status, String browser, long startTime, long endTime) {
		this.name = name;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public TestCaseSummaryModel(ITestResult result, String browser) {
		name = result.getName();
		status = result.getStatus();
		this.browser = browser;
		startTime = result.getStartMillis();
		endTime = result.getEndMillis();
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		if (status == 2)
			return "Failed";
		else if (status == 1)
			return "Passed";
		
		return null;
	}

	public String getBrowser() {
		return browser;
	}
	
	public String getStartTime() {
		return TestCaseSummaryModel.formatMillisecond(startTime);
	}

	public String getEndTime() {
		return TestCaseSummaryModel.formatMillisecond(endTime);
	}
	
	public String getDuration() {
		long duration = endTime - startTime;
		
		return getMinSecStringFromLong(duration);
	}

	public long getDurationInMils() {
		return (endTime - startTime);
	}

	private static String formatMillisecond(long mils) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mils);

		SimpleDateFormat format = new SimpleDateFormat(dateFormatString);
		return format.format(calendar.getTime());
	}
	
	public static String getMinSecStringFromLong(long time) {
		long minutes = (time / 1000)  / 60;
		long seconds = (time / 1000) % 60;
		
		return String.valueOf(minutes + " mins, " + seconds + " seconds");
	}
}
