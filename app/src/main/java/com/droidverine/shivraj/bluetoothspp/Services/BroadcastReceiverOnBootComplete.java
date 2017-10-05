package com.droidverine.shivraj.bluetoothspp.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DELL on 05-10-2017.
 */

public class BroadcastReceiverOnBootComplete extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {

            Intent serviceIntent = new Intent(context, MyBlservices.class);

            context.startService(serviceIntent);

        }

    }
}
