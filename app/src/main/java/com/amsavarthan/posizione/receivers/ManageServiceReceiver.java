package com.amsavarthan.posizione.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.amsavarthan.posizione.ui.activities.MainActivity;
import com.amsavarthan.posizione.services.LocationService;

import static android.content.Context.MODE_PRIVATE;

public class ManageServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("com.amsavarthan.posizione.START")) {

            boolean showNotification=context.getSharedPreferences("service", MODE_PRIVATE).getBoolean("notification",true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, LocationService.class).putExtra("notification", showNotification));
            } else {
                context.startService(new Intent(context, LocationService.class).putExtra("notification", showNotification));
            }

        }else{

            try{
                MainActivity.getInstance().location_switch.setChecked(false);
            }catch (Exception e){
                e.printStackTrace();
            }
            context.getSharedPreferences("lock",MODE_PRIVATE).edit().putString("password","0").apply();
            context.stopService(new Intent(context, LocationService.class));

        }
    }
}
