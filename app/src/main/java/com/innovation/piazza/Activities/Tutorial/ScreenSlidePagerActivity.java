package com.innovation.piazza.Activities.Tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.innovation.piazza.Activities.SignInMethodActivity;
import com.innovation.piazza.R;

public class ScreenSlidePagerActivity extends FragmentActivity {
    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        final Button next = (Button) findViewById(R.id.next_slide);
        final Button previous = (Button) findViewById(R.id.previous_slide);
        previous.setVisibility(View.INVISIBLE);
        
        mPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if(mPager.getCurrentItem() > 0)
                            previous.setVisibility(View.VISIBLE);
                        else
                            previous.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onPageSelected(int position) { }

                    @Override
                    public void onPageScrollStateChanged(int state) { }
                }
        );

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPager.getCurrentItem() == NUM_PAGES - 1) {
                    Intent nextActivity;
                    nextActivity = new Intent(getBaseContext(), SignInMethodActivity.class);
                    startActivity(nextActivity);
                    finishAffinity();
                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    previous.setVisibility(View.VISIBLE);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPager.getCurrentItem() == 1)
                    v.setVisibility(View.INVISIBLE);
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new WelcomePageFragment();
                case 1:
                    return new HowItWorksPageFragment();
                case 2:
                    return new AvantagesPageFragment();
                case 3:
                    return new Avantages2PageFragment();
                default:
                    return new Avantages3PageFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}