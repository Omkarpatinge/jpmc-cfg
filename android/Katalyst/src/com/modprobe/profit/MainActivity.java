package com.modprobe.profit;



import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends ActionBarActivity {
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "MainActivity";
	private BroadcastReceiver mRegistrationBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sendRegistration();//Account followed by gcm
		setupReciever();//Callback
		AppController app = AppController.getInstance();
		Editor edit = app.prefs.edit();
		edit.putBoolean("mentor", false);
		edit.putString("mobile","9029415675");
//		edit.putString("mobile","9322503511");
		edit.commit();
//		if(app.prefs.getBoolean("first",true)){
//			//Take to edit screen
//		}
//		else{
//			
//		}
				getSupportFragmentManager().beginTransaction()
						.add(R.id.container, new MainListFragment()).commit();
		}
	
	
	
	private boolean checkPlayServices() {
		GoogleApiAvailability apiAvailability = GoogleApiAvailability
				.getInstance();
		int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (apiAvailability.isUserResolvableError(resultCode)) {
				apiAvailability.getErrorDialog(this, resultCode,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}
	
	@Override
	protected void onResume() {

		Log.e("MAIN", "RESUME");
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mRegistrationBroadcastReceiver,
				new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
		super.onResume();
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mRegistrationBroadcastReceiver);
		super.onPause();
	}
	
	protected void sendRegistration(){
		TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		final String uuid = tManager.getDeviceId();
		if (checkPlayServices()) {
			// Start IntentService to register this application with GCM.
			Intent intent = new Intent(MainActivity.this,
					RegistrationIntentService.class);
			startService(intent);
		}
	}
	
	protected void setupReciever(){
		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				SharedPreferences sharedPreferences = PreferenceManager
						.getDefaultSharedPreferences(context);
				boolean sentToken = sharedPreferences.getBoolean(
						QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
				if (sentToken) {
					Log.e("GCM", "Done");
				} else {
					Log.e("GCM", "Not Done");
				}
			}
		};
	}
	
}
