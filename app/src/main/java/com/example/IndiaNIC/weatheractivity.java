package com.example.IndiaNIC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class weatheractivity extends AppCompatActivity {
    //array_list_of_data's
    ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<>();
    ArrayList<Long> jsonObjectArrayListOther = new ArrayList<Long>();
    ArrayList<JSONObject> jsonweather = new ArrayList<>();
    ArrayList<String> stringArrayList_city = new ArrayList<>();
    public static  String key = "8a91e4f900281b68668a469d51273599";
Double lat,lon;
    //Text_View of current_weather
    TextView city,temp,tempmini,tempmax,cursts,radarcity;
    TextView humidity,fog,rain_chance,wind;

    //Text_view of time ,tempm1(max_temp),tempn(min_temp),im_t1(image_weather) of 5 hours data
    TextView time1,time2,time3,time4,time5;
    TextView tempm1,tempm2,tempm3,tempm4,tempm5;
    TextView tempn1,tempn2,tempn3,tempn4,tempn5;
    ImageView im_t1,im_t2,im_t3,im_t5;

    //cim (weather) and declaration
    ImageView cim;
    String curent_city ="Nathdwara";
    SimpleArcDialog mDialog;


    String[]  tempreaturemax = new String[5];
    String[]  tempreaturemin = new String[5];
    String[] date = new String[5];
    String[] weather_dic = new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatheractivity);

        city = findViewById(R.id.tx_city);
        temp = findViewById(R.id.tx_temp);
        tempmax = findViewById(R.id.max_temp);
        tempmini = findViewById(R.id.min_temp);
        cursts = findViewById(R.id.status);
        cim = findViewById(R.id.c_w);



        //time
        time1 = findViewById(R.id.tx1);
        time2 = findViewById(R.id.tx2);
        time3 = findViewById(R.id.tx3);
        time4 = findViewById(R.id.tx4);
        time5 = findViewById(R.id.tx5);

        //images
        im_t1 = findViewById(R.id.fim1);
        im_t2 = findViewById(R.id.fim2);
        im_t3 = findViewById(R.id.fim3);
        im_t5 = findViewById(R.id.fim4);
        im_t5 = findViewById(R.id.fim5);

        //maximum_temp(5)
        tempm1 = findViewById(R.id.txm1);
        tempm2 = findViewById(R.id.txm2);
        tempm3 = findViewById(R.id.txm3);
        tempm4 = findViewById(R.id.txm4);
        tempm5 = findViewById(R.id.txm5);

        //minimum_temp(5)
        tempn1 = findViewById(R.id.txd1);
        tempn2 = findViewById(R.id.txd2);
        tempn3 = findViewById(R.id.txd3);
        tempn4 = findViewById(R.id.txd4);
        tempn5 = findViewById(R.id.txd5);

        //cards od radar


        mDialog = new SimpleArcDialog(this);
        mDialog.setConfiguration(new ArcConfiguration(this));
        mDialog.show();
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }



        new weather_det().execute();
        new weatherTask().execute();

    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+key);
            return response;                        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute(); }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                JSONObject winnd = jsonObj.getJSONObject("wind");


                String city_name = jsonObj.getString("name");
                String temperature = main.getString("temp");
                String cast = weather.getString("description");
                String temp_min = main.getString("temp_min");
                String temp_max = main.getString("temp_max");
                String hum = "Humidity \n   "+main.getString("humidity")+"";
                String windd = "Wind \n  "+winnd.getString("speed")+"";
                String fogg = "Visibility \n  "+jsonObj.getString("visibility")+"";






                mDialog.dismiss();
                city.setText(city_name);
                temp.setText(temperature + "°C");
                tempmini.setText("MIN   :   "+temp_min+ "°C");
                tempmax.setText("MAX   :  "+temp_max+ "°C");
                cursts.setText(cast);
                humidity.setText(hum);
                wind.setText(windd);
                fog.setText(fogg);

                rain_chance.setText("  0% \n Rain Chances");


            }
            catch (Exception e) {
                mDialog.dismiss();


                Toast.makeText(weatheractivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(weatheractivity.this,map.class);
                startActivity(i);
                finish();
            }
        }
    }

    //class  for get 5 hours data
    class weather_det extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {
            String response6 = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+key);

            return response6;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                mDialog.show();
                JSONObject jsonObj = new JSONObject(s);

                for (int i = 0;i<5;i++){
                    JSONArray jsonArray  = jsonObj.getJSONArray("list");
                    JSONObject jsonObject;

                    jsonObjectArrayList.add(i,jsonObject = jsonArray.getJSONObject(i).getJSONObject("main"));
                    jsonObjectArrayListOther.add(i,jsonArray.getJSONObject(i).getLong("dt"));
                    jsonweather.add(i,jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0));
                }


                tempreaturemax[0] = "MAX : "+jsonObjectArrayList.get(0).getString("temp_max")+"°C";
                tempreaturemax[1] = "MAX : "+jsonObjectArrayList.get(1).getString("temp_max")+"°C";
                tempreaturemax[2] = "MAX : "+jsonObjectArrayList.get(2).getString("temp_max")+"°C";
                tempreaturemax[3] = "MAX : "+jsonObjectArrayList.get(3).getString("temp_max")+"°C";
                tempreaturemax[4] = "MAX : "+jsonObjectArrayList.get(4).getString("temp_max")+"°C";

                tempm1.setText( tempreaturemax[0]);
                tempm2.setText( tempreaturemax[1]);
                tempm3.setText( tempreaturemax[2]);
                tempm4.setText( tempreaturemax[3]);
                tempm5.setText( tempreaturemax[4]);

                tempreaturemin[0] = "MIN : "+jsonObjectArrayList.get(0).getString("temp_min")+"°C";
                tempreaturemin[1] = "MIN : "+jsonObjectArrayList.get(1).getString("temp_min")+"°C";
                tempreaturemin[2] = "MIN : "+jsonObjectArrayList.get(2).getString("temp_min")+"°C";
                tempreaturemin[3] = "MIN : "+jsonObjectArrayList.get(3).getString("temp_min")+"°C";
                tempreaturemin[4] = "MIN : "+jsonObjectArrayList.get(4).getString("temp_min")+"°C";


                tempn1.setText(tempreaturemin[0]);
                tempn2.setText(tempreaturemin[1]);
                tempn3.setText( tempreaturemin[2]);
                tempn4.setText(tempreaturemin[3]);
                tempn5.setText( tempreaturemin[4]);


                weather_dic[0] = jsonweather.get(0).getString("description");
                weather_dic[1] = jsonweather.get(1).getString("description");
                weather_dic[2] = jsonweather.get(2).getString("description");
                weather_dic[3] = jsonweather.get(3).getString("description");
                weather_dic[4] = jsonweather.get(4).getString("description");


                time1.setText(   date [0] =  new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(jsonObjectArrayListOther.get(0) * 1000)));
                time2.setText(   date [1] =  new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(jsonObjectArrayListOther.get(1) * 1000)));
                time3.setText(   date [2] =  new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(jsonObjectArrayListOther.get(2) * 1000)));
                time4.setText(   date [3] =  new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(jsonObjectArrayListOther.get(3) * 1000)));
                time5.setText(   date [4] =  new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(jsonObjectArrayListOther.get(4) * 1000)));

                mDialog.dismiss();



            } catch (JSONException jsonException) {
                mDialog.dismiss();
                Intent i = new Intent(weatheractivity.this,map.class);
                startActivity(i);
                finish();
                Toast.makeText(weatheractivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
            }

        }

    }

}