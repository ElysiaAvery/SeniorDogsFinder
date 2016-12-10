package com.example.guest.seniordogsfinder.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.Constants;
import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.fragments.DogDetailFragment;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.ui.DogDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/9/16.
 */
public class FirebaseSponsoredDogViewHolder extends RecyclerView.ViewHolder{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseSponsoredDogViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }

    public void bindDog(Dog dog) {
        ImageView dogImageView = (ImageView) mView.findViewById(R.id.dogImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.dogNameTextView);
        TextView genderTextView = (TextView) mView.findViewById(R.id.dogSexTextView);
        TextView breedsTextView = (TextView) mView.findViewById(R.id.breedsTextView);
        TextView addressTextView = (TextView) mView.findViewById(R.id.addressTextView);



        Picasso.with(mContext)
                .load(String.valueOf(dog.getPhotos()))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(dogImageView);

        nameTextView.setText(dog.getName());
        genderTextView.setText(dog.getGender());
        String toBeReplaced = dog.getBreeds().toString().replace("[", "");
        String dogBreeds = toBeReplaced.replace("]", "");
        breedsTextView.setText(dogBreeds);
        addressTextView.setText(dog.getCity() + ", " + dog.getState() + " " + dog.getZip());
    }

//    @Override
//    public void onClick(View view) {
//        final ArrayList<Dog> mDogs = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SPONSORED_DOG);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    mDogs.add(snapshot.getValue(Dog.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
//                Intent intent = new Intent(mContext, DogDetailActivity.class);
//                intent.putExtra("position", itemPosition);
//                intent.putExtra("dogs", Parcels.wrap(mDogs));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
}