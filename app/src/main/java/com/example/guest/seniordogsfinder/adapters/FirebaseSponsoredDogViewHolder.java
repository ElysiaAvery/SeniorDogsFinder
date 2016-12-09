package com.example.guest.seniordogsfinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by Guest on 12/9/16.
 */
public class FirebaseSponsoredDogViewHolder extends RecyclerView implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseSponsoredDogViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
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
}
