package com.hmmelton.tangential.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.utils.LocalStorage;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;

/**
 * Created by harrisonmelton on 7/16/16.
 * This is an adapter for the PortfolioFragment RecyclerView.
 */
public class PortfolioListAdapter extends
        RecyclerView.Adapter<PortfolioListAdapter.PortfolioListHolder> {

    private List<Stock> mPortfolios;

    /**
     * Default constructor
     */
    @SuppressWarnings("unchecked")
    public PortfolioListAdapter() {
        mPortfolios = LocalStorage.getPortfolioStocks();
    }

    @Override
    public PortfolioListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PortfolioListHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.portfolio_row, parent, false));
    }

    @Override
    public void onBindViewHolder(PortfolioListHolder holder, int position) {
        //holder.assetTitle.setText(mPortfolios.get(position));
    }

    @Override
    public int getItemCount() {
        return mPortfolios.size();
    }

    public class PortfolioListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.asset_title)
        TextView assetTitle;
        @BindView(R.id.asset_price)
        TextView assetPrice;

        @BindColor(android.R.color.white) int white;
        @BindColor(R.color.gain) int green;

        public PortfolioListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
