package com.example.timebank;

import android.app.Application;

import com.parse.Parse;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // hijm1k8z6Nbo

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                .clientKey("hijm1k8z6Nbo")
                .server("http://3.142.84.47/parse/")
                .build()
        );
    }
}
