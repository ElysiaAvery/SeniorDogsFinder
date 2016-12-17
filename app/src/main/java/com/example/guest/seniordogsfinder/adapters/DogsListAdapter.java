package com.example.guest.seniordogsfinder.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.seniordogsfinder.Constants;
import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.fragments.DogDetailFragment;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.ui.DogDetailActivity;
import com.example.guest.seniordogsfinder.util.OnDogSelectedListener;
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
    int lastPosition = -1;
    private OnDogSelectedListener mOnDogSelectedListener;

    public DogsListAdapter(Context context, ArrayList<Dog> dogs, OnDogSelectedListener dogSelectedListener) {
        mContext = context;
        mDogs = dogs;
        mOnDogSelectedListener = dogSelectedListener;
    }

    @Override
    public DogsListAdapter.DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_list_item, parent, false);
        DogViewHolder viewHolder = new DogViewHolder(view, mDogs, mOnDogSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DogsListAdapter.DogViewHolder holder, int position) {
        holder.bindDog(mDogs.get(position));

        if(position >lastPosition) {

            Animation set = AnimationUtils.loadAnimation(mContext,
                    R.anim.scroll_view);
            holder.itemView.startAnimation(set);
            lastPosition = position;
        }
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
        private int mOrientation;
        private Context mContext;
        private ArrayList<Dog> mDogs = new ArrayList<>();
        private OnDogSelectedListener mDogSelectedListener;

        public DogViewHolder(View itemView, ArrayList<Dog> dogs, OnDogSelectedListener dogSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mDogs = dogs;
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mDogSelectedListener.onDogSelected(itemPosition, mDogs);
            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, DogDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_DOGS, Parcels.wrap(mDogs));
                mContext.startActivity(intent);
            }
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

    private void createDetailFragment(int position) {
        DogDetailFragment detailFragment = DogDetailFragment.newInstance(mDogs, position);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.dogDetailContainer, detailFragment);
        ft.commit();
    }
}
