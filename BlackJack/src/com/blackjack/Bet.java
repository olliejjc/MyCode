package com.blackjack;

import java.math.BigDecimal;
import java.util.Scanner;

public class Bet {
	private static Scanner sc = new Scanner(System.in);
	private BigDecimal amountBet;
	
	public void makeBet(Player player){
		BigDecimal cash = player.getCash();
		while(true){
			System.out.println("Please enter the amount you wish to bet.");
			System.out.println("The minimum bet is 2, the maximum bet is 100.");
			System.out.println("The bet must be in 50c intervals, e.g €5.00, €5.50, €6.00");
			System.out.print("Enter your bet: ");
			amountBet = sc.nextBigDecimal();
			if(isBetValid(amountBet, cash)){
				System.out.println();
				System.out.println("Your bet of " + CashHandler.formatCurrencyForDisplay(amountBet) + " is valid, please wait hands will be dealt.");
				System.out.println();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public boolean isBetValid(BigDecimal bet, BigDecimal cash){
		if(bet.doubleValue()<2){
			System.out.println();
			System.out.println("The bet is too small, please bet again.");
			System.out.println();
			return false;
		}
		else if(bet.doubleValue() > 100 || bet.compareTo(cash) == 1){
			System.out.println();
			System.out.println("The bet is too large, please bet again.");
			System.out.println();
			return false;
		}
		else if((bet.doubleValue() * 100) % 50 != 0){
			System.out.println();
			System.out.println("The bet must be in 50c intervals, e.g €5.00, €5.50, €6.00, please bet again");
			System.out.println();
			return false;
		}
		else{
			return true;
		}
	}
	
	public BigDecimal getAmountBet() {
		return amountBet;
	}

	public void setAmountBet(BigDecimal amountBet) {
		this.amountBet = amountBet;
	}
}
