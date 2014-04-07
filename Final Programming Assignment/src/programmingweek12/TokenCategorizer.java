package programmingweek12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TokenCategorizer {

	private String fileName;
	private String langCode;
	private String cntryCode;
	private static ArrayList<Long> longList = new ArrayList<Long>();
	private static ArrayList<Double> doubleList = new ArrayList<Double>();
	private static ArrayList<String> stringList = new ArrayList<String>();
	private Display display;

	public TokenCategorizer(String fileName, String langCode, String cntryCode) {
		this.fileName = fileName;
		this.langCode = langCode;
		this.cntryCode = cntryCode;
	}

	public static void main(String[] args) throws IOException {
		
		try {
			
			System.out.println("Please enter a filename: ");
			String str1 = args[0];

			System.out.println("Please enter the language code: ");
			String str2 = args[1];
			
			System.out.println("Please enter a country code: ");
			String str3 = args[2];

			TokenCategorizer tc = new TokenCategorizer(str1, str2, str3);
			
			tc.readFile();//reads the file in
			tc.initializeDisplay();
			
			
		} catch (IOException e) { // all I/O operations can cause exceptions
			System.err.println(e);
		}
	}
	
	public void initializeDisplay()//initializes the display object with the necessary values
	{
		
		try {
			display = new Display(longList,doubleList,stringList,langCode,cntryCode, this);
			display.createDisplay();//creates the display
			display.setVisible(true);
						
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void readFile() throws IOException//used to pass the new file name
	{
		readFile(fileName);//assigns the new file path and calls the readFile method
	}

	public void readFile(String filePath) throws IOException {//receives the file path, reads the data
		                                                      //+ and divides the contents into lists
		
		
		try {
			
			Scanner s = new Scanner(new BufferedReader(new FileReader(filePath)));//reads the file name
			s.useDelimiter("\\s* \\s*");//uses white space delimiters
			Locale locale = new Locale(langCode,cntryCode);//creates the locale object
			
			longList.clear();//clears list, used every time a file is read
			doubleList.clear();
			stringList.clear();
			

			while (s.hasNext()) {//gets the next token
				String word = s.next();//assigns the token to a string
				NumberFormat nf = NumberFormat.getInstance(locale);//uses the reference of locale to get the instance
				                                                   // + of language and country
				try {
					Number n = nf.parse(word);//parses the string word by the numberformat defined above
					if(n instanceof Long){//checks if long
					longList.add((Long) n);//if it is long add it to long list
					System.out.println("Long: " + n);
					}
					if(n instanceof Double){
					doubleList.add((Double) n);
					System.out.println("Double: " + n);
					}
				} catch (ParseException e1) {//if n cannot be parsed it is a string and added to the string list
					stringList.add(word);
					System.out.println("String: " + word);
				}
				

			}


		} catch (IOException e) { // any stream operation might fail!
			System.err.println(e); // (e.g., if file doesn't exist)
		} finally {
		}
	}

	public String getFileName() {
		return fileName;
	}

	public String getLangCode() {
		return langCode;
	}

	public String getCntryCode() {
		return cntryCode;
	}
	
	
}
