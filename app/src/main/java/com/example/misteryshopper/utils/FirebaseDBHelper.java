package com.example.misteryshopper.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.models.EmployerModel;
import com.example.misteryshopper.models.ShopModel;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBHelper implements DBHelper {

    private final String EMPLOYER = "Employer";
    private final String SHOPPER = "Shopper";
    private final String EMAIL = "email";


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    public static DBHelper mDbHelper;
    List<ShopperModel> shoppers = new ArrayList<>();


    private FirebaseDBHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    public static DBHelper getInstance() {
        if (mDbHelper == null) {
            mDbHelper = new FirebaseDBHelper();
        }
        return mDbHelper;
    }


    @Override
    public void readShoppers(final DataStatus dataStatus) {
        mReference = mDatabase.getReference(SHOPPER);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shoppers.clear();
                List<String> kyes = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    kyes.add(keyNode.getKey());
                    ShopperModel shopper = keyNode.getValue(ShopperModel.class);
                    shoppers.add(shopper);
                }
                dataStatus.dataIsLoaded(shoppers, kyes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void register(final User model, String email, String password, final DataStatus status) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    status.dataNotLoaded();
                }
            }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String UId = mAuth.getCurrentUser().getUid();
                    model.setId(UId);
                    if (task.isSuccessful()) {
                      updateUsers(model,UId,status);

                    } else {
                        status.dataNotLoaded();
                    }
                }
            });
        }
    }

    @Override
    public void login(String user, String password, final Context context, DataStatus status) throws InvalidParamsException {
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password))
            mAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        final String uId = task.getResult().getUser().getUid();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Login effettuato con successo");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getRole(uId, context, status);
                            }
                        });
                        builder.show();
                    } else {
                        DialogUIHelper.createRegistationDialog(context);
                    }
                }
            });
        else
            throw new InvalidParamsException();
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public void getShopperByMail(String mail, DataStatus status) {
        List<ShopperModel> shopListUpdate = new ArrayList<>();
        Query query = mDatabase.getReference(SHOPPER).orderByChild(EMAIL).equalTo(mail);
        doQuery(query, ShopperModel.class, shopListUpdate, status);
    }

    @Override
    public void getEmployerByMail(String mail, DataStatus status) {
        List<EmployerModel> listUpdate = new ArrayList<>();
        Query query = mDatabase.getReference(EMPLOYER).orderByChild(EMAIL).equalTo(mail);
        doQuery(query, EmployerModel.class, listUpdate, status);

    }


    @Override
    public Object getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public void getUserById(String UId, DataStatus status) {
        List<User> userList = new ArrayList<>();
        mReference = mDatabase.getReference();
        Query query = mReference.orderByChild("id").equalTo(UId);
        doQuery(query, User.class, userList, status);
    }


    @Override
    public void getRole(String uId, Context context, DataStatus status) {
        List<String> role = new ArrayList<>();
        Query query = mDatabase.getReference(uId);
        doQuery(query, String.class, role, status);
    }

    @Override
    public void readShopsOfSpecificUser(String UId, DataStatus status) {
        List<ShopModel> shops = new ArrayList<>();
         getUserById(UId, new DataStatus() {
             @Override
             public void dataIsLoaded(List<?> obj, List<String> keys) {
                 User user = (User) obj.get(0);
                 if(user instanceof EmployerModel){
                     EmployerModel employer = (EmployerModel) user;
                     shops.addAll(employer.getShops());
                 }else if(user instanceof ShopperModel){
                     ShopperModel shopper = (ShopperModel) user;
                     shops.addAll(shopper.getShops());
                 }

                 status.dataIsLoaded(shops,keys);
             }

             @Override
             public void dataIsInserted() {

             }

             @Override
             public void dataNotLoaded() {

             }
         });
    }

    @Override
    public void updateUsers(User model, String UId, DataStatus status) {
        if (model instanceof ShopperModel) {
            mReference = mDatabase.getReference(SHOPPER);
        } else if (model instanceof EmployerModel) {
            mReference = mDatabase.getReference(EMPLOYER);
        }
        mReference.child(UId).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                status.dataIsInserted();
            }
        });
    }


    private void doQuery(Query query, Class myClass, List listUpdate, DataStatus status) {
        List<String> keys = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUpdate.clear();
               if( dataSnapshot.hasChildren()) {
                   for (DataSnapshot node : dataSnapshot.getChildren()) {
                       keys.add(node.getKey());
                       listUpdate.add(node.getValue(myClass));
                   }
               }else {
                   keys.add(dataSnapshot.getKey());
                   listUpdate.add(dataSnapshot.getValue(myClass));

               }
                Log.i("QUERYLIST", listUpdate.toString());
                status.dataIsLoaded(listUpdate, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("ERROR", databaseError.toString());
            }
        });
    }


    //    public Intent selectProfile(String mail){
//      Query query =  mDatabase.getReference().orderByChild("email").equalTo(mail);
//      query.
//    }


}