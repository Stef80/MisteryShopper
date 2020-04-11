package com.example.misteryshopper.models;

import java.util.ArrayList;
import java.util.List;

public class EmployerModel {

    String emName;
    String category;
    String pIva;
    String eMail;
    List<ShopperModel> employedList;

    public EmployerModel(String emName, String category, String pIva) {
        this.emName = emName;
        this.category = category;
        this.pIva = pIva;
        this.employedList = new ArrayList<>();
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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setEmployedList(ShopperModel employedList) {
        this.employedList.add(employedList);
    }

    public ShopperModel getEmplyed(ShopperModel shopperModel) {
        if (employedList.contains(shopperModel)) {
            for (int i = 0; i < employedList.size(); i++) {
                if (shopperModel.getEmail().equals(employedList.get(i).getEmail())) {
                    return employedList.get(i);
                }
            }
        }
        return null;
    }
}
