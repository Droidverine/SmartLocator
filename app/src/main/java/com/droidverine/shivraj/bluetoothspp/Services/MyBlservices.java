package com.droidverine.shivraj.bluetoothspp.Services;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.droidverine.shivraj.bluetoothspp.BluetoothManagement.BluetoothSPP;
import com.droidverine.shivraj.bluetoothspp.BluetoothManagement.BluetoothState;
import com.droidverine.shivraj.bluetoothspp.R;


/**
 * Created by DELL on 05-10-2017.
 */

public class MyBlservices extends Service {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private BluetoothSPP bt;

    @Override
    public void onCreate() {
        super.onCreate();
        bt = new BluetoothSPP(this);
        bt.setupService();
        bt.startService(BluetoothState.DEVICE_OTHER);
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onDeviceDisconnected() {
                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                mp.start();


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

                alertDialog.setTitle("ATTENTION...");

                alertDialog.setMessage("DEVICE OUT OF RANGE");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                        bt.disconnect();
                        mp.stop();
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            }

            @Override
            public void onDeviceConnectionFailed() {

            }


        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
