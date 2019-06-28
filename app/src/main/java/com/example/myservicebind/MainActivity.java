package com.example.myservicebind;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RandomNumberService mServiceRef;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("myLog", "MainActivity.onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Bind to LocalService
        Intent intent = new Intent(this, RandomNumberService.class);
        Log.i("myLog", "MainActivity.onStart() - bind service explicit " + intent + " " + mConnection);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("myLog", "MainActivity.onStop() - unbind service");
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute) */
    public void onButtonClick(View v) {
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            int num = mServiceRef.getRandomNumber();
            Log.i("myLog", "MainActivity.onButtonClick() - got random number - " + num);
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            mServiceRef = ((RandomNumberService.LocalBinder) service).getService();
            mBound = true;
            Log.i("myLog", "MainActivity.ServiceConnection.onServiceConnected() " + mServiceRef);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("myLog", "MainActivity.ServiceConnection.onServiceDisconnected()" + mServiceRef);
            mBound = false;
            mServiceRef = null;

        }
    };
}
