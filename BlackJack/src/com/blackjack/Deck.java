package com.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> deckOfCards;

	public Deck(){
		deckOfCards = new ArrayList<Card>();
		for (Suit suit : Suit.values()) {
		    for (Rank rank : Rank.values()) {
		         Card card = new Card(suit,rank);
		         deckOfCards.add(card);
		    }  
		}
	}
	
	public void shuffle() {
	    Collections.shuffle(this.deckOfCards); 
	}

	public int getSizeOfDeck(){
		return deckOfCards.size();
	}
	public List<Card> getDeckOfCards() {
		return deckOfCards;
	}

	public void setDeckOfCards(List<Card> deckOfCards) {
		this.deckOfCards = deckOfCards;
	}
	
}
