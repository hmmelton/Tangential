package com.hmmelton.tangential.utils;

import com.hmmelton.tangential.models.Portfolio;

import org.jblas.DoubleMatrix;
import org.jblas.Solve;

import java.util.List;

/**
 * Created by harrisonmelton on 7/18/16.
 * This class is used to calculated the efficiency frontier, minimum variance portfolio, and
 * tangency portfolio of a set of assets.
 */
public class EfficiencyFrontier {

    private List<String> mAssets;
    private double mRiskFreeRate;
    // Variance/Covariance matrices
    private DoubleMatrix mCovars;
    private DoubleMatrix mInv;
    // Ones matrices
    private DoubleMatrix mOnes;
    private DoubleMatrix mOnesT;
    // Expected returns matrix
    private DoubleMatrix mRe;

    /**
     * Default constructor
     *
     * @param assets list of assets in portfolio
     */
    public EfficiencyFrontier(List<String> assets, double riskFreeRate) {
        mAssets = assets;
        mRiskFreeRate = riskFreeRate;
        init();
    }

    /**
     * This method initializes the instance variables.
     */
    private void init() {
        // Create new DoubleMatrix with row & column numbers equal to number of portfolio assets
        mCovars = calculateCovarMatrix();
        // Find the inverse of the covariance matrix
        mInv = Solve.pinv(mCovars);

        // Ones vectors
        mOnes = new DoubleMatrix(mAssets.size(), 1);
        for (String str : mAssets)
            mOnes.add(1);
        mOnesT = mOnes.transpose();

        // Yearly returns column vector
        mRe = calculateYearlyReturns();
    }

    /**
     * This method calculates the tangency portfolio of the portfolio's assets.
     * @return tangency portfolio
     */
    public Portfolio calculateTangency() {
        // Multiply inverse covariance matrix by expected return column vector
        DoubleMatrix tangencynum = mInv.mmul(mRe);
        // Multiply ones row vector by inverse covariance matrix
        DoubleMatrix invColSums = mOnesT.mmul(mInv);
        // Multiply inverse covariance matrix column sums by expected returns
        DoubleMatrix invColReturns = invColSums.mmul(mRe);

        return buildPortfolio(invColReturns, tangencynum);
    }

    /**
     * This method calculates the minimum variance portfolio of the portfolio's assets.
     * @return minimum variance portfolio
     */
    public Portfolio calculateMinVar() {
        // Multiply inverse covariance matrix by ones column vector
        DoubleMatrix minVarNum = mInv.mmul(mOnes);
        // Multiply ones row vector by inverse covariance matrix
        DoubleMatrix columnSums = mOnesT.mmul(mInv);
        // Multiply column sums by ones column vector
        DoubleMatrix minVarDen = columnSums.mmul(mOnes);

        return buildPortfolio(minVarDen, minVarNum);
    }

    /**
     * This method builds and returns a portfolio
     * @param denominator denominator of weights equation
     * @param numerator numerator of weights equation
     * @return portfolio
     */
    private Portfolio buildPortfolio(DoubleMatrix denominator, DoubleMatrix numerator) {
        Portfolio portfolio = new Portfolio(mAssets);

        // Calculate weights
        DoubleMatrix tangencynumT = numerator.transpose();
        DoubleMatrix weights = tangencynumT.div(denominator);

        // Add to weights and return to portfolio
        portfolio.setWeights(weights.toArray());
        portfolio.setExpectedReturn(weights.mmul(mRe).toArray()[0]);

        return portfolio;
    }

    /**
     * This method calculates the variances/covariances of the portfolio's assets
     * @return
     */
    private DoubleMatrix calculateCovarMatrix() {
        int years = -100;
        DoubleMatrix covars = new DoubleMatrix(mAssets.size(), mAssets.size());
        // Set matrix cells to covariance of assets on on whose row/column the cell lies
        for (int i = 0; i < mAssets.size(); i++) {
            for (int j = 0; j < mAssets.size(); j++) {
                covars.add(QuoteAnalysis.assetCovariance(mAssets.get(i), mAssets.get(j), years));
            }
        }

        return covars;
    }

    /**
     * This method returns a column vector of the portfolio assets' average yearly returns.
     * @return column vector of returns
     */
    private DoubleMatrix calculateYearlyReturns() {
        DoubleMatrix re = new DoubleMatrix(mAssets.size(), 1);
        // Find average yearly return of every stock in portfolio
        for (String str : mAssets) {
            double mReturn = QuoteAnalysis.getAverageReturn(str);
            re.add(mReturn - mRiskFreeRate);
        }

        return re;
    }

}
