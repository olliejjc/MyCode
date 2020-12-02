package com.blackjack;

public class Dealer extends Participant {
	private boolean secondCardHidden = true;

	public boolean isSecondCardHidden() {
		return secondCardHidden;
	}

	public void setSecondCardHidden(boolean secondCardHidden) {
		this.secondCardHidden = secondCardHidden;
	}

	@Override
	public void hitOrStick(Deck deck) {
		System.out.println("The dealers cards cards add up to " + getHand().getTotalHandValue());
		if(getHand().getTotalHandValue()<16){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(true){
			if(getHand().getTotalHandValue() >= 16 && getHand().getTotalHandValue() <= 21){
				System.out.println("The dealer has stuck with this hand");
				break;
			}
			else if(getHand().getTotalHandValue() < 16){
				System.out.println("The dealer hits");
				getHand().addCardToHand(deck);
				System.out.println();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getHand().outputCurrentHand(this);
				getHand().outputHandValue(this);
				System.out.println();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(getHand().isHandBust()){
					break;
				}
			}
		}
	}
	
	
}
