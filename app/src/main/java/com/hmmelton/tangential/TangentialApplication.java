package com.hmmelton.tangential;

import android.app.Application;

import com.firebase.client.Firebase;
import com.hmmelton.tangential.utils.LocalStorage;

import org.androidannotations.annotations.EApplication;

/**
 * Created by harrisonmelton on 7/13/16.
 * This is the app's application class.
 */
@EApplication
public class TangentialApplication extends Application {

    private static Firebase mRootRef;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        LocalStorage.initialize(this);

        mRootRef = new Firebase("https://tangential-893d0.firebaseio.com/");
    }

    /**
     * This method returns the global Firebase instance.
     * @return global Firebase instance
     */
    public static Firebase getFirebase() {
        return mRootRef;
    }
}
