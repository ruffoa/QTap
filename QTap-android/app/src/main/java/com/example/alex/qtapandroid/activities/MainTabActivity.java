package com.example.alex.qtapandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.buildings.Building;
import com.example.alex.qtapandroid.common.database.buildings.BuildingManager;
import com.example.alex.qtapandroid.common.database.courses.Course;
import com.example.alex.qtapandroid.common.database.courses.CourseManager;
import com.example.alex.qtapandroid.common.database.services.Service;
import com.example.alex.qtapandroid.common.database.services.ServiceManager;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.users.UserManager;
import com.example.alex.qtapandroid.ui.fragments.AboutFragment;
import com.example.alex.qtapandroid.ui.fragments.CalendarFragment;
import com.example.alex.qtapandroid.ui.fragments.EngSocFragment;
import com.example.alex.qtapandroid.ui.fragments.ItsFragment;
import com.example.alex.qtapandroid.ui.fragments.InformationFragment;
import com.example.alex.qtapandroid.ui.fragments.StudentToolsFragment;

/**
 * activity holding most of the app.
 * contains the drawer that navigates user to fragments with map, schedule, info etc.
 */
public class MainTabActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mIsViewAtHome;
    //used to show off database
    private CourseManager mCourseManager;
    private BuildingManager mBuildingManager;
    private UserManager mUserManager;
    private ServiceManager mServiceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCourseManager = new CourseManager(this);
        mBuildingManager = new BuildingManager(this);
        mUserManager = new UserManager(this);
        mServiceManager = new ServiceManager(this);
        //TODO replace fab, or get rid of it
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //shows off the database in logcat
                /*User carson = new User("14cdwc", "Carson", "Cook");
                User alex = new User("14abcr", "Alex", "Ruffo");
                User lachlan = new User("14labd", "Lachlan", "Devir");
                carson.setID(mUserManager.insertRow(carson));
                alex.setID(mUserManager.insertRow(alex));
                lachlan.setID(mUserManager.insertRow(lachlan));
                User.printUsers(mUserManager.getTable());
                User michael = new User("15mabw", "Michael", "Wang");
                lachlan = mUserManager.updateRow(lachlan, michael);
                User.printUsers(mUserManager.getTable());
                mUserManager.deleteRow(lachlan);
                User.printUsers(mUserManager.getTable());
                mUserManager.deleteTable();*/
                //User.printUsers(mUserManager.getTable());
                Service.printServices(mServiceManager.getTable());
                /*
                Building bio = new Building("biosci");
                Building wlh = new Building("wlh");
                Building ilc = new Building("ilc");
                bio.setID(mBuildingManager.insertRow(bio));
                wlh.setID(mBuildingManager.insertRow(wlh));
                ilc.setID(mBuildingManager.insertRow(ilc));
                Building.printBuildings(mBuildingManager.getTable());
                Course sigs = new Course("252", bio.getID(), "1102", "11:30");
                Course sci = new Course("212", wlh.getID(), "210", "4:30");
                Course funds = new Course("280", ilc.getID(), "205", "9:30");
                sigs.setID(mCourseManager.insertRow(sigs));
                sci.setID(mCourseManager.insertRow(sci));
                sci = mCourseManager.updateRow(sci, funds);
                Course.printCourses(mCourseManager.getTable());
                mCourseManager.deleteRow(sigs);
                Course.printCourses(mCourseManager.getTable());
                mCourseManager.deleteTable();
                Course.printCourses(mCourseManager.getTable());
                mBuildingManager.deleteTable();
                Building.printBuildings(mBuildingManager.getTable());*/
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
    }

    /**
     * does not call super onBackPressed.
     * back closes drawer and sends user to calendar fragment (schedule).
     * If already on calendar, exits app.
     */
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
    protected void onDestroy() {
        mCourseManager.close();
        mBuildingManager.close();
        mUserManager.close();
        mServiceManager.close();
        super.onPause();
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

    /**
     * logic to decide what fragment to show, based on what drawer item user clicked.
     * will attach new fragment.
     * contains logic to know if on the home fragment or not, for back pressed logic.
     * changes title of screen as well.
     *
     * @param viewId the ID of the drawer item user clicked.
     */
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
                fragment = new InformationFragment();
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
