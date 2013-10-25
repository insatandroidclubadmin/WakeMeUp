package iac.challenge.wakemeup;

import iac.challenge.sql.MyAlarmManager;
import iac.challenge.sql.MySettingsManager;
import iac.challenge.sql.Setting;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ItsTime extends Activity {
	MyAlarmManager alarmMgr;
	private MediaPlayer mp = null;
	private MySettingsManager setMgr;
	private Setting sets;

	TextView time;
	private String ch1;
	private String ch2;
	private String ch3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_its_time);
		ch1 = getResources().getString(R.string.ch1);
		ch2 = getResources().getString(R.string.ch2);
		ch3 = getResources().getString(R.string.ch3);
		setMgr = new MySettingsManager(this);
		setMgr.open();
		sets = setMgr.getSettings();
		setMgr.close();
		alarmMgr = new MyAlarmManager(this);
		alarmMgr.open();
		alarmMgr.clear();
		alarmMgr.close();
		time = (TextView) findViewById(R.id.time);
		Date d = new Date();
		SimpleDateFormat dateStandard = new SimpleDateFormat("HH:mm");
		time.setText(dateStandard.format(d));
		if (sets.getSound() == 0)
			mp = MediaPlayer.create(this, R.raw.harlemshake);
		if (sets.getSound() == 1)
			mp = MediaPlayer.create(this, R.raw.gstyle);
		if (sets.getSound() == 2)
			mp = MediaPlayer.create(this, R.raw.talking);
			float v = (float) sets.getVolume()/100;
			mp.setVolume(v, v);
			mp.start();
		SensorManager mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		Sensor LightSensor = mySensorManager
				.getDefaultSensor(Sensor.TYPE_LIGHT);

		if (LightSensor != null) {
			mySensorManager.registerListener(LightSensorListener, LightSensor,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	private final SensorEventListener LightSensorListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
				if (event.values[0] >= 100 + sets.getLight()*3) {
					mp.stop();
					finish();
				}

			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_its_time, menu);
		return true;
	}

	public void onClickInfo(View view) {
		Toast.makeText(getApplicationContext(),
				ch1,
				Toast.LENGTH_LONG).show();
		Toast.makeText(
				getApplicationContext(),
				ch2,
				Toast.LENGTH_LONG).show();
		Toast.makeText(
				getApplicationContext(),
				ch3,
				Toast.LENGTH_LONG).show();
		Toast.makeText(
				getApplicationContext(),
				"INSAT ANDROID CLUB 2013 (c) \n \n GHDIRI Selim\n THABET Mohamed Wael \n THABTI Mariem",
				Toast.LENGTH_LONG).show();

	}

	public void onClickHome(View view) {

		Intent i = new Intent(this, Reveil.class);
		startActivity(i);
	}

	public void onClickSettings(View view) {
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
	}
	
}
