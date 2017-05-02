package currency.calculator.app;

import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import currency.calculator.app.exception.CurrencyException;
import currency.calculator.app.util.CurrencyCalculatorUtil;
import currency.calculator.app.util.LoadProperty;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class contains test cases for Currency Calculator Application.
 * @Developer: Singh, Sunny 
 */
public class CurrencyCalculatorAppTest extends TestCase
{
    public CurrencyCalculatorAppTest( String testName )
    {
        super( testName );
    }
 
    // Test for convertStringToBigDecimal Method
    
    @Test
    public void testConvertStringToBigDecimal()throws CurrencyException {
    	BigDecimal big = CurrencyCalculatorUtil.convertStringToBigDecimal("100.25");
    	double d = big.doubleValue();
       assertEquals(100.25, d , 0.0);
    }
    @Test
     public void testConvertStringToBigDecimalWithNegativeInput() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.convertStringToBigDecimal("-100.25");
    	double d = big.doubleValue();
    	assertNotSame(100.25, d);
    }
    @Test
    public void testConvertStringToBigDecimalWithInvalidInput() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.convertStringToBigDecimal("100,00");
    	double d = big.doubleValue();
    	assertNotSame(10000, d);
   }
    @Test
    public void testConvertStringToBigDecimalWithNull() throws CurrencyException {
    	try{
    		BigDecimal big = CurrencyCalculatorUtil.convertStringToBigDecimal("");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
    
    // Test for calculateCurrencyValue Method
    
    @Test
    public void testCalculateCurrencyValue() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("AUD", "100", "CAD");
    	assertEquals(96.09, big.doubleValue() , 0.0);
    }
    @Test
    public void testCalculateCurrencyValueSimilarCurrency() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("AUD", "100", "AUD");
    	assertEquals(100, big.doubleValue() , 0.0);
    }
    @Test
    public void testCalculateCurrencyValueWithNotNull() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("AUD", "100", "DKK");
    	assertEquals(505.76, big.doubleValue() , 0.0);
    }
    @Test
    public void testCalculateCurrencyValueWithCrossReference() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("NOK", "100", "JPY");
    	assertEquals(1704, big.doubleValue() , 0.0);
    }
    @Test
    public void testCalculateCurrencyValueExceptionWithNegativeArgs() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("NOK", "-100", "JPY");
    	assertEquals(-1704, big.doubleValue() , 0.0);
    }
    @Test
    public void testCalculateCurrencyValueExceptionWithNull() throws CurrencyException {
    	try{
    		BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("NOK", "100","");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
    @Test
    public void testCalculateCurrencyValueExceptionWithAllNullArgs() throws CurrencyException {
    	try{
    		BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("", "","");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
    @Test
    public void testCalculateCurrencyValueExceptionWithInvalidArgs() throws CurrencyException{
    	try{
    		BigDecimal big = CurrencyCalculatorUtil.calculateCurrencyValue("NOK", "100","100");
    		fail("Currency details are not found in currency matrix table");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Currency details are not found in currency matrix table"));
        }
    }
    
    // Test for IsCurrencyAvailable Method
    
    @Test
    public void testIsCurrencyAvailable() throws CurrencyException{
    	boolean flag = CurrencyCalculatorUtil.isCurrencyAvailable("AUD");
    	assertTrue(flag);
    	assertNotNull(flag);
    }
    @Test
    public void testIsCurrencyAvailableWithException() throws CurrencyException{
    	try{
    		boolean flag = CurrencyCalculatorUtil.isCurrencyAvailable("");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
    @Test
    public void testIsCurrencyAvailableWithInvalidArgs() throws CurrencyException{
    	boolean flag = CurrencyCalculatorUtil.isCurrencyAvailable("WSD");
    	assertNull(null);
    }
    @Test
    public void testIsCurrencyAvailableWithNull() throws CurrencyException{
    	boolean flag = CurrencyCalculatorUtil.isCurrencyAvailable(" ");
    	assertFalse(flag);
    	assertNotNull(flag);
    }
    
 // Test for getFXPropValue Method
    
    @Test
    public void testGetFXPropValue() throws CurrencyException{
    	HashMap<String, String> map = LoadProperty.getFXPropValue();
    	assertNotNull(map);
    	String value = null;
    	if(map.containsKey("AUDUSD")){
    		 value = map.get("AUDUSD");
    	}
    	assertEquals("D", value);
    }
    @Test
    public void testGetFXPropValueWithNull() throws CurrencyException{
    	HashMap<String, String> map = LoadProperty.getFXPropValue();
    	assertNotNull(map);
    	String value = null;
    	String key ="";
    	if(map.containsKey(key)){
    		 value = map.get("AUDUSD");
    	}
    	assertEquals(null, value);
    }
    
    @Test 
    public void testValidateThatGetFXPropValueWorks() throws CurrencyException {
    	HashMap<String, String> map = LoadProperty.getFXPropValue();
    	assertNotNull(map);
    }
    @Test
    public void testValidatesThatClassGetFXPropNotInstantiable() throws CurrencyException {
      try{
    	  Class cls = Class.forName("currency.calculator.app.util.LoadProperty");
          cls.newInstance(); // exception here
          fail("Class currency.calculator.app.CurrencyCalculatorAppTest can not access a member of class currency.calculator.app.util.LoadProperty with modifiers \"private\"");
  		}catch (java.lang.IllegalAccessException e) {
  			assertThat(e.getMessage(), is("Class currency.calculator.app.CurrencyCalculatorAppTest can not access a member of class currency.calculator.app.util.LoadProperty with modifiers \"private\""));
        }catch(Exception ex){
    	  assertThat(ex.getMessage(), is("Class currency.calculator.app.CurrencyCalculatorAppTest can not access a member of class currency.calculator.app.util.LoadProperty with modifiers \"private\""));
       }
    }
    
 // Test for main Method
    
    @Test
    public void testCurrencyCalculatorMainApp() throws CurrencyException{
    	String[] args = { "AUD", "100", "in", "CAD" };
    	CurrencyCalculatorMainApp.main(args);
    }
    @Test
    public void testCurrencyCalculatorMainAppWithInvalidInput() throws CurrencyException{
    	String[] args = { "AUD", "AUD", "in", "CAD" };
    	try{
    		CurrencyCalculatorMainApp.main(args);
    		//fail("Invalid input arguments provided. Please provide input only in format : <ccy1> <amount1> in <ccy2>");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Invalid input arguments provided. Please provide input only in format : <ccy1> <amount1> in <ccy2>"));
        }
    }
    @Test
    public void testCurrencyCalculatorMainAppWithNull() throws CurrencyException{
    	String[] args = { "AUD", "100", "in", "" };
    	try{
    		CurrencyCalculatorMainApp.main(args);
    		//fail("Invalid input arguments provided. Please provide input only in format : <ccy1> <amount1> in <ccy2>");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Invalid input arguments provided. Please provide input only in format : <ccy1> <amount1> in <ccy2>"));
        }
    }
    @Test
    public void testCurrencyCalculatorMainAppWithException() throws CurrencyException{
    	String[] args = { "AUD", "100", "in"};
    	try{
    		CurrencyCalculatorMainApp.main(args);
    		//fail("Invalid input arguments provided. Please provide input only in format : <ccy1> <amount1> in <ccy2>");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Invalid input arguments provided. Please provide input only in format : <ccy1> <amount1> in <ccy2>"));
        }
    }

    //Test cases for getDecimalPlace method
    
    @Test
    public void testGetFXRates() throws CurrencyException{
    		double rate = CurrencyCalculatorUtil.getFXRates("AUDUSD");
    		assertEquals(0.8371, rate);
    	
    }
    @Test
    public void testGetFXRatesWithSpaces() throws CurrencyException{
    		double rate = CurrencyCalculatorUtil.getFXRates("CADUSD ");
    		assertEquals(0.8711, rate);
    	
    }
    @Test
    public void testGetFXRatesWithoutCurrencyMatch() throws CurrencyException{
    		double rate = CurrencyCalculatorUtil.getFXRates("DKKUSD");
    		assertEquals(0.0, rate);
    		assertNotSame(100.25, rate);
    	
    }
    @Test
    public void testGetFXRatesWithException() throws CurrencyException {
    	try{
    		double rate = CurrencyCalculatorUtil.getFXRates("");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
    
   //Test cases for getDecimalPlace method
    
    @Test
    public void testGetDecimalPlace() throws CurrencyException{
    		int decimalPlace = CurrencyCalculatorUtil.getDecimalPlace("AUD");
    		assertEquals(2, decimalPlace);
    	
    }
    @Test
    public void testGetDecimalPlaceWithInvalidInput() throws CurrencyException{
    		int decimalPlace = CurrencyCalculatorUtil.getDecimalPlace("JPY ");
    		assertEquals(0, decimalPlace);
    	
    }
    @Test
    public void testGetDecimalPlaceWithoutMatch() throws CurrencyException{
    		int decimalPlace = CurrencyCalculatorUtil.getDecimalPlace("DKKUSD");
    		assertEquals(10, decimalPlace);
    	
    }
    @Test
    public void testGetDecimalPlaceWithException() throws CurrencyException {
    	try{
    		int rate = CurrencyCalculatorUtil.getDecimalPlace("");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
    
  //Test cases for fetchCrossCurrencyValue method
    
    @Test
    public void testFetchCrossCurrencyValue() throws CurrencyException{
    		String key = CurrencyCalculatorUtil.fetchCrossCurrencyValue("AUD","USD");
    		assertEquals("D", key);
    	
    }
    @Test
    public void testFetchCrossCurrencyValueWithUnity() throws CurrencyException{
    		String key = CurrencyCalculatorUtil.fetchCrossCurrencyValue("CAD","CAD");
    		assertEquals("U", key);
    	
    }
    @Test
    public void testFetchCrossCurrencyValueWithInversion() throws CurrencyException{
    		String key = CurrencyCalculatorUtil.fetchCrossCurrencyValue("DKK","EUR");
    		assertEquals("I", key);
    	
    }
    @Test
    public void testFetchCrossCurrencyValueWithoutMatch() throws CurrencyException{
    		String key = CurrencyCalculatorUtil.fetchCrossCurrencyValue("DKK","WSD");
    		assertEquals(null, key);
    	
    }
    @Test
    public void testFetchCrossCurrencyValueWithException() throws CurrencyException {
    	try{
    		String key = CurrencyCalculatorUtil.fetchCrossCurrencyValue("","");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
    
//Test cases for calculateRateFromCrossCurrency method
    
    @Test
    public void testCalculateRateFromCrossCurrency() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult = CurrencyCalculatorUtil.calculateRateFromCrossCurrency("GBP","JPY", "100","USD");
    	assertEquals(18811.75850658, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateFromCrossCurrencyWithValidInput() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult = CurrencyCalculatorUtil.calculateRateFromCrossCurrency("NOK","USD", "100","EUR");
    	assertEquals(14.2121845, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateFromCrossCurrencyWithDirectCrossRef() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult =  CurrencyCalculatorUtil.calculateRateFromCrossCurrency("DKK","GBP", "100","USD");
    	assertEquals(10.55366131, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateFromCrossCurrencyWithInvalidArgs() throws CurrencyException{
    	try{
    		BigDecimal finalFXResult = BigDecimal.ZERO;
    		finalFXResult =  CurrencyCalculatorUtil.calculateRateFromCrossCurrency("NOK","JPY", "100","");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }    	
    }
    @Test
    public void testCalculateRateFromCrossCurrencyWithException() throws CurrencyException {
    	try{
    		BigDecimal finalFXResult = BigDecimal.ZERO;
        	finalFXResult = CurrencyCalculatorUtil.calculateRateFromCrossCurrency("","", "","");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
 
    //Test cases for calculateRateByCrossCurrency method
    
    @Test
    public void testCalculateRateByCrossCurrency() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult = CurrencyCalculatorUtil.calculateRateByCrossCurrency("EUR","AUD","USD");
    	assertEquals(1.4711504002, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateByCrossCurrencyWithCrossRef() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult =  CurrencyCalculatorUtil.calculateRateByCrossCurrency("CNY","ANZ","USD");
    	assertEquals(0.0, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateByCrossCurrencyWithDifferentCrossRefVal() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult =  CurrencyCalculatorUtil.calculateRateByCrossCurrency("CZK","USD","EUR");
    	assertEquals(0.044615039, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateByCrossCurrencyWithUnity() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult = CurrencyCalculatorUtil.calculateRateByCrossCurrency("AUD","AUD","U");
    	assertEquals(0.0, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateByCrossCurrencyWithDirect() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult = CurrencyCalculatorUtil.calculateRateByCrossCurrency("GBP","USD","D");
    	assertEquals(1.5683, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateByCrossCurrencyWithInversion() throws CurrencyException{
    	BigDecimal finalFXResult = BigDecimal.ZERO;
    	finalFXResult = CurrencyCalculatorUtil.calculateRateByCrossCurrency("NOK","EUR","I");
    	assertEquals(0.1154054771, finalFXResult.doubleValue());
    	
    }
    @Test
    public void testCalculateRateByCrossCurrencyWithException() throws CurrencyException {
    	try{
    		BigDecimal finalFXResult = BigDecimal.ZERO;
        	finalFXResult =  CurrencyCalculatorUtil.calculateRateByCrossCurrency("","","");
    		fail("Input field provided is not valid ");
    	}catch (CurrencyException e) {
    		assertThat(e.getMessage(), is("Input field provided is not valid "));
        }
    }
}
