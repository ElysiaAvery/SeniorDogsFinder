package com.example.guest.seniordogsfinder.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.ui.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    @Bind(R.id.header) TextView mHeader;
    @Bind(R.id.signIn) Button mSignIn;
    @Bind(R.id.signOut) Button mSignOut;
    @Bind(R.id.findDogsButton) Button mFindDogsButton;
    @Bind(R.id.userPageButton) Button mUserPageButton;
    @Bind(R.id.locationInput) EditText mLocationInput;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView)findViewById(R.id.navList);
        String[] navigationArray = { "Profile", "Organizations", "Dogs" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navigationArray);
        mDrawerList.setAdapter(mAdapter);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();

        Typeface goodDogFont = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.otf");
        mHeader.setTypeface(goodDogFont);
        Typeface bonvenoFont = Typeface.createFromAsset(getAssets(), "fonts/BonvenoCF-Light.otf");
        mSignIn.setTypeface(bonvenoFont);
        mSignOut.setTypeface(bonvenoFont);
        mUserPageButton.setTypeface(bonvenoFont);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0) {
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                    } else if(position == 2) {
                        Intent intent = new Intent(MainActivity.this, DogsActivity.class);
                        startActivity(intent);
                    }
                }
        });

        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        if (auth.getCurrentUser() != null) {
            mSignIn.setVisibility(View.GONE);
            mSignOut.setVisibility(View.VISIBLE);
            mUserPageButton.setVisibility(View.VISIBLE);
        } else if (auth.getCurrentUser() == null) {
            mSignIn.setVisibility(View.VISIBLE);
            mSignOut.setVisibility(View.GONE);
            mUserPageButton.setVisibility(View.GONE);
        }
        mSignIn.setOnClickListener(this);
        mSignOut.setOnClickListener(this);
        mUserPageButton.setOnClickListener(this);
        mFindDogsButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == mSignIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if(v == mSignOut) {
            auth.signOut();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if(v == mUserPageButton) {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        } else if(v == mFindDogsButton) {
            String location = mLocationInput.getText().toString();
            Intent intent = new Intent(MainActivity.this, DogsActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }

}