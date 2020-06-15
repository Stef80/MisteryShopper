package com.example.misteryshopper.utils;

import android.content.Context;

import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.models.User;

import java.util.List;

public interface DBHelper {


    public void readShoppers(DataStatus status);

    public void register(final User model, String email, String password, final DataStatus status);

    public void login(String user, String password, final Context context,final DataStatus status) throws InvalidParamsException;

    public void signOut();

    public void getShopperByMail(String mail, DataStatus callback);

    public void getEmployerByMail(String mail, DataStatus status);

    public Object getCurrentUser();

    public void  getUserById(String UId,DataStatus status);

    public void getRole(String uId,Context context,DataStatus status);

    public void readShopsOfSpecificUser(String UId, DataStatus status);

    public void updateUsers(User model, String id, DataStatus status);





    public interface DataStatus {
        void dataIsLoaded(List<? extends Object> obj, List<String> keys);

        void dataIsInserted();

        void dataNotLoaded();
    }

}
