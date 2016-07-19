package com.hmmelton.tangential.adapters;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.models.StyledQuote;
import com.hmmelton.tangential.utils.LocalStorage;
import com.hmmelton.tangential.utils.QuoteAnalysis;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;


/**
 * Created by harrisonmelton on 7/16/16.
 * This is an adapter for the PortfolioFragment RecyclerView.
 */
public class PortfolioListAdapter extends
        RecyclerView.Adapter<PortfolioListAdapter.PortfolioListHolder> {

    private List<String> mPortfolio;

    /**
     * Default constructor
     */
    @SuppressWarnings("unchecked")
    public PortfolioListAdapter() {
        mPortfolio = LocalStorage.getPortfolioStocks();
    }

    @Override
    public PortfolioListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PortfolioListHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.portfolio_row, parent, false));
    }

    @Override
    public void onBindViewHolder(PortfolioListHolder holder, int position) {
        String ticker = mPortfolio.get(position);
        holder.assetTitle.setText(ticker);
        checkCorrectBgColor(ticker, holder);
    }

    /**
     * This method checks what the background color of the asset price should be.
     * @param ticker
     * @param holder
     */
    private void checkCorrectBgColor(String ticker, PortfolioListHolder holder) {
        new AsyncTask<String, Void, StyledQuote>() {
            @Override
            protected StyledQuote doInBackground(String... strings) {
                return QuoteAnalysis.getChangeStyledQuote(ticker, 1);
            }

            @Override
            protected void onPostExecute(StyledQuote quote) {
                Resources res = holder.assetPrice.getResources();
                if (quote.getColor() == R.color.gain)
                    holder.assetPrice.setBackground(res.getDrawable(R.drawable.price_up_bg));
                else
                    holder.assetPrice.setBackground(res.getDrawable(R.drawable.price_down_bg));
                String price = NumberFormat.getCurrencyInstance(Locale.US).format(quote.getValue());
                holder.assetPrice.setText(price);
            }
        }.execute(ticker);
    }



    /**
     * This method adds an asset to the portfolio.
     * @param stock asset to be added
     */
    public void addValue(String stock) {
        mPortfolio.add(stock);
        notifyItemInserted(mPortfolio.size() - 1);
        LocalStorage.savePortfolioStocks(mPortfolio);
    }

    @Override
    public int getItemCount() {
        return mPortfolio.size();
    }

    public class PortfolioListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.asset_title)
        TextView assetTitle;
        @BindView(R.id.asset_price)
        TextView assetPrice;

        View mainView;
        int position;

        View.OnClickListener mUndoListener = view -> addItem(position);

        public PortfolioListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mainView = itemView;
        }

        @OnLongClick({ R.id.asset_title, R.id.asset_price })
        boolean onLongClick() {
            position = getAdapterPosition();
            removeItem(getAdapterPosition());
            Snackbar.make(mainView, String.format(Locale.US, "Removed asset %s",
                    assetTitle.getText().toString()),
                    Snackbar.LENGTH_LONG)
                    .setAction("Undo", mUndoListener)
                    .setActionTextColor(Color.GREEN)
                    .show();
            return true;
        }

        private void removeItem(int position) {
            mPortfolio.remove(position);
            notifyItemRemoved(position);
            LocalStorage.removePortfolioStock(position);
        }

        private void addItem(int position) {
            if (position < mPortfolio.size())
                mPortfolio.add(position, assetTitle.getText().toString());
            else
                mPortfolio.add(assetTitle.getText().toString());
            notifyItemInserted(position);
            LocalStorage.savePortfolioStockAtPosition(position, assetTitle.getText().toString());
        }
    }

}
