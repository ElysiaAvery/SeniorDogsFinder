package com.example.guest.seniordogsfinder.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.services.GoogleService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    @Bind(R.id.header) TextView mHeader;
    @Bind(R.id.signIn) Button mSignIn;
    @Bind(R.id.signOut) Button mSignOut;
    @Bind(R.id.findDogsButton) Button mFindDogsButton;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean location_permission;
    boolean internet_permission;
    boolean accounts_permission;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;

    Geocoder geocoder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView)findViewById(R.id.navList);
        String[] navigationArray = { "Profile", "About", "Contact Us", "Resources" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navigationArray);
        mDrawerList.setAdapter(mAdapter);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();
        geocoder = new Geocoder(this, Locale.getDefault());

        Typeface goodDogFont = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        mHeader.setTypeface(goodDogFont);
        Typeface bonvenoFont = Typeface.createFromAsset(getAssets(), "fonts/BonvenoCF-Light.otf");
        mFindDogsButton.setTypeface(bonvenoFont);
        mSignIn.setTypeface(bonvenoFont);
        mSignOut.setTypeface(bonvenoFont);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {};
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0) {
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                    } else if(position == 1) {
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                    } else if(position == 2) {
                        Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
                        startActivity(intent);
                    } else if(position == 3) {
                        Intent intent = new Intent(MainActivity.this, ResourcesActivity.class);
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
        } else if (auth.getCurrentUser() == null) {
            mSignIn.setVisibility(View.VISIBLE);
            mSignOut.setVisibility(View.GONE);
        }
        mSignIn.setOnClickListener(this);
        mSignOut.setOnClickListener(this);
        mFindDogsButton.setOnClickListener(this);
        fn_permission();
        internet_permission();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == mSignIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if(v == mSignOut) {
            auth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if(v == mFindDogsButton) {
            if (location_permission) {

                if (mPref.getString("service", "").matches("")) {
                    medit.putString("service", "service").commit();

                    Intent googleIntent = new Intent(getApplicationContext(), GoogleService.class);
                    startService(googleIntent);

                } else {
                    Log.v("Success", "services enabled");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enable the GPS", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(MainActivity.this, DogsActivity.class);
//            intent.putExtra("zipCode", Parcels.wrap(mZipCode));
            startActivity(intent);
        }

    }
    private void fn_permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {


            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION

                        },
                        REQUEST_PERMISSIONS);

            }
        } else {
            location_permission = true;
        }
    }

    private void internet_permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.INTERNET))) {


            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.INTERNET

                        },
                        REQUEST_PERMISSIONS);

            }
        } else {
            internet_permission = true;
        }
    }

    private void accounts_permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.GET_ACCOUNTS))) {


            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.GET_ACCOUNTS

                        },
                        REQUEST_PERMISSIONS);

            }
        } else {
            accounts_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location_permission = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

}