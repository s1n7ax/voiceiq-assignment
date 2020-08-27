package seleniumq.automation.core.runtime.helper.exceptions;

public class InvalideBrowserType extends Exception{
	private static final long serialVersionUID = 1L;
	
	private String message = "Invalid browser type";
	
	public InvalideBrowserType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
