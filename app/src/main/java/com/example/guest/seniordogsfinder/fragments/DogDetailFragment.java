package com.example.guest.seniordogsfinder.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DogDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    private Dog mDog;

    public static DogDetailFragment newInstance(Dog dog) {
        DogDetailFragment dogDetailFragment = new DogDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("dog", Parcels.wrap(dog));
        dogDetailFragment.setArguments(args);
        return dogDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDog = Parcels.unwrap(getArguments().getParcelable("dog"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dog_detail, container, false);
        ButterKnife.bind(this, view);

//        Picasso.with(view.getContext())
//                .load(String.valueOf(mDog.getPhotos()))
//                .resize(MAX_WIDTH, MAX_HEIGHT)
//                .centerCrop()
//                .into(mImageLabel);

        //add areas to be set

        return view;
    }

    @Override
    public void onClick(View v) {
        //add implicit intents
    }

}
