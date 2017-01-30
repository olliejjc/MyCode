package com.blackjack;

import java.math.BigDecimal;

public class Player {
	private Hand hand;
	private String name;
	private BigDecimal cash;
	
	public Player(String name, BigDecimal cash){
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
