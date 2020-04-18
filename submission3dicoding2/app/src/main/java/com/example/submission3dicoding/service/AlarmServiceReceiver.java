package com.example.submission3dicoding.service;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.example.submission3dicoding.R;
import com.example.submission3dicoding.activity.DetailActivity;
import com.example.submission3dicoding.model.ModelMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmServiceReceiver extends BroadcastReceiver {
    public static final String TYPING_RELEASE = "Dicoding Notif   :";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    public final int ID_REPEATING = 101;

    @Override
    public void onReceive(final Context context, Intent intent) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Date date = new Date();
        String formatdate;
        formatdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=ed94c15844d5687a41edbd52b892330d&" +
                "language=en-US&sort_by=popularity.desc&primary_release_date.gte=" + formatdate + "&primary_release_date.lte=" + formatdate;

        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray jsonArray = object.getJSONArray("results");
                    ModelMovie modelMovie = new ModelMovie();
                    if (jsonArray.length() == 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            modelMovie = new ModelMovie(object1, true);
                        }
                        showAlarmNotif(context, TYPING_RELEASE + " " + modelMovie.getName(), modelMovie.getName() + context.getString(R.string.was_just_released), modelMovie.getId_movie(), 1);


                    } else if (jsonArray.length() > 1) {
                        for (int l = 0; l <= 4; l++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(l);
                            modelMovie = new ModelMovie(jsonObject, true);
                            showAlarmNotif(context, TYPING_RELEASE + " " + modelMovie.getName(), modelMovie.getName() + context.getString(R.string.was_just_released), modelMovie.getId_movie(), l);


                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailute", error.getMessage());
            }
        });
    }

    private void showAlarmNotif(Context context, String title, String msg, int movie_id, int channel_id) {
        String CHANNEL_ID = "Channel_0"+channel_id;
        String CHANNEL_NAME = "AlarmManager channel";
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra("data", movie_id);
        i.putExtra("jenis", true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, movie_id, i, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarm);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(movie_id, notification);
        }
    }

    public AlarmServiceReceiver() {
    }

    public void setAlarmRepeat(Context context, String type, String time) {
        String TIME_FORMAT = "HH:mm";
        if (isDateInvalid(time, TIME_FORMAT)) {
            Log.d("error", "Not Valid");
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent a = new Intent(context,AlarmServiceReceiver.class);
        a.putExtra(EXTRA_MESSAGE,2);
        a.putExtra(EXTRA_TYPE,type);

        String[] timeArr =time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArr[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArr[1]));
        calendar.set(Calendar.SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,ID_REPEATING,a,PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null){
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.set_repeating_started), Toast.LENGTH_SHORT).show();

    }
    public void cancleAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent =new Intent(context,AlarmServiceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,ID_REPEATING,intent,0);
        pendingIntent.cancel();

        if (alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.set_repeating_cancelled), Toast.LENGTH_SHORT).show();


    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

}
