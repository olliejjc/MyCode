package com.blackjack;

public class Driver {
	public static void main(String [] args){
		BlackJack blackJack = new BlackJack();
		blackJack.joinBlackJackTable();
		blackJack.makeBet();
		blackJack.dealStartingHands();
		blackJack.playRoundOfBlackJack();
	}
}
