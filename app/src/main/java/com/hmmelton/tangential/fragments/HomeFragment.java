package com.hmmelton.tangential.fragments;

import com.hmmelton.tangential.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by harrisonmelton on 7/10/16.
 * This class is a fragment for the home screen.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends android.support.v4.app.Fragment {

    /**
     * Default constructor
     */
    public HomeFragment(){}

    public static HomeFragment newInstance() {
        return new HomeFragment_();
    }
}
