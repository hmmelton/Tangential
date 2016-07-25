package com.hmmelton.tangential.fragments;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hmmelton.tangential.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by harrisonmelton on 7/24/16.
 * This is a Fragment class for the Tangency Portfolio page.
 */
@EFragment(R.layout.fragment_tangency)
public class TangencyFragment extends Fragment {

    @ViewById(R.id.tangent_e_return)
    TextView mReturn;
    @ViewById(R.id.tangent_e_sr)
    TextView mSharpeRatio;
    @ViewById(R.id.tangent_e_sd)
    TextView mStandardDeviation;
    @ViewById(R.id.tangent_recycler)
    RecyclerView mRecyclerView;

    public TangencyFragment() {}

    /**
     * This method creates a new instance of the TangencyFragment class.
     * @return new instance of TangencyFragment class
     */
    public static TangencyFragment newInstance() {
        return new TangencyFragment_();
    }

}
