package currency.calculator.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.math.BigDecimal.ONE;
import currency.calculator.app.constant.CurrencyCalculatorConstant;
import currency.calculator.app.exception.CurrencyException;

/**
 * This class contains all methods which helps calculating exchange rate from property value
 * @Operations: calculateCurrencyValue, convertStringToBigDecimal, isCurrencyAvailable, getDecimalPlace, getFXRates, fetchCrossCurrencyValue, calculateRateFromCrossCurrency and calculateRateByCrossCurrency - utility/helper methods
 * @Developer: Singh, Sunny 
 */
public class CurrencyCalculatorUtil {
	
	private static final Logger LOG = Logger.getLogger(CurrencyCalculatorUtil.class.getName());
	
	/**
	 * The following operation calculate the  currency exchange rate received from user.
	 * @Input - sourceCurrency, inputAmount, destinationCurrency
	 * @Response - BigDecimal
	 * @Exception - CurrencyException
	 */
	public static BigDecimal calculateCurrencyValue(String sourceCurrency, String inputAmount, String destinationCurrency) throws CurrencyException{
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->calculateCurrencyValue()-> Enters to calculate rate ");
		}
		/*
		 * Initialization of BigDecimal variable with zero. This stores the final currency calculation value
		 */
		BigDecimal intendedCurrencyValue = BigDecimal.ZERO;
		/*
		 * Validating the input provided for this method
		 */
		if( sourceCurrency!=null && !sourceCurrency.equals("")
								 && inputAmount!=null && !inputAmount.equals("")
								 && destinationCurrency!=null && !destinationCurrency.equals("")){
		
			/*
			 * Retrieving cross Currency details for the input currency  
			 */
			String crossCurrency = fetchCrossCurrencyValue(sourceCurrency, destinationCurrency);
			/*
			 * Validating input request. Cross Currency must exist in the cross currency matrix table. 
			 */
			if(crossCurrency!=null && !crossCurrency.equals("")){
				/*
				 * Switch case statement for calculating the intended currency value depending upon the cross currency received 
				 */
				switch(crossCurrency){
					/*
					 * This case statement calculates the value of currency if the cross currency matches with "D" Direct feed.
					 */
				case CurrencyCalculatorConstant.CROSS_REF_KEY_DIRECT:
					intendedCurrencyValue = BigDecimal.valueOf(getFXRates(sourceCurrency+destinationCurrency));
					intendedCurrencyValue = intendedCurrencyValue.multiply(convertStringToBigDecimal(inputAmount));
					break;
					/*
					 * This case statement calculates the value of currency if the cross currency matches with "I" Inverted.
					 */	
				case CurrencyCalculatorConstant.CROSS_REF_KEY_INVERSION:
					intendedCurrencyValue = BigDecimal.valueOf(getFXRates(destinationCurrency+sourceCurrency));
					intendedCurrencyValue = ONE.divide(intendedCurrencyValue, CurrencyCalculatorConstant.GENERIC_DECIMAL_PLACE, RoundingMode.HALF_UP).multiply(convertStringToBigDecimal(inputAmount));
					break;
					/*
					 * This case statement calculates the value of currency if the cross currency matches with "U" Unity.
					 */	
				case CurrencyCalculatorConstant.CROSS_REF_KEY_UNITY:
					intendedCurrencyValue = convertStringToBigDecimal(inputAmount);
					break;
				default:
					if(LOG.isLoggable(Level.INFO)){
						LOG.info("CurrencyCalculatorUtil->calculateCurrencyValue()-> calling cross currency matrix ");
					}
					/*
					 * If the cross currency does not match in the case then it calls a function where the function fetches the details with the help of 
					 *  cross currency value.
					 */
					intendedCurrencyValue = calculateRateFromCrossCurrency(sourceCurrency, destinationCurrency, inputAmount, crossCurrency );
				}
			/*
			 * Validation of input request fails then throw CurrencyException with required information.
			*/		
			}else{
				LOG.severe("CurrencyCalculatorUtil->calculateCurrencyValue()->Nothing returned from currency matrix table ");
				throw new CurrencyException("Currency details are not found in currency matrix table","CurrencyCalculatorUtil->calculateCurrencyValue()->Nothing returned from currency matrix table",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
			}
		}else{
			LOG.severe("CurrencyCalculatorUtil->calculateCurrencyValue()-> Invalid input provided -> Null input amount provided , sourceCurrency : "+sourceCurrency+", inputAmount : "+inputAmount+", destinationCurrency : "+destinationCurrency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->calculateCurrencyValue()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->calculateCurrencyValue()-> Exiting with value "+intendedCurrencyValue);
		}
		/*
		 * Returning currency conversion value after calculation. The decimal place to which precision is required - is fetched against each intended currency from file. 
		 */
		return intendedCurrencyValue.setScale(getDecimalPlace(destinationCurrency), BigDecimal.ROUND_DOWN);
	}
	/**
	 * The following operation convert String To BigDecimal.
	 * @Input - inputAmount
	 * @Response - BigDecimal
	 * @Exception - CurrencyException
	 */
	public static BigDecimal convertStringToBigDecimal(String inputAmount)throws CurrencyException{
		BigDecimal result = BigDecimal.ZERO;
		/*
		 * Validating the input provided for this method
		 */
		if(inputAmount != null && !inputAmount.equals("")){
			result = new BigDecimal(inputAmount.replaceAll(",", ""));
		}else {
			LOG.severe("CurrencyCalculatorUtil->convertStringToBigDecimal()-> Invalid input provided -> Null input amount provided , Amount : "+inputAmount);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->convertStringToBigDecimal()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}
	      if(LOG.isLoggable(Level.INFO)){
	        	LOG.info("CurrencyCalculatorUtil->convertStringToBigDecimal()-> Exiting with value "+result);
	      }
	       return result;
	}
	
	/**
	 * The following operation returns decimal place against each currency provided.
	 * @Input - currency
	 * @Response - int
	 * @Exception - CurrencyException
	 */
	public static int getDecimalPlace(String currency)throws CurrencyException{
		int result = 0;
		/*
		 * Validating the input provided for this method
		 */
		if(currency != null && !currency.equals("")){
			/*
			 * Switch case statement for calculating the decimal places with the provided currency. 
			*/	
			switch(currency.trim()){
				case CurrencyCalculatorConstant.CURRENCY_AUD:
					result =  CurrencyCalculatorConstant.AUD_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_CAD:
					result =  CurrencyCalculatorConstant.CAD_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_CNY:
					result =  CurrencyCalculatorConstant.CNY_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_CZK:
					result =  CurrencyCalculatorConstant.CZK_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_DKK:
					result =  CurrencyCalculatorConstant.DKK_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_EUR:
					result =  CurrencyCalculatorConstant.EUR_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_GBP:
					result =  CurrencyCalculatorConstant.GBP_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_JPY:
					result =  CurrencyCalculatorConstant.JPY_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_NOK:
					result =  CurrencyCalculatorConstant.NOK_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_NZD:
					result =  CurrencyCalculatorConstant.NZD_DECIMAL_PLACE;
					break;
				case CurrencyCalculatorConstant.CURRENCY_USD:
					result =  CurrencyCalculatorConstant.USD_DECIMAL_PLACE;
					break;
				default:
					/*
					 * In case nothing matches with the property value this method returns with generic decimal place provided. If needed, can be specified as per requirement. 
					*/
					result = CurrencyCalculatorConstant.GENERIC_DECIMAL_PLACE ;
					if(LOG.isLoggable(Level.INFO)){
						LOG.info("Unable to find decimal place in table for "+currency);
					}
			}
		}else {
			LOG.severe("CurrencyCalculatorUtil->getDecimalPlace()-> Invalid input provided -> Null currency provided , Currency : "+currency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->getDecimalPlace()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->getDecimalPlace()-> Exiting with value "+result);
		}
		return result;
	}
	
	/**
	 * The following operation currency rate when currency details are provided
	 * @Input - inputCurrency
	 * @Response - double
	 * @Exception - CurrencyException
	 */
	public static double getFXRates(String inputCurrency) throws CurrencyException {
		double result = 0.0;
		/*
		 * Validating the input provided for this method
		 */
		if(inputCurrency != null && !inputCurrency.equals("")){
			/*
			 * Switch case statement for getting exchange rate. This takes in currency as a parameter.
			*/
			switch(inputCurrency.replaceAll("\\s+","")){
				case CurrencyCalculatorConstant.CURRENCY_AUDUSD :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_AUDUSD;
					break;
				case CurrencyCalculatorConstant.CURRENCY_CADUSD :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_CADUSD;
					break;
				case CurrencyCalculatorConstant.CURRENCY_USDCNY :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_USDCNY;
					break;
				case CurrencyCalculatorConstant.CURRENCY_EURUSD :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_EURUSD;
					break;
				case CurrencyCalculatorConstant.CURRENCY_GBPUSD :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_GBPUSD;
					break;
				case CurrencyCalculatorConstant.CURRENCY_NZDUSD :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_NZDUSD;
					break;
				case CurrencyCalculatorConstant.CURRENCY_USDJPY :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_USDJPY;
					break;
				case CurrencyCalculatorConstant.CURRENCY_EURCZK :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_EURCZK;
					break;
				case CurrencyCalculatorConstant.CURRENCY_EURDKK :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_EURDKK;
					break;
				case CurrencyCalculatorConstant.CURRENCY_EURNOK :
					result = CurrencyCalculatorConstant.CURRENCY_RATE_EURNOK;
					break;
				default:
					/*
					 * In case nothing matches with the property value this method returns exchange rate as zero. 
					*/
					result = 0.0;
					if(LOG.isLoggable(Level.INFO)){
						LOG.info("CurrencyCalculatorUtil->getFXRates()-> Unable to find rate for "+inputCurrency);
					}
			}
		}else{
			LOG.severe("CurrencyCalculatorUtil->getFXRates()-> Invalid input provided -> Null currency provided , Currency : "+inputCurrency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->getFXRates()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->getFXRates()-> Exiting with value "+result);
		}
		return result;
	}
	
	/**
	 * The following operation checks if the input currency by user exists or not
	 * @Input - currency
	 * @Response - boolean
	 * @Exception - CurrencyException
	 */
	public static boolean isCurrencyAvailable(String currency) throws CurrencyException {
		boolean flag = false;
		/*
		 * Validating the input provided for this method
		 */
		if(currency != null && !currency.equals("")){
			/*
			 * Switch case statement for checking currency availability. If found, it return boolean value back to caller.
			*/
			switch(currency.trim()){
			case CurrencyCalculatorConstant.CURRENCY_AUD:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_CAD:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_CNY:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_CZK:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_DKK:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_EUR:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_GBP:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_JPY:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_NOK:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_NZD:
				flag = true;
				break;
			case CurrencyCalculatorConstant.CURRENCY_USD:
				flag = true;
				break;
			default:
				/*
				 * In case nothing matches with the property value this method returns currency availability as false. 
				*/
				flag = false;
				if(LOG.isLoggable(Level.INFO)){
					LOG.info("CurrencyCalculatorUtil->isCurrencyAvailable()-> Unable to find currency "+currency+" in the table.");
				}
			}
		}else {
			LOG.severe("CurrencyCalculatorUtil->isCurrencyAvailable()-> Invalid input provided -> Null currency provided , Currency : "+currency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->isCurrencyAvailable()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}	
		
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->isCurrencyAvailable()-> Exiting with value "+flag);
		}
		return flag;
	}
	
	/**
	 * The following operation fetches value from map against given Key
	 * @Input - sourceCurrency, destinationCurrency
	 * @Response - String
	 * @Exception - CurrencyException
	 */
	private static String fetchValueFromCrossMatrixKey(String sourceCurrency, String destinationCurrency) throws CurrencyException{
		String value = null;
		/*
		 * Validating the input provided for this method
		 */
		if( sourceCurrency!=null && !sourceCurrency.equals("")
								 && destinationCurrency!=null && !destinationCurrency.equals("")){
			/*
			 * This is a call being made to load property file into memory. The property value is stored into map collections for further use. 
			*/
			HashMap<String, String> mapCurrency = LoadProperty.getFXPropValue();
			String key = sourceCurrency.trim()+destinationCurrency.trim();
			
			/*
			 * This checks if map contains the key provided.If found, return the value of corresponding key. 
			*/
			if(!mapCurrency.isEmpty() && mapCurrency.containsKey(key.toUpperCase())){
				value = mapCurrency.get(key.toUpperCase());
				if(LOG.isLoggable(Level.INFO)){
					LOG.info("CurrencyCalculatorUtil->fetchValueFromCrossMatrixKey()-> Value : "+ value + " found against key : "+key);
				}
			}
		}else {
			LOG.severe("CurrencyCalculatorUtil->fetchValueFromCrossMatrixKey()-> Invalid input provided -> Null input provided , sourceCurrency : "+sourceCurrency+", destinationCurrency : "+destinationCurrency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->fetchValueFromCrossMatrixKey()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->fetchValueFromCrossMatrixKey()-> Exiting with value "+value);
		}
		return value;
	}
	
	/**
	 * The following operation returns key which is used to fetch value from map
	 * @Input - sourceCurrency, destinationCurrency
	 * @Response - String
	 * @Exception - CurrencyException
	 */
	public static String fetchCrossCurrencyValue(String sourceCurrency, String destinationCurrency) throws CurrencyException{
		/*
		 * Validating the input provided for this method
		 */
		if( sourceCurrency!=null && !sourceCurrency.equals("") && destinationCurrency!=null && !destinationCurrency.equals("")){
			if(LOG.isLoggable(Level.INFO)){
				LOG.info("CurrencyCalculatorUtil->fetchCrossCurrencyValue()-> Exiting with value  "+fetchValueFromCrossMatrixKey(sourceCurrency, destinationCurrency));
			}
			/*
			 * This calls a method with key to find out the corresponding value in the map loaded into memory. 
			*/
			return fetchValueFromCrossMatrixKey(sourceCurrency, destinationCurrency);
		}else {
			LOG.severe("CurrencyCalculatorUtil->fetchCrossCurrencyValue()-> Invalid input provided -> Null input provided , sourceCurrency : "+sourceCurrency+", destinationCurrency : "+destinationCurrency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->fetchCrossCurrencyValue()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}
	}
	/**
	 * The following operation calculate rate from given cross currency.
	 * @Input - sourceCurrency, destinationCurrency, inputAmount, crossCurrency
	 * @Response - BigDecimal
	 * @Exception - CurrencyException
	 */
	public static BigDecimal calculateRateFromCrossCurrency(String sourceCurrency, String destinationCurrency, String inputAmount, String crossCurrency) throws CurrencyException{
		BigDecimal finalFXResult = BigDecimal.ZERO;
		/*
		 * Validating the input provided for this method
		 */
		if( sourceCurrency!=null && !sourceCurrency.equals("")
								 && inputAmount!=null && !inputAmount.equals("")
								 && destinationCurrency!=null && !destinationCurrency.equals("")){
			/*
			 * Initializing BigDecimal variable to zero which will be used for storing values. firstPartFXResult meant when searching for a cross 
			 * reference currency for e.g AUDJPY does not have direct dependency hence when it tries to go through cross reference via USD it splits
			 *  into AUDUSD and USDJPY. Here AUDUSD refers to firstPartFXResult and USDJPY refers to secondPartFXResult.
			 * 
			*/
			
			BigDecimal firstPartFXResult = BigDecimal.ZERO;
			BigDecimal secondPartFXResult = BigDecimal.ZERO;
			
			/*
			 * This fetches the cross reference currency details. 
			*/
			String firstPartCrossRefCurrency = fetchCrossCurrencyValue(sourceCurrency, crossCurrency);
			String secondPartCrossRefCurrency = fetchCrossCurrencyValue(crossCurrency, destinationCurrency);
			
			/*
			 *  Checks first part of the cross reference currency does not return null or blank ie. it contains a value. 
			*/
			if(firstPartCrossRefCurrency != null && !firstPartCrossRefCurrency.equals("")){
				firstPartFXResult = calculateRateByCrossCurrency(sourceCurrency, crossCurrency, firstPartCrossRefCurrency);
				if(LOG.isLoggable(Level.INFO)){
					LOG.info("CurrencyCalculatorUtil->calculateRateFromCrossCurrency()->  "+firstPartFXResult);
				}   
			}
			
			/*
			 * Checks second part of the cross reference currency does not return null or blank ie. it contains a value. 
			*/
			if(secondPartCrossRefCurrency != null && !secondPartCrossRefCurrency.equals("")){
				secondPartFXResult = calculateRateByCrossCurrency(crossCurrency,destinationCurrency, secondPartCrossRefCurrency);
				if(LOG.isLoggable(Level.INFO)){
					LOG.info("CurrencyCalculatorUtil->calculateRateFromCrossCurrency()->  "+secondPartFXResult);
				}
			}
			
			/*
			 * Checks if both part first as well second part of the currency details are not zero. if true, calculates the final exchange rate. 
			*/
			if(firstPartFXResult.compareTo(BigDecimal.ZERO) != 0  && secondPartFXResult.compareTo(BigDecimal.ZERO) != 0 ){
				secondPartFXResult = ONE.divide(secondPartFXResult, CurrencyCalculatorConstant.GENERIC_DECIMAL_PLACE, RoundingMode.HALF_UP);
				finalFXResult = firstPartFXResult.divide(secondPartFXResult,CurrencyCalculatorConstant.GENERIC_DECIMAL_PLACE, RoundingMode.HALF_UP);
			}
		}else {
			LOG.severe("CurrencyCalculatorUtil->calculateRateFromCrossCurrency()-> Invalid input provided -> Null input provided , sourceCurrency : "+sourceCurrency+", inputAmount : "+inputAmount+", destinationCurrency : "+destinationCurrency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->calculateRateFromCrossCurrency()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}	
		/*
		 * This multiplies the final result with input amount by user. 
		*/
		finalFXResult = finalFXResult.multiply(convertStringToBigDecimal(inputAmount));
		
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->calculateRateFromCrossCurrency()-> Exiting with value "+finalFXResult);
		}
		return finalFXResult;
	}
	/**
	 * The following operation calculate rate for given currency.
	 * @Input - sourceCurrency, destinationCurrency, result
	 * @Response - BigDecimal
	 * @Exception - CurrencyException 
	 */
	public static BigDecimal calculateRateByCrossCurrency(String sourceCurrency, String destinationCurrency, String crossRefCurrency ) throws CurrencyException{
		/*
		 * Initializing BigDecimal variable to zero which will be used for storing values. 
		*/
		BigDecimal resultFX = BigDecimal.ZERO;
		/*
		 * Validating the input provided for this method
		 */
		if( sourceCurrency!=null && !sourceCurrency.equals("")
								 && crossRefCurrency!=null && !crossRefCurrency.equals("")
								 && destinationCurrency!=null && !destinationCurrency.equals("")){
				/*
				 * Switch case statement for calculating the exchange rate of intended currency. 
				*/	
				switch(crossRefCurrency){
				
				case CurrencyCalculatorConstant.CROSS_REF_KEY_DIRECT:
					resultFX = BigDecimal.valueOf(getFXRates(sourceCurrency+destinationCurrency));
					break;
				case CurrencyCalculatorConstant.CROSS_REF_KEY_INVERSION:
					resultFX = BigDecimal.valueOf(getFXRates(destinationCurrency+sourceCurrency));
					/*
					 * Since this is inversion of value so inverting the resultant value by dividing 1 with the value. 
					*/
					resultFX = ONE.divide(resultFX,CurrencyCalculatorConstant.GENERIC_DECIMAL_PLACE, RoundingMode.HALF_UP);
					break;
				case CurrencyCalculatorConstant.CROSS_REF_KEY_UNITY:
					resultFX = BigDecimal.valueOf(getFXRates(sourceCurrency+destinationCurrency));
					break;
				default:
					/*
					 * Initializing BigDecimal variable to zero which will be used for storing values. firstPartFXResult meant when searching for a cross 
					 * reference currency for e.g AUDJPY does not have direct dependency hence when it tries to go through cross reference via USD it splits
					 * into AUDUSD and USDJPY. Here AUDUSD refers to firstPartFXResult and USDJPY refers to secondPartFXResult.
					*/
					BigDecimal firstPartFXResult = BigDecimal.ZERO;
					BigDecimal secondPartFXResult = BigDecimal.ZERO;
					
					/*
					 * This fetches the cross reference currency details. 
					*/
					String firstPartCrossRefCurrency = fetchCrossCurrencyValue(sourceCurrency, crossRefCurrency);
					String secondPartCrossRefCurrency = fetchCrossCurrencyValue(crossRefCurrency, destinationCurrency);
					/*
					 *  Checks first part of the cross reference currency does not return null or blank ie. it contains a value. Here the method is recursively calls itself. 
					*/
					if(firstPartCrossRefCurrency != null && !firstPartCrossRefCurrency.equals("")){
						firstPartFXResult = calculateRateByCrossCurrency(sourceCurrency,crossRefCurrency, firstPartCrossRefCurrency);
						if(LOG.isLoggable(Level.INFO)){
							LOG.info("CurrencyCalculatorUtil->calculateRateByCrossCurrency()-> "+firstPartFXResult);
						}
					}
					/*
					 * Checks second part of the cross reference currency does not return null or blank ie. it contains a value. Here the method is recursively calls itself.
					*/
					if(secondPartCrossRefCurrency != null && !secondPartCrossRefCurrency.equals("")){
						secondPartFXResult = calculateRateByCrossCurrency(crossRefCurrency,destinationCurrency, secondPartCrossRefCurrency);
						if(LOG.isLoggable(Level.INFO)){
							LOG.info("CurrencyCalculatorUtil->calculateRateByCrossCurrency()-> "+secondPartFXResult);
						}
					}
					/*
					 * Checks if both part first as well second part of the currency details are not zero. if true, calculates the final exchange rate. 
					*/
					if(firstPartFXResult.compareTo(BigDecimal.ZERO) != 0  && secondPartFXResult.compareTo(BigDecimal.ZERO) != 0 ){
						secondPartFXResult = ONE.divide(secondPartFXResult, CurrencyCalculatorConstant.GENERIC_DECIMAL_PLACE, RoundingMode.HALF_UP);
						resultFX = firstPartFXResult.divide(secondPartFXResult,CurrencyCalculatorConstant.GENERIC_DECIMAL_PLACE, RoundingMode.HALF_UP);
						if(LOG.isLoggable(Level.INFO)){
							LOG.info("CurrencyCalculatorUtil->calculateRateByCrossCurrency()-> "+resultFX);
						}
					}
				}
			/*
			* Validation of input request fails then throw CurrencyException with required information.
			*/	
		}else {
			LOG.severe("CurrencyCalculatorUtil->calculateRateByCrossCurrency()-> Invalid input provided -> Null input provided , sourceCurrency : "+sourceCurrency+", crossRefCurrency : "+crossRefCurrency+", destinationCurrency : "+destinationCurrency);
			throw new CurrencyException("Input field provided is not valid ","CurrencyCalculatorUtil->calculateRateByCrossCurrency()-> Invalid input provided ",CurrencyCalculatorConstant.EXCEPTION_SYSTEM);
		}	
		
		if(LOG.isLoggable(Level.INFO)){
			LOG.info("CurrencyCalculatorUtil->calculateRateByCrossCurrency()-> Exiting with value "+resultFX);
		}
		return resultFX;
	}
}