package com.example.jonnski.mpxd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etMessage, etNumber;
    Button buttonSave;
    RadioButton rbLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = (EditText) findViewById(R.id.et_message);
        etNumber = (EditText) findViewById(R.id.et_number);
        buttonSave = (Button) findViewById(R.id.button_save);
        rbLocation = (RadioButton) findViewById(R.id.rb_location);

        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String message = dsp.getString("message", null);
        String number = dsp.getString("number", null);

        if(message != null && number != null)
        {
            Intent i = new Intent(getBaseContext(), SetButton.class);
            startActivity(i);
            finish();
        }

        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor dspEditor = dsp.edit();


                String message = etMessage.getText().toString();
                String numberTemp = etNumber.getText().toString();
                if(message.equals("") || numberTemp.equals(""))
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                else
                {
                    dspEditor.putString("message", message);
                    String number = etNumber.getText().toString();
                    dspEditor.putString("number", number);
                    dspEditor.apply();
                    dspEditor.commit();

                    Intent i = new Intent(getBaseContext(), SetButton.class);
                    startActivity(i);
                }
            }
        });
    }
}
