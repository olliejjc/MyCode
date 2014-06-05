package com.example.pocketbells1;
import com.example.pocketbells1.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoPlayer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		
		String videoSource = getIntent().getExtras().getString("videoSource");
		
        VideoView videoView = (VideoView) findViewById(R.id.excerciseVideo);
        videoView.setVideoPath(videoSource);
        
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        
        videoView.start();
		
		
	}
}
