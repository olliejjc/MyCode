package com.example.pocketbells1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.pocketbells.sqlite.model.User;
import com.pocketbells.sqlite.model.Workout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHistory extends Activity {

	DatabaseHelper db;
	SharedPreferences shared;
	User user;
	List<Workout> workoutList;
	double calsToday,calsThisMonth,calsAvg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workouthistory);
		db = new DatabaseHelper(getApplicationContext());
		shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
		int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set
		user=db.getUser(shareId);//instantiate user from db
		List<User> users = new ArrayList<User>();
		workoutList=db.getWorkouts(shareId);
		calsToday=getCalsToday(workoutList);
		calsThisMonth=getCalsThisMonth(workoutList);


		Button returnbutton = (Button) findViewById(R.id.whreturnButton);

		double dbDayCals = calsToday;
		double dbMonthCals = calsThisMonth;
		double dbAvgCals = getAvgCals(workoutList, user);
		double dbCurrentWeight =user.getWeight();
		double dbTargetWeight = user.getTargetWeight();

		TextView weekCals = (TextView) findViewById(R.id.whcaloriesPerWeek);
		TextView monthCals = (TextView) findViewById(R.id.whcaloriesPerMonth);
		TextView averageCals = (TextView) findViewById(R.id.whcaloriesAverage);
		TextView currentWeight = (TextView) findViewById(R.id.whcurrentWeight);
		TextView targetWeight = (TextView) findViewById(R.id.whTargetWeight);

		weekCals.setText(dbDayCals + " cals");
		monthCals.setText(dbMonthCals + " cals");
		averageCals.setText(dbAvgCals + " cals");
		currentWeight.setText(dbCurrentWeight + " kg");
		targetWeight.setText(dbTargetWeight + " kg");

		returnbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ViewHistory.this, MainActivity.class));

			}
		});




	}
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd ", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	@SuppressLint("SimpleDateFormat")
	public double getCalsToday(List<Workout> woList){
		double calsTday=0;

		//current system date
		Date dt = new Date();
		// set date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// parse 
		String check = dateFormat.format(dt);

		for (Workout workout:woList){

			//test	
			System.out.println("DATE TO FROM DATABASE   " + workout.getWorkoutDate().toString());
			System.out.println("CURRENT DATE   " + check);
			System.out.println("compare    "+ workout.getWorkoutDate().toString().equals(check));

			//compare
			if (workout.getWorkoutDate().toString().equals(check)){

				calsTday+=workout.getCals();//add cals for each workout to daily total
			}

		}return calsTday;
	}
	@SuppressLint("SimpleDateFormat")
	public double getCalsThisMonth(List<Workout> woList){



		double calsTmonth=0;

		//current system date
		Date dt = new Date();

		// set date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		// parse 
		String check = dateFormat.format(dt);

		for (Workout workout:woList){
			Date workDt= new Date();
			String month = dateFormat.format(workDt);

			//test	
			System.out.println("DATE TO FROM DATABASE   " + month);
			System.out.println("CURRENT DATE   " + check);
			System.out.println("compare    "+ month.equals(check));

			//compare
			if (month.equals(check)){

				calsTmonth+=workout.getCals();//add cals for each workout to monthly total
			}

		}return calsTmonth;
	}
	public double getAvgCals(List<Workout> woList, User user){
		String dateString = user.getStart_date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1 = new Date();
		try {
			date1 = dateFormat.parse(dateString);
		} catch (java.text.ParseException e) {
			System.out.println("parse exception on date format 1 ");
			e.printStackTrace();

		}
		System.out.println("date 1" +date1);

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = dateFormat.format(c.getTime());
		Date date2 = new Date();
		try {
			date2 = dateFormat.parse(formattedDate);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("parse exception on date format 2 ");
		}
		System.out.println("date 2" +date2);
		//Date startDate = user.getStart_date();
		long diff = date1.getTime() - date2.getTime();//difference in milliseconds
		System.out.println("milliseconds=>"+diff);
		long seconds = diff / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		System.out.println("days =>"+days);
		double calsTotal=0;
		double d = (double) days;
		if(d<1.0){
			d=1.0;
		}
		

		for (Workout workout:woList){

			calsTotal+=workout.getCals();//add cals for each workout to daily total
		}
		
		double avgCals=calsTotal/d;
		System.out.println("d =>"+d);
		System.out.println("total =>"+calsTotal);
		System.out.println("avg =>"+avgCals);
		
		return avgCals;
		
	}
}
