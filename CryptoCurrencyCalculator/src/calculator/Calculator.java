package calculator;

import java.util.Scanner;

public class Calculator {

	Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		calc.calculate();

	}

	void calculate() {

		String currencyType = currencyType();
		System.out.println("Please name the type of Cryptocurrency you own: ");
		String crypto = input.next();
		System.out.println("Please enter the amount of coins: ");
		double amount = input.nextDouble();
		System.out.println("Please enter current value of the currency: ");
		double price = input.nextDouble();
		double total = amount * price;

		System.out.println("You have " + amount + " " + crypto + " worth "
				+ currencyType + total);
	}

	String currencyType() {
		System.out.println("Please choose the currency you are using: ");
		System.out.println("1 for $: ");
		System.out.println("2 for €: ");
		System.out.println("3 for £: ");
		int cChoice = input.nextInt();
		String cSymbol = "";

		switch (cChoice) {
		case 1:
			cSymbol = "$";
			break;
		case 2:
			cSymbol = "€";
			break;
		case 3:
			cSymbol = "£";
			break;


		}
		return cSymbol;
	}
}
