package com.example.pocketbells1;
import com.example.pocketbells1.R;

import android.app.Activity; 
import android.content.Intent; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button;

public class Login extends Activity 
{  
	Button registerbutton;
	Button loginbutton;
	RegisterAdapter register;

	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.login);

		// create a instance of SQLite Database 
		register=new RegisterAdapter(this); 
		register=register.open();

		// Get The Reference Of Buttons 
		registerbutton=(Button)findViewById(R.id.reg);

		// Set OnClick Listener on SignUp button 
		registerbutton.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
				// TODO Auto-generated method stub

				/// Create Intent for SignUpActivity  and Start The Activity 
				Intent registerintent=new Intent(getApplicationContext(),SignupActivity.class); 
				startActivity(registerintent); 
			} 
		}); 
		loginbutton=(Button)findViewById(R.id.login);

		//Set OnClick Listener on SignUp button 
		loginbutton.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
				//TODO Auto-generated method stub

				//Create Intent for SignUpActivity  and Start The Activity 
				Intent registerintent=new Intent(getApplicationContext(),LoginActivity.class); 
				startActivity(registerintent); 
				
			} 
		}); 
	}

	@Override 
	protected void onDestroy() { 
		super.onDestroy(); 
		// Close The Database 
		register.close(); 
	} 
}
