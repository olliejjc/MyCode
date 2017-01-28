package com.blackjack;

import java.util.Scanner;

public enum Rank {
	TWO(2, 2), THREE(3, 3), FOUR(4, 4), FIVE(5, 5), SIX(6, 6), SEVEN(7, 7), EIGHT(8, 8), 
    NINE(9, 9), TEN(10, 10), JACK(11, 10), QUEEN(12, 10), KING(13, 10), ACE(14, 0);
	
	private int id;
	private int value;
	private static final Scanner sc = new Scanner(System.in);
	
	private Rank(int id, int value){
		this.id = id;
		this.value = value;
	}
	
	private int getAceValue(){
		System.out.println("Please choose 1 or 11 for the value of your Ace");
		System.out.println("Enter Ace Value: ");
		int aceValue;
		while(true){
			aceValue = sc.nextInt();
			if(aceValue!=1 || aceValue!=11){
				System.out.println("Error please choose 1 or 11 for your Ace Value: ");
			}
			else{
				break;
			}
		}
		
		return aceValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
