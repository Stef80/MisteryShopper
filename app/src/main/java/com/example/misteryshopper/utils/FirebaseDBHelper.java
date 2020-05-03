package com.example.misteryshopper.utils;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.misteryshopper.MainActivity;
import com.example.misteryshopper.activity.RegisterShopperActivity;
import com.example.misteryshopper.activity.ShopperListActivity;
import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.models.EmployerModel;
import com.example.misteryshopper.models.ShopperModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;
    public static FirebaseDBHelper mDbHelper;
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
        if(!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    status.dataNotLoaded();
                }
            }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String UId = mAuth.getCurrentUser().getUid();
                    if (task.isSuccessful()) {
                        if (model instanceof ShopperModel) {
                            mReference = mDatabase.getReference("Shopper");
                            mDatabase.getReference(UId).setValue("shopper");
                        } else if (model instanceof EmployerModel) {
                            mReference = mDatabase.getReference("Employer");
                            mDatabase.getReference(UId).setValue("Employer");
                        }
                        mReference.child(UId).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    }


    public void login(String user, String password, final Context context) throws InvalidParamsException {
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password))
            mAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Login effettuato con successo");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               context.startActivity(new Intent(context, ShopperListActivity.class));
                            }
                        });
                        builder.show();
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Username o password errati\nriprova o effettua la registrazione");
                        builder.setPositiveButton("registrati", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(context, RegisterShopperActivity.class));
                            }
                        });
                        builder.setNeutralButton("riprova", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();

                    }
                }
            });
        else
            throw new InvalidParamsException();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void signOut() {
        mAuth.signOut();
    }

    //    public Intent selectProfile(String mail){
//      Query query =  mDatabase.getReference().orderByChild("email").equalTo(mail);
//      query.
//    }
}
