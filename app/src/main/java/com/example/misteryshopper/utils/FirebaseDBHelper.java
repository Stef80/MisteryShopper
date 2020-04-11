package com.example.misteryshopper.utils;


import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.models.EmployerModel;
import com.example.misteryshopper.models.ShopperModel;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class FirebaseDBHelper {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    public static FirebaseDBHelper mDbHelper;
    private FirebaseAuth mAuth;
    List<ShopperModel> shoppers = new ArrayList<>();

    public interface DataStatus {
        void dataIsLoaded(List<? extends Object> obj, List<String> keys);

        void dataIsInserted();

        void dataIsUpdated();

        void dataIsDeleted();

        void dataNotLoaded();
    }

    private FirebaseDBHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseDBHelper getInstance() {
        if (mDbHelper == null) {
            mDbHelper = new FirebaseDBHelper();
        }
        return mDbHelper;
    }

    public void readShoppers(final DataStatus dataStatus) {
        mReference = mDatabase.getReference("Shopper");
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

    public void addToDb(final Object model, String email, String password, final DataStatus status) {
        if(!email.isEmpty() && !password.isEmpty())
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (model instanceof ShopperModel) {
                        mReference = mDatabase.getReference("Shopper");
                    } else if (model instanceof EmployerModel) {
                        mReference = mDatabase.getReference("Employer");
                    }
                    mReference.child(mAuth.getCurrentUser().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            status.dataIsInserted();
                        }
                    });
                } else {
                    status.dataNotLoaded();
                }
            }
        });

    }


    public Task<AuthResult> login(String user, String password) throws InvalidParamsException {
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password))
            return mAuth.signInWithEmailAndPassword(user, password);
        else
            throw new InvalidParamsException();
    }

//    public Intent selectProfile(String mail){
//      Query query =  mDatabase.getReference().orderByChild("email").equalTo(mail);
//      query.
//    }
}
