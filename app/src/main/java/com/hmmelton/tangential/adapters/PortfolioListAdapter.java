package com.hmmelton.tangential.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.models.StyledQuote;
import com.hmmelton.tangential.utils.LocalStorage;
import com.hmmelton.tangential.utils.QuoteAnalysis;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DrawableRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;


/**
 * Created by harrisonmelton on 7/16/16.
 * This is an adapter for the PortfolioFragment RecyclerView.
 */
@EBean
public class PortfolioListAdapter extends
        RecyclerView.Adapter<PortfolioListAdapter.PortfolioListHolder> {

    private List<Stock> mPortfolios;

    @DrawableRes(R.drawable.price_up_bg)
    Drawable priceUp;
    @DrawableRes(R.drawable.price_down_bg)
    Drawable priceDown;

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
        String ticker = mPortfolios.get(position).getSymbol();
        holder.assetTitle.setText(ticker);
        double price = mPortfolios.get(position).getQuote().getPrice().doubleValue();
        StyledQuote quote = QuoteAnalysis.getChangeStyledQuote(ticker, -1);
        if (quote.getColor() == R.color.gain)
            holder.assetPrice.setBackground(priceUp);
        else
            holder.assetPrice.setBackground(priceDown);
        holder.assetPrice.setText(price + "");
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

        public PortfolioListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
