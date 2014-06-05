/*package com.example.pocketbells1;

import com.example.pocketbells1.R;
import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.pocketbells.sqlite.model.Routine;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;



public class ChooseRoutine extends Activity {

	Routine routine;
	DatabaseHelper db;
	SharedPreferences shared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooseroutine);
		shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
		int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set

		//Declare and instantiate routine using constructor
		routine=new Routine( shareId, "stringy");
		//Declare and instantiate DatabaseHelper
		DatabaseHelper db = new DatabaseHelper(getBaseContext());
		//write to db
		db.createRoutine(routine);
		Toast.makeText(getApplicationContext(), "Your id is " +shareId, Toast.LENGTH_SHORT).show();
	}

}*/
package com.example.pocketbells1;

import java.util.ArrayList;

import com.example.pocketbells1.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.pocketbells.sqlite.model.Routine;
import android.content.SharedPreferences;


public class ChooseRoutine extends Activity {

	//Create arraylist so routines can easily be added using .add() 
	static ArrayList<String> routinesArray = new ArrayList<String>();
	static String routinesString = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooseroutine);
		final int userID = getIntent().getIntExtra("id", -1);
		//clear previous array if any
		routinesArray.removeAll(routinesArray);

		populateListView();

		registerClickCallback();

		Button confirmButton = (Button) findViewById(R.id.routineconfirmbutton);
		Button resetButton = (Button) findViewById(R.id.routineresetbutton);
		Button viewButton = (Button) findViewById(R.id.routineviewbutton);

		resetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				routinesArray.removeAll(routinesArray);
				Toast.makeText(ChooseRoutine.this, "routines cleared", Toast.LENGTH_LONG);

			}
		});

		viewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < routinesArray.size(); i++){
					Toast.makeText(ChooseRoutine.this, ((i + 1) + ". " + routinesArray.get(i)) , Toast.LENGTH_SHORT).show();
				}

			}
		});

		confirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Validate that items have been added to the array
				if (routinesArray.size() > 0){
					//Convert arraylist back to simple string array so it can be bundled in the intent
					String[] routinesStringArray = routinesArray.toArray(new String[routinesArray.size()]);

					for (String s : routinesStringArray){
						routinesString += (s + ",");

					}

					Routine routine;
					SharedPreferences shared;
					shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
					int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set

					//Declare and instantiate routine using constructor
					routine=new Routine( shareId, routinesString);
					//Declare and instantiate DatabaseHelper 
					DatabaseHelper db = new DatabaseHelper(getBaseContext());
					//write to db
					boolean isInDb =db.CheckIsDataInDB("routines", "id", shareId);
					//int count =db.getRoutineCount(shareId);
					Toast.makeText(getApplicationContext(), "in db ="+isInDb, Toast.LENGTH_SHORT).show();

					if (!isInDb){
						db.createRoutine(routine);}
					else{
						db.updateRoutine(routine);

					}
					db.close();

					//Toast.makeText(getApplicationContext(), "Routine saved to db "+routinesString, Toast.LENGTH_SHORT).show();



					Intent intent = new Intent(ChooseRoutine.this, Timer.class);
					intent.putExtra("Routine", routinesStringArray);
					startActivity(intent);

				}
				else{
					Toast.makeText(ChooseRoutine.this, "You have not entered any routines...", Toast.LENGTH_LONG).show();

				}


			}
		});

	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.listViewRoutines);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				TextView textview = (TextView) viewClicked;
				String message = (textview.getText().toString() + " added to Your Routines");
				routinesArray.add(textview.getText().toString());
				Toast.makeText(ChooseRoutine.this, message, Toast.LENGTH_SHORT).show();

			}
		});


	}

	private void populateListView() {
		//Create list of routines
		String[] routines = getResources().getStringArray(R.array.routines);

		//Build adapter 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.stringitem, routines);

		//Configure List view
		ListView list = (ListView) findViewById(R.id.listViewRoutines);
		list.setAdapter(adapter);

	}

}

