package com.example.pocketbells1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.pocketbells1.R;
import com.example.pocketbells1.RegisterAdapter;
import android.app.Activity; 
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Toast;
import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.pocketbells.sqlite.model.*;

public class SignupActivity extends Activity 
{ 
	EditText editTextUsername,editTextPassword,editTextEmail; 
	Button btnregister;
	Workout workouts;
	
	DatabaseHelper db;

	RegisterAdapter register; 
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_signup);
		// get Instance  of DatabaseHelper
		db = new DatabaseHelper(getApplicationContext());
		// get Instance of User
		final User user= new User();

		// get Instance  of Database Adapter 
		register=new RegisterAdapter(this); 
		register=register.open();

		// Get References of Views 
		editTextUsername=(EditText)findViewById(R.id.editTextUsername); 
		editTextPassword=(EditText)findViewById(R.id.editTextPassword); 
		editTextEmail=(EditText)findViewById(R.id.editTextEmail);

		btnregister=(Button)findViewById(R.id.registerbutton);

		btnregister.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) { 
				// TODO Auto-generated method stub

				String userName=editTextUsername.getText().toString(); 
				String password=editTextPassword.getText().toString(); 
				//String confirmPassword=editTextEmail.getText().toString();
				String email=editTextEmail.getText().toString();

				// check if any of the fields are vacant 
				if(userName.equals("")||password.equals("")||email.equals("")) 
				{ 
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show(); 
					return; 
				} 
				// check if both password matches

				else 
				{ 
					// Save the Data in Database
					
					//set User members
					user.setUserName(userName);
					user.setEmail(email);
					user.setPassword(password);
					user.setStartDate(getDateTime());
					//insert user into db 
					db.createUser(user);
					
					
					int id= db.getUserId(userName);
					User user = db.getUser(id);
					 
					SharedPreferences shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE); // get the set of Preferences labeled "A"
					//creating a SharedPreferences.Editor by calling edit() on shared
					final Editor editor = shared.edit();
					editor.putInt("id", id);  // set value
					editor.commit(); // commit to save changes
					int dispID = shared.getInt("id", id);
					Toast.makeText(getApplicationContext(), "Your id is " + dispID, Toast.LENGTH_SHORT).show();

					//register.insertEntry(userName, password,email); 
					Toast.makeText(getApplicationContext(), "Registered Successfully ", Toast.LENGTH_LONG).show(); 
					startActivity(new Intent(SignupActivity.this, EnterWeight.class));
				} 
			} 
		}); 
	} 
	@Override 
	protected void onDestroy() { 
		// TODO Auto-generated method stub 
		super.onDestroy();

		register.close(); 
	} 
	//get datetime
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}

