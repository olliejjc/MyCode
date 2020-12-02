package com.example.pocketbells1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GoalMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goalsmenu);
		
		Button updateButton = (Button) findViewById(R.id.gmupdatebutton);
		Button historyButton = (Button) findViewById(R.id.gmworkouthistorybutton);
		
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GoalMenu.this, UpdateDetails.class));
				
			}
		});
		
		historyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GoalMenu.this, ViewHistory.class));
				
			}
		});
	}
}
