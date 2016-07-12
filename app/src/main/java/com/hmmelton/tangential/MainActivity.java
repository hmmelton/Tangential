package com.hmmelton.tangential;

import android.os.AsyncTask;
import android.os.Bundle;
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

import com.hmmelton.tangential.fragments.CorrelationFragment;
import com.hmmelton.tangential.fragments.HomeFragment;
import com.hmmelton.tangential.models.StyledQuote;
import com.hmmelton.tangential.utils.QuoteHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();


    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    // List of display fields for index values
    private List<TextView> indexes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Change text color of toolbar from main text color
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        // Get header view in order to
        View headerLayout = navigationView.getHeaderView(0);
        // Add index display fields to list
        indexes.add((TextView) headerLayout.findViewById(R.id.index_1));
        indexes.add((TextView) headerLayout.findViewById(R.id.index_2));
        indexes.add((TextView) headerLayout.findViewById(R.id.index_3));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_holder, HomeFragment.newInstance())
                .commit();

        // Set up Navigation Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setIndexPrices();
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
                        .replace(R.id.main_fragment_holder, HomeFragment.newInstance())
                        .commit();
                getSupportActionBar().setTitle(getString(R.string.app_name));
                break;
            case R.id.nav_correlation:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_holder, CorrelationFragment.newInstance())
                        .commit();
                getSupportActionBar().setTitle(getString(R.string.correlation));
                break;
            case R.id.nav_s_ratio:
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
    private void setIndexPrices() {
        // Ticker symbols for the indexes
        final String[] indexTickers = { "^DJI", "^IXIC", "^GSPC" };

        for (int i = 0; i < indexes.size(); i++) {
            final int j = i;
            new AsyncTask<String, Void, StyledQuote>() {
                @Override
                protected StyledQuote doInBackground(String... strings) {
                    return QuoteHelper.getChangeStyledQuote(strings[0], 1);
                }

                @Override
                protected void onPostExecute(StyledQuote quote) {
                    super.onPostExecute(quote);
                    if (quote != null) {
                        indexes.get(j).setText(quote.getValue() + "");
                        indexes.get(j).setTextColor(getResources().getColor(quote.getColor()));
                    } else
                        indexes.get(j).setText(getString(R.string.error));
                }
            }.execute(indexTickers[i]);
        }
    }
}
