package seleniumq.automation.core.runtime.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	Properties prop = new Properties();
	InputStream input;
	
	public PropertyReader(String propertyFileName) throws IOException {
		input = new FileInputStream(new File(propertyFileName));
		prop.load(input);
	}
	
	public String getProperty(String key) {
		return this.prop.getProperty(key);
	}
}
