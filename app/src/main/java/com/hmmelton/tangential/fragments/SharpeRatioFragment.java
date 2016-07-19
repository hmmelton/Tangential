package com.hmmelton.tangential.fragments;


import android.app.Fragment;

import com.hmmelton.tangential.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by harrisonmelton on 7/11/16.
 * This class is a Fragment used to calculate Sharpe Ratios.
 */
@EFragment(R.layout.fragment_sharpe_ratio)
public class SharpeRatioFragment extends Fragment {

    @Click(R.id.fab) void onFabClick() {
        AddAssetDialog addAsset = new AddAssetDialog_();
        addAsset.show(getActivity().getFragmentManager(), "AddAsset");
    }

    /**
     * Default constructor
     */
    public SharpeRatioFragment() {}

    /**
     * This method creates a new instance of the SharpeRatioFragment class.
     * @return newly instantiated SharpeRatioFragment
     */
    public static SharpeRatioFragment newInstance() {
        return new SharpeRatioFragment_();
    }
}
