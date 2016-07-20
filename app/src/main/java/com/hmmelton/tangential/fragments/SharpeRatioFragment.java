package com.hmmelton.tangential.fragments;


import android.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.models.AnalyzedQuote;
import com.hmmelton.tangential.utils.LocalStorage;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.text.DecimalFormat;

/**
 * Created by harrisonmelton on 7/11/16.
 * This class is a Fragment used to calculate Sharpe Ratios.
 */
@EFragment(R.layout.fragment_sharpe_ratio)
public class SharpeRatioFragment extends Fragment {

    @SuppressWarnings("unused")
    private static final String TAG = "SharpeRatioFrag";

    @StringRes(R.string.quote_request_error) String error;

    @ViewById(R.id.individual_asset)
    EditText mAssetInput;
    @ViewById(R.id.individual_asset_sr)
    TextView mAssetOutput;

    @Click(R.id.individual_sr_button)
    void onSrCalcClick() {
        Toast.makeText(getActivity(), "HERE", Toast.LENGTH_LONG).show();
        String assetTicker = mAssetInput.getText().toString();
        analyzeQuote(assetTicker);
    }

    /**
     * This method runs in a background thread to query and collect data on an asset.
     * @param assetTicker ticker of asset to be queried
     */
    @Background
    public void analyzeQuote(String assetTicker) {
        AnalyzedQuote quote = LocalStorage.getAnalyzedQuote(assetTicker);

        if (quote == null)
            alertError();
        else {
            displaySharpeRatio(quote);
        }
    }

    @UiThread
    public void alertError() {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    public void displaySharpeRatio(AnalyzedQuote quote) {
        DecimalFormat format = new DecimalFormat("##.0000");
        Log.e(TAG, quote.expectedReturn + "");
        mAssetOutput.setText(format.format(quote.sharpeRatio) + "");
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
