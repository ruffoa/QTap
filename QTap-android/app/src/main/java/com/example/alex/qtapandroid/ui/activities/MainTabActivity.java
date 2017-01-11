package com.example.alex.qtapandroid.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.ui.fragments.AboutFragment;
import com.example.alex.qtapandroid.ui.fragments.CalendarFragment;
import com.example.alex.qtapandroid.ui.fragments.EngSocFragment;
import com.example.alex.qtapandroid.ui.fragments.ItsFragment;
import com.example.alex.qtapandroid.ui.fragments.StudentToolsFragment;

public class MainTabActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mIsViewAtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(R.id.nav_schedule); //start at calendar view

        ////// Set Name and Email in nav header
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userEmail = preferences.getString("UserEmail", "defaultStringIfNothingFound");
        String userName = preferences.getString("UserName", "defaultStringIfNothingFound");

        View header = navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView name = (TextView) header.findViewById(R.id.navHeaderAccountName);
        TextView email = (TextView) header.findViewById(R.id.navHeaderAccountEmail);
        name.setText(userName);
        email.setText(userEmail);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!mIsViewAtHome) { //if the current view is not the calendar fragment
            displayView(R.id.nav_schedule); //display the calendar fragment
        } else {
            moveTaskToBack(true);  //If view is in calendar fragment, exit application
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_tab, menu);
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
        displayView(item.getItemId());
        return true;
    }

    private void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_schedule:
                fragment = new CalendarFragment();
                title = getString(R.string.calendar_fragment);
                mIsViewAtHome = true;
                break;
            case R.id.nav_map:
                startActivity(new Intent(MainTabActivity.this, MapsActivity.class));

                break;
            case R.id.nav_information:
//                fragment = new InformationFragment();
                title = getString(R.string.information_fragment);
                mIsViewAtHome = false;
                break;
            case R.id.nav_tools:
                fragment = new StudentToolsFragment();
                title = getString(R.string.student_tools_fragment);
                mIsViewAtHome = false;
                break;
            case R.id.nav_its:
                fragment = new ItsFragment();
                title = getString(R.string.its_fragment);
                mIsViewAtHome = false;
                break;
            case R.id.nav_engsoc:
                fragment = new EngSocFragment();
                title = getString(R.string.engsoc_fragment);
                mIsViewAtHome = false;
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                title = getString(R.string.about_fragment);
                mIsViewAtHome = false;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
