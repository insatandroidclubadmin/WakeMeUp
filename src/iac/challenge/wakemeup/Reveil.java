package iac.challenge.wakemeup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import iac.challenge.sql.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;

public class Reveil extends Activity {

MyAlarmManager alarmMgr;
	Alarm al;

	Integer h, m;
	Boolean active;
	ToggleButton on;
	TimePicker timePicker;
	Calendar reveil;
	TextView dateText;
	Date date;
	private PendingIntent pendingIntent;
	static final int ALARM_ID = 13405811;
	String t1;
	String t2;
	String t3;
	private MySettingsManager setMgr;
	private Setting sets;
	private String ch1;
	private String ch3;
	private String ch2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reveil);
		ch1 = getResources().getString(R.string.ch1);
		 ch2 = getResources().getString(R.string.ch2);
		ch3 = getResources().getString(R.string.ch3);

		active = false;
		alarmMgr = new MyAlarmManager(this);
		alarmMgr.open();
		al = alarmMgr.getAlarm();
		alarmMgr.close();
		on = (ToggleButton) findViewById(R.id.toggle);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		
		if (al != null) {
			on.setChecked(al.getActive());
			timePicker.setCurrentHour(al.getHour());
			timePicker.setCurrentMinute(al.getMin());
		} else on.setChecked(false);
		timePicker.setOnTimeChangedListener(timePickerDialogCallBack);
		reveil = Calendar.getInstance();
		reveil.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
		reveil.set(Calendar.MINUTE, timePicker.getCurrentMinute());
		date = new Date();
		SimpleDateFormat dateStandard = new SimpleDateFormat("dd - MM - yyyy");
		dateText = (TextView) findViewById(R.id.date);
		dateText.setText(dateStandard.format(date));

		on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				al = new Alarm();
				al.setId(1);
				if (isChecked) {
					active = true;

					al.setActive(1);
					planifier();
				}

				else {
					active = false;
					al.setActive(0);
					annuler();
				}
				h = reveil.get(Calendar.HOUR_OF_DAY);
				m = reveil.get(Calendar.MINUTE);
				al.setHour(h);
				al.setMin(m);
				alarmMgr.open();
				alarmMgr.saveAlarm(al);
				alarmMgr.close();

			}
		});
		
		setMgr = new MySettingsManager(this);
		setMgr.open();
		sets = setMgr.getSettings();
		setMgr.close();
		
		if (sets == null) 
		{
			sets = new Setting();
			sets.setId(1);
			sets.setLight(50);
			sets.setMode(0);
			sets.setVolume(50);
			sets.setSound(0);
			setMgr.open();
			setMgr.saveSettings(sets);
			setMgr.close();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_reveil, menu);
		return true;
	}

	public void planifier() {
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Toast.makeText(
				this,
				"Alarme plannifiée à " + reveil.get(Calendar.HOUR_OF_DAY) + ":"
						+ +reveil.get(Calendar.MINUTE), Toast.LENGTH_LONG)
				.show();
		Intent myIntent = new Intent(Reveil.this, AlarmReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(Reveil.this, ALARM_ID,
				myIntent, 0);
		am.cancel(pendingIntent);
		Calendar cal = Calendar.getInstance();
		reveil.set(Calendar.SECOND, 0);
		cal.set(Calendar.SECOND, 0);
		long diff = reveil.getTimeInMillis() - cal.getTimeInMillis();
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + diff,
				pendingIntent);

	}

	public void annuler() {
		Toast.makeText(this, "Alarme annulée", Toast.LENGTH_SHORT).show();
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);

	}

	OnTimeChangedListener timePickerDialogCallBack = new OnTimeChangedListener() {

		@Override
		public void onTimeChanged(TimePicker picker, int hours, int mins) {
			if (active) {
				annuler();
				on.setChecked(false);
			}
			reveil = Calendar.getInstance();
			reveil.set(Calendar.HOUR_OF_DAY, hours);
			reveil.set(Calendar.MINUTE, mins);
			if (reveil.compareTo(Calendar.getInstance()) == -1)
				reveil.add(Calendar.DAY_OF_YEAR, 1);
		}
	};

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
	}

	public void onClickSettings(View view) {
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
	}

}
