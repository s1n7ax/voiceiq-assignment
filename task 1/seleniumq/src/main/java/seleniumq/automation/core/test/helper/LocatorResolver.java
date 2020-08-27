package seleniumq.automation.core.test.helper;


public class LocatorResolver {
	static String paramFinderKeyword = "<>";
	static String paramSplitKeyword = "<,>";
	
	/*
	 * use the PARAM:user_value,PARAM:user_value2 syntax to pass the locator parameters
	 */
	public static String resolveParameters(String locator, String parameters) {
		// parameters will be split by paramSplitKeyword 
		String[] paramArr = parameters.split(paramSplitKeyword);
		
		locator = locator.replace(paramFinderKeyword, "%s");
		return String.format(locator, (Object[])paramArr);
	}
}
