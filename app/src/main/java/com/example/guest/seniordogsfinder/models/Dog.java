package com.example.guest.seniordogsfinder.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/2/16.
 */
@Parcel
public class Dog {
    String name;
    String id;
    ArrayList<String> breeds = new ArrayList<>();
    String sex;
    String description;
    ArrayList<String> options = new ArrayList<>();
    String phone;
    String email;
    String photos;
    String city;
    String state;
    String zip;
    private String pushId;

    public Dog() {}

    public Dog(String name, String id, ArrayList<String> breeds, String sex, String description, ArrayList<String> options, String phone, String email, String photos, String city, String state, String zip) {
        this.name = name;
        this.id = id;
        this.breeds = breeds;
        this.sex = sex;
        this.description = description;
        this.options = options;
        this.phone = phone;
        this.email = email;
        this.photos = photos;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getBreeds() {
        return breeds;
    }

    public String getGender() {
        return sex;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotos() {
        return photos;
    }

    public String getCity() {
        return city;
    };

    public String getState() {
        return state;
    };

    public String getZip() {
        return zip;
    };

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
