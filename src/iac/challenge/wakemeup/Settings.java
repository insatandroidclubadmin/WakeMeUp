package iac.challenge.wakemeup;

import iac.challenge.sql.MyAlarmManager;
import iac.challenge.sql.MySettingsManager;
import iac.challenge.sql.Setting;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity {
	String ch1 ;
	String ch2 ;
	String ch3 ;

	MySettingsManager setMgr;
	Setting sets;
	
	//Widgets
	
	SeekBar volume, light;
	RadioGroup mode;
	RadioButton lightMode;
	RadioButton mathMode;
	Spinner sound;
	Button save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ch1 = getResources().getString(R.string.ch1);
		ch2 = getResources().getString(R.string.ch2);
		ch3 = getResources().getString(R.string.ch3);
		volume = (SeekBar) findViewById(R.id.Volume);
		volume.setMax(100);
		light = (SeekBar) findViewById(R.id.light);
		light.setMax(100);
		mode = (RadioGroup) findViewById(R.id.mode);
		lightMode = (RadioButton) findViewById(R.id.mode1);
		mathMode = (RadioButton) findViewById(R.id.mode2);
		sound = (Spinner) findViewById(R.id.sound);
		setMgr = new MySettingsManager(this);
		setMgr.open();
		sets = setMgr.getSettings();
		setMgr.close();
		
		if (sets == null) 
		{
			sets = new Setting();
			sets.setId(1);
			sets.setLight(50);
			sets.setMode(1);
			sets.setVolume(50);
			sets.setSound(0);
			setMgr.open();
			setMgr.saveSettings(sets);
			setMgr.close();
		}
		updateDisplay(sets);
		
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sets.setSound(sound.getSelectedItemPosition());
                sets.setVolume(volume.getProgress());
                sets.setLight(light.getProgress());
                if (lightMode.isChecked())
                sets.setMode(1);
                else sets.setMode(2);
                setMgr.open();
                setMgr.saveSettings(sets);
                setMgr.close();
                onClickHome(v);
            }
        });
		
		
	}

	private void updateDisplay(Setting sets2) {
		if(sets.getMode() == 1) lightMode.setChecked(true);
		else mathMode.setChecked(true);
		
		volume.setProgress(sets.getVolume());
		light.setProgress(sets.getLight());
		
		sound.setSelection(sets.getSound());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}
	
	public void onClickSettings(View view){
	}
	
	public void onClickInfo(View view) { Toast.makeText(getApplicationContext(),
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


}
