package com.pocketbells.sqlite.helper;

import com.pocketbells.sqlite.model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name contactsManager
	private static final String DATABASE_NAME = "pocketbellsDB";

	// Table Names todos, tags todo tags
	private static final String TABLE_USERS = "users";
	private static final String TABLE_ROUTINES = "routines";
	private static final String TABLE_WORKOUTS = "workouts";

	// Common column names 
	private static final String USER_ID = "id";


	// NOTES Table - column names
	private static final String USER_NAME = "username";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";		
	private static final String START_DATE = "start_date";
	private static final String WEIGHT= "weight";
	private static final String TARGET_WEIGHT = "tweight";

	// TAGS Table - column names
	private static final String ROUTINE = "routine";

	// NOTE_TAGS Table - column names

	private static final String WORKOUT_ID ="workout_id" ;
	private static final String CALS = "calories";
	private static final String WORKOUT_DATE = "workout_date";
	private static final String CURRENT_WEIGHT = "weight";


	// Table Create Statements
	// Todo table create statement USER
	private static final String CREATE_TABLE_USERS = "CREATE TABLE "
			+ TABLE_USERS + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME
			+ " TEXT,"  + PASSWORD + " TEXT," +EMAIL + " TEXT,"+ WEIGHT+ " DOUBLE, "+ TARGET_WEIGHT + " DOUBLE, " + START_DATE
			+ " DATETIME" + ")";

	// Tag table create statement ROUTINE
	private static final String CREATE_TABLE_ROUTINES = "CREATE TABLE " + TABLE_ROUTINES
			+ "(" + USER_ID + " INTEGER PRIMARY KEY," + ROUTINE + " TEXT" + ")";



	// todo_tag table create statement WORKOUT
	private static final String CREATE_TABLE_WORKOUTS = "CREATE TABLE "
			+ TABLE_WORKOUTS + "(" + WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ USER_ID + " INTEGER," + CALS + " INTEGER,"+ CURRENT_WEIGHT + " DOUBLE, "+ 
			WORKOUT_DATE + " DATETIME" + ")";

	private static final String SQL_SELECT_ROUTINE_ID = "select count(*) id from routines where id =";



	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_USERS);
		db.execSQL(CREATE_TABLE_ROUTINES);
		db.execSQL(CREATE_TABLE_WORKOUTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "todos" users table methods ----------------//

	/*
	 * Creating a todo user
	 */

	public void createUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_NAME, user.getUserName());
		values.put(EMAIL, user.getEmail());
		values.put(PASSWORD, user.getPassword());
		values.put(START_DATE, user.getStart_date());
		values.put(WEIGHT, user.getWeight());
		values.put(TARGET_WEIGHT, user.getTargetWeight());


		// insert row
		long user_in = db.insert(TABLE_USERS, null, values);


	}

	//create a workout
	public void createWorkout(Workout workout) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_ID , workout.getUserWorkId());
		values.put(CALS, workout.getCals());
		values.put(WORKOUT_DATE, workout.getWorkoutDate());
		values.put(CURRENT_WEIGHT, workout.getWeight());


		// insert row
		long workout_in = db.insert(TABLE_WORKOUTS, null, values);
	}

	// create a routine
	public void createRoutine(Routine routine) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_ID , routine.getUserId());
		values.put(ROUTINE, routine.getUserRoutine());
		// insert row
		long user_in = db.insert(TABLE_ROUTINES, null, values);



	}
	public void updateRoutine(Routine routine) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_ID , routine.getUserId());
		values.put(ROUTINE, routine.getUserRoutine());
		// insert row
		long user_in = db.update(TABLE_ROUTINES, values, USER_ID + " = ?",
				new String[] { String.valueOf(routine.getUserId()) });			

	}
	public void checkForRoutine(int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_ID , id);

		// insert row
		Cursor user_in = db.rawQuery(SQL_SELECT_ROUTINE_ID, 
				new String[] { String.valueOf(id) });			

	}
	/* insert tag_ids
			for (long id : tag_ids) {
				createTodoTag(todo_id, tag_id);
			}

			return todo_id;
		}
		// insert row
			long user_in = db.update(TABLE_ROUTINES, values, USER_ID + " = ?",
					new String[] { String.valueOf(routine.getUserId()) });


	 */

	public User getUserByUserName(String userName) 
	{ 
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE "
				+ USER_NAME + " = " + userName;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0){
			c.moveToFirst();

			User td = new User();
			td.setId(c.getInt(c.getColumnIndex(USER_ID)));
			td.setUserName((c.getString(c.getColumnIndex(USER_NAME))));
			td.setPassword((c.getString(c.getColumnIndex(PASSWORD))));
			td.setEmail((c.getString(c.getColumnIndex(EMAIL))));
			td.setWeight((c.getDouble(c.getColumnIndex(WEIGHT))));
			td.setTargetWeight((c.getDouble(c.getColumnIndex(TARGET_WEIGHT))));
			td.setStartDate(c.getString(c.getColumnIndex(START_DATE)));
			return td;
		}
		else {
			return null;
		}

	}

	//get user id
	public int getUserId(String userName) 
	{ 
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT "+ USER_ID +" FROM " + TABLE_USERS + " WHERE "
				+ USER_NAME + " = '" +  userName+"'"; 

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0){
			c.moveToFirst();
			int id =(c.getInt(c.getColumnIndex(USER_ID)));

			return id;}
		else{
			return-1;
		}
	}

	/*
	 * get single user
	 */
	public User getUser (int user_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE "
				+ USER_ID + " = '" + user_id +"'";

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		User td = new User();
		td.setId(c.getInt(c.getColumnIndex(USER_ID)));
		td.setUserName((c.getString(c.getColumnIndex(USER_NAME))));
		td.setPassword((c.getString(c.getColumnIndex(PASSWORD))));
		td.setEmail((c.getString(c.getColumnIndex(EMAIL))));
		td.setWeight((c.getDouble(c.getColumnIndex(WEIGHT))));
		td.setTargetWeight((c.getDouble(c.getColumnIndex(TARGET_WEIGHT))));
		td.setStartDate(c.getString(c.getColumnIndex(START_DATE)));

		return td;
	}

	/**
	 * getting all users
	 * */
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				User td = new User();
				td.setId(c.getInt(c.getColumnIndex(USER_ID)));
				td.setUserName((c.getString(c.getColumnIndex(USER_NAME))));
				td.setPassword((c.getString(c.getColumnIndex(PASSWORD))));
				td.setEmail((c.getString(c.getColumnIndex(EMAIL))));
				td.setWeight((c.getDouble(c.getColumnIndex(WEIGHT))));
				td.setTargetWeight((c.getDouble(c.getColumnIndex(TARGET_WEIGHT))));
				td.setStartDate(c.getString(c.getColumnIndex(START_DATE)));

				// adding to users list
				users.add(td);
			} while (c.moveToNext());
		}

		return users;
	}
	
	public double getWeight (int user_id, String weight) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  " + weight +" FROM " + TABLE_USERS + " WHERE "+ USER_ID + " = '" +  user_id +"'";
		//String selectQuery ="SELECT EXISTS (SELECT * FROM " + TABLE_ROUTINES + " WHERE " + USER_ID + " = '" +  user_id +"'"+" ) ";
		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null && c.getCount() > 0){
			c.moveToFirst();
			double retWeight =(c.getInt(c.getColumnIndex(WEIGHT)));

			return retWeight;}
		else{
			return-1;
		}
		
		
	}
	// Getting user workouts
	public List<Workout> getWorkouts(int id) {
		List<Workout> workouts = new ArrayList<Workout>();
		String selectQuery = "SELECT  * FROM " + TABLE_WORKOUTS+ " WHERE "
				+ USER_ID + " = '" +  id +"'";

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Workout wo = new Workout();

				wo.setUserWorkId(c.getInt(c.getColumnIndex(USER_ID)));
				wo.setCals(c.getDouble(c.getColumnIndex(CALS)));
				wo.setWeight(c.getDouble(c.getColumnIndex(CURRENT_WEIGHT)));
				wo.setWorkoutDate(c.getString(c.getColumnIndex(WORKOUT_DATE)));

				// adding to workouts list
				workouts.add(wo);
			} while (c.moveToNext());
		}

		return workouts;
	}

	/*
	 * Updating weights
	 */
	public int updateWeight(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(WEIGHT, user.getWeight());
		values.put(TARGET_WEIGHT, user.getTargetWeight());

		// updating row
		return db.update(TABLE_USERS, values, USER_ID + " = ?",
				new String[] { String.valueOf(user.getId()) });
	}
	
	public int updateCurrentWeight(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(WEIGHT, user.getWeight());
		//values.put(TARGET_WEIGHT, user.getTargetWeight());

		// updating row
		return db.update(TABLE_USERS, values, USER_ID + " = ?",
				new String[] { String.valueOf(user.getId()) });
	}
	
	public int updateTargetWeight(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		//values.put(WEIGHT, user.getWeight());
		values.put(TARGET_WEIGHT, user.getTargetWeight());

		// updating row
		return db.update(TABLE_USERS, values, USER_ID + " = ?",
				new String[] { String.valueOf(user.getId()) });
	}
	
	// Getting userroutine
	public Routine getRoutine (int user_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_ROUTINES + " WHERE "
				+ USER_ID + " = '" +  user_id +"'";

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.getCount() > 0)
			c.moveToFirst();

			Routine rt = new Routine();
			rt.setUserId(c.getInt(c.getColumnIndex(USER_ID)));
			rt.setUserRoutine(c.getString(c.getColumnIndex(ROUTINE)));

		
		return rt;
		
	}
	
	public int getRoutineCount (int user_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_ROUTINES + " WHERE "+ USER_ID + " = '" +  user_id +"'";
		//String selectQuery ="SELECT EXISTS (SELECT * FROM " + TABLE_ROUTINES + " WHERE " + USER_ID + " = '" +  user_id +"'"+" ) ";


		Log.e(LOG, selectQuery);
		int i;

		Cursor c = db.rawQuery(selectQuery, null);
		if (c.getCount() > 0){
			i = 1;}
		
		else{
			i=0;
		}


		return i;
	}
	public boolean CheckIsDataInDB(String TableName,String dbfield, int fieldValue) {
		SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "Select * from " + TableName + " where " + dbfield + "="
	            + fieldValue;
	    //Cursor cursor = db.rawQuery(Query, null);
	    Cursor c = db.rawQuery(selectQuery, null);
	            if(c.getCount()<=0){
	    return false;
	           }
	        return true;
	}



	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * get datetime
	 * */
	@SuppressWarnings("unused")
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
		
		
	}
}



