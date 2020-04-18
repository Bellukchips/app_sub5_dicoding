package com.example.submission3dicoding.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.submission3dicoding.R;
import com.example.submission3dicoding.mapping.MappingHelper;
import com.example.submission3dicoding.model.ModelMovie;

import java.net.URL;
import java.util.ArrayList;

import static com.example.submission3dicoding.db.DatabaseContract.MovieColumn.CONTENT_URI;


public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ModelMovie> modelMovies = new ArrayList<>();
    private final Context mcontext;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context context) {
        mcontext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identyToken = Binder.clearCallingIdentity();
        Uri uri = Uri.parse(CONTENT_URI + "/jenis/m");
        cursor = mcontext.getContentResolver().query(uri, null, null, null, null);
        modelMovies = MappingHelper.mapCursor(cursor);
        Binder.restoreCallingIdentity(identyToken);


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return modelMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        ModelMovie newModel = modelMovies.get(position);


        RemoteViews rv = new RemoteViews(mcontext.getPackageName(), R.layout.widget_item);


        Bundle extras = new Bundle();
        extras.putInt(WidgetFavorite.EXTRA_ITEM, position);
        extras.putInt("data", newModel.getId_movie());
        extras.putString("jenis", "1");
        extras.putString("nama",newModel.getName());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        final String file_path = "https://image.tmdb.org/t/p/w342";

        Bitmap bitmap = null;
        try {
            URL imageUrl = new URL(file_path+newModel.getPhoto());
            bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        } catch (Exception e){
            Log.d("ERROR WIDGET",e.toString());
        }

        rv.setImageViewBitmap(R.id.imageView,bitmap);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
