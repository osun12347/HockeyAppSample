package com.opentok.example.efflorescence.hockeyappsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.LoginManager;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.metrics.MetricsManager;
import net.hockeyapp.android.utils.HockeyLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import static android.provider.UserDictionary.Words.APP_ID;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button crashbutton = (Button) findViewById(R.id.crashbutton);
        crashbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throw new ArithmeticException("ArithmeticException");
            }
        });
        //try {
        //checkForCrashes();
        //checkForUpdates();
//        HockeyLog.setLogLevel(Log.VERBOSE);
//            tv = (TextView) findViewById(R.id.text1);
//            tv.setText("sample");
       // }
        //catch(Exception e) {

           // e.printStackTrace();
        //}

        MetricsManager.register(getApplication());
        //add this line wherever you want to track an event
        MetricsManager.trackEvent("OnSetText");
        // add this wherever you want to track a custom event and attach properties or measurements to it
        HashMap<String, String> properties = new HashMap<>();
        properties.put("Property1", "Value1");
        HashMap<String, Double> measurements = new HashMap<>();
        measurements.put("Measurement1", 1.0);
        MetricsManager.trackEvent("OnSetText", properties, measurements);

        //checkForUpdates();
//in-app feedback
        FeedbackManager.register(this);
        //add this wherever u want to add feedback
        FeedbackManager.showFeedbackActivity(MainActivity.this);
//authentication using app secret
        LoginManager.register(this, Common.APP_SECRET, LoginManager.LOGIN_MODE_EMAIL_PASSWORD);
        LoginManager.verifyLogin(this, getIntent());

    }
    private void checkForCrashes() {
//try {
//
//    CrashManager.register(this, Common.APP_ID, new MyCrashManagerListener());
//    Log.d("check crashes", "entered");
//}
//catch(Exception e)
//{
//    e.printStackTrace();
//}
        CrashManager.register(this,new MyCrashManagerListener());
       //CrashManager.register(this, Common.APP_ID, new MyCrashManagerListener());
    }
    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();

    }
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
        checkForUpdates();
    }
    private static class MyCrashManagerListener extends CrashManagerListener {
        public boolean shouldAutoUploadCrashes() {
            Log.d("Crash Report:","Entered");
            return true;
        }

        @Override
        public void onNewCrashesFound() {
            super.onNewCrashesFound();
            Log.d("Crash Report:","Crash Found");
        }

        @Override
        public void onCrashesNotSent() {
            super.onCrashesNotSent();
            Log.d("Crash Report:","Crash not sent");
        }

        @Override
        public void onCrashesSent() {
            super.onCrashesSent();
            Log.d("Crash Report:","crash sent");
        }
    }
}
