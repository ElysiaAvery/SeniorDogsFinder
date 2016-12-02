package com.example.guest.seniordogsfinder.services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guest.seniordogsfinder.Constants;
import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;
import com.example.guest.seniordogsfinder.ui.DogsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PetService extends AppCompatActivity {
    public static final String TAG = PetService.class.getSimpleName();
    public static void findDogs(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.PET_URL).newBuilder();
        urlBuilder.addQueryParameter("key", Constants.PET_KEY);
        urlBuilder.addQueryParameter("animal", "dog");
        urlBuilder.addQueryParameter(Constants.PET_LOCATION ,location);
        urlBuilder.addQueryParameter("age", "senior");
        urlBuilder.addQueryParameter("output", "full");
        urlBuilder.addQueryParameter("format", "json");
        String url = urlBuilder.build().toString();
        Log.v(TAG, url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_service);
    }

    public ArrayList<Dog> processResults (Response response) {
        ArrayList<Dog> dogList = new ArrayList<>();

        try {
            if (response.isSuccessful()) {
                String jsonData = response.body().toString();
                JSONObject dogJSON = new JSONObject(jsonData);
                JSONArray dogListJSON = dogJSON.getJSONArray("pet");
                for (int i = 0; i < dogListJSON.length(); i++) {
                    JSONObject dogObjectJSON = dogListJSON.getJSONObject(i);
                    String name = dogObjectJSON.getJSONObject("name").getString("$t");
                    String id = dogObjectJSON.getJSONObject("id").getString("$t");
                    ArrayList<String> breeds = new ArrayList<>();
                    JSONArray breedsJSON = dogObjectJSON.getJSONObject("breeds").getJSONArray("breed");
                    for (int y = 0; y < breedsJSON.length(); y++) {
                        breeds.add(breedsJSON.get(y).toString());
                    }
                    String sex = dogObjectJSON.getJSONObject("sex").getString("$t");
                    String description = dogObjectJSON.getJSONObject("description").getString("$t");
                    ArrayList<String> options = new ArrayList<>();
                    JSONArray optionsJSON = dogObjectJSON.getJSONObject("options").getJSONArray("option");
                    for (int j = 0; j < optionsJSON.length(); j++) {
                        options.add(optionsJSON.get(j).toString());
                    }
                    String contact = dogObjectJSON.getJSONObject("contact").getJSONObject("phone").getString("$t");
                    String email = dogObjectJSON.getJSONObject("contact").getJSONObject("email").getString("$t");
                    ArrayList<String> photos = new ArrayList<>();
                    JSONArray photosJSON = dogObjectJSON.getJSONObject("media").getJSONObject("photos").getJSONArray("photo");
                    for (int t = 0; t < photosJSON.length(); t++) {
                        photos.add(photosJSON.get(t).toString());
                    }
                    Dog dog = new Dog(name, id, breeds, sex, description, options, contact, email, photos);
                    dogList.add(dog);
                }
            }
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dogList;
    }
}