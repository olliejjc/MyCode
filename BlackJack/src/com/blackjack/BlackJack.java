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
	private Deck deck;
	private double bank = 1000;
	private static Scanner sc = new Scanner(System.in);
	private DecimalFormat df = new DecimalFormat("#.00");
	private boolean playerWantsToPlay = true;
	
	public BlackJack(){
		dealer = new Dealer();
	}
	public void joinBlackJackTable(){
		System.out.println("New Player has joined the game");
		System.out.print("Please enter your name: ");
		String name = sc.nextLine();
		while(true){
			System.out.println();
			System.out.println("Please enter the amount of cash you wish to sit down with (MIN is 5, MAX is 100)");
			System.out.println("The cash must be in 50c intervals, e.g €50.00, €50.50, €51.00");
			System.out.print("Enter your cash balance: ");
			BigDecimal cash = sc.nextBigDecimal();
			
			if(CashHandler.isPlayerCashAmountValid(cash)){
				System.out.println();
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
		player.setBet(bet);
	}
	
	public void dealStartingHands(){
		deck = new Deck();
		Hand dealerHand = new Hand();
		Hand playerHand = new Hand();
		dealerHand = dealerHand.generateStartingHand(deck);
		playerHand = playerHand.generateStartingHand(deck);
		dealer.setHand(dealerHand);
		player.setHand(playerHand);
		
	}
	
	public void playRoundOfBlackJack(){
		player.getHand().outputCurrentHand(player);
		dealer.getHand().outputCurrentHand(dealer);
		System.out.println();
		player.hitOrStick(deck);
		if(player.getHand().isHandBust()){
			System.out.println(player.getName() + " loses " + CashHandler.formatCurrencyForDisplay(player.getBet().getAmountBet()));
			System.out.println();
			CashHandler.removeLosingBetFromPlayerBalance(player);
		}
		else{
			dealer.setSecondCardHidden(false);
			player.getHand().outputCurrentHand(player);
			dealer.getHand().outputCurrentHand(dealer);
			System.out.println();
			dealer.hitOrStick(deck);
			if(dealer.getHand().isHandBust()){
				System.out.println("The dealer loses");
				System.out.println(player.getName() + " wins " + CashHandler.formatCurrencyForDisplay(player.getBet().getAmountBet()));
				System.out.println();
				CashHandler.addWinningBetToPlayerBalance(player);
			}
			else{
				String winner = checkWhoWins(player, dealer);
				if(winner.equals("Player")){
					System.out.println(player.getName() + " wins");
					System.out.println();
					CashHandler.addWinningBetToPlayerBalance(player);
				}
				else if(winner.equals("Dealer")){
					System.out.println("Dealer wins");
					System.out.println();
					CashHandler.removeLosingBetFromPlayerBalance(player);
				}
				else{
					System.out.println("Push, no one wins");
					System.out.println();
				}
			}
		}
		
		System.out.println("The players current balance at the table is " + CashHandler.formatCurrencyForDisplay(player.getCash()));
		System.out.println();
		while(playerWantsToPlay){
			System.out.print("Do you want to play again? Enter YES or NO: ");
			String choice = sc.next();
			if(choice.equalsIgnoreCase("YES")){
				System.out.println();
				break;
			}
			else if(choice.equalsIgnoreCase("NO")){
				System.out.println("The game is over your final balance is " + CashHandler.formatCurrencyForDisplay(player.getCash()));
				playerWantsToPlay = false;
				break;
			}
			else{
				System.out.println("You have not entered YES or NO please try again");
				System.out.println();
			}
		}
		
		
	}
	
	public String checkWhoWins(Player player, Dealer dealer){
		String winner = "";
		if(player.getHand().getTotalHandValue() > dealer.getHand().getTotalHandValue()){
			winner = "Player";
		}
		else if(player.getHand().getTotalHandValue() < dealer.getHand().getTotalHandValue()){
			winner = "Dealer";
		}
		else{
			winner = "None";
		}
		return winner;
	}
	
	public boolean playerWantsToPlay() {
		return playerWantsToPlay;
	}
}
