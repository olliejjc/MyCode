package com.blackjack;

public abstract class Participant {
	private Hand hand;
	
	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public abstract void hitOrStick(Deck deck);
}
