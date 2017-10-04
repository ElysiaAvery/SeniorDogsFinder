package com.example.guest.seniordogsfinder.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.guest.seniordogsfinder.Constants;
import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.adapters.DogsListAdapter;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.services.PetService;
import com.example.guest.seniordogsfinder.util.OnDogSelectedListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DogsActivity extends AppCompatActivity implements OnDogSelectedListener {
    public static final String TAG = DogsActivity.class.getSimpleName();
    private Integer mPosition;
    ArrayList<Dog> mDogs;
    String mSource;
    @Bind(R.id.dogPattern) ImageView dogPattern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);
        ButterKnife.bind(this);
        dogPattern.setBackgroundResource(R.drawable.dogpattern);

        if (savedInstanceState != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mDogs = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_DOGS));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);

                if (mPosition != null && mDogs != null) {
                    Intent intent = new Intent(this, DogDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_DOGS, Parcels.wrap(mDogs));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(intent);
                }

            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition != null && mDogs != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_DOGS, Parcels.wrap(mDogs));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }

    }

    @Override
    public void onDogSelected(Integer position, ArrayList<Dog> dogs, String source) {
        mPosition = position;
        mDogs = dogs;
        mSource = source;
    }

}
