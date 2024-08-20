package com.example.myapplication.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Fragments.CartFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.SearchFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context context;
    public FragmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Search";
            case 2:
                return "Shopping Cart";
        }
        return super.getPageTitle(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new CartFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
