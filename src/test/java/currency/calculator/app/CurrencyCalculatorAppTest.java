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
    
    @Test
    public void testConvertStringToBigDecimal()throws CurrencyException {
    	BigDecimal big = CurrencyCalculatorUtil.convertStringToBigDecimal("100.25");
    	double d = big.doubleValue();
       assertEquals(100.25, d , 0.0);
    }
    @Test
     public void testConvertStringToBigDecimalNegativeInput() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.convertStringToBigDecimal("-100.25");
    	double d = big.doubleValue();
    	assertNotSame(100.25, d);
    }
    @Test
    public void testConvertStringToBigDecimalInvalidInput() throws CurrencyException{
    	BigDecimal big = CurrencyCalculatorUtil.convertStringToBigDecimal("100,00");
    	double d = big.doubleValue();
    	assertNotSame(10000, d);
   }
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
    	if(map.containsKey("")){
    		 value = map.get("AUDUSD");
    	}
    	assertEquals(null, value);
    }
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
}
