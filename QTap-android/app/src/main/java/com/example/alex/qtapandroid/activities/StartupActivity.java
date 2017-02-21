package com.example.alex.qtapandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.alex.qtapandroid.common.PrefManager;
import com.example.alex.qtapandroid.R;

import io.fabric.sdk.android.Fabric;

/**
 * Activity that holds on app start UI/info.
 * Checks/stores if this is the first time the user has run the app.
 * If it is, shows some introduction pages.
 */
public class StartupActivity extends AppCompatActivity {

    //TODO document methods
    //TODO move shared pref info to database

    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private int[] mLayouts;
    private Button mButtonSkip, mButtonNext;
    private PrefManager mPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        // Checking for first time launch - before calling setContentView()
        mPrefManager = new PrefManager(this);
        if (!mPrefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_startup);

        // layouts of all welcome sliders, can add more layouts
        mLayouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4
        };

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mDotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        mButtonSkip = (Button) findViewById(R.id.btn_skip);
        mButtonNext = (Button) findViewById(R.id.btn_next);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.addOnPageChangeListener(mViewPagerPageChangeListener);

        addBottomDots(0); // adding bottom dots
        changeStatusBarColor(); // making notification bar transparent

        mButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page -> if so, home screen will be launched
                int current = getItem(+1);
                if (current < mLayouts.length) {
                    // move to next screen
                    mViewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    /**
     * Adds dots to the bottom of the introduction pages, individually.
     *
     * @param currentPage The introduction page currently being shown, aka the one to
     *                    add dots to.
     */
    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[mLayouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        mDotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml(getString(R.string.ellipses)));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            mDotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    /**
     * Method that launches the home screen. Starts another activity and finishes this one.
     * Also logs that the user has opened the app before, so this won't be shown again.
     */
    private void launchHomeScreen() {
        mPrefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(StartupActivity.this, LoginActivity.class));
        finish();
    }

    //defines the on page change listener for the view pager
    ViewPager.OnPageChangeListener mViewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == mLayouts.length - 1) {
                // last page. make button text to GOT IT
                mButtonNext.setText(getString(R.string.start));
                mButtonSkip.setVisibility(View.GONE);
            } else {
                // there are still pages left
                mButtonNext.setText(getString(R.string.next));
                mButtonSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     * Method that makes the status bar change colour.
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * Class that makes a custom View Pager adapter.
     * No new methods, however overrides and changes (with no super call) instantiateItem(),
     * getCount(), isViewFromObject() and destroyItem().
     * These changes allow for custom layouts, and manipulating those custom layouts.
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater mLayoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mLayoutInflater.inflate(mLayouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mLayouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
