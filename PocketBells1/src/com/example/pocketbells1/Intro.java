package com.example.pocketbells1;
import com.example.pocketbells1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Intro extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		
		ImageView myImageView = (ImageView) findViewById(R.id.introImage);
		Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
		
		myImageView.startAnimation(myFadeInAnimation);
		
		CountDownTimer introTimer = new CountDownTimer(10000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				startActivity(new Intent(Intro.this, Login.class));
				
			}
		};
		
		introTimer.start();
	}

}
