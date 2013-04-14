package com.wifiplayer.activitys;

import java.util.Timer;
import java.util.TimerTask;

import com.wifiplayer.R;
import com.wifiplayer.R.layout;
import com.wifiplayer.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				startActivity(new Intent(MainActivity.this, FunctionActivity.class));
				finish();
			}
		}, 1000);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
