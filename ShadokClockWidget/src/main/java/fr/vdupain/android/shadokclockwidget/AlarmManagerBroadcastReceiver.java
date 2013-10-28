package fr.vdupain.android.shadokclockwidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.RemoteViews;

import java.util.Calendar;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    Calendar cal = Calendar.getInstance();

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		//Acquire the lock
		wl.acquire();

		//You can do the processing here update the widget/remote views.
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.clock_widget_layout);
        cal.setTimeInMillis(System.currentTimeMillis());
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //String time = Utility.getCurrentTime("hh:mm:ss a");
        String time = encode(hour) + ":" + encode(minute) + ":" + encode(second);
        remoteViews.setTextViewText(R.id.tvTime, time);
		//Toast.makeText(context, time, Toast.LENGTH_LONG).show();
		ComponentName thiswidget = new ComponentName(context, ClockWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(thiswidget, remoteViews);
		//Release the lock
		wl.release();
	}

    public static String encode(int base10) {
        String converted = Integer.toString(base10, 4);
        return converted.replaceAll("0", "GA").replaceAll("1", "BU").replaceAll("2", "ZO").replaceAll("3", "MEU");
    }


}