package com.example.guest.seniordogsfinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by Guest on 12/9/16.
 */
public class FirebaseSponsoredDogViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
    private static final int MAX_WIDTH = 300;
    private static final int MAX_HEIGHT = 200;
    public ImageView mDogImageView;


    View mView;
    Context mContext;


    public FirebaseSponsoredDogViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindDog(Dog dog) {
        mDogImageView = (ImageView) mView.findViewById(R.id.dogImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.dogNameTextView);
        TextView genderTextView = (TextView) mView.findViewById(R.id.dogSexTextView);
        TextView breedsTextView = (TextView) mView.findViewById(R.id.breedsTextView);
        TextView addressTextView = (TextView) mView.findViewById(R.id.addressTextView);



        Picasso.with(mContext)
                .load(String.valueOf(dog.getLargePhotos()))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mDogImageView);

        nameTextView.setText(dog.getName());
        genderTextView.setText(dog.getGender());
        String toBeReplaced = dog.getBreeds().toString().replace("[", "");
        String dogBreeds = toBeReplaced.replace("]", "");
        breedsTextView.setText(dogBreeds);
        addressTextView.setText(dog.getCity() + ", " + dog.getState() + " " + dog.getZip());
    }

    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
