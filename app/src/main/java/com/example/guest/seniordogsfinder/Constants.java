package com.example.guest.seniordogsfinder;

/**
 * Created by Guest on 12/2/16.
 */
public class Constants {
    public static final String PET_KEY = BuildConfig.PET_KEY;
    public static final String PET_TOKEN = BuildConfig.PET_TOKEN;
    public static final String PET_URL = "http://api.petfinder.com/pet.find";
    public static final String PET_LOCATION = "location";
    public static final String PREFERENCES_LOCATION_KEY = "location";
    public static final String FIREBASE_CHILD_SPONSORED_DOG = "sponsoredDogs";
    public static final String FIREBASE_QUERY_ID = "id";
    public static final String FIREBASE_QUERY_INDEX = "index";
    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_DOGS = "dogs";
    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";
}
