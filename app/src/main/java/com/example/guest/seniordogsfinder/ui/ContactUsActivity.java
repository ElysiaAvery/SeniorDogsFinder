package com.example.guest.seniordogsfinder.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = ContactUsActivity.class.getSimpleName();
    @Bind(R.id.animatedBackground) ImageView mAnimatedBackground;
    @Bind(R.id.travelBackground) ImageView mTravelBackground;
    @Bind(R.id.howTo) TextView mHowTo;
    @Bind(R.id.gitHub) TextView mGitHub;
    @Bind(R.id.phone) TextView mPhone;
    @Bind(R.id.email) TextView mEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ButterKnife.bind(this);

        mAnimatedBackground.setBackgroundResource(R.drawable.beautiful_aniamtion);

        AnimationDrawable progressAnimation = (AnimationDrawable) mAnimatedBackground.getBackground();

        progressAnimation.start();

        mTravelBackground.setBackgroundResource(R.drawable.travel_animation);

        AnimationDrawable travelAnimation = (AnimationDrawable) mTravelBackground.getBackground();

        travelAnimation.start();

        Typeface goodDogFont = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        mGitHub.setTypeface(goodDogFont);
        mPhone.setTypeface(goodDogFont);
        mEmail.setTypeface(goodDogFont);
        Typeface bonvenoFont = Typeface.createFromAsset(getAssets(), "fonts/BonvenoCF-Light.otf");
        mHowTo.setTypeface(bonvenoFont);

        mGitHub.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mGitHub) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/ElysiaAvery"));
            startActivity(webIntent);
        } else if (v == mPhone) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel: (971)-284-0378)"));
            startActivity(phoneIntent);
        } else if (v == mEmail) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"elysia.avery@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I saw your Senior Pup! app");
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Choose an E-mail client: "));
        }

    }
}
