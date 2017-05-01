# CurrencyCalculatorApp

This application calculates FX from one currency to another. It takes in "<ccy1> <amount1> in <ccy2>" from console which then program 
parse to calculate the amount1 from currency ccy1 to ccy2. 

For example:
%> AUD 100.00 in USD
AUD 100.00 = USD 83.71
%> AUD 100.00 in AUD
AUD 100.00 = AUD 100.00
%> AUD 100.00 in DKK
AUD 100.00 = DKK 505.76
%> JPY 100 in USD
JPY 100 = USD 0.83
If the rate cannot be calculated, the program should alert the user:
%> KRW 1000.00 in FJD
Unable to find rate for KRW/FJD
