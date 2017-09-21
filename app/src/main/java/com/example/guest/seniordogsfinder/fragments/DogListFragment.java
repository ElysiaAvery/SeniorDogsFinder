package com.example.guest.seniordogsfinder.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.seniordogsfinder.Constants;
import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.adapters.DogsListAdapter;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.services.GoogleService;
import com.example.guest.seniordogsfinder.services.PetService;
import com.example.guest.seniordogsfinder.util.OnDogSelectedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DogListFragment extends Fragment {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private DogsListAdapter mAdapter;
    public ArrayList<Dog> mDogs = new ArrayList<>();
    private OnDogSelectedListener mOnDogSelectedListener;
    private String mZipCode;
    Double latitude,longitude;
    Geocoder geocoder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnDogSelectedListener = (OnDogSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }

    }

    public DogListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(this.getContext(), Locale.getDefault());
//        mZipCode = Parcels.unwrap(getArguments().getParcelable("zipCode"));

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final Context that = this.getContext();
        if (!mSharedPreferences.contains("initialized")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Location Preferences");
            builder.setMessage("Search by either entering a zip code or using your current location.");

            builder.setPositiveButton("Current Location", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    getDogs(mZipCode);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Manual Search", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    mEditor = mSharedPreferences.edit();

                    setHasOptionsMenu(true);
                    dialog.dismiss();
                }
            });

            builder.show();
        } else {
            mEditor = mSharedPreferences.edit();

            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getDogs(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }

    private void getDogs(String location) {
        final PetService petService = new PetService();

        petService.findDogs(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mDogs = petService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new DogsListAdapter(getActivity(), mDogs, mOnDogSelectedListener);

                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dog_list, container, false);
        ButterKnife.bind(this, view);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        if (mRecentAddress != null) {
            getDogs(mRecentAddress);
        }

        return view;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v("HERE", "HERE!");

            latitude = Double.valueOf(intent.getStringExtra("latitude"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));

            Log.v("lat ", latitude + "");
            Log.v("lng ", longitude + "");

            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                while (addresses.size() == 0) {
                    addresses = geocoder.getFromLocation(latitude, longitude, 10);
                }
                if (addresses.size() > 0) {
                    mZipCode = addresses.get(0).getPostalCode();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
//            }catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//            Log.v("lat ", latitude + "");
//            Log.v("lng ", longitude + "");
            Log.v("zipcode: ", mZipCode);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.v("broadcast", " receiver");
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

}
