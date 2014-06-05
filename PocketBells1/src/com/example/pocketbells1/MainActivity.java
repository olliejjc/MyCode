package com.example.pocketbells1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	//Bundle extras = getIntent().getExtras();
	//int id = extras.getInt("user_id");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button goalsButton = (Button) findViewById(R.id.AMGoalButton);
		Button routinesButton = (Button) findViewById(R.id.AMRoutineButton);
		Button tutorialsButton = (Button) findViewById(R.id.AMTutorialButton);
		
		routinesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(getApplicationContext(), Routines.class);
				//i.putExtra("user_id",id);
				//startActivity(i);
				startActivity(new Intent(MainActivity.this, Routines.class));
				
			}
		});
		
		tutorialsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Tutorials.class));
				
			}
		});
		
		goalsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, GoalMenu.class));
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
