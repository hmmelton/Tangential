package com.hmmelton.tangential.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hmmelton.tangential.models.AnalyzedQuote;

import java.util.ArrayList;
import java.util.List;

import yahoofinance.Stock;

/**
 * Created by harrisonmelton on 7/15/16.
 * This is a helper class for local storage
 */
public class LocalStorage {

    private static final String PREFS_FILE = "com.hmmelton.tangential.local_storage";
    private static final String RECENTLY_ANALYZED_KEY = "recently_analyzed";
    private static final String PORTFOLIOS_KEY = "portfolios";
    private static SharedPreferences prefs;

    /**
     * This method initializes the SharedPreferences instance.
     * @param context context by which to instantiate SharedPreferences
     */
    public static void initialize(Context context) {
        prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    /**
     * This method saves an analyzed quote to local storage.
     * @param quote analyzed quote to be saved
     */
    @SuppressWarnings("unchecked")
    public static void saveAnalyzedQuote(AnalyzedQuote quote) {
        // Get recently analyzed quotes from storage
        ArrayList<AnalyzedQuote> recentQuotes = getAnalyzedQuotes();
        recentQuotes.add(quote);
        // Save changes to local storage
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(RECENTLY_ANALYZED_KEY, new Gson().toJson(recentQuotes));
        editor.commit();
    }

    /**
     * This method retrieves recently analyzed quotes from storage.
     * @return ArrayList of recently analyzed quotes
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<AnalyzedQuote> getAnalyzedQuotes() {
        // Get recently analyzed quotes from storage
        String recentString = prefs.getString(RECENTLY_ANALYZED_KEY, null);
        // Convert JSON string to ArrayList
        if (recentString != null)
            return new Gson().fromJson(recentString, ArrayList.class);
        else
            return new ArrayList<>();
    }

    /**
     * This method saves a new portfolio to local storage
     * @param stocks stocks in portfolio
     */
    public static void savePortfolioStocks(List<Stock> stocks) {
        // Get portfolios from storage
        List<Stock> portfolios = getPortfolioStocks();
        portfolios.clear();
        portfolios.addAll(stocks);
        // Save changes to local storage
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PORTFOLIOS_KEY, new Gson().toJson(portfolios));
        editor.commit();
    }

    /**
     * This is a method for returning the user's portfolios
     * @return user's portfolios
     */
    @SuppressWarnings("unchecked")
    public static List<Stock> getPortfolioStocks() {
        // Get portfolios from storage
        String portfolios = prefs.getString(PORTFOLIOS_KEY, null);
        // Convert JSON string to HashMap
        if (portfolios != null)
            return new Gson().fromJson(portfolios, List.class);
        else
            return new ArrayList<>();
    }
}
