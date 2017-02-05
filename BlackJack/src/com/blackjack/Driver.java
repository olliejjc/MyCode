package com.blackjack;

import java.util.Scanner;

public class Driver {
	private static Scanner sc = new Scanner(System.in);
	public static void main(String [] args){
		BlackJack blackJack = new BlackJack();
		blackJack.joinBlackJackTable();
		while(blackJack.playerWantsToPlay()){
			blackJack.handleBetting();
			blackJack.dealStartingHands();
			blackJack.playRoundOfBlackJack();
		}
	}
}
