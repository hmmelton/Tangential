package com.hmmelton.tangential.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    @SuppressWarnings("unused")
    private final String TAG = "PortfolioFragment";

    private final int NEW_ASSET_REQUEST_CODE = 1;

    @ViewById(R.id.portfolios_recycler_view)
    RecyclerView mRecyclerView;

    AddAssetDialog_ mAddAssetDialog;

    // OnClick for floating action button
    @Click(R.id.fab) void onFabClick() {
        mAddAssetDialog = new AddAssetDialog_();
        mAddAssetDialog.setTargetFragment(this, NEW_ASSET_REQUEST_CODE);
        mAddAssetDialog.show(getActivity().getFragmentManager(), "AddAsset");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_ASSET_REQUEST_CODE) {
            String asset = data.getStringExtra(AddAssetDialog_.NEW_ASSET_KEY);
            Log.e(TAG, asset);
            // Add asset to portfolio
            ((PortfolioListAdapter) mRecyclerView.getAdapter()).addValue(asset);
        }
    }
}
