package com.example.misteryshopper.models;

import java.util.ArrayList;
import java.util.List;

public class ShopperModel extends User{


    String name;
    String surname;
    String address;
    String city;
    String cf;
    boolean available;
    List<HiringModel> hire;
    List<ShopModel> shops;



    public ShopperModel() {
        this.hire = new ArrayList<>();
        available = true;
    }

    public ShopperModel(String id, String name, String surname, String address, String city, String cf, String email) {
        super(email,id);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.cf = cf;
        this.hire = new ArrayList<>();
        shops = new ArrayList<>();
        this.available = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void acceptHiring(HiringModel hire){
        this.hire.add(hire);
    }

    public List<ShopModel> getShops() {
        return shops;
    }

    @Override
    public String toString() {
        return "ShopperModel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", cf='" + cf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
