package com.blackjack;

import java.math.BigDecimal;
import java.util.Scanner;

public class Bet {
	private static Scanner sc = new Scanner(System.in);
	private BigDecimal bet;
	
	public void makeFirstBet(){
		while(true){
			System.out.println("Please enter the amount you wish to bet.");
			System.out.println("The minimum bet is 2, the maximum bet is 100.");
			System.out.println("The bet must be in 50c intervals, e.g €5.00, €5.50, €6.00");
			System.out.println("Enter your bet: ");
			bet = sc.nextBigDecimal();
			if(isBetValid(bet)){
				System.out.println("Your bet of " + CashHandler.formatCurrencyForDisplay(bet) + " is valid, please wait hands will be dealt.");
				break;
			}
		}
	}

	public boolean isBetValid(BigDecimal bet){
		if(bet.doubleValue()<2){
			System.out.println("The bet is too small, please bet again.");
			return false;
		}
		else if(bet.doubleValue() > 100){
			System.out.println("The bet is too large, please bet again.");
			return false;
		}
		else if((bet.doubleValue() * 100) % 5 != 0){
			System.out.println("The bet must be in 50c intervals, e.g €5.00, €5.50, €6.00, please bet again");
			return false;
		}
		else{
			return true;
		}
	}
	
	public BigDecimal getBet() {
		return bet;
	}

	public void setBet(BigDecimal bet) {
		this.bet = bet;
	}
}
