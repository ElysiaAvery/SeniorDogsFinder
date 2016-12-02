package com.example.guest.seniordogsfinder.models;

import java.util.ArrayList;

/**
 * Created by Guest on 12/2/16.
 */
public class Dog {
    private String mName;
    private String mId;
    private ArrayList<String> mBreeds = new ArrayList<>();
    private String mSex;
    private String mDescription;
    private ArrayList<String> mOptions = new ArrayList<>();
    private String mPhone;
    private String mEmail;
    private ArrayList<String> mPhotos = new ArrayList<>();

    public Dog(String name, String id, ArrayList<String> breeds, String sex, String description, ArrayList<String> options, String phone, String email, ArrayList<String> photos) {
        this.mName = name;
        this.mId = id;
        this.mBreeds = breeds;
        this.mSex = sex;
        this.mDescription = description;
        this.mOptions = options;
        this.mPhone = phone;
        this.mEmail = email;
        this.mPhotos = photos;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public ArrayList<String> getBreeds() {
        return mBreeds;
    }

    public String getGender() {
        return mSex;
    }

    public String getDescription() {
        return mDescription;
    }

    public ArrayList<String> getOptions() {
        return mOptions;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public ArrayList<String> getPhotos() {
        return mPhotos;
    }
}
