package com.example.guest.seniordogsfinder.ui;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    @Bind(R.id.animatedBackground) ImageView mAnimatedBackground;
    @Bind(R.id.header) TextView mHeader;
    @Bind(R.id.aboutText) TextView mAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mAnimatedBackground.setBackgroundResource(R.drawable.progress_animation);

        AnimationDrawable progressAnimation = (AnimationDrawable) mAnimatedBackground.getBackground();

        progressAnimation.start();

        Typeface goodDogFont = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        mHeader.setTypeface(goodDogFont);
        Typeface bonvenoFont = Typeface.createFromAsset(getAssets(), "fonts/BonvenoCF-Light.otf");
        mAbout.setTypeface(bonvenoFont);
    }
}
