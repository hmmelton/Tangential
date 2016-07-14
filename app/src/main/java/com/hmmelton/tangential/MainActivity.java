package com.hmmelton.tangential;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hmmelton.tangential.fragments.CorrelationFragment_;
import com.hmmelton.tangential.fragments.HomeFragment_;
import com.hmmelton.tangential.fragments.SharpeRatioFragment_;
import com.hmmelton.tangential.models.StyledQuote;
import com.hmmelton.tangential.utils.QuoteHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // String resources
    @StringRes String appName;
    @StringRes String correlation;
    @StringRes (R.string.s_ratio) String sharpeRatio;
    @StringRes String error;

    @ColorRes int white;

    // List of display fields for index values
    private List<TextView> indexes = new ArrayList<>();

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();


    @ViewById(R.id.drawer_layout) DrawerLayout drawer;
    @ViewById(R.id.nav_view) NavigationView navigationView;

    @AfterViews
    void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Change text color of toolbar from main text color
        toolbar.setTitleTextColor(white);
        setSupportActionBar(toolbar);

        // Set up Navigation Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setIndexPrices();
    }

    @AfterViews
    void setUpFragmentManager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_holder, HomeFragment_.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_holder, HomeFragment_.newInstance())
                        .commit();
                getSupportActionBar().setTitle(appName);
                break;
            case R.id.nav_correlation:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_holder, CorrelationFragment_.newInstance())
                        .commit();
                getSupportActionBar().setTitle(correlation);
                break;
            case R.id.nav_s_ratio:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_holder, SharpeRatioFragment_.newInstance())
                        .commit();
                getSupportActionBar().setTitle(sharpeRatio);
                break;
            case R.id.nav_tangency:
                break;
            case R.id.nav_min_var:
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This method finds and displays the current prices of the given indexes.
     */
    @Background
    void setIndexPrices() {
        // Ticker symbols for the indexes
        final String[] indexTickers = { "^DJI", "^IXIC", "^GSPC" };
        // Get header view in order to
        View headerLayout = navigationView.getHeaderView(0);
        // Add index display fields to list
        indexes.add((TextView) headerLayout.findViewById(R.id.index_1));
        indexes.add((TextView) headerLayout.findViewById(R.id.index_2));
        indexes.add((TextView) headerLayout.findViewById(R.id.index_3));

        for (int i = 0; i < indexes.size(); i++) {
            displayStyledQuote(QuoteHelper.getChangeStyledQuote(indexTickers[i], 1), i);
        }
    }

    @UiThread
    void displayStyledQuote(StyledQuote quote, int index) {
        if (quote != null) {
            indexes.get(index).setText(quote.getValue() + "");
            indexes.get(index).setTextColor(getResources().getColor(quote.getColor()));
        } else
            indexes.get(index).setText(error);
    }
}
