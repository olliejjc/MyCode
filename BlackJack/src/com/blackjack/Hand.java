package com.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hand {
	private List<Card> cards;
	private Random random = new Random();
	
	public Hand(){
		
	}
	
	public Hand(Card card1, Card card2){
		cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
	}

	public List<Card> getCards() {
		return cards;
	}

	public Hand generateStartingHand(Deck deck){
		int card1Rank = (random.nextInt(deck.getSizeOfDeck()) + 1);
		Card card1 = deck.getDeckOfCards().get(card1Rank);
		deck.getDeckOfCards().remove(card1Rank);
		int card2Rank = (random.nextInt(deck.getSizeOfDeck()) + 1);
		Card card2 = deck.getDeckOfCards().get(card2Rank);
		deck.getDeckOfCards().remove(card2Rank);
		Hand hand = new Hand(card1, card2);
		return hand;
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
