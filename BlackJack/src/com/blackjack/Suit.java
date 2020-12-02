package com.blackjack;

public enum Suit {
	HEARTS(1), DIAMONDS(2), SPADES(3), CLUBS(4);

	private int id;
	
	private Suit(int id){
		this.id = id;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
