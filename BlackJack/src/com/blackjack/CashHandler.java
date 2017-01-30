package com.blackjack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class CashHandler {

	public static boolean isPlayerCashAmountValid(BigDecimal cash){
		if(cash.doubleValue() >=5 && cash.doubleValue() <= 100 && (cash.doubleValue() * 100) % 5 == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String formatCurrencyForDisplay(BigDecimal cash){
		BigDecimal displayCash = cash.setScale(2, RoundingMode.HALF_EVEN);
		NumberFormat euroCostFormat = NumberFormat.getCurrencyInstance(new Locale("en","IE"));
		euroCostFormat.setMinimumFractionDigits(2);
		euroCostFormat.setMaximumFractionDigits(2);
		return euroCostFormat.format(displayCash.doubleValue());
	}
}
