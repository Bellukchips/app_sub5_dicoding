package com.example.submission3dicoding.service;

import android.content.Intent;
import android.widget.RemoteViewsService;
import com.example.submission3dicoding.widget.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
