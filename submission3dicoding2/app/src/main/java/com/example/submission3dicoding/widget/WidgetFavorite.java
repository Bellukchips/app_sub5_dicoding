package com.example.submission3dicoding.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.example.submission3dicoding.R;
import com.example.submission3dicoding.activity.DetailActivity;
import com.example.submission3dicoding.model.ModelMovie;
import com.example.submission3dicoding.service.StackWidgetService;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetFavorite extends AppWidgetProvider {
    private static final String TOAST_ACTION="com.example.submission3dicoding.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.submission3dicoding.EXTRA_ITEM";
    public static final String EXTRA_ID = "com.example.submission3dicoding.EXTRA_ID";
    private ArrayList<ModelMovie>modelMovies;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_favorite);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);
        Intent toastIntent = new Intent(context, WidgetFavorite.class);
        toastIntent.setAction(WidgetFavorite.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_favorite);

            Intent startActivityIntent = new Intent(context, DetailActivity.class);
            startActivityIntent.putExtra("data",EXTRA_ID);
            startActivityIntent.putExtra("jenis","1");
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.stack_view, startActivityPendingIntent);

            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                String viewIndex = intent.getStringExtra("nama");

                Toast.makeText(context, "Film :  " +viewIndex , Toast.LENGTH_SHORT).show();
            }
        }
    }
    }




