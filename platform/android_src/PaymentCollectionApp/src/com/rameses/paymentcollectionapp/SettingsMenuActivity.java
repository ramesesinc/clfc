package com.rameses.paymentcollectionapp;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsMenuActivity extends ControlActivity {
	private Context context = this;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.settings:
					Intent intent = new Intent(context, Settings.class);
					startActivity(intent);
					break;
		}
		return true;
	}
}
