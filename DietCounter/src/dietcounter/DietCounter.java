package dietcounter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DietCounter {
	//declare private variables
	private String date;
	private double calories;
	private double protein;
	private double carbs;
	private double fat;
	private int function;
	static DietCounter dc = new DietCounter();
	
	public static void main(String[]args) throws IOException
	{
		
		dc.displayInput();//display method which allows you to input data
		dc.fileWriter();//write data to file
		
		while(dc.getFunction()==1){//keep running the program while the user wants you to
			System.out.println();
				dc.displayInput();
				dc.fileWriter();
			}
		
		
		System.exit(0);//exit the program
		
		


	}
	
	public void displayInput(){
		//input data
		Scanner input = new Scanner(System.in);
		System.out.println("Oliver's Macro Counter 2014 \n\n");
        System.out.println("Please input todays date: ");
        setDate(input.next());
		System.out.println("\nPlease input total amount of calories: ");
		setCalories(input.nextDouble());
		System.out.println("Please input total amount of protein: ");
		setProtein(input.nextDouble());
		System.out.println("Please input total amount of carbs: ");
		setCarbs(input.nextDouble());
		System.out.println("Please input total amount of fat: ");
		setFat(input.nextDouble());
		System.out.println("Please enter 1 to continue and add another date or any other number to quit");
		setFunction(input.nextInt());
	}
	void fileWriter() throws IOException{
		
		
		FileWriter writer1 = new FileWriter("Macro-Counter.txt", true);//create a object with text-file called Macro-Counter which will show the inputted data
		PrintWriter writer = new PrintWriter(writer1);//pass the object with textfile through PrintWriter object to enable writing to file
		DietCounter dc = new DietCounter();
		writer.println();
		String date = getDate();
		double calories = getCalories();
		double protein = getProtein();
		double carbs = getCarbs();
		double fat = getFat();
		//write data to file
		writer.println("The date is:" + date);
		writer.println();
		writer.println("Total Calories: " + calories);
		writer.println("Total Protein: " + protein);
		writer.println("Total Carbs: " + carbs);
		writer.println("Total Fat: " + fat);

		writer.close();
		
		
		
	}
	
	//get and set methods
	public String getDate() {
		return date;
	}
	public double getCalories() {
		return calories;
	}
	public double getProtein() {
		return protein;
	}
	public double getCarbs() {
		return carbs;
	}
	public double getFat() {
		return fat;
	}
	private int getFunction() {
		return function;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setCalories(double calories) {
		this.calories = calories;
	}
	public void setProtein(double protein) {
		this.protein = protein;
	}
	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}
	public void setFat(double fat) {
		this.fat = fat;
	}
	public void setFunction(int function) {
		this.function = function;
	}

}
