package com.ondesarrollo.psicoanalyzer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Info extends AppCompatActivity {

    private CheckBox activeSound;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        activeSound = (CheckBox) findViewById(R.id.activeSound);
        prefs =
                getSharedPreferences("Psicoanalyzer", Context.MODE_PRIVATE);
        activeSound.setChecked(prefs.getBoolean("activeSound",false));

        activeSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG);
                if (((CheckBox) v).isChecked()) {
                    activeSound.setChecked(true);
                }else{
                    activeSound.setChecked(false);
                }

                SharedPreferences.Editor editor=prefs.edit();
                boolean estado = activeSound.isChecked();
                editor.putBoolean("activeSound",estado);
                prefs =
                        getSharedPreferences("Psicoanalyzer", Context.MODE_PRIVATE);
                activeSound.setChecked(prefs.getBoolean("activeSound",false));


            }
        });
    }
}
