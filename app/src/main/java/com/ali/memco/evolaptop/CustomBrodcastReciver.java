package com.ali.memco.evolation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CustomBrodcastReciver extends BroadcastReceiver {
    private static final String TAG = "CustomBrodcastReciver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("mess");
        Log.i(TAG, "onReceive: "+message);
    }
}
