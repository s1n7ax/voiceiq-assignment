package seleniumq.automation.core.test.helper.data_provider;

import java.io.IOException;

import javax.xml.bind.PropertyException;

public interface UserDataProviderIntf {
	public Object[][] getProvider(String fileName, String sheetName) throws IOException, PropertyException;
}
