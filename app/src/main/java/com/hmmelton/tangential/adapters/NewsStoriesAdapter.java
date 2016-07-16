package com.hmmelton.tangential.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmmelton.tangential.R;
import com.hmmelton.tangential.TangentialApplication_;
import com.hmmelton.tangential.utils.rss.RssItem;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by harrisonmelton on 7/15/16.
 * This is an adapter for the news stories on the front page.
 */
public class NewsStoriesAdapter extends RecyclerView.Adapter<NewsStoriesAdapter.NewsStoriesHolder> {

    private List<RssItem> stories;
    private final String SPACE = " ";

    /**
     * Generic constructor.
     * @param stories List of stories to populate RecyclerView
     */
    public NewsStoriesAdapter(List<RssItem> stories) {
        this.stories = stories;
    }

    @Override
    public NewsStoriesAdapter.NewsStoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsStoriesHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.news_story, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsStoriesAdapter.NewsStoriesHolder holder, int position) {
        RssItem story = stories.get(position);

        // Set content of list
        holder.newsTime.setText(formatDate(story.getPubDate()));
        holder.newsTitle.setText(story.getTitle());
        holder.newsSubtitle.setText(story.getDescription());
        holder.subscriptionLock.setVisibility(
                story.getCategory().equals("PAID") ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    /**
     * This class formats the date of the article's posting.
     * @param calendar Calendar object containing article's posting date/time
     * @return String format --> DD MMM(M) YYYY, hh:mm AM/PM
     */
    private String formatDate(Calendar calendar) {
        String[] months = {"Jan", "Feb", "March", "Apr", "May", "June", "July", "Aug", "Sept",
        "Oct", "Nov", "Dec"};

        // Get values from calendar
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Begin conversion to String
        String minuteString = minute < 10 ? "0" + minute : minute + "";
        String dayHalf = hour >= 12 ? "PM" : "AM";
        hour %= 12;
        String hourString = hour == 0 ? "12" : hour + "";
        String monthString = months[month];

        return (day + SPACE + monthString + SPACE + year + ", " + SPACE +
                hourString + ":" + minuteString + SPACE + dayHalf);
    }

    public class NewsStoriesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_time)
        TextView newsTime;
        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_subtitle)
        TextView newsSubtitle;
        @BindView(R.id.subscription_lock)
        ImageView subscriptionLock;

        public NewsStoriesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({ R.id.news_time, R.id.news_title, R.id.news_subtitle, R.id.subscription_lock})
        void onClick() {
            // Prepare intent for launching
            Uri webpage = Uri.parse(stories.get(getAdapterPosition()).getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Context context = TangentialApplication_.getInstance().getApplicationContext();

            // Verify it resolves
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            boolean isIntentSafe = activities.size() > 0;

            // Start an activity if it's safe
            if (isIntentSafe) {
                context.startActivity(intent);
            }
        }
    }
}
