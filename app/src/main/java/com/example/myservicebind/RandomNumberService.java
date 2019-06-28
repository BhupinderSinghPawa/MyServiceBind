package com.example.myservicebind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

// define the Service in Manifest - serves as entry point into application

public class RandomNumberService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        RandomNumberService getService() {
            // Return this instance of RandomNumberService so clients can call public methods
            return RandomNumberService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("myLog", "RandomNumberService.onBind() " + intent + " " + mBinder);
        return mBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
        int randInt = mGenerator.nextInt(100);
        Log.i("myLog", "RandomNumberService.getRandomNumber() " + " " + randInt);
        return randInt;
    }

}
