package com.example.pocketbells1;
import com.example.pocketbells1.R;
import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.pocketbells.sqlite.model.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class UpdateDetails extends Activity {
	SharedPreferences shared;
	User user;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DatabaseHelper db = new DatabaseHelper(getBaseContext());
		shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
		int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set 
		user=new User();
		user=db.getUser(shareId);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatedetails);
		
		Button returnButton = (Button) findViewById(R.id.udreturnbutton);
		
		final int userID = getIntent().getIntExtra("id", -1 );
		Toast.makeText(this, "udid: " + userID, Toast.LENGTH_LONG).show();
		final double currentWeight =db.getWeight(shareId, "weight");;//db.getWeight(shareId, "weight");//get form db
		db.close();
		final double goalWeight = user.getTargetWeight();//db.getWeight(shareId, "tweight");;//db.getWeight(shareId, "target_weight");//get form db
		db.close();
		final TextView currentweightDisplay = (TextView) findViewById(R.id.currentweighttext);
		currentweightDisplay.setText(currentWeight + " kg");
		
		final TextView goalweightDisplay = (TextView) findViewById(R.id.goalweighttext);
		goalweightDisplay.setText(goalWeight + " kg");
		
		Button currentWeightButton = (Button) findViewById(R.id.currentupdatebutton);
		Button goalWeightButton = (Button) findViewById(R.id.goalupdatebutton);
		
		currentWeightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder currentalert = new AlertDialog.Builder(UpdateDetails.this);
 
				currentalert.setTitle("Update Current Weight");
				currentalert.setMessage("Enter new weight:");

				// Set an EditText view to get user input 
				final EditText input = new EditText(UpdateDetails.this);
				currentalert.setView(input);

				currentalert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@SuppressLint("ShowToast")
				public void onClick(DialogInterface dialog, int whichButton) {
				String value = (input.getText().toString());
				currentweightDisplay.setText(value);
				if (isNumber(value)){
					
					double doublevalue = Double.parseDouble(value);
					currentweightDisplay.setText(doublevalue + " kg");
					DatabaseHelper db = new DatabaseHelper(getBaseContext());
					shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
					int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set 
					user=new User();// declare user
					user=db.getUser(shareId);//initialise user from db
					//user.setId(shareId);// set user id  using shared preference
					user.setWeight(doublevalue); //set current weight
					//double currweight =db.getWeight(shareId, "weight");
					//db.close();
					//double tweight =db.getWeight(shareId, "tweight");
					
					//user.setTargetWeight(currweight); //set current weight
					//double currweight =db.getWeight(shareId, "target_weight");
					//double tar =db.getWeight(shareId, "target_weight");
					//user.setTargetWeight(tweight);
					db.updateCurrentWeight(user);//write to db
					db.close();
				}
				else{
					currentweightDisplay.setText(currentWeight + "kg");
					Toast.makeText(UpdateDetails.this, "Invalid Entry - Please try again...", Toast.LENGTH_LONG).show();
				}

				  // Do something with value!
				  }
				});

				currentalert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});
				
				currentalert.show();
			}
		});
		
		goalWeightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			
			public void onClick(View v) {
				AlertDialog.Builder goalalert = new AlertDialog.Builder(UpdateDetails.this);

				goalalert.setTitle("Update Target Weight");
				goalalert.setMessage("Enter new weight:");

				// Set an EditText view to get user input 
				final EditText input1 = new EditText(UpdateDetails.this);
				goalalert.setView(input1);

				goalalert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@SuppressLint("ShowToast")
				public void onClick(DialogInterface dialog, int whichButton) {
				String value = (input1.getText().toString());
				goalweightDisplay.setText(value);
				if (isNumber(value)){
					double doublevalue = Double.parseDouble(value);
					goalweightDisplay.setText(doublevalue + " kg");
					
					DatabaseHelper db = new DatabaseHelper(getBaseContext());
					shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
					int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set 
					user=new User();// declare user
					user.setId(shareId);// set user id  using shared preference
					//double currweight =db.getWeight(shareId, "weight");
					//user.setWeight(currweight); //set current weight
					user.setTargetWeight(doublevalue);
					db.updateTargetWeight(user);//write to db
				}
				else{
					goalweightDisplay.setText(goalWeight + "kg");
					Toast.makeText(UpdateDetails.this, "Invalid Entry - Please try again...", Toast.LENGTH_LONG).show();
				}

				  // Do something with value!
				  }
				});

				goalalert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});
				
				goalalert.show();
			}
		});
		
		returnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(UpdateDetails.this, MainActivity.class));
				
			}
		});
	}		
	
	@SuppressLint("NewApi")
	public static boolean isNumber(String string) {
	    if (string == null || string.isEmpty()) {
	        return false;
	    }
	    int i = 0;
	    if (string.charAt(0) == '-') {
	        if (string.length() > 1) {
	            i++;
	        } else {
	            return false;
	        }
	    }
	    for (; i < string.length(); i++) {
	        if (!Character.isDigit(string.charAt(i))) {
	            return false;
	        }
	    }
	    return true;
	
	}
}


