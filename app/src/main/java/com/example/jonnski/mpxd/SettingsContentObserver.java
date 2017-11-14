package com.example.jonnski.mpxd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Created by Jonnski on 12/11/2017.
 */

public class SettingsContentObserver extends ContentObserver {
    int previousVolume;
    Context context;
    SharedPreferences dsp;
    String combo;
    String realCombo;
    Boolean reinit;

    public SettingsContentObserver(Context c, Handler handler) {
        super(handler);
        context = c;
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
        dsp = PreferenceManager.getDefaultSharedPreferences(context);

        reinit = false;
        combo = "";
        realCombo = dsp.getString("combo", null);
        if (realCombo == null)
            context.stopService(new Intent(c, volumeListener.class));

        Timer timer = new Timer();

        timer.schedule(new TimerTask()
        {
            public void run()
            {
                combo = ""; //reinitialize combo every 15 seconds
                reinit = true;
            }
        }, 15000, 15000);

    }


    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        if (reinit == true)
            reinit = false;
        //TODO somehow make it work when volume is already 0 and actually send the message and include location
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_RING);

        int x = previousVolume - currentVolume;
        Log.d("SD", x + " " + previousVolume + " " + currentVolume);

        if(x > 0 )
        {
            //Toast.makeText(context, "Decreased", Toast.LENGTH_SHORT).show();
            Log.d("SCC", "Down");
            Log.d("SCC", previousVolume + " " + currentVolume);
            previousVolume=currentVolume;
            combo = combo + "Down, ";
        }
        else if(x < 0)
        {
            //Toast.makeText(context, "Increased", Toast.LENGTH_SHORT).show();
            Log.d("SC", "Up");
            Log.d("SCC", previousVolume + " " + currentVolume);
            previousVolume=currentVolume;
            combo = combo + "Up, ";
        }

        if (combo.equals(realCombo))
        {
            Toast.makeText(context, "Sending message...", Toast.LENGTH_SHORT).show();
            Log.d("message", "YAY");
        }
    }
}
