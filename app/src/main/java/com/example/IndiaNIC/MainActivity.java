package com.example.IndiaNIC;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.IndiaNIC.R;
import com.example.IndiaNIC.login_regisgter_fragemnts.otp_genrator_fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
     

        getSupportFragmentManager().beginTransaction().replace(R.id.fm_log,new otp_genrator_fragment()).commit();
        //Checking That The Build Version is Kitkat or More Higher Then


    }
}