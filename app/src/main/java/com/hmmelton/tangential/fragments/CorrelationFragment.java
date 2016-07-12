package com.hmmelton.tangential.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.utils.QuoteHelper;

/**
 * Created by harrisonmelton on 7/10/16.
 * This class is a fragment for the correlation screen.
 */
public class CorrelationFragment extends Fragment {

    private int correlationPeriod;

    /**
     * Default constructor
     */
    public CorrelationFragment() {
        correlationPeriod = -1;
    }

    /**
     * This method creates a new instance of the CorrelationFragment class.
     * @return new instance of CorrelationFragment
     */
    public static CorrelationFragment newInstance() {
        return new CorrelationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_correlation, container, false);
        final EditText asset1 = (EditText) mainView.findViewById(R.id.asset_1);
        final EditText asset2 = (EditText) mainView.findViewById(R.id.asset_2);

        // OnCheckedChangeListener for correlation period RadioGroup
        ((RadioGroup) mainView.findViewById(R.id.year_button_group))
                .setOnCheckedChangeListener((radioGroup, checkedId) -> {
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

        // OnClick for calculation button
        mainView.findViewById(R.id.corr_calculation).setOnClickListener(view -> {
            final String assetText1 = asset1.getText().toString().trim();
            final String assetText2 = asset2.getText().toString().trim();
            new AsyncTask<Void, Void, Double>() {
                @Override
                protected Double doInBackground(Void... voids) {
                    // Calculate correlation
                    return QuoteHelper.assetCorrelation(assetText1, assetText2,
                            correlationPeriod);
                }

                @Override
                protected void onPostExecute(Double correlation) {
                    super.onPostExecute(correlation);
                    // Display correlation value
                    ((TextView) mainView.findViewById(R.id.corr_result)).setText(correlation + "");

                    // Fade in correlation result section
                    AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
                    anim.setDuration(500);
                    View result = mainView.findViewById(R.id.corr_section);
                    result.setVisibility(View.VISIBLE);
                    result.startAnimation(anim);
                }
            }.execute();
        });
        return mainView;
    }
}
