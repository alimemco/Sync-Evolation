package com.ali.memco.evolation;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.ali.memco.evolation.dataModel.ApiService;
import com.ali.memco.evolation.dataModel.WheaterInfo;
import com.ali.memco.evolation.view.activity.Weather;


public class WeatherWidget extends AppWidgetProvider {

    public static final String ACTION_WIDGET_UPDATE_WEATHER="com.ali.memco.evolation.UPDATE_WEATHER";
    public static final String ACTION_NETWORK_STATE_CHANGED="android.net.conn.CONNECTIVITY_CHANGE";
    private static final String TAG = "WeatherWidget";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, WheaterInfo wheaterInfo) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        remoteViews.setTextViewText(R.id.widget_txt_temp,String.valueOf((int)(wheaterInfo.getWeatherTemprature()-272.15)+""));

        switch (wheaterInfo.getWeatherId()/100){
            case 2:
                remoteViews.setImageViewResource(R.id.widget_img_weather,R.drawable.thunderstorm);
                break;
            case 3:
                remoteViews.setImageViewResource(R.id.widget_img_weather,R.drawable.drizzle);
                break;
            case 5:
                remoteViews.setImageViewResource(R.id.widget_img_weather,R.drawable.rain);
                break;
            case 6:
                remoteViews.setImageViewResource(R.id.widget_img_weather,R.drawable.snow);
                break;
            case 7:
                remoteViews.setImageViewResource(R.id.widget_img_weather,R.drawable.mist);
                break;
            case 8:
                remoteViews.setImageViewResource(R.id.widget_img_weather,R.drawable.clear_sky);
                break;
            default:
                remoteViews.setImageViewResource(R.id.widget_img_weather,R.drawable.clear_sky);

        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {

        context.startService(new Intent(context,AppWidgetUpdaterService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_WIDGET_UPDATE_WEATHER)){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            WheaterInfo wheaterInfo = intent.getParcelableExtra("data");
            int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,WeatherWidget.class));
            for (int i = 0; i < appWidgetIds.length; i++) {
                updateAppWidget(context,appWidgetManager,appWidgetIds[i],wheaterInfo);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

