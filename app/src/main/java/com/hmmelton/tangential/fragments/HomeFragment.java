package com.hmmelton.tangential.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmmelton.tangential.R;

import butterknife.ButterKnife;

/**
 * Created by harrisonmelton on 7/10/16.
 */
public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mainView);

        return mainView;
    }
}
