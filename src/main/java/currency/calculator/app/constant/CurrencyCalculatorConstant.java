package currency.calculator.app.constant;

/**
 * This class contains all values which helps calculating exchange rate from property value
 * @Operations: N/A
 * @Developer: Singh, Sunny 
 */

public interface CurrencyCalculatorConstant {
	
	//Max input string length 
	public static final int INPUT_ARGUMENT_LENGTH = 4 ;
	
	//Generic decimal place
	public static final int GENERIC_DECIMAL_PLACE = 10 ;
	
	//Decimal value details for each currency.
	public static final int AUD_DECIMAL_PLACE = 2 ;
	public static final int CAD_DECIMAL_PLACE = 2 ;
	public static final int CNY_DECIMAL_PLACE = 2 ;
	public static final int CZK_DECIMAL_PLACE = 2 ;
	public static final int DKK_DECIMAL_PLACE = 2 ;
	public static final int EUR_DECIMAL_PLACE = 2 ;
	public static final int GBP_DECIMAL_PLACE = 2 ;
	public static final int JPY_DECIMAL_PLACE = 0 ;
	public static final int NOK_DECIMAL_PLACE = 2 ;
	public static final int NZD_DECIMAL_PLACE = 2 ;
	public static final int USD_DECIMAL_PLACE = 2 ;
	
	//Currency details
	public static final String CURRENCY_USD = "USD" ;
	public static final String CURRENCY_CAD = "CAD" ;
	public static final String CURRENCY_CNY = "CNY" ;
	public static final String CURRENCY_CZK = "CZK" ;
	public static final String CURRENCY_DKK = "DKK" ;
	public static final String CURRENCY_EUR = "EUR" ;
	public static final String CURRENCY_GBP = "GBP" ;
	public static final String CURRENCY_JPY = "JPY" ;
	public static final String CURRENCY_NOK = "NOK" ;
	public static final String CURRENCY_NZD = "NZD" ;
	public static final String CURRENCY_AUD = "AUD" ;
	
	//FX Rate details
	public static final double CURRENCY_RATE_AUDUSD = 0.8371;
	public static final double CURRENCY_RATE_CADUSD = 0.8711;
	public static final double CURRENCY_RATE_USDCNY = 6.1715;
	public static final double CURRENCY_RATE_EURUSD = 1.2315;
	public static final double CURRENCY_RATE_GBPUSD = 1.5683;
	public static final double CURRENCY_RATE_NZDUSD = 0.7750;
	public static final double CURRENCY_RATE_USDJPY = 119.95;
	public static final double CURRENCY_RATE_EURCZK = 27.6028;
	public static final double CURRENCY_RATE_EURDKK = 7.4405;
	public static final double CURRENCY_RATE_EURNOK = 8.6651;
	
	//Key details for cross reference
	public static final String CROSS_REF_KEY_DIRECT = "D";
	public static final String CROSS_REF_KEY_INVERSION = "I";
	public static final String CROSS_REF_KEY_UNITY = "U";
	
	//FX currency details
	public static final String CURRENCY_AUDUSD = "AUDUSD";
	public static final String CURRENCY_CADUSD = "CADUSD";
	public static final String CURRENCY_USDCNY = "USDCNY";
	public static final String CURRENCY_EURUSD = "EURUSD";
	public static final String CURRENCY_GBPUSD = "GBPUSD";
	public static final String CURRENCY_NZDUSD = "NZDUSD";
	public static final String CURRENCY_USDJPY = "USDJPY";
	public static final String CURRENCY_EURCZK = "EURCZK";
	public static final String CURRENCY_EURDKK = "EURDKK";
	public static final String CURRENCY_EURNOK = "EURNOK";
	
	//system name for exception
	public static final String EXCEPTION_SYSTEM = "APPLICATION";
	public static final String EXCEPTION_GENERIC_MESSAGE = "A Generic excpetion occured";
	

}
