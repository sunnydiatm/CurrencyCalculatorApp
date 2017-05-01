package currency.calculator.app;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import currency.calculator.app.constant.CurrencyCalculatorConstant;
import currency.calculator.app.exception.CurrencyException;
import currency.calculator.app.exception.ExceptionHandler;
import currency.calculator.app.util.CurrencyCalculatorUtil;

/**
 * This class contains main methods which takes in argument from command prompt and calculate currency details with the specified rate.
 * @Operations: main
 * @Developer: Singh, Sunny 
 */
public class CurrencyCalculatorMainApp {
	
	private static final Logger LOG = Logger.getLogger(CurrencyCalculatorMainApp.class.getName());
	 /**
	  * Entry point for this application. When user specified input in correct format, this method calculates the rates in intended currency. 
	  * @Input - args
	  * @Response - only print the details to user.
	  * @Exception - CurrencyException
	*/
    public static void main( String[] argss ) throws CurrencyException{
    	//used for testing purpose
    	String[] args = { "AUD", "ASD", "in", "JPY" };
    	 if(LOG.isLoggable(Level.INFO)){
    		 LOG.info("App->main()-> Input String length is : "+args.length);
    	 }
    	 /* 
    	  * Check if the input provided by user is correct and in specified format 
    	  * The correct format is : <ccy1> <amount1> in <ccy2> i.e AUD 100.00 in USD
    	*/
    	 if(args.length > 0 && args.length == CurrencyCalculatorConstant.INPUT_ARGUMENT_LENGTH 
    			 			&& args[0]!= null && !args[0].equals("") 
    			 			&& args[1]!= null && !args[1].equals("")
    			 			&& NumberUtils.isNumber(args[1].replaceAll(",", ""))
    			 			&& args[2]!= null && !args[2].equals("")
    			 			&& args[3]!= null && !args[3].equals("")){
    		if(CurrencyCalculatorUtil.isCurrencyAvailable(args[0].toUpperCase()) && CurrencyCalculatorUtil.isCurrencyAvailable(args[3].toUpperCase())){
		    	try{
		    		/*
		    		 * Initializing BigDecimal variable to zero which will be used for storing currency conversion value. 
		    		*/
	    			BigDecimal result =  BigDecimal.ZERO;
	    			/*
	    			 * Calculate the rate of given currency to the intended currency. 
	    			 * Just to make sure currency input is provided in upper case so that back end system does not fail. 
	    			 * */
		    		result = CurrencyCalculatorUtil.calculateCurrencyValue(args[0].toUpperCase(), args[1], args[3].toUpperCase());
		    		/*
		    		 * Display back the calculated currency value to user. 
		    		*/
		    		System.out.println((args[0]+" "+args[1]+" = "+args[3]+" "+result));
		    	}catch(CurrencyException ex){
		    		throw ExceptionHandler.generateException(ex.getMessage(), ex.getDetailedMessage(), ex.getSystem());
		    	}catch(Exception ex){
		    		throw ExceptionHandler.generateException(ex);
		    	}
    		}else {
    			/* 
    	    	  * Currency provided by user if does not exists then prompt this message to user console
    	    	*/
    			System.out.println(("Unable to find rate for "+args[0]+"/"+args[3]));
    		}
    	}else {
    		/*
    		 * Display back the error message to user if input provided does not match to the expected input. 
    		*/
    		System.out.println(("Invalid input arguments provided. Please provide input only in format : <ccy1> <amount1> in <ccy2>"));
    	}
    }
}
