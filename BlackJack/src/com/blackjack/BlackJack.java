package com.blackjack;

import java.util.Random;
import java.util.Scanner;

public class BlackJack {
	private Player player;
	private Dealer dealer;
	private double bank = 1000;
	private static Scanner sc = new Scanner(System.in);
	private Random random = new Random();
	
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
			double cash = sc.nextDouble();
			if(isPlayerCashAmountValid(cash)){
				System.out.println("The player " + name + " has sat down with €" + cash + " balance");
				player = new Player(name, cash);
				break;
			}
			else{
				System.out.println("You have entered an incorrect amount of cash, please try again.");
				System.out.println();
			}
		}
		
	}
	
	public void makeBet(){
		while(true){
			System.out.println("Please enter the amount you wish to bet.");
			System.out.println("The minimum bet is 2, the maximum bet is 100.");
			System.out.println("The bet must be in 50c intervals, e.g €5.00, €5.50, €6.00");
			System.out.println("Enter your bet: ");
			double bet = sc.nextDouble();
			if(isBetValid(bet)){
				System.out.println("Your bet of €" + bet + " is valid, please wait hands will be dealt.");
				break;
			}
		}
	}
	
	public void dealStartingHands(){
		Hand dealerHand = generateStartingHand();
		Hand playerHand = generateStartingHand();
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
	
	public Hand generateStartingHand(){
		int card1Suit = (random.nextInt(4) + 1);
		int card1Rank = (random.nextInt(14) + 1);
		int card2Suit = (random.nextInt(4) + 1);
		int card2Rank = (random.nextInt(14) + 1);
		Card card1 = new Card(getRank(card1Rank), getSuit(card1Suit));
		Card card2 = new Card(getRank(card2Rank), getSuit(card2Suit));
		Hand hand = new Hand(card1, card2);
		return hand;
		
	}
	
	
	public boolean isPlayerCashAmountValid(double cash){
		if(cash >=5 && cash <= 100 && (cash * 100) % 5 == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isBetValid(double bet){
		if(bet<2){
			System.out.println("The bet is too small, please bet again.");
			return false;
		}
		else if(bet > 100){
			System.out.println("The bet is too large, please bet again.");
			return false;
		}
		else if((bet * 100) % 5 != 0){
			System.out.println("The bet must be in 50c intervals, e.g €5.00, €5.50, €6.00, please bet again");
			return false;
		}
		else{
			return true;
		}
	}
	
	public Rank getRank(int id) {
		for(Rank rank: Rank.values()) {
		    if(rank.getId() == id) {
		      return rank;
		    }
		  }
		  return null;// not found
	}
	
	public Suit getSuit(int id) {
		for(Suit suit: Suit.values()) {
		    if(suit.getId() == id) {
		      return suit;
		    }
		  }
		  return null;// not found
	}
}
