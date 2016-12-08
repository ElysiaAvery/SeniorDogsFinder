package com.example.guest.seniordogsfinder.ui;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.guest.seniordogsfinder.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    @Bind(R.id.animatedBackground) ImageView mAnimatedBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mAnimatedBackground.setBackgroundResource(R.drawable.progress_animation);

        AnimationDrawable progressAnimation = (AnimationDrawable) mAnimatedBackground.getBackground();

        progressAnimation.start();
    }
}
