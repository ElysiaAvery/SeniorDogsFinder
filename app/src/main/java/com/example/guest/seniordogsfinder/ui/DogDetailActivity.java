package com.example.guest.seniordogsfinder.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.adapters.DogPagerAdapter;
import com.example.guest.seniordogsfinder.models.Dog;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DogDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private DogPagerAdapter adapterViewPager;
    ArrayList<Dog> mDogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_detail);
        ButterKnife.bind(this);

        mDogs = Parcels.unwrap(getIntent().getParcelableExtra("dogs"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new DogPagerAdapter(getSupportFragmentManager(), mDogs);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
