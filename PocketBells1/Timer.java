package com.example.pocketbells1;

import java.io.IOException;
import java.net.URI;

import com.example.pocketbells1.R;
//import com.testtimer.TimerActivity.MyCountDownTimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//public class Timer extends Activity {
	public class Timer extends Activity implements OnClickListener {

		private CountDownTimer countDownTimer;
		private CountDownTimer warmTimer;

		private boolean hasStarted = false;
		private boolean hasFarted = false;

		private Button btnStart;

		public TextView disp;
		public TextView rout;
		
		public int loop =0;// loop counter 
		
		private final long startTime = 6 * 1000;
		private final long warmStart=7*1000;

		private final long interval = 1 * 1000;
	

		@Override

		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);

			setContentView(R.layout.activity_timer);

			btnStart = (Button) this.findViewById(R.id.button);

			btnStart.setOnClickListener(this);
			rout = (TextView) this.findViewById(R.id.routine);

			disp = (TextView) this.findViewById(R.id.timer);

			countDownTimer = new MyCountDownTimer(startTime, interval);
			warmTimer = new MyCountDownTimer(warmStart, interval);

			disp.setText(disp.getText() + String.valueOf(startTime / 1000));
			final String[] timerRoutine = getIntent().getStringArrayExtra("Routine");		
			rout.setText(timerRoutine[loop]);
			
			CountDownTimer warmDownTimer =new CountDownTimer(warmStart, 1000) {

			     public void onTick(long millisUntilFinished) {
			         disp.setText("seconds remaining: " + millisUntilFinished / 1000);
			     }

			     public void onFinish() {
			         disp.setText("done!");
			 
			     }
			     
			  };

		}

		@Override

		public void onClick(View v) {
			hasStarted = true;
			CountDownTimer warmTimer =new CountDownTimer(warmStart, 1000) {

			     public void onTick(long millisUntilFinished) {
			         disp.setText("seconds remaining: " + millisUntilFinished / 1000);
			     }

			     public void onFinish() {
			         disp.setText("done!");
			         countDownTimer.start();
			         
			        
			     }
			     
			  };
			  
			  if(!hasFarted) {
			  
			  warmTimer.start();
			  hasFarted=true;
			  
			  hasStarted = false;}
			  else if (!hasStarted&&hasFarted) {
				
				countDownTimer.start();

				hasStarted = true;

				btnStart.setText("STOP");
				

			} else {
				countDownTimer.cancel();
				hasStarted = false;
				btnStart.setText("RESTART");
			}
		}
		
		

		public class MyCountDownTimer extends CountDownTimer {

		



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
					
					disp.setText("Workout Complete!");
					
					
					
				}
				
				while (loop<timerRoutine.length){
					rout.setText(timerRoutine[loop]);
					countDownTimer.start();
					loop++;
					
					break;							
				}
				
			}
			



			@Override

			public void onTick(final long millisUntilFinished) {

				disp.setText(""+ millisUntilFinished / 1000);
				
				if (millisUntilFinished / 1000 <= 5){ 

					  new Runnable(){
					
					
					@SuppressLint("DefaultLocale")
					@Override
					public void run() {
						// get current exercise from textview
						String routTxt =rout.getText().toString();
						//clean string
						String routTxtLr= routTxt.toLowerCase().replace(" ", "");
						//String routTxtTrm =routTxtLr.trim();
						//String routTxtTrm = routTxtLr.replace(" " , "");
						// get audio file ID using cleaned string
						int drawableID=getResources().getIdentifier(routTxtLr, "drawable", "com.example.pocketbells1");
						 
						
						if ( millisUntilFinished / 1000 == 4){
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
					           
					       }
						
				}.run();
				
			}
			}
			
		}

		
	}








	
	
	/*Boolean paused = true;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer);

		final String[] beginnersRoutine = getIntent().getStringArrayExtra("beginnersRoutine"); 

		 final CountDownTimer timer = new CountDownTimer(10000, 1000) {

			TextView t = (TextView) findViewById(R.id.timerText);
			//Counter counter = new Counter(10000, 1000);

			public void onTick(long millisUntilFinished) {
				t.setText("seconds remaining: " + millisUntilFinished / 1000);
			}
			

			public void onFinish() {
				t.setText("Done");
				timer.start(); 
				
				paused = false;
			}
		} .start();
		TextView r = (TextView) findViewById(R.id.routineText);
		TextView t = (TextView) findViewById(R.id.timerText);
		AsyncTaskStaller runner = new AsyncTaskStaller();
	    String sleepTime ="10000";
	   
		/*int i=0;
		
		do {
			switch(i){
			case 0:
				r.setText(beginnersRoutine[i]);
				timer.start();
				 runner.execute(sleepTime);
				
			}
			
			
			
		}
		while (i<3);*/
			
		//for (String s : beginnersRoutine){
	    
	    	 //r.setText("one");
	    	 //timer.start();	
	    	 //r.setText("two");
	    	// timer.start();
	    	// r.setText("fuck");
	    	// timer.start();
	    	// timer.cancel();
	   // }
	   
		
			
			/*r.setText(s);
			t.setText("seconds remaining: " +00.00);
			while (!t.equals("Done")){
				t.setText("Done");
				timer.start();
					
		}*/
			//paused = true;
		
		//}

	

	//}	


