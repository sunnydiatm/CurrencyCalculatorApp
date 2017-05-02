package currency.calculator.app.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import currency.calculator.app.constant.CurrencyCalculatorConstant;
import currency.calculator.app.exception.CurrencyException;

/**
 * This class contains operation to fetch property value and store it into a collection (MAP) in a singleton pattern.
 * @Operations: getFXPropValue
 * @Developer: Singh, Sunny 
 */

public final class LoadProperty {
	
	private static final Logger LOG = Logger.getLogger(LoadProperty.class.getName());
	
	private static HashMap<String, String> propInstance = null;
	private static String PROPERTY_FILE_NAME = "currency.properties";
	private File file = null;
	private Properties properties = null;
	private FileInputStream fileInput = null;
	
	/**
	 * The private constructor for not to allow creation of object for this class
	 * @throws CurrencyException 
	 */
	private LoadProperty() throws CurrencyException{
		try {
			propInstance = new HashMap<String, String>();
			file = new File(PROPERTY_FILE_NAME);
			properties = new Properties();
			fileInput = new FileInputStream(file);
			properties.load(fileInput);
			Set<String> propertyNames = properties.stringPropertyNames();
			if(LOG.isLoggable(Level.INFO)){
				LOG.info("Size of property MAP is : "+propertyNames.size());
			}
			for (String currencyProperty : propertyNames) {
				propInstance.put(currencyProperty, properties.getProperty(currencyProperty));
			}
		}catch (FileNotFoundException ex) {
			LOG.severe(" FileNotFoundException Exception occured while reading the file "+ex.getMessage());
			throw new CurrencyException("FileNotFoundException Exception occured while reading the file",ex.getMessage(),CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}catch (IOException ex) {
			LOG.severe("IOException Exception occured while reading the file "+ex.getMessage());
			throw new CurrencyException("IOException Exception occured while reading the file",ex.getMessage(),CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}finally{
			try {
				if(fileInput!=null){
					fileInput.close();
				}
			} catch (IOException ex) {
				LOG.severe("IOException Exception occured while closing the file "+ex.getMessage());
				throw new CurrencyException("IOException Exception occured while closing the file",ex.getMessage(),CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
			}
		}
	}
	/**
	 * The following operation returns map of property values to caller
	 * @Input - N/A
	 * @Response - HashMap<String, String>
	 * @Exception - CurrencyException
	 */
	public static HashMap<String, String> getFXPropValue() throws CurrencyException{
		
		/* Create new instance only when no existing instance exists. 
		 * This is just to make sure only one instance of this class exists throughout 
		 * the application life cycle to ensure singleton pattern.  
		*/
		
		 if(propInstance == null) {
			 	new LoadProperty();
	        }
	       return propInstance;
	    }
}
