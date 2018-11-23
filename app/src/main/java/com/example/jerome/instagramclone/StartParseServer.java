package com.example.jerome.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFile;

public class StartParseServer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("1b261e2dd81eb79da7be889cb20db0ab95f2123b")
        .clientKey("18219babed118fdb96255d30e90effb2af751f7c")
        .server("http://13.236.186.150:80/parse/")
        .build());
    }
}
