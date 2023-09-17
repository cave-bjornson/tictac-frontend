package com.considlia.tictac.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0: {
                return new TimeFragment();

            }
            case 1: {
                return new ReportsFragment();

            }
            case 2: {
                return new SummaryFragment();
            }
            case 3: {
                return new AccountFragment();
            }
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
