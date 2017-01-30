package com.blackjack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class BlackJack {
	private Player player;
	private Dealer dealer;
	private double bank = 1000;
	private static Scanner sc = new Scanner(System.in);
	private DecimalFormat df = new DecimalFormat("#.00"); 
	
	public BlackJack(){
		dealer = new Dealer();
	}
	public void joinBlackJackTable(){
		System.out.println("New Player has joined the game");
		System.out.println("Please enter your name: ");
		String name = sc.nextLine();
		while(true){
			System.out.println("Please enter the amount of cash you wish to sit down with (MIN is 5, MAX is 100)");
			System.out.println("The cash must be in 50c intervals, e.g €50.00, €50.50, €51.00");
			System.out.println("Enter your cash balance: ");
			BigDecimal cash = sc.nextBigDecimal();
			
			if(CashHandler.isPlayerCashAmountValid(cash)){
				System.out.println("The player " + name + " has sat down with " + CashHandler.formatCurrencyForDisplay(cash) + " balance");
				player = new Player(name, cash);
				break;
			}
			else{
				System.out.println("You have entered an incorrect amount of cash, please try again.");
				System.out.println();
			}
		}
		
	}
	
	public void handleBetting(){
		Bet bet = new Bet();
		bet.makeFirstBet();
	}
	
	public void dealStartingHands(){
		Deck deck = new Deck();
		Hand dealerHand = new Hand();
		Hand playerHand = new Hand();
		dealerHand = dealerHand.generateStartingHand(deck);
		playerHand = playerHand.generateStartingHand(deck);
		dealer.setHand(dealerHand);
		player.setHand(playerHand);
		
	}
	
	public void playRoundOfBlackJack(){
		System.out.println("The players starting hand is the " + player.getHand().getCards().get(0).getRank().name() + " of " + player.getHand().getCards().get(0).getSuit().name() + " and the "
				+ player.getHand().getCards().get(1).getRank().name() + " of " + player.getHand().getCards().get(1).getSuit().name());
		System.out.println("The dealers starting hand is the " + dealer.getHand().getCards().get(0).getRank().name() + " of " + dealer.getHand().getCards().get(0).getSuit().name() + " and the "
				+ dealer.getHand().getCards().get(1).getRank().name() + " of " + dealer.getHand().getCards().get(1).getSuit().name());
		System.out.println();
		System.out.println("Game Over For Now");
		
	}
	
}
