package com.considlia.tictac.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.considlia.tictac.MyViewModel;
import com.considlia.tictac.R;
import com.considlia.tictac.fragments.FragmentAdapter;
import com.considlia.tictac.network.ForegroundService;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 4;
    //The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    public static ViewPager2 viewPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private FragmentStateAdapter pagerAdapter;
    // Array of strings FOR TABS TITLES
    private String[] titles = new String[]{"Time", "Reports", "Summary", "Account"};

    private MyViewModel myViewModel;
    private MainActivity mainActivity;

    // tab titles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.myPager);
        viewPager.setAdapter(new FragmentAdapter(this));
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        //inflating tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position) {
                    case 0: {
                        tab.setText("TIME");
                        tab.setIcon(R.drawable.ic_baseline_timer_24);
                        break;
                    }
                    case 1: {
                        tab.setText("REPORT");
                        tab.setIcon(R.drawable.ic_baseline_report_24);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.red_tictacgram)
                        );
                        badgeDrawable.setVisible(true);
                        myViewModel.getUnreportedTimeLogs().observe(mainActivity, (Observer<Long>) longValue -> badgeDrawable.setNumber(longValue.intValue()));
                        break;
                    }
                    case 2: {
                        tab.setText("SUMMARY");
                        tab.setIcon(R.drawable.ic_baseline_summary_24);
                        break;
                    }
                    case 3: {
                        tab.setText("ACCOUNT");
                        tab.setIcon(R.drawable.ic_baseline_account_circle_24);
                        break;
                    }

                }
            }
        }
        );
        tabLayoutMediator.attach();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.d
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
}
