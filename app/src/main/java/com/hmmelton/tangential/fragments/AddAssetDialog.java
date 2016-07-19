package com.hmmelton.tangential.fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.InputFilter;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.utils.QuoteAnalysis;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by harrisonmelton on 7/11/16.
 * This is a DialogFragment that allows the user to add a new stock quote to the
 */
@EFragment(R.layout.add_asset_dialog)
public class AddAssetDialog extends DialogFragment {

    @ViewById(R.id.asset_input) EditText mInput;
    @StringRes(R.string.invalid_ticker) String invalidTicker;

    @Click(R.id.add_asset_button)
    void onAddAssetClick() {
        checkIsValidTicker(mInput.getText().toString().trim());
    }

    public static final String NEW_ASSET_KEY = "new_asset";

    @AfterViews
    void buildDialog() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mInput.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        builder.setView(getView());
    }

    /**
     * This method checks whether or not the ticker is valid.
     * @param asset ticker of asset to be tested
     */
    private void checkIsValidTicker(String asset) {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                return QuoteAnalysis.getQuote(strings[0]) == -1;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean)
                    Toast.makeText(getActivity(), invalidTicker, Toast.LENGTH_LONG)
                            .show();
                else {
                    sendResult(asset);
                }

            }
        }.execute(asset);
    }

    /**
     * This method sends the ticker of the newly added asset back to its target Fragment.
     * @param asset ticker of asset to be sent back to Fragment
     */
    private void sendResult(String asset) {
        Intent intent = new Intent();
        intent.putExtra(NEW_ASSET_KEY, asset);
        getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intent);
        AddAssetDialog.this.getDialog().dismiss();
    }
}
