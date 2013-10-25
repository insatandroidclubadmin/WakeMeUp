package iac.challenge.wakemeup;

import java.text.SimpleDateFormat;
import java.util.Date;

import iac.challenge.sql.MyAlarmManager;
import iac.challenge.sql.MySettingsManager;
import iac.challenge.sql.Setting;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ItsMathsTime extends Activity {

	MyAlarmManager alarmMgr;
	private MediaPlayer mp = null;
	TextView time;
	private String ch1;
	private String ch2;
	private String ch3;
	private String fch;
	private MySettingsManager setMgr;
	private Setting sets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_its_maths_time);
		ch1 = getResources().getString(R.string.ch1);
		ch2 = getResources().getString(R.string.ch2);
		ch3 = getResources().getString(R.string.ch3);
		fch = getResources().getString(R.string.faux);
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
		float v = (float) sets.getVolume() / 100;
		mp.setVolume(v, v);
		mp.start();

		TextView op = (TextView) findViewById(R.id.MathOp);
		final int a = (int) (Math.random() * (20));
		final int b = (int) (Math.random() * (20));
		op.setText(a + " + " + b + " =  ");

		Button ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText res = (EditText) findViewById(R.id.res);
				int r = Integer.parseInt(res.getText().toString());
				if (r == (a + b))
				{
					mp.stop();
					Toast.makeText(getApplicationContext(), "Bonjour ! ",
							Toast.LENGTH_SHORT).show();
					onClickHome(v);
					
				}
				else
					Toast.makeText(getApplicationContext(), fch,
							Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_its_maths_time, menu);
		return true;
	}

	public void onClickInfo(View view) {
		Toast.makeText(getApplicationContext(), ch1, Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), ch2, Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), ch3, Toast.LENGTH_LONG).show();
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
