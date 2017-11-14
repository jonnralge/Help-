package com.example.jonnski.mpxd;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Jonnski on 12/11/2017.
 */

public class volumeListener extends Service
{
    SettingsContentObserver mSettingsContentObserver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Help! Successfully configured", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        mSettingsContentObserver = new SettingsContentObserver(this, new Handler());

        getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver);
    }

    @Override
    public void onDestroy()
    {
        getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
    }
}
