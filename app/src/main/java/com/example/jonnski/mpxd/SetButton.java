package com.example.jonnski.mpxd;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;

public class SetButton extends AppCompatActivity {

    TextView tvWork, tvMessage, tvNumber;
    String combo;
    Button buttonSave, buttonSet;
    Boolean saved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_button);
        tvWork = (TextView) findViewById(R.id.tv_work);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvNumber = (TextView) findViewById(R.id.tv_contact);
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSet = (Button) findViewById(R.id.button_set);


        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String message = dsp.getString("message", null);
        String number = dsp.getString("number", null);
        Boolean dspSaved = dsp.getBoolean("saved", false);
        saved = dspSaved;

        tvNumber.setText("Current emergency contact: " + number);
        tvMessage.setText("Current message: " + message);
        String buttonCombo = dsp.getString("combo", null);
        if (buttonCombo != null)
        {
            tvWork.setText(buttonCombo);
            combo = buttonCombo;
        }
        else
            combo = "";

        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (combo == "")
                    Toast.makeText(getApplicationContext(), "You didn't set a button sequence.", Toast.LENGTH_SHORT).show();
                else
                {
                    saved = true;
                    SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor dspEditor = dsp.edit();

                    dspEditor.putString("combo", combo);
                    dspEditor.putBoolean("saved", saved);
                    dspEditor.apply();
                    dspEditor.commit();
                    Intent pleaseWork = new Intent(getBaseContext(), volumeListener.class);
                    pleaseWork.addFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
                    startService(pleaseWork);
                }
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor dspEditor = dsp.edit();
                dspEditor.clear();
                dspEditor.apply();
                dspEditor.commit();

                Intent i = new Intent(getBaseContext(), MainActivity.class);

                startActivity(i);
                finish();
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) && saved == false)
        {
            combo = combo + "Down, ";
            tvWork.setText(combo);
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP) && saved == false)
        {
            combo = combo + "Up, ";
            tvWork.setText(combo);
            return true;
        }
        return false;
    }

}
