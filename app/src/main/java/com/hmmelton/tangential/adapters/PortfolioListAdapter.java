package com.hmmelton.tangential.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmmelton.tangential.MainActivity;
import com.hmmelton.tangential.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by harrisonmelton on 7/16/16.
 * This is an adapter for the PortfoliosFragment RecyclerView.
 */
public class PortfolioListAdapter extends
        RecyclerView.Adapter<PortfolioListAdapter.PortfolioListHolder> {

    private List<String> mPortfolios;

    /**
     * Default constructor
     */
    @SuppressWarnings("unchecked")
    public PortfolioListAdapter() {
        Set<String> keys = new HashSet<>(Arrays.asList(new String[] {"Portfolio1", "Portfolio2"}));   /*LocalStorage.getPortfolios().keySet();
        mPortfolios.add("Portfolio 1");
        mPortfolios.add("Portfolio 2");*/
        mPortfolios = new ArrayList<>(keys);
    }

    @Override
    public PortfolioListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PortfolioListHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.portfolio_row, parent, false));
    }

    @Override
    public void onBindViewHolder(PortfolioListHolder holder, int position) {
        holder.portfolioTitle.setText(mPortfolios.get(position));
    }

    @Override
    public int getItemCount() {
        return mPortfolios.size();
    }

    public class PortfolioListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.portfolio_title)
        TextView portfolioTitle;

        @BindColor(android.R.color.white) int white;
        @BindColor(R.color.gain) int green;

        public PortfolioListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            MainActivity.changeFragment();
        }

        @OnClick(R.id.portfolio_title)
        void onRowClick() {
            portfolioTitle.setBackgroundColor(green);
            portfolioTitle.setTextColor(white);
        }
    }

}
