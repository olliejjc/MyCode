package com.example.pocketbells1;

import android.content.ContentValues; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase;

public class RegisterAdapter 
{ 
	static final String DATABASE_NAME = "USERS.db"; 
	static final int DATABASE_VERSION = 1; 
	public static final int NAME_COLUMN = 1; 
	// TODO: Create public field for each column in your table. 
	// SQL Statement to create a new database. 
	static final String DATABASE_CREATE = "create table "+"USERS"+ 
			"( " +"ID"+" integer primary key autoincrement,"+ "USERNAME text,PASSWORD text,EMAIL text); "; 
	// Variable to hold the database instanceSTER 
	public  SQLiteDatabase db; 
	// Context of the application using the database. 
	private final Context context; 
	// Database open/upgrade helper 
	private DataBaseHelper dbHelper; 
	public  RegisterAdapter(Context _context) 
	{ 
		context = _context; 
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION); 
	} 
	public  RegisterAdapter open() throws SQLException 
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

	public void insertEntry(String userName,String password, String email) 
	{ 
		ContentValues newValues = new ContentValues(); 
		// Assign values for each row. 
		newValues.put("USERNAME", userName); 
		newValues.put("PASSWORD",password);
		newValues.put("EMAIL",email);

		// Insert the row into your table 
		db.insert("USERS", null, newValues); 
		///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show(); 
	} 
	public int deleteEntry(String UserName) 
	{ 
		//String id=String.valueOf(ID); 
		String where="USERNAME=?"; 
		int numberOFEntriesDeleted= db.delete("USERS", where, new String[]{UserName}) ; 
		// Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show(); 
		return numberOFEntriesDeleted; 
	} 

	public String getSinlgeEntry(String userName) 
	{ 
		Cursor cursor=db.query("USERS", null, " USERNAME=?", new String[]{userName}, null, null, null); 
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
	public int getUserId(String userName) 
	{ 
		Cursor cursor=db.query("USERS", null, " USERNAME=?", new String[]{userName}, null, null, null); 
		if(cursor.getCount()<1) // UserName Not Exist 
		{ 
			cursor.close(); 
			return -1; 
		} 
		cursor.moveToFirst(); 
		int id= cursor.getInt(cursor.getColumnIndex("ID")); 
		cursor.close(); 
		return id; 
	}

	public void  updateEntry(String userName,String password) 
	{ 
		// Define the updated row content. 
		ContentValues updatedValues = new ContentValues(); 
		// Assign values for each row. 
		updatedValues.put("USERNAME", userName); 
		updatedValues.put("PASSWORD",password);

		String where="USERNAME = ?"; 
		db.update("PASSWORD",updatedValues, where, new String[]{userName}); 
	} 
}

