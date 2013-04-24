package com.wifiplayer.activitys;

import java.util.Timer;
import java.util.TimerTask;

import com.wifiplayer.R;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				startActivity(new Intent(MainActivity.this, FunctionActivity.class));
				finish();
			}
		}, 3000);
		
		
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
