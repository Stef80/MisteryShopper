package com.example.misteryshopper.models;

public class ShopModel {

    String id;
    String idStore;
    String manager;
    String city;
    String address;

    public ShopModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
