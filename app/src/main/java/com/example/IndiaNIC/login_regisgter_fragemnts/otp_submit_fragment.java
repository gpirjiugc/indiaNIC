package com.example.IndiaNIC.login_regisgter_fragemnts;


import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.IndiaNIC.R;
import com.example.IndiaNIC.map;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.concurrent.TimeUnit;


public class otp_submit_fragment extends Fragment {
    EditText editText;
    String s;
    Button button;
    AppCompatActivity cc;

    FirebaseAuth firebaseAuth;
    SimpleArcDialog mDialog,m;
    String sd,code;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         mDialog = new SimpleArcDialog(getContext());
  m = new SimpleArcDialog(getContext());
ArcConfiguration arcConfiguration = new ArcConfiguration(getContext());
arcConfiguration.setText("Pls Verify You human");
        ArcConfiguration rr =  new ArcConfiguration(getContext());
        new Prefs.Builder()
                .setContext(getContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getActivity().getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        m.setConfiguration(rr);
        mDialog.setConfiguration(arcConfiguration);


        View v = inflater.inflate(R.layout.fragment_submit_fragment, container, false);
        editText = v.findViewById(R.id.otp);
        cc = (AppCompatActivity) v.getContext();
        button = v.findViewById(R.id.submit);




        firebaseAuth = FirebaseAuth.getInstance();

        String s = null;
        s =  Prefs.getString("number");
        String ss = "+91"+s;

sendcode(ss);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if(s.isEmpty()){
                    Toast.makeText(getContext(), "Enter otp pls", Toast.LENGTH_SHORT).show();
                }
                else {
           m.show();

                    verifycode(s);
                }



            }
        });
        return v;
    }

    private void sendcode(String number) {

        mDialog.show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 10, TimeUnit.SECONDS, getActivity(),mcallbacks); }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
       code = phoneAuthCredential.getSmsCode();
  verifycode(code);


        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            mDialog.dismiss();

            Toast.makeText(cc, e.getMessage()+"  cant generate otp", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
            cc.getSupportFragmentManager().beginTransaction().replace(R.id.fm_log, new otp_genrator_fragment()).commit();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mDialog.dismiss();

            Toast.makeText(cc, "otp generated", Toast.LENGTH_SHORT).show();
            sd = s;
        }
    };


  private  void verifycode(String code ) {
      PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sd,code);
      firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){

                  m.dismiss();
                  Prefs.putInt("sts",1);
                  Intent i = new Intent(getActivity(), map.class);
                  startActivity(i);
                  getActivity().finish();


              }
              else {

                  Toast.makeText(cc, "enter valid otp", Toast.LENGTH_SHORT).show();
                 m.dismiss();
              }
          }
      });
  } }

