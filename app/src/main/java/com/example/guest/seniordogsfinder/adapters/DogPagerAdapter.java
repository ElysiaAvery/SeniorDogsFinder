package com.example.guest.seniordogsfinder.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guest.seniordogsfinder.fragments.DogDetailFragment;
import com.example.guest.seniordogsfinder.models.Dog;

import java.util.ArrayList;

/**
 * Created by Guest on 12/2/16.
 */
public class DogPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Dog> mDogs;
    private String mSource;

    public DogPagerAdapter(FragmentManager fm, ArrayList<Dog> dogs, String source) {
        super(fm);
        mDogs = dogs;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return DogDetailFragment.newInstance(mDogs, position, mSource);
    }

    @Override
    public int getCount() {
        return mDogs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDogs.get(position).getName();
    }
}
