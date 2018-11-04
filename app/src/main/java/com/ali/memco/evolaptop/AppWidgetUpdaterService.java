package com.ali.memco.evolation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ali.memco.evolation.dataModel.ApiService;
import com.ali.memco.evolation.dataModel.WheaterInfo;

public class AppWidgetUpdaterService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ApiService apiService = new ApiService(this);
        apiService.getCurrentWeather(new ApiService.OnWeatherInfoRecived() {
            @Override
            public void onRecived(WheaterInfo wheaterInfo) {
                if (wheaterInfo!=null){
                    Intent widgetIntent = new Intent(WeatherWidget.ACTION_WIDGET_UPDATE_WEATHER);
                    widgetIntent.putExtra("data",wheaterInfo);
                    sendBroadcast(widgetIntent);
                }
                stopSelf();
            }
        });
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
