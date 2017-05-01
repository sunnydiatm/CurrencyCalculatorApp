package currency.calculator.app.exception;

/**
 * This class is custom exception class for Currency Calculator Application
 * @Developer: Singh, Sunny
 */

public class CurrencyException extends Exception{

	private static final long serialVersionUID = 1845654979975484082L;

	private String message;
	private String detailedMessage;
	private String system;
	
	public CurrencyException(){
		
	}
	
	public CurrencyException(String message, String detailedMessage,String system) {
		super();
		this.message = message;
		this.detailedMessage = detailedMessage;
		this.system = system;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetailedMessage() {
		return detailedMessage;
	}
	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
}
