package com.hmmelton.tangential.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.adapters.PortfolioListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by harrisonmelton on 7/15/16.
 * This is a fragment for user portfolios
 */
@EFragment(R.layout.fragment_portfolio)
public class PortfolioFragment extends Fragment {

    @ViewById(R.id.portfolios_recycler_view)
    RecyclerView mRecyclerView;

    // OnClick for floating action button
    @Click(R.id.fab) void onFabClick() {

    }

    /**
     * Default constructor
     */
    public PortfolioFragment() {

    }

    public static PortfolioFragment_ newInstance() {
        return new PortfolioFragment_();
    }

    // This method initializes the fragment's RecyclerView
    @AfterViews
    void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new PortfolioListAdapter());
    }
}
