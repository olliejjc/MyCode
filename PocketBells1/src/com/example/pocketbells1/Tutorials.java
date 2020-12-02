package com.example.pocketbells1;

import com.example.pocketbells1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Tutorials extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorialslist);
		
		Button returnButton = (Button) findViewById(R.id.treturnButton);

		TextView excercise1 = (TextView) findViewById(R.id.exercise1);
		excercise1.setText("Burpees");

		TextView excercise2 = (TextView) findViewById(R.id.exercise2);
		excercise2.setText("Front Squats");

		TextView excercise3 = (TextView) findViewById(R.id.exercise3);
		excercise3.setText("Snatches");

		TextView excercise4 = (TextView) findViewById(R.id.exercise4);
		excercise4.setText("KettleBell Swings");

		TextView excercise5 = (TextView) findViewById(R.id.exercise5);
		excercise5.setText("Turkish Get Ups");

		ImageButton excerciseButton1 = (ImageButton) findViewById(R.id.exerciseButton1);
		ImageButton excerciseButton2 = (ImageButton) findViewById(R.id.exerciseButton2);
		ImageButton excerciseButton3 = (ImageButton) findViewById(R.id.exerciseButton3);
		ImageButton excerciseButton4 = (ImageButton) findViewById(R.id.exerciseButton4);
		ImageButton excerciseButton5 = (ImageButton) findViewById(R.id.exerciseButton5);

		excerciseButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Tutorials.this, VideoPlayer.class);
				intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbburpeesgalaxyn.mp4");
				//intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbburpeesgalaxyy.mp4");
				startActivity(intent);
			}
		});

		excerciseButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Tutorials.this, VideoPlayer.class);
				intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbfrontsquatgalaxyn.mp4");
				//intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbfrontsquatgalaxyy.mp4");
				startActivity(intent);	
			}
		});

		excerciseButton3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Tutorials.this, VideoPlayer.class);
				intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbsnatchgalaxyn.mp4");
				//intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbsnatchgalaxyy.mp4");
				startActivity(intent);

			}
		});

		excerciseButton4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Tutorials.this, VideoPlayer.class);
				intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbswinggalaxyn.mp4");
				//intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbswinggalaxyy.mp4");
				startActivity(intent);
			}
		});

		excerciseButton5.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Tutorials.this, VideoPlayer.class);
				intent.putExtra("videoSource", "http://danu6.it.nuigalway.ie/fitapp/videos/kbturkishgalaxyn.mp4");
				//intent.putExtra("videoSource","http://danu6.it.nuigalway.ie/fitapp/videos/kbturkishgalaxyy.mp4");
				startActivity(intent);

			}
		});

		returnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Tutorials.this, MainActivity.class));
				
			}
		});
	}

}
