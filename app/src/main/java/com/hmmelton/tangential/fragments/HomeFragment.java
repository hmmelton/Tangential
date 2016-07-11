package com.hmmelton.tangential.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmmelton.tangential.R;

import butterknife.ButterKnife;

/**
 * Created by harrisonmelton on 7/10/16.
 * This class is a fragment for the home screen.
 */
public class HomeFragment extends android.support.v4.app.Fragment {


    /**
     * Default constructor
     */
    public HomeFragment(){}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mainView);

        return mainView;
    }
}
