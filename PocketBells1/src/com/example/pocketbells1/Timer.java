package com.example.pocketbells1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.pocketbells.sqlite.model.*;
import com.pocketbells.sqlite.helper.DatabaseHelper;
import com.example.pocketbells1.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//public class Timer extends Activity {
public class Timer extends Activity implements OnClickListener {

	private CountDownTimer workoutTimer;
	private CountDownTimer warmUpTimer;

	private boolean hasStarted = false;
	private boolean hasWarmedUp = false;

	private Button btnStart;
	Button btnSave;

	public TextView disp;
	public TextView rout;

	public int loop =0;// loop counter
	public int minutes=0;

	private final long startTime = 12 * 1000;
	private final long warmStart=15*1000;

	private final long interval = 1 * 1000;
	String userName;
	WorkoutAdapter workout;
	int cals = 30*2;
	double weight =12.5;
	SharedPreferences shared;
	DatabaseHelper db;
	User user;
	Workout workouts;
	/*Average calories burned <125 90 p/30 min
	 *  >125 122 p/min ; >185 133 p/min; add 30 cal per 30 min for variance in activity
	  */ 
	static double caloriesBurned;
	static double userPounds; 
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		//shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
		//int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set
		

		setContentView(R.layout.activity_timer);

		btnStart = (Button) this.findViewById(R.id.button);

		btnStart.setOnClickListener(this);
		
		
		rout = (TextView) this.findViewById(R.id.routine);

		disp = (TextView) this.findViewById(R.id.timer);

		workoutTimer = new MyCountDownTimer(startTime, interval);
		warmUpTimer = new WarmupTimer(warmStart, interval);

		disp.setText(disp.getText() + String.valueOf(startTime / 1000));
		final String[] timerRoutine = getIntent().getStringArrayExtra("Routine");
		minutes=timerRoutine.length;
		rout.setText("Warm up");

		//Toast.makeText(getApplicationContext(), "Your id is " +shareId, Toast.LENGTH_SHORT).show();//test sharedpref
		

	}

	@Override

	public void onClick(View v) {


		if(!hasWarmedUp) {

			warmUpTimer.start();
			hasWarmedUp=true;
			hasStarted = false;
			btnStart.setText("STOP");
			
			
		}
		else if (!hasStarted&&hasWarmedUp) {
			warmUpTimer.cancel();
			workoutTimer.start();
			hasStarted = true;
			btnStart.setText("STOP");

		} 
		else {
			workoutTimer.cancel();
			hasStarted = false;
			btnStart.setText("RESTART");
			
		}
	}



	public class MyCountDownTimer extends CountDownTimer {
		
		//constructor
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			final String[] timerRoutine = getIntent().getStringArrayExtra("Routine");
			

			if (loop==0){
				loop++;
			}
			if (loop== timerRoutine.length){
				//play exercise completed audio
				MediaPlayer mp = MediaPlayer.create(getBaseContext(), (R.drawable.finish));
				mp.start();

				disp.setText("Workout Complete!");
				shared = getSharedPreferences("MYPrefs", Context.MODE_PRIVATE);   // get the sharedpreference set named "MYPrefs"
				int shareId = shared.getInt("id", 0);  // get value from key but return 0 if nothing is set
				 
				user=new User();// declare user
				user.setId(shareId);// set user id  using shared preference
				
				
				if (user.getWeight() < 125){
				 	caloriesBurned = 4 * timerRoutine.length;
				}
				 	
				 else if (user.getWeight() > 155){
				 	caloriesBurned = 4.7 * timerRoutine.length;
				 	}
				 	
				 else{
				 	caloriesBurned = 5.4 * timerRoutine.length;
				 	}
				//Declare and instantiate workout using constructor
				Workout workout = new Workout(shareId,user.getWeight(),caloriesBurned,getDateTime()); // create new instance of Workout
				//Declare and instantiate DatabaseHelper
				DatabaseHelper db = new DatabaseHelper(getBaseContext());
				//write to db
				db.createWorkout(workout);
				Toast.makeText(getApplicationContext(), "You burned " +caloriesBurned + " calories", Toast.LENGTH_LONG).show();
				startActivity(new Intent(Timer.this, MainActivity.class));
	

			}

			while (loop<timerRoutine.length){
				rout.setText(timerRoutine[loop]);
				workoutTimer.start();
				loop++;

				break;							
			}

		}

		@Override

		public void onTick(final long millisUntilFinished) {

			disp.setText(""+ millisUntilFinished / 1000);

			if (millisUntilFinished / 1000 <= 10){ 

				new Runnable(){


					@SuppressLint("DefaultLocale")
					@Override
					public void run() {
						// get current exercise from textview
						String routTxt =rout.getText().toString();
						//clean string
						String routTxtLr= routTxt.toLowerCase().replace(" ", "");
						// get audio file ID using cleaned string
						int drawableID=getResources().getIdentifier(routTxtLr, "drawable", "com.example.pocketbells1");


						if ( millisUntilFinished / 1000 == 9){
							//play audio file (exercise name)
							playSound(drawableID);
							// display exercise name
							disp.setText(""+ routTxt);

						}
						if (millisUntilFinished / 1000 == 1){
							//last second of rep -play final beep
							playSound(R.drawable.beeep);}

						else if (millisUntilFinished / 1000 <= 3){
							//last seconds of rep -play warning beeps
							playSound(R.drawable.beep);}


					}

					public void playSound(int sound) {

						MediaPlayer mp = MediaPlayer.create(getBaseContext(), (sound));
						mp.start();
						//setOnCompletionListener(mp.OnCompletionListener, 1);
						mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
				        {           
				            public void onCompletion(MediaPlayer mp) 
				            {
				               mp.release();

				            }           
				        }); 
						
						    
						

						
					}

				}.run();

			}
		}

	}
	public class WarmupTimer extends MyCountDownTimer {
		final String[] timerRoutine = getIntent().getStringArrayExtra("Routine");		


		public WarmupTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);

		}

		// onTick inherited from workout timer not overidden

		@Override // overridden onFinish to calls workout timer
		public void onFinish() {
			rout.setText(timerRoutine[loop]);
			workoutTimer.start();

		}


	}
	//get datetime
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	/*public void updateWorkout(){
		workouts = new Workout(6,150.0,30,getDateTime()); // create new instance of Workout
		DatabaseHelper db = new DatabaseHelper(getBaseContext());
		db.createWorkout(workouts);
		
		
	}*/
}

