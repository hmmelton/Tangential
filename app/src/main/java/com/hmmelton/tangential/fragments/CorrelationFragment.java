package com.hmmelton.tangential.fragments;

import android.app.Fragment;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.utils.QuoteAnalysis;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by harrisonmelton on 7/10/16.
 * This class is a fragment for the correlation screen.
 */
@EFragment(R.layout.fragment_correlation)
public class CorrelationFragment extends Fragment {

    // Asset ticker input fields
    @ViewById(R.id.asset_1) EditText mAsset;
    @ViewById(R.id.asset_2) EditText mAsset2;
    @ViewById(R.id.corr_result) TextView mResult;
    @ViewById(R.id.corr_section) View mResultSection;
    @ViewById(R.id.year_button_group) RadioGroup mYearButtonGroup;

    // OnClick for correlation calculate button
    @Click(R.id.corr_calculation) void onCorrelationClick() {
        String assetText1 = mAsset.getText().toString().trim();
        String assetText2 = mAsset2.getText().toString().trim();
        findCorrelation(assetText1, assetText2);
    }

    private int correlationPeriod;

    /**
     * Default constructor
     */
    public CorrelationFragment() {
        correlationPeriod = -5;
    }

    /**
     * This method creates a new instance of the CorrelationFragment class.
     * @return new instance of CorrelationFragment
     */
    public static CorrelationFragment newInstance() {
        return new CorrelationFragment_();
    }

    @AfterViews
    void prepareYearButtons() {
        // OnCheckedChangeListener for correlation period RadioGroup
        mYearButtonGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.button_1y:
                    correlationPeriod = -1;
                    break;
                case R.id.button_3y:
                    correlationPeriod = -3;
                    break;
                case R.id.button_5y:
                    correlationPeriod = -5;
                    break;
                case R.id.button_10y:
                    correlationPeriod = -10;
                    break;
                case R.id.button_20y:
                    correlationPeriod = -20;
                    break;
                default:
                    break;
            }
        });
    }

    @Background
    void findCorrelation(String ticker1, String ticker2) {
        displayCorrelation(QuoteAnalysis.assetCorrelation(ticker1, ticker2, correlationPeriod));

    }

    @UiThread
    void displayCorrelation(double correlation) {
        mResult.setText(correlation + "");

        // Fade in correlation result section
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        mResultSection.setVisibility(View.VISIBLE);
        mResultSection.startAnimation(anim);
    }
}
