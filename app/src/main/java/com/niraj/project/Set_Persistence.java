package com.niraj.project;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Set_Persistence extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
