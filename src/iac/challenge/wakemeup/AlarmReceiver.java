package iac.challenge.wakemeup;

import iac.challenge.sql.Alarm;
import iac.challenge.sql.MyAlarmManager;
import iac.challenge.sql.MySettingsManager;
import iac.challenge.sql.Setting;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private MySettingsManager setMgr;
	private Setting sets;
	private MyAlarmManager alMgr;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		alMgr = new MyAlarmManager(context);
		alMgr.open();
		alMgr.clear();
		alMgr.close();
		setMgr = new MySettingsManager(context);
		setMgr.open();
		sets = setMgr.getSettings();
		setMgr.close();

		try {
			Intent i = new Intent();
			if (sets.getMode() == 1)
			i.setClassName("iac.challenge.wakemeup",
					"iac.challenge.wakemeup.ItsTime");
			if (sets.getMode() == 2)
			i.setClassName("iac.challenge.wakemeup",
					"iac.challenge.wakemeup.ItsMathsTime");
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);

		} catch (Exception r) {
		}
	}
}
