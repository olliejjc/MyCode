package com.example.pocketbells1;

import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.pocketbells.sqlite.model.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EnterWeight extends Activity {
	SharedPreferences shared;
	User user;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_weight);
		
		final EditText current = (EditText) (findViewById(R.id.editcurrentWeight));
		final EditText target = (EditText) (findViewById(R.id.edittargetWeight));	
			
		Button confirmWeight = (Button) findViewById(R.id.confirmweightbutton);
		
		confirmWeight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (target.getText().toString().equals("") || current.getText().toString().equals("")){
					Toast.makeText(EnterWeight.this, "Please fill in both fields...", Toast.LENGTH_LONG).show();
				}
				
				else{
					DatabaseHelper db = new DatabaseHelper(getBaseContext());
					shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
					int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set
					 
					user=new User();// declare user
					user.setId(shareId);// set user id  using shared preference
					
					double targetWeight = Double.parseDouble(target.getText().toString());
					double currentweight =Double.parseDouble(current.getText().toString());
					user.setWeight(currentweight);
				
					user.setTargetWeight(targetWeight);
					
					db.updateWeight(user);
					
					
					startActivity(new Intent (EnterWeight.this, MainActivity.class));
				}
				
			}
		});
		
		
	}
}
