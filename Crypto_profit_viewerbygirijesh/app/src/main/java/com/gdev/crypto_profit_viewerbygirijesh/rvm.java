package com.gdev.crypto_profit_viewerbygirijesh;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.lukelorusso.verticalseekbar.VerticalSeekBar;

import java.util.ArrayList;


public class rvm extends Fragment {
    VerticalSeekBar v1,v2,v3,v4,v5,v6;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {







View v =  inflater.inflate(R.layout.fragment_rvm, container, false);
            // initializing variable for bar chart.
        v1 = v.findViewById(R.id.a1);
        v2 = v.findViewById(R.id.a2);
        v3 = v.findViewById(R.id.a3);
        v4 = v.findViewById(R.id.a4);
        v5 = v.findViewById(R.id.a5);
        v6 = v.findViewById(R.id.a6);

try{
        v1.setProgress(10);
       v2.setProgress(10);
       v3.setProgress(10);
       v4.setProgress(10);
       v5.setProgress(10);
       v6.setProgress(10);}
catch (Exception e){
    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
}

            return  v;
    }
}