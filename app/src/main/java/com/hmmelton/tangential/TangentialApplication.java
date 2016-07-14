package com.hmmelton.tangential;

import android.app.Application;

import com.firebase.client.Firebase;

import org.androidannotations.annotations.EApplication;

/**
 * Created by harrisonmelton on 7/13/16.
 * This is the app's application class.
 */
@EApplication
public class TangentialApplication extends Application {

    private Firebase mRootRef;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        mRootRef = new Firebase("https://tangential-893d0.firebaseio.com/");
    }
}
