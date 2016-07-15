package com.hmmelton.tangential.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hmmelton.tangential.models.AnalyzedQuote;

import java.util.ArrayList;

/**
 * Created by harrisonmelton on 7/15/16.
 * This is a helper class for local storage
 */
public class LocalStorage {

    private static final String PREFS_FILE = "com.hmmelton.tangential.local_storage";
    private static final String RECENTLY_ANALYZED_KEY = "recently_analyzed";
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
    public static ArrayList<AnalyzedQuote> getAnalyzedQuotes() {
        // Get recently analyzed quotes from storage
        String recentString = prefs.getString(RECENTLY_ANALYZED_KEY, null);
        // Convert JSON string to ArrayList
        return new Gson().fromJson(recentString, ArrayList.class);
    }
}
