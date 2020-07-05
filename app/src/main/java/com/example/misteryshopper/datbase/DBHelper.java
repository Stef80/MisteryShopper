package com.example.misteryshopper.datbase;

import android.content.Context;

import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.models.HiringModel;
import com.example.misteryshopper.models.StoreModel;
import com.example.misteryshopper.models.User;

import java.util.List;

public interface DBHelper {


    public void readShoppers(DataStatus status);

    public void register(final User model, String email, String password, Context context, final DataStatus status);

    public void login(String user, String password, final Context context,final DataStatus status) throws InvalidParamsException;

    public void signOut(Context context);

    public void getShopperByMail(String mail, DataStatus callback);

    public void getEmployerByMail(String mail, DataStatus callback);

    public Object getCurrentUser();

    public void  getUserById(String UId,DataStatus status);

    public void getRole(String uId,DataStatus status);

    public void readStoreOfSpecificUser(String UId, DataStatus status);

    public void updateUsers(User model, String id, Context context, DataStatus status);

    public void addStoreOfScificId(StoreModel model,DataStatus status);

    void addToketoUser(User user, Context context);

    void getTokenbyMail(String mail,DataStatus status);

    void addHiringModel(HiringModel model, DataStatus dataStatus);


    public interface DataStatus {
        void dataIsLoaded(List<? extends Object> obj, List<String> keys);

    }

}
