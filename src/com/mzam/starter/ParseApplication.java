package com.mzam.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Add your initialization code here
    Parse.initialize(this, "fILXREshml4VZrvn3ze5Woa1O7aQB6izmc7w83wd", "Pvvt1NpEsDpM7MJdmN4FV0vW87kij4jfZkJiUdlG");

    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    
    // Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
