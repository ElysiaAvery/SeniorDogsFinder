package com.example.guest.seniordogsfinder.util;

import com.example.guest.seniordogsfinder.models.Dog;

import java.util.ArrayList;

/**
 * Created by Guest on 12/17/16.
 */
public interface OnDogSelectedListener {
    public void onDogSelected(Integer position, ArrayList<Dog> dogs, String source);
}
