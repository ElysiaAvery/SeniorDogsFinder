package com.example.guest.seniordogsfinder.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResourcesActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.animatedBackground) ImageView mAnimatedBackground;
    @Bind(R.id.header) TextView mHeader;
    @Bind(R.id.seniorWeb) TextView mSeniorWeb;
    @Bind(R.id.vetWeb) TextView mVetWeb;
    @Bind(R.id.muttville) TextView mMuttville;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        ButterKnife.bind(this);

        mAnimatedBackground.setBackgroundResource(R.drawable.clap_animation);

        AnimationDrawable progressAnimation = (AnimationDrawable) mAnimatedBackground.getBackground();

        progressAnimation.start();

        Typeface goodDogFont = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        Typeface bonvenoFont = Typeface.createFromAsset(getAssets(), "fonts/BonvenoCF-Light.otf");
        mHeader.setTypeface(bonvenoFont);
        mSeniorWeb.setTypeface(goodDogFont);
        mVetWeb.setTypeface(goodDogFont);
        mMuttville.setTypeface(goodDogFont);

        mSeniorWeb.setOnClickListener(this);
        mVetWeb.setOnClickListener(this);
        mMuttville.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mSeniorWeb) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.srdogs.com/"));
            startActivity(webIntent);
        } else if(v == mVetWeb) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.avma.org/public/PetCare/Pages/Caring-for-an-Older-Pet-FAQs.aspx"));
            startActivity(webIntent);
        } else if(v == mMuttville) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.muttville.org/resources"));
            startActivity(webIntent);
        }
    }
}
