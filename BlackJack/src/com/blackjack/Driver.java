package com.blackjack;

public class Driver {
	public static void main(String [] args){
		BlackJack blackJack = new BlackJack();
		blackJack.joinBlackJackTable();
		while(blackJack.playerIsPlaying()){
			blackJack.handleBetting();
			blackJack.dealStartingHands();
			blackJack.playRoundOfBlackJack();
		}
	}
}
