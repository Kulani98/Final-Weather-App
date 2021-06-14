package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    TextView Country,City,Temprature,Longitude,Latitude,Humidity,Sunrise,Sunset,Pressure,Wind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        Country = findViewById(R.id.countryV);
        City = findViewById(R.id.cityV);
        Temprature = findViewById(R.id.tempV);

        Latitude = findViewById(R.id.latitude);
        Longitude = findViewById(R.id.longitude);
        Humidity = findViewById(R.id.humidity);
        Sunrise = findViewById(R.id.sunrise);
        Sunset = findViewById(R.id.sunset);
        Pressure = findViewById(R.id.pressure);
        Wind = findViewById(R.id.windspeed);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findweather();

            }
        });

    }

    public void findweather(){
        String city=editText.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=6adc4fa304483fe04299ecdf1ec967fc";

        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //calling API

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    //find country

                    JSONObject object1 = jsonObject.getJSONObject("sys");
                    String country_find= object1.getString("country");
                    Country.setText(country_find);

                    //find city
                    String city_find= jsonObject.getString("name");
                    City.setText(city_find);

                    //find temperature
                    JSONObject object2 = jsonObject.getJSONObject("main");
                    String temp_find= object2.getString("temp");
                    Temprature.setText(temp_find+"%");

                    //find image icon
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String img = jsonObject1.getString("icon");



                    //find latitude
                    JSONObject object3 = jsonObject.getJSONObject("coord");
                    double lat_find= object3.getDouble("lat");
                    Latitude.setText(lat_find+"°  N");

                    //find longitude
                    JSONObject object4 = jsonObject.getJSONObject("coord");
                    double lon_find= object4.getDouble("lon");
                    Longitude.setText(lon_find+"°  E");


                    //find humidity
                    JSONObject object5 = jsonObject.getJSONObject("main");
                    int hum_find= (int) object5.getDouble("humidity");
                    Humidity.setText(hum_find+"°  %");

                    //find sunrise
                    JSONObject object6 = jsonObject.getJSONObject("sys");
                    String sunrise_find=object6.getString("sunrise");
                    Sunrise.setText(sunrise_find);

                    //find sunset
                    JSONObject object7 = jsonObject.getJSONObject("sys");
                    String sunset_find=object7.getString("sunset");
                    Sunset.setText(sunset_find);

                    //find pressure
                    JSONObject object8 = jsonObject.getJSONObject("sys");
                    String pressure_find=object8.getString("pressure");
                    Pressure.setText(pressure_find+"  hPa");

                    //find wind speed
                    JSONObject object9 = jsonObject.getJSONObject("wind");
                    String wind_find=object9.getString("speed");
                    Wind.setText(wind_find+"  km/h");





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringrequest);



    }


}