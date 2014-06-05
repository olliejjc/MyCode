package com.example.pocketbells1;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class WorkoutAdapter {
	static final String DATABASE_NAME = "USERS.db"; 
	static final int DATABASE_VERSION = 1; 
	public static final int NAME_COLUMN = 1; 
	// TODO: Create public field for each column in your table. 
	// SQL Statement to create a new database. 
	//static final String DATABASE_CREATE = "create table "+"WORKOUTS"+ 
			//"( " +"ID"+" integer primary key autoincrement,"+ "MINUTES text,CALORIES text); "; 
	static final String CREATE_TABLE_WORKOUTS = "CREATE TABLE "
			+ "WORKOUTS" + "(" + "WORKOUT_ID " + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "USER_ID" + " INTEGER," + "CALS" + " INTEGER,"+ "WEIGHT" + " DOUBLE, "+ 
			"WORKOUT_DATE" + " DATETIME" + ")";//
	// Variable to hold the database instanceSTER 
	public  SQLiteDatabase db; 
	// Context of the application using the database. 
	private final Context context; 
	// Database open/upgrade helper 
	private DataBaseHelper dbHelper; 
	public  WorkoutAdapter(Context _context) 
	{ 
		context = _context; 
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION); 
	} 
	public  WorkoutAdapter open() throws SQLException 
	{ 
		db = dbHelper.getWritableDatabase(); 
		return this; 
	} 
	public void close() 
	{ 
		db.close(); 
	}

	public  SQLiteDatabase getDatabaseInstance() 
	{ 
		return db; 
	}

	public void insertEntry(int userId,int cals, double weight  ) 
	{ 
		ContentValues newValues = new ContentValues(); 
		// Assign values for each row. 
		newValues.put("USER_ID", userId); 
		newValues.put("CALORIES",cals);
		newValues.put("WEIGHT",weight);
		newValues.put("WORKOUT_DATE",getDateTime());
		//values.put(START_DATE, getDateTime())
		

		// Insert the row into your table 
		db.insert("WORKOUTS", null, newValues); 
		///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show(); 
	} 
	//get datetime
	
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
	/*public int deleteEntry(String UserName) 
	{ 
	//String id=String.valueOf(ID); 
	String where="USERNAME=?"; 
	int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ; 
	// Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show(); 
	return numberOFEntriesDeleted; 
	} 
	public String getSinlgeEntry(String userName) 
	{ 
	Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null); 
	if(cursor.getCount()<1) // UserName Not Exist 
	{ 
	cursor.close(); 
	return "NOT EXIST"; 
	} 
	cursor.moveToFirst(); 
	String password= cursor.getString(cursor.getColumnIndex("PASSWORD")); 
	cursor.close(); 
	return password; 
	} 
	public void  updateEntry(String userName,String password) 
	{ 
	// Define the updated row content. 
	ContentValues updatedValues = new ContentValues(); 
	// Assign values for each row. 
	updatedValues.put("USERNAME", userName); 
	updatedValues.put("PASSWORD",password);

	String where="USERNAME = ?"; 
	db.update("LOGIN",updatedValues, where, new String[]{userName}); 
	} 
	}

	- See more at: http://androidituts.com/android-sqlite-database-insert-example/#sthash.0Vo645GA.dpuf

	/*public WorkoutAdapter(OnClickListener onClickListener) {
		// TODO Auto-generated constructor stub
	}

	public WorkoutAdapter open() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertEntry(long finalTime) {
		// TODO Auto-generated method stub
	 */

}


