package com.hmmelton.tangential.models;

import java.util.List;

/**
 * Created by harrisonmelton on 7/23/16.
 * This class holds the information of a portfolio.
 */
public class Portfolio {

    private List<String> assets;
    private double[] weights;
    private double stdDev, expectedReturn;

    public Portfolio() {}

    public Portfolio(List<String> assets) {
        this.assets = assets;
    }

    /*
    START SECTION SETTERS
     */

    public void setAssets(List<String> assets) {
        this.assets = assets;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public void setExpectedReturn(double expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    /*
    END SECTION SETTERS

    START SECTION GETTERS
     */

    public List<String> getAssets() {

        return assets;
    }

    public double getExpectedReturn() {
        return expectedReturn;
    }

    public double getStdDev() {
        return stdDev;
    }

    public double[] getWeights() {
        return weights;
    }

    /*
    END SECTION GETTERS
     */
}
