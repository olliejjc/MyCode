package com.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hand {
	private List<Card> cards;
	private Random random = new Random();
	private boolean isSecondDealerCardHidden = true;
	private boolean isHandBust = false;
	
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
		int card1Rank = (random.nextInt(deck.getSizeOfDeck()));
		Card card1 = deck.getDeckOfCards().get(card1Rank);
		deck.getDeckOfCards().remove(card1Rank);
		int card2Rank = (random.nextInt(deck.getSizeOfDeck()));
		Card card2 = deck.getDeckOfCards().get(card2Rank);
		deck.getDeckOfCards().remove(card2Rank);
		Hand hand = new Hand(card1, card2);
		return hand;
	}
	
	public void addCardToHand(Deck deck){
		int cardRank = (random.nextInt(deck.getSizeOfDeck()));
		Card card = deck.getDeckOfCards().get(cardRank);
		cards.add(card);
		deck.getDeckOfCards().remove(cardRank);
	}
	
	public int getTotalHandValue(){
		int cardValueTotal = 0;
		for(Card card: cards){
			cardValueTotal += card.getRank().getValue();
		}
		if(cardValueTotal<=12){
			for(Card card: cards){
				if(card.getRank().name().equals("ACE")){
					cardValueTotal += 10;
					break;
				}
			}
		}
		if(cardValueTotal>21){
			isHandBust = true;
		}
		return cardValueTotal;
	}
	
	public void outputHandValue(Participant participant){
		if(participant.getClass().getSimpleName().toString().equals("Dealer")){
			System.out.println("The dealers cards add up to " + participant.getHand().getTotalHandValue());
			if(isHandBust){
				System.out.println("The dealer is bust");
			}
			System.out.println();
		}
		else if(participant.getClass().getSimpleName().toString().equals("Player")){
			Player player = (Player) participant;
			System.out.println(player.getName() + " your cards add up to " + player.getHand().getTotalHandValue());
			if(isHandBust){
				System.out.println(player.getName() + " is bust");
			}
			System.out.println();
		}
	}
	
	public void outputCurrentHand(Participant participant){
		StringBuffer output = new StringBuffer("");
		if(participant.getClass().getSimpleName().toString().equals("Dealer")){
			output.append("The dealers hand is the ");
		}
		else if(participant.getClass().getSimpleName().toString().equals("Player")){
			output.append("The players hand is the ");
		}
		for(int i = 0; i < participant.getHand().cards.size(); i++){
			if(i==1 && isSecondDealerCardHidden && participant.getClass().getSimpleName().toString().equals("Dealer")){
				output.append("second card is hidden");
				isSecondDealerCardHidden = false;
			}
			else{
				output.append(participant.getHand().cards.get(i).getRank().name() + " of " + cards.get(i).getSuit().name());
				if(i!=(cards.size()-1)){
					output.append(" and the ");
				}
			}
		}
		System.out.println(output);
	}
	
	public boolean isHandBust(){
		if(this.getTotalHandValue()<=21){
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
