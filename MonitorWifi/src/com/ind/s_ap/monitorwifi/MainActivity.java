package com.ind.s_ap.monitorwifi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btnStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.btnStart = (Button) findViewById(R.id.btnStart);
		this.btnStart.setVisibility(View.GONE);

		moveTaskToBack(true);
	}

	// private void startService() {
	// Intent intent1 = new Intent();
	// intent1.setClass(MainActivity.this, Service1.class);
	// startService(intent1);
	//
	// Intent intent2 = new Intent();
	// intent2.setClass(MainActivity.this, Service2.class);
	// startService(intent2);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
