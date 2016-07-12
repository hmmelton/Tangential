package com.hmmelton.tangential.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.utils.QuoteHelper;

/**
 * Created by harrisonmelton on 7/11/16.
 * This is a DialogFragment that allows the user to add a new stock quote to the
 */
public class AddAssetDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialog = getActivity()
                .getLayoutInflater()
                .inflate(R.layout.add_asset_dialog, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText input = (EditText) dialog.findViewById(R.id.asset_input);
        input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        dialog.findViewById(R.id.add_asset_button).setOnClickListener(view ->
            checkIsValidTicker(input.getText().toString().trim()));

        builder.setView(dialog);
        return builder.create();
    }

    /**
     * This method checks whether or not the ticker is valid.
     * @param asset ticker of asset to be tested
     */
    private void checkIsValidTicker(String asset) {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                return QuoteHelper.getQuote(strings[0]).equals("error");
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean)
                    Toast.makeText(getActivity(), getString(R.string.invalid_ticker), Toast.LENGTH_LONG)
                            .show();
                else
                    AddAssetDialog.this.getDialog().dismiss();

            }
        }.execute(asset);
    }
}
