package com.example.guest.seniordogsfinder.services;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.guest.seniordogsfinder.Constants;
import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.models.Dog;

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
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject dogJSON = new JSONObject(jsonData);
                JSONArray dogListJSON = dogJSON.getJSONObject("petfinder").getJSONObject("pets").getJSONArray("pet");
                for (int i = 0; i < dogListJSON.length(); i++) {
                    JSONObject dogObjectJSON = dogListJSON.getJSONObject(i);
                    // Get dog's name.
                    String name = dogObjectJSON.getJSONObject("name").getString("$t");
                    // Get dog's id.
                    String id = dogObjectJSON.getJSONObject("id").getString("$t");
                    // Get dog's breed(s).
                    ArrayList<String> breeds = new ArrayList<>();
                    String breed;
                    try {
                        breed = dogObjectJSON.getJSONObject("breeds").getJSONObject("breed").getString("$t");
                        breeds.add(breed);
                    } catch (JSONException e){
                        JSONArray newBreed;
                        newBreed = dogObjectJSON.getJSONObject("breeds").getJSONArray("breed");
                        for (int y = 0; y < newBreed.length(); y++) {
                            breeds.add(newBreed.getJSONObject(y).getString("$t").toString());
                        }
                    }
                    // Get dog's gender.
                    String sex = dogObjectJSON.getJSONObject("sex").getString("$t");
                    if(sex.equals("M")) {
                        sex = "Guy";
                    } else if (sex.equals("F")) {
                        sex = "Gal";
                    }
                    String shelterId = dogObjectJSON.getJSONObject("shelterId").getString("$t");
                    // Get dog's description.
                    String description = dogObjectJSON.getJSONObject("description").getString("$t");
                    // Get dog's attributes.
                    ArrayList<String> options = new ArrayList<>();
                    JSONArray optionsJSON = dogObjectJSON.getJSONObject("options").getJSONArray("option");
                    for (int j = 0; j < optionsJSON.length(); j++) {
                        options.add(optionsJSON.getJSONObject(j).getString("$t").toString());
                            if (optionsJSON.getJSONObject(j).getString("$t").toString().contains("hasShots")) {
                                options.remove("hasShots");
                                options.add("Has shots");
                            } else if (optionsJSON.getJSONObject(j).getString("$t").toString().contains("specialNeeds")) {
                                options.remove("specialNeeds");
                                options.add("Special needs");
                            } else if (optionsJSON.getJSONObject(j).getString("$t").toString().contains("noDogs")) {
                                options.remove("noDogs");
                                options.add("No dogs");
                            } else if (optionsJSON.getJSONObject(j).getString("$t").toString().contains("noCats")) {
                                options.remove("noCats");
                                options.add("No cats");
                            } else if (optionsJSON.getJSONObject(j).getString("$t").toString().contains("noChildren")) {
                                options.remove("noChildren");
                                options.add("No children");
                            }
                    }
                    // Get Shelter's phone number.
                    String contact = dogObjectJSON.getJSONObject("contact").getJSONObject("phone").getString("$t");
                    // Get Shelter's email.
                    String email = dogObjectJSON.getJSONObject("contact").getJSONObject("email").getString("$t");
                    // Get dog's best quality photo.
                    String largePhotos = dogObjectJSON.getJSONObject("media").getJSONObject("photos").getJSONArray("photo").getJSONObject(2).getString("$t");
                    // Get dog's medium quality photo.
                    String smallPhotos = dogObjectJSON.getJSONObject("media").getJSONObject("photos").getJSONArray("photo").getJSONObject(1).getString("$t");
                    // Get Shelter's city.
                    String city = dogObjectJSON.getJSONObject("contact").getJSONObject("city").getString("$t");
                    // Get Shelter's state.
                    String state = dogObjectJSON.getJSONObject("contact").getJSONObject("state").getString("$t");
                    // Get Shelter's zip code.
                    String zip = dogObjectJSON.getJSONObject("contact").getJSONObject("zip").getString("$t");
                    // Construct new dog object.
                    Dog dog = new Dog(name, id, breeds, sex, shelterId, description, options, contact, email, largePhotos, smallPhotos, city, state, zip);
                    dogList.add(dog);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dogList;
    }
}
