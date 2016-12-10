package com.example.guest.seniordogsfinder.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.ui.DogDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/2/16.
 */
public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogViewHolder>{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    private ArrayList<Dog> mDogs = new ArrayList<>();
    private Context mContext;

    public DogsListAdapter(Context context, ArrayList<Dog> dogs) {
        mContext = context;
        mDogs = dogs;
    }

    @Override
    public DogsListAdapter.DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_list_item, parent, false);
        DogViewHolder viewHolder = new DogViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DogsListAdapter.DogViewHolder holder, int position) {
        holder.bindDog(mDogs.get(position));
    }

    @Override
    public int getItemCount() {
        return mDogs.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.dogImageView) ImageView mDogImageView;
        @Bind(R.id.dogNameTextView) TextView mNameTextView;
        @Bind(R.id.dogSexTextView) TextView mGenderTextView;
        @Bind(R.id.breedsTextView) TextView mBreedsTextView;
        @Bind(R.id.addressTextView) TextView mAddressTextView;
        private Context mContext;

        public DogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, DogDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("dogs", Parcels.wrap(mDogs));
            mContext.startActivity(intent);
        }

        public void bindDog(Dog dog) {
            mNameTextView.setText(dog.getName());
            mGenderTextView.setText(dog.getGender());
            String toBeReplaced = dog.getBreeds().toString().replace("[", "");
            String dogBreeds = toBeReplaced.replace("]", "");
            mBreedsTextView.setText(dogBreeds);
            mAddressTextView.setText(dog.getCity() + ", " + dog.getState() + " " + dog.getZip());


            Picasso.with(mContext)
                    .load(String.valueOf(dog.getPhotos()))
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mDogImageView);
        }
    }
}
