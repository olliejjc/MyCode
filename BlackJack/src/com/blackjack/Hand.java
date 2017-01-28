package com.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> cards;
	
	public Hand(Card card1, Card card2){
		cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
	}

	public List<Card> getCards() {
		return cards;
	}

	
}
