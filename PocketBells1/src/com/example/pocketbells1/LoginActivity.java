package com.example.pocketbells1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.pocketbells.sqlite.model.*;
import android.app.Activity; 
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle; 
import android.util.Log;
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Toast;


public class LoginActivity extends Activity 
{ 
	EditText editTextUsername,editTextPassword,editTextEmail; 
	Button btnlogin;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	private static final String USERNAME = null;
	private static final String USER_ID = "";// not used yet
	private static final int  LOOPINDEX=0;

	DatabaseHelper db;

	RegisterAdapter register; 
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_login);
		// get Instance  of DatabaseHelper
		db = new DatabaseHelper(getApplicationContext());
		//Get instance of user
		final User user= new User();

		// get Instance  of Database Adapter 
		//register=new RegisterAdapter(this); 
		//register=register.open();

		// Get References of Views 
		editTextUsername=(EditText)findViewById(R.id.editTextUsername); 
		editTextPassword=(EditText)findViewById(R.id.editTextPassword);

		@SuppressWarnings("unused")
		int id;  

		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		if (sharedpreferences.contains(USERNAME))
		{
			editTextUsername.setText(sharedpreferences.getString(USERNAME, "editTextUsername"));

		}


		btnlogin=(Button)findViewById(R.id.loginbutton);

		btnlogin.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) { 
				// TODO Auto-generated method stub

				String userName=editTextUsername.getText().toString(); 
				String password=editTextPassword.getText().toString(); 
				//String confirmPassword=editTextEmail.getText().toString();

				// check for empty fields 
				if(userName.equals("")||password.equals("")) 
				{ 
					Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show(); 
					return; 
				} 

				else 
				{ 
					// check if both passwords match
					if( db.getUserId(userName) == -1){
						Toast.makeText(getApplicationContext(), "No Record Found, Try Again", 1000).show();
					}else{
						int idd= db.getUserId(userName);

						//Toast.makeText(getApplicationContext(), "Incorrect details, try again ", Toast.LENGTH_LONG).show();

						User user = db.getUser(idd);

						final String pass= user.getPassword();//register..getPassword();

						//if entered password matches db
						if (password.equals(pass)){
							Toast.makeText(getApplicationContext(), "Logged In ", Toast.LENGTH_LONG).show();

							//get userID from db to use as sharedpreference for updates
							int id= db.getUserId(userName);

							SharedPreferences shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE); // get the set of Preferences labeled "A"
							//creating a SharedPreferences.Editor by calling edit() on shared
							final Editor editor = shared.edit();

							editor.putInt("id", idd);  // set value
							editor.commit(); // commit to save changes
							int dispID = shared.getInt("id", id);
							Toast.makeText(getApplicationContext(), "Your id is " + dispID, Toast.LENGTH_SHORT).show();


							try {   
								MailSender sender = new MailSender("nuigfitapp@gmail.com", "fitapppass1");
								sender.sendMail("Thank you for regigistering with Fitapp",   
										"Your password is "+pass,   
										"nuigfitapp@gmail.com",   
										"nuigfitapp@gmail.com");//from??   
							} catch (Exception e) {   
								Log.e("SendMail", e.getMessage(), e);   
							} 

							startActivity(new Intent(LoginActivity.this, MainActivity.class));
						}
					}
				}

			} 
		}); 
	} 

	@Override 
	protected void onDestroy() { 
		// TODO Auto-generated method stub 
		super.onDestroy();

		db.close(); 
	} 

}
