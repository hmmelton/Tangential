package com.hmmelton.tangential.fragments;

import android.support.v4.app.Fragment;

import com.hmmelton.tangential.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by harrisonmelton on 7/15/16.
 * This is a fragment for user portfolios
 */
@EFragment(R.layout.fragment_portfolios)
public class PortfolioFragment extends Fragment{

    // OnClick for floating action button
    @Click(R.id.fab) void onFabClick() {

    }

    /**
     * Default constructor
     */
    public PortfolioFragment() {}

    public static PortfolioFragment_ newInstance() {
        return new PortfolioFragment_();
    }
}
