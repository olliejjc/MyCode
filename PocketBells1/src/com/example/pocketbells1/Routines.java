package com.example.pocketbells1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.pocketbells1.R;
import com.pocketbells.sqlite.model.User;
import com.pocketbells.sqlite.model.Routine;
import com.pocketbells.sqlite.model.Workout;
import com.pocketbells.sqlite.helper.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Routines extends Activity {
	//Bundle extras = getIntent().getExtras();
	Routine routine;
	DatabaseHelper db;
	SharedPreferences shared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routines);

		/*shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
		int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set

		//Declare and instantiate routine using constructor
		routine=new Routine( shareId, "stringy");
		//Declare and instantiate DatabaseHelper
		DatabaseHelper db = new DatabaseHelper(getBaseContext());
		//write to db
		db.createRoutine(routine);
		Toast.makeText(getApplicationContext(), "Your id is " +shareId, Toast.LENGTH_SHORT).show();
		 */
		final String[] beginnersRoutine = {"Burpees","burp", "Front Squat", "OneArmFloorPress"};
		final String[] intermediateRoutine = {"Burpees", "Front Squat", "One Arm Floor Press","Burpees",};
		final String[] advancedRoutine = {"Burpees", "Front Squat", "One Arm Floor Press","Front Squat",};

		final ArrayList<String> myRoutine = new ArrayList<String>();

		myRoutine.add("A1");
		myRoutine.add("A2");

		Button beginnerButton = (Button) findViewById(R.id.rmbeginnerbutton);
		Button intermediateButton = (Button) findViewById(R.id.rmintermediatebutton);
		Button advancedButton = (Button) findViewById(R.id.rmadvancedbutton);
		Button chooseButton = (Button) findViewById(R.id.rmchoosebutton);
		Button savedRoutineButton = (Button) findViewById(R.id.rmsavedroutinebutton);


		chooseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Routines.this, ChooseRoutine.class));

			}
		});

		beginnerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Routines.this, Timer.class);
				// bundle routine
				intent.putExtra("Routine", beginnersRoutine);
				startActivity(intent);

			}
		});

		intermediateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Routines.this, Timer.class);
				intent.putExtra("Routine", intermediateRoutine);
				startActivity(intent);
			}
		});

		advancedButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Routines.this, Timer.class);
				intent.putExtra("Routine", advancedRoutine);
				startActivity(intent);
			}
		});


		savedRoutineButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences shared;

				shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
				int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set
				Toast.makeText(getApplicationContext(), "shareid"+ shareId, 1000).show();


				Routine routine = new Routine();
				DatabaseHelper db= new DatabaseHelper(getBaseContext());
				if (!db.CheckIsDataInDB("routines", "id", shareId)){
					Toast.makeText(getApplicationContext(), "No Record Found, Try Again", 1000).show();

				}
				else{
					Toast.makeText(getApplicationContext(), "in here record found", 1000).show();

					routine =db.getRoutine(shareId);

					String routineStr = routine.getUserRoutine(); 

					String[] savedRoutine = routineStr.split(",");

					Intent intent = new Intent(Routines.this, Timer.class);
					intent.putExtra("Routine", savedRoutine);
					startActivity(intent);
				}


			}
		});
		chooseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Routines.this, ChooseRoutine.class);
				intent.putExtra("Routine", myRoutine);
				startActivity(intent);

			}
		});
	}
	//get datetime
	@SuppressWarnings("unused")
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}