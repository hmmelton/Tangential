package com.hmmelton.tangential.models;

/**
 * Created by harrisonmelton on 7/14/16.
 * This is a model class that holds calculated data about an asset.
 */
public class AnalyzedQuote {

    public String asset;
    public double expectedReturn, var_95, var_99, cVar;

    /**
     * This is the basic constructor.
     * @param expectedReturn expected return of asset over period specified in Monte Carlo
     *                       simulation - default is 250 trading days (1 year)
     * @param var Value at Risk of asset
     * @param cVar Conditional Value at Risk of asset
     */
    public AnalyzedQuote(String asset, double expectedReturn, double var_95, double var_99,
                         double cVar) {
        this.asset = asset;
        this.expectedReturn = expectedReturn;
        this.var_95 = var_95;
        this.var_99 = var_99;
        this.cVar = cVar;
    }
}
