package currency.calculator.app.exception;

import currency.calculator.app.constant.CurrencyCalculatorConstant;


/**
 * This class contains all the logic for generating exception response to be sent back to user/application
 * @Operations: generateException
 * @Developer: Singh, Sunny
 */
public final class ExceptionHandler {
	
	/**
	 * The private constructor for not to allow creation of object for this class
	 */
	private ExceptionHandler() {		
	}
	
	/**
	 * This class contains all the logic for generating exception response to be sent
	 * @Input: message, detailedMessage, system
	 * @Output: CurrencyException
	 * @Exception: none
	 */
	public static CurrencyException generateException(String message, String detailedMessage, String system){
		CurrencyException exception = new CurrencyException(system, system, system);
		exception.setMessage(message);
		exception.setSystem(system);
		exception.setDetailedMessage(detailedMessage);
		return exception;
	}
	/**
	 * This class contains all the logic for generating exception response to be sent
	 * @Input: Exception
	 * @Output: CurrencyException
	 * @Exception: none
	 */
	 public static CurrencyException generateException(Exception ex) {
		 	CurrencyException exception = new CurrencyException(CurrencyCalculatorConstant.EXCEPTION_GENERIC_MESSAGE, ex.getMessage(), CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
	        return exception;
	    }

}
