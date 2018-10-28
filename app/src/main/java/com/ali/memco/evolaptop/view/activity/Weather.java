package com.ali.memco.evolaptop.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.dataModel.ApiService;
import com.ali.memco.evolaptop.dataModel.WheaterInfo;

public class Weather extends AppCompatActivity implements ApiService.OnWeatherInfoRecived{

    private ProgressBar progressBar;
    private TextView txtWeatherName;
    private TextView txtWeatherDescription;
    private TextView txtTemp;
    private TextView txtHumidity;
    private TextView txtPressure;
    private TextView txtMinTemp;
    private TextView txtMaxTemp;
    private TextView txtWindSpeed;
    private TextView txtWindDegree;

    ApiService apiService;

    private Button sendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        apiService = new ApiService(this);

        initViews();


        sendRequest = findViewById(R.id.weather_btn_sendRequest);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               apiService.getCurrentWeather(Weather.this,"Abadan,IR");
               progressBar.setVisibility(View.VISIBLE);

            }
        });
    }

    private  void initViews(){

        progressBar=(ProgressBar)findViewById(R.id.progress_bar);

        txtWeatherName=(TextView)findViewById(R.id.txt_weather_name);
        txtWeatherDescription=(TextView)findViewById(R.id.txt_weather_description);
        txtTemp=(TextView)findViewById(R.id.txt_temprature);
        txtHumidity=(TextView)findViewById(R.id.txt_humidity);
        txtPressure=(TextView)findViewById(R.id.txt_pressure);
        txtMinTemp=(TextView)findViewById(R.id.txt_min_temp);
        txtMaxTemp=(TextView)findViewById(R.id.txt_max_temp);
        txtWindSpeed=(TextView)findViewById(R.id.txt_wind_speed);
        txtWindDegree=(TextView)findViewById(R.id.txt_wind_degree);

    }

    @Override
    public void onRecived(WheaterInfo wheaterInfo) {
   if (wheaterInfo!=null){

            txtWeatherName.setText("آب و هوای فعلی"+wheaterInfo.getWeatherName());
            txtWeatherDescription.setText("توضیحات: "+wheaterInfo.getWeatherDescription());
            txtTemp.setText("دمای فعلی: "+String.valueOf(wheaterInfo.getWeatherTemprature()));
            txtHumidity.setText("رطوبت هوا: "+String.valueOf(wheaterInfo.getHumidity()));
            txtPressure.setText("میزان فشار هوا: "+String.valueOf(wheaterInfo.getPressure()));
            txtMinTemp.setText("کم ترین دما: "+String.valueOf(wheaterInfo.getMinTemprature()));
            txtMaxTemp.setText("بیشترین دما: "+String.valueOf(wheaterInfo.getMaxTemprature()));
            txtWindSpeed.setText("سرعت باد: "+String.valueOf(wheaterInfo.getWindSpeed()));
            txtWindDegree.setText("درجه ی باد: "+String.valueOf(wheaterInfo.getWindDegree()));

        }else{
            Toast.makeText(this, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
        }
       progressBar.setVisibility(View.INVISIBLE);

    }
}
