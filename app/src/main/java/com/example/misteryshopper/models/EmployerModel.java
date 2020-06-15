package com.example.misteryshopper.models;

import java.util.ArrayList;
import java.util.List;

public class EmployerModel extends User{

    String emName;
    String category;
    String pIva;
    List<ShopperModel> employedList;
    List<ShopModel> shops;


    public EmployerModel(String emName, String category, String pIva,String id,String eMail) {
        super(eMail,id);
        this.emName = emName;
        this.category = category;
        this.pIva = pIva;
        this.employedList = new ArrayList<>();
        this.shops = new ArrayList<>();
    }

    public EmployerModel() {
        this.employedList = new ArrayList<>();
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getpIva() {
        return pIva;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }

    public void setEmployedList(ShopperModel employedList) {
        this.employedList.add(employedList);
    }

    public List<ShopperModel> getEmployedList() {
        return employedList;
    }

    public List<ShopModel> getShops() {
        return shops;
    }

    public ShopperModel getEmployed(ShopperModel shopperModel) {
        if (employedList.contains(shopperModel)) {
            for (int i = 0; i < employedList.size(); i++) {
                if (shopperModel.getEmail().equals(employedList.get(i).getEmail())) {
                    return employedList.get(i);
                }
            }
        }
        return null;
    }

    public void addShop(ShopModel shop){
        if(!shops.contains(shop)){
            shops.add(shop);
        }
    }

    public void removeShop(ShopModel shop){
        if(shops.contains(shop))
            shops.remove(shop);
    }
}
