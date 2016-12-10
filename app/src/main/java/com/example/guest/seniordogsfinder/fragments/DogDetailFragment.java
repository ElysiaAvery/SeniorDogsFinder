package com.example.guest.seniordogsfinder.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.seniordogsfinder.Constants;
import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    @Bind(R.id.dogOptions) TextView mDogOptions;
    @Bind(R.id.dogPhone) TextView mDogPhone;
    @Bind(R.id.dogEmail) TextView mDogEmail;
    @Bind(R.id.dogDescription) TextView mDogDescription;
    @Bind(R.id.addressTextView) TextView mAddressTextView;
    @Bind(R.id.sponsorDogButton) Button mSponsoredDogButton;
    @Bind(R.id.dogInfo) RelativeLayout mDogInfo;

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
        mAddressTextView.setText(mDog.getCity() + ", " + mDog.getState() + " " + mDog.getZip());
        mDogGender.setText("Gender: " + mDog.getGender());
        mDogOptions.setText(android.text.TextUtils.join(", ", mDog.getOptions()));
        mDogPhone.setText(mDog.getPhone());
        mDogEmail.setText(mDog.getEmail());
        mDogDescription.setText(mDog.getDescription());

        Typeface goodDogFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/LobsterTwo-Regular.otf");
        mDogNameTextView.setTypeface(goodDogFont);
        Typeface bonvenoFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BonvenoCF-Light.otf");
        mDogDescription.setTypeface(bonvenoFont);
        mSponsoredDogButton.setTypeface(bonvenoFont);
        mDogGender.setTypeface(bonvenoFont);
        mDogOptions.setTypeface(bonvenoFont);
        mDogPhone.setTypeface(bonvenoFont);
        mDogEmail.setTypeface(bonvenoFont);

        mDogPhone.setOnClickListener(this);
        mDogEmail.setOnClickListener(this);
        mDogImageView.setOnClickListener(this);
        mSponsoredDogButton.setOnClickListener(this);

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
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mDog.getEmail()});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I want to know more about " + mDog.getName());
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Choose an E-mail client: "));
        } else if (v == mDogImageView) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.petfinder.com/petdetail/" + mDog.getId()));
            startActivity(webIntent);
        } else if (v == mSponsoredDogButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference dogRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_SPONSORED_DOG)
                    .child(uid);

            DatabaseReference pushRef = dogRef.push();
            String pushId = pushRef.getKey();
            mDog.setPushId(pushId);
            dogRef.push().setValue(mDog);
            Toast.makeText(getContext(), "Added to your Sponsored Pups!", Toast.LENGTH_SHORT).show();
        }

    }

}
