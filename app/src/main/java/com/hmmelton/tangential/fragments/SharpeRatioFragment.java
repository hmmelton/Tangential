package com.hmmelton.tangential.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmmelton.tangential.R;

/**
 * Created by harrisonmelton on 7/11/16.
 */
public class SharpeRatioFragment extends Fragment {

    /**
     * Default constructor
     */
    public SharpeRatioFragment() {}

    /**
     * This method creates a new instance of the SharpeRatioFragment class.
     * @return newly instantiated SharpeRatioFragment
     */
    public static SharpeRatioFragment newInstance() {
        return new SharpeRatioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_sharpe_ratio, container, false);
        mainView.findViewById(R.id.fab).setOnClickListener(view -> {
            AddAssetDialog addAsset = new AddAssetDialog();
            addAsset.show(getActivity().getFragmentManager(), "AddAsset");
        });

        return mainView;
    }
}
