package fr.vdupain.android.shadokclockwidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.RemoteViews;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    public static String WEEKDAYS_NAMES[] = new DateFormatSymbols()
            .getWeekdays();
    public static String MONTHS_NAMES[] = new DateFormatSymbols().getMonths();

    Calendar calendar = Calendar.getInstance();

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		//Acquire the lock
		wl.acquire();

		//You can do the processing here update the widget/remote views.
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.clock_widget_layout);
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        //String time = Utility.getCurrentTime("hh:mm:ss a");
        String time = encode(hour) + ":" + encode(minute) + ":" + encode(second);
        remoteViews.setTextViewText(R.id.time, time);
        remoteViews.setTextViewText(R.id.weekday, WEEKDAYS_NAMES[calendar
                .get(Calendar.DAY_OF_WEEK)].toUpperCase());
        remoteViews.setTextViewText(R.id.day,
                Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        remoteViews.setTextViewText(R.id.month,
                MONTHS_NAMES[calendar.get(Calendar.MONTH)].toUpperCase());
        remoteViews.setTextViewText(R.id.year,
                Integer.toString(calendar.get(Calendar.YEAR)));


		//Toast.makeText(context, time, Toast.LENGTH_LONG).show();
		ComponentName thiswidget = new ComponentName(context, ClockWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(thiswidget, remoteViews);
		//Release the lock
		wl.release();
	}

    public static String encode(int base10) {
        String converted = Integer.toString(base10, 4);
        return converted.replaceAll("0", "O").replaceAll("1", "-").replaceAll("2", "⌋").replaceAll("3", "Meu");
    }


}