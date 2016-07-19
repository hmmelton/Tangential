package com.hmmelton.tangential.utils;

import java.util.List;

/**
 * Created by harrisonmelton on 7/18/16.
 * This class is used to calculated the efficiency frontier, minimum variance portfolio, and
 * tangency portfolio of a set of assets.
 */
public class EfficiencyFrontier {

    private List<String> mStocks;

    /**
     * Default constructor
     * @param stocks list of stocks in portfolio
     */
    public EfficiencyFrontier(List<String> stocks) {
        mStocks = stocks;
    }
}
