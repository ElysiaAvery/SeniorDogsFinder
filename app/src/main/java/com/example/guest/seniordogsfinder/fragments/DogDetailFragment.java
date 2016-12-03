package com.example.guest.seniordogsfinder.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DogDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.dogImageView) ImageView mDogImageView;
    @Bind(R.id.dogName) TextView mDogNameTextView;
    @Bind(R.id.dogGender) TextView mDogGender;
//    @Bind(R.id.dogOptions) TextView mDogOptions;
    @Bind(R.id.dogPhone) TextView mDogPhone;
    @Bind(R.id.dogEmail) TextView mDogEmail;
    @Bind(R.id.dogDescription) TextView mDogDescription;

    private Dog mDog;

    public static DogDetailFragment newInstance(Dog dog) {
        DogDetailFragment dogDetailFragment = new DogDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("dogs", Parcels.wrap(dog));
        dogDetailFragment.setArguments(args);
        return dogDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDog = Parcels.unwrap(getArguments().getParcelable("dogs"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dog_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(String.valueOf(mDog.getPhotos()))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mDogImageView);

        //add areas to be set
        mDogNameTextView.setText(mDog.getName());
        mDogGender.setText("Gender: " + mDog.getGender());
//        mDogOptions.setText(android.text.TextUtils.join(", ", mDog.getOptions()));
        mDogPhone.setText(mDog.getPhone());
        mDogEmail.setText(mDog.getEmail());
        mDogDescription.setText(mDog.getDescription());
        mDogDescription.setMovementMethod(new ScrollingMovementMethod());

        mDogPhone.setOnClickListener(this);
        mDogEmail.setOnClickListener(this);
        mDogImageView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mDogPhone) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mDog.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mDogEmail) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND,
                    Uri.parse(mDog.getEmail()));
            startActivity(emailIntent);
        }
        if (v == mDogImageView) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.petfinder.com/petdetail/" + mDog.getId()));
            startActivity(webIntent);
        }
    }

}
