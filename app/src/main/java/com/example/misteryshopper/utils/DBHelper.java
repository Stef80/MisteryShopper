package com.example.misteryshopper.utils;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.models.User;

import java.util.List;

public interface DBHelper {


    public void readShoppers(DataStatus status);
    public void addToDb(final User model, String email, String password, final DataStatus status);
    public void login(String user, String password, final Context context) throws InvalidParamsException;
    public void signOut();
    public void getShopperByMail(String mail, MyCallback callback);
    public Object getCurrentUser();


    public interface DataStatus {
        void dataIsLoaded(List<? extends Object> obj, List<String> keys);

        void dataIsInserted();

        void dataIsUpdated();

        void dataIsDeleted();

        void dataNotLoaded();
    }

    public interface MyCallback{

        public void onCallback(List<ShopperModel> shopperList);
    }
}
