package com.hmmelton.tangential.utils;

import com.hmmelton.tangential.TangentialApplication_;
import com.hmmelton.tangential.models.AnalyzedQuote;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yahoofinance.histquotes.HistoricalQuote;

/**
 * Created by harrisonmelton on 7/14/16.
 * This is a class for handling Monte Carlo simulations.
 */
public class MonteCarlo {

    String quote;
    int days;

    /**
     * Class constructor.
     * @param quote ticker of asset to be simulated
     * @param days number of days in the future after which the simulation should stop running
     */
    public MonteCarlo(String quote, int days) {
        this.quote = quote;
        this.days = days;
    }

    /**
     * This method runs a Monte Carlo simulation.
     */
    public AnalyzedQuote simulate() {
        List<HistoricalQuote> quotes = QuoteAnalysis.getAllQuotes(quote);
        if (quotes == null)
            return null;
        DescriptiveStatistics stats = collectData(quotes);

        double mean = stats.getMean();
        double variance = stats.getVariance();
        double standardDeviation = stats.getStandardDeviation();
        double drift = mean + (variance / 2);

        // Most recent asset quote
        double lastPrice = stats.getElement(0);

        return runSimulation(lastPrice, mean, drift, standardDeviation);
    }

    /**
     * This method runs the simulation on the main thread, after the background thread has finished
     * gathering the required data.
     * @param quotes list of simulated asset's historical quotes
     */
    private DescriptiveStatistics collectData(List<HistoricalQuote> quotes) {
        List<Double> dailyReturns = findDailyReturns(quotes);

        // Array required for DescriptiveStatistics
        double[] returnsArray = new double[dailyReturns.size()];
        // Add every return from the list to the array
        for (int i = 0; i < dailyReturns.size(); i++)
            returnsArray[i] = dailyReturns.get(i);

        return new DescriptiveStatistics(returnsArray);
    }

    /**
     * This method finds the daily returns of the given stock quotes.
     * @param quotes list of historical quotes
     * @return list of daily returns
     */
    private List<Double> findDailyReturns(List<HistoricalQuote> quotes) {
        // Initialize list of returns
        List<Double> dailyReturns = new ArrayList<>();
        // For each day, calculate return and add to returns list
        for (int i = 1; i < quotes.size(); i++) {
            double today = quotes.get(i).getAdjClose().doubleValue();
            double yesterday = quotes.get(i - 1).getAdjClose().doubleValue();
            // Calculate daily return
            dailyReturns.add((today - yesterday) / today);
        }

        return dailyReturns;
    }

    /**
     * This method runs Monte Carlo simulations on the given asset.
     * @param lastPrice most recent price of asset
     * @param drift calculated as (mean + (variance / 2))
     * @param stdDev standard deviation of asset
     * @return {@link com.hmmelton.tangential.models.AnalyzedQuote} holding information about analyzed asset
     */
    private AnalyzedQuote runSimulation(double lastPrice, double mean,
                                        double drift, double stdDev) {
        NormalDistribution distribution = new NormalDistribution(mean, stdDev);
        ArrayList<Double> finalVals = new ArrayList<>();
        // Run 1,000 simulations
        for (int i = 0; i < 1000; i++) {
            double finalVal = lastPrice;
            for (int j = 0; j < days; j++) {
                finalVal = finalVal * Math.exp(drift +
                        (stdDev * distribution.inverseCumulativeProbability(Math.random())));
            }
            finalVals.add(finalVal);
        }
        // Sort returns in ascending order
        Collections.sort(finalVals);

        // Convert list to arrays
        double[] valsArray = new double[finalVals.size()];
        double[] valsLow10 = new double[10]; // Used for VaR 95% confidence interval
        double[] valsLow50 = new double[50]; // Used for VaR 99% confidence interval
        // Add values from Set to arrays
        for (int i = 0; i < finalVals.size(); i++) {
            valsArray[i] = finalVals.iterator().next();
            if (i < 10)
                valsLow10[i] = valsArray[i];
            if (i < 50)
                valsLow50[i] = valsArray[i];
        }

        DescriptiveStatistics statsAll = new DescriptiveStatistics(valsArray);
        DescriptiveStatistics stats95 = new DescriptiveStatistics(valsLow10);
        DescriptiveStatistics stats99 = new DescriptiveStatistics(valsLow50);
        // Calculate mean return, VaR 95% CI, and VaR 99% CI
        double valsMean = statsAll.getMean();
        double vals95Mean = stats95.getMean();
        double vals99Mean = stats99.getMean();

        double riskFreeRate = TangentialApplication_.getRiskFreeRate();

        AnalyzedQuote newQuote = new AnalyzedQuote(quote, valsMean, vals95Mean, vals99Mean,
                -1, (valsMean - riskFreeRate) / stdDev);

        // Save newly analyzed quote to local storage
        LocalStorage.saveAnalyzedQuote(newQuote);
        return newQuote;
    }

}
