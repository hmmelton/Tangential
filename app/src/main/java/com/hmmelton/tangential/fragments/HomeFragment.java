package com.hmmelton.tangential.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.adapters.NewsStoriesAdapter;
import com.hmmelton.tangential.utils.rss.RssFeed;
import com.hmmelton.tangential.utils.rss.RssItem;
import com.hmmelton.tangential.utils.rss.RssReader;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrisonmelton on 7/10/16.
 * This class is a fragment for the home screen.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends android.app.Fragment {

    @SuppressWarnings("unused")
    private final String TAG = "HomeFragment";

    @ViewById(R.id.home_recycler_view)
    RecyclerView mRecyclerView;
    @ViewById(R.id.news_feed_error)
    TextView mNewsFeedError;

    /**
     * Default constructor
     */
    public HomeFragment(){
        populateNewsFeed();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment_();
    }

    /**
     * This method populates the news feed with stories
     */
    @Background
    void populateNewsFeed() {
        try {
            // Attempt to fetch items from WSJ
            URL url = new URL("http://www.wsj.com/xml/rss/3_7031.xml");
            RssFeed feed = RssReader.read(url);

            ArrayList<RssItem> rssItems = feed.getRssItems();

            if (rssItems.size() > 0) {
                // Items were fetched successfully
                setUpAdapter(rssItems);
            } else {
                // Display error message
                displayErrorMessage(null);
            }
        } catch (Exception e) {
            // Display error message
            displayErrorMessage(e);
            e.printStackTrace();
        }
    }

    @UiThread
    void displayErrorMessage(Exception e) {
        Log.e(TAG, e == null ? "error" : e.getMessage());
    }

    @UiThread
    void setUpAdapter(List<RssItem> stories) {
        mNewsFeedError.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new NewsStoriesAdapter(stories));
    }

}
