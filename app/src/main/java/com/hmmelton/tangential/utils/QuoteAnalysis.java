package com.hmmelton.tangential.utils;

import android.util.Log;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.models.StyledQuote;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by harrisonmelton on 7/10/16.
 * This is a helper class for analyzing quotes.
 */
public class QuoteAnalysis {

    private static final String TAG = "QuoteAnalysis";

    /**
     * This method returns the most recent asset quote.
     * @param quote
     * @return
     */
    public static String getQuote(String quote) {
        String value;
        // Get historical quotes
        List<HistoricalQuote> quotes = getQuotes(quote, Calendar.YEAR, -1);
        // Format to have 2 decimal places
        if (quotes != null) {
            BigDecimal bd = quotes.get(0)
                    .getAdjClose()
                    .setScale(2, RoundingMode.CEILING);
            value = bd.toString();
            return value;
        }
        return "error";
    }

    /**
     * this method returns a list of historical quotes for the given asset, over the given period
     * type and period length.
     * @param quote ticker symbol of asset
     * @param period Calendar object period (YEAR, WEEK_OF_MONTH, etc.)
     * @param periodLength -1 for 1 period in the past (YEAR, -1 would be 1 year in the past), etc.
     * @return
     */
    public static List<HistoricalQuote> getQuotes(String quote, int period, int periodLength) {
        try {
            Calendar from = Calendar.getInstance(); // start date
            Calendar to = Calendar.getInstance(); // end date today
            // TODO: change this to 1 day previous
            from.add(period, periodLength); // set start date to 1 year ago
            return YahooFinance.get(quote).getHistory(from, to, Interval.DAILY);
        } catch (Exception e) {
            Log.e(TAG, "error!!! " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method checks whether or not an asset has increased in the period provided.
     * @param quote ticker of asset
     * @param daysAgo number of days over which period asset is being performance-reviewed
     * @return
     */
    public static StyledQuote getChangeStyledQuote(String quote, int daysAgo) {
        List<HistoricalQuote> quotes = getQuotes(quote, Calendar.YEAR, -1);
        double mostRecent = quotes.get(0)
                .getAdjClose()
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
        double previous = quotes.get(daysAgo)
                .getAdjClose()
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
        return new StyledQuote(mostRecent, (mostRecent > previous) ? R.color.gain : R.color.loss);
    }

    /**
     * This method calculates the correlation between 2 assets.
     * @param quote1 ticker of first asset
     * @param quote2 ticker of second asset
     * @param numYears number of years for correlation
     * @return double representation of correlation value
     */
    public static double assetCorrelation(String quote1, String quote2, int numYears) {
        // Get historical quotes for both assets
        List<HistoricalQuote> quotes1 = getQuotes(quote1, Calendar.YEAR, numYears);
        List<HistoricalQuote> quotes2 = getQuotes(quote2, Calendar.YEAR, numYears);

        // Check if there was an error retrieving historical quotes
        if (quotes1 == null || quotes2 == null)
            return -2;

        // Convert Lists to arrays
        double[] quotesArray1 = listToDoubleArray(quotes1);
        double[] quotesArray2 = listToDoubleArray(quotes2);

        return new PearsonsCorrelation().correlation(quotesArray1, quotesArray2);
    }

    /**
     * This method converts a List to an array of doubles
     * @param list List to be converted
     * @return array version of list
     */
    private static double[] listToDoubleArray(List<HistoricalQuote> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i)
                    .getAdjClose()
                    .setScale(2, RoundingMode.CEILING)
                    .doubleValue();
        }
        return array;
    }

}
