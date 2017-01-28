package com.blackjack;

public class Player {
	private Hand hand;
	private String name;
	private double cash;
	
	public Player(String name, double cash){
		this.name = name;
		this.cash = cash;
	}
	
	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
}
