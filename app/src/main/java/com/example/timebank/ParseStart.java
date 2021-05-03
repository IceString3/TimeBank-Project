package com.example.timebank;

import android.app.Application;

import com.parse.Parse;

public class ParseStart extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                .clientKey("myclientkey")
                .server("http://localhost/parse")
                .build()
        );
    }
}
