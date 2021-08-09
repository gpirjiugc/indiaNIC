package com.example.IndiaNIC.login_regisgter_fragemnts;

import android.content.ContextWrapper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.IndiaNIC.R;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.pixplicity.easyprefs.library.Prefs;

public class otp_genrator_fragment extends Fragment {

    EditText e1,e2;
    String s1;
    TextView textView;
    Button button;
     String sc;
    String s2;

    AppCompatActivity cc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_otp_genrator_fragment, container, false);

        new Prefs.Builder()
                .setContext(getContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getActivity().getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


        cc = (AppCompatActivity) v.getContext();

        SimpleArcDialog mDialog = new SimpleArcDialog(getContext());
        mDialog.setConfiguration(new ArcConfiguration(getContext()));


        e1 = v.findViewById(R.id.number);
        e2 = v.findViewById(R.id.name);

        textView = v.findViewById(R.id.errorloign);
     
        button = v.findViewById(R.id.next2);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = e1.getText().toString();
               s2  = e2.getText().toString();
                if(s1.isEmpty()){
                    textView.setText("Enter Number Field is Empty ");
                }
                if(s2.isEmpty()){
                    textView.setText("Name Field is Empty");
                }
                else {
                    if(s1.length()<=9){
                        textView.setText("Enter 10 digit number don't add +91");
                    }

                    else{
 ;
                        Prefs.putString("number",s1);
                        Prefs.putString("name",s2);

                        Toast.makeText(getContext(), "pls verify are u human or not ", Toast.LENGTH_SHORT).show();

                        cc.getSupportFragmentManager().beginTransaction().replace(R.id.fm_log, new otp_submit_fragment()).commit(); }
                    }
                }

        });







 return  v;
    }

}