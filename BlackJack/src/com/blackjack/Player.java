package com.blackjack;

import java.math.BigDecimal;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthScrollBarUI;

public class Player extends Participant{
	private String name;
	private BigDecimal cash;
	private Bet bet;

	private Scanner sc = new Scanner(System.in);
	
	public Player(String name, BigDecimal cash){
		this.name = name;
		this.cash = cash;
	}
	
	@Override
	public void hitOrStick(Deck deck){
		System.out.println(name + " your cards add up to " + getHand().getTotalHandValue());
		System.out.println();
		while(true){
			System.out.print("Please enter hit to get another card or stick to stay with the cards you have: ");
			String choice = sc.nextLine();
			if(choice.equalsIgnoreCase("hit")){
				getHand().addCardToHand(deck);
				System.out.println();
				getHand().outputCurrentHand(this);
				getHand().outputHandValue(this);
				System.out.println();
				if(getHand().isHandBust()){
					break;
				}
			}
			else if(choice.equalsIgnoreCase("stick")){
				System.out.println();
				getHand().outputCurrentHand(this);
				getHand().outputHandValue(this);
				System.out.println("You have stuck with this hand");
				System.out.println();
				break;
			}
			else{
				System.out.println("You have not entered a correct choice please try again");
				System.out.println();
			}
		}
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}
	
}
