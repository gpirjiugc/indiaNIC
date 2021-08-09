package com.example.IndiaNIC;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.IndiaNIC.R;
import com.example.IndiaNIC.login_regisgter_fragemnts.otp_genrator_fragment;
import com.pixplicity.easyprefs.library.Prefs;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();

            //IF Condition is True Then Set Windows No Limit Flag
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        new Prefs.Builder()
                .setContext(getApplicationContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getApplication().getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Prefs.getInt("sts",0) == 0 ){
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();}
                else {
                    Intent i = new Intent(Splash.this, map.class);
                    startActivity(i);
                    finish();
                }
            }
        },12000);
    }
}