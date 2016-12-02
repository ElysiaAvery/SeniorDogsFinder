package com.example.guest.seniordogsfinder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.adapters.DogsListAdapter;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.services.PetService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DogsActivity extends AppCompatActivity {
    public static final String TAG = DogsActivity.class.getSimpleName();

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private DogsListAdapter mAdapter;

    public ArrayList<Dog> mDogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        getDogs(location);
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

//                DogsActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter = new DogListAdapter(getApplicationContext(), mDogs);
//                        mRecyclerView.setAdapter(mAdapter);
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DogsActivity.this);
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);
//                    }
//                });
            }
        });
    }
}
