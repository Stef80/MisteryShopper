package com.example.misteryshopper.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.misteryshopper.R;
import com.example.misteryshopper.activity.RegisterEmployerActivity;
import com.example.misteryshopper.activity.RegisterShopperActivity;
import com.example.misteryshopper.activity.ShopperListActivity;
import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.models.EmployerModel;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.models.User;
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

public class FirebaseDBHelper implements DBHelper {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    public static DBHelper mDbHelper;
    List<ShopperModel> shoppers = new ArrayList<>();
    List<ShopperModel> shopListUpdate = new ArrayList<>();


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

    @Override
    public void addToDb(final User model, String email, String password, final DataStatus status) {
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
                        if (model instanceof ShopperModel) {
                            mReference = mDatabase.getReference("Shopper");
                            mDatabase.getReference(UId).setValue("shopper");
                        } else if (model instanceof EmployerModel) {
                            mReference = mDatabase.getReference("Employer");
                            mDatabase.getReference(UId).setValue("employer");
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

    @Override
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
                        createDialog(context);
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
    public void getShopperByMail(String mail, MyCallback callback) {
        ShopperModel tmp = new ShopperModel();
        shopListUpdate.add(tmp);
        Query query = mDatabase.getReference("Shopper").orderByChild("email").equalTo(mail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("DATASNAPSHOT", dataSnapshot.toString());
                for (DataSnapshot node:dataSnapshot.getChildren()) {
                    shopListUpdate.clear();
                    ShopperModel shopper = node.getValue(ShopperModel.class);
                    Log.i("DATA", shopper.toString());
                    shopListUpdate.add(shopper);
                }

                Log.i("SNAPSHOTLIST", shopListUpdate.toString());
                 callback.onCallback(shopListUpdate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("ERROR", databaseError.toString());
            }
        });
    }

    @Override
    public Object getCurrentUser() {
      return mAuth.getCurrentUser();
    }
/*
*       List<User> userList = new ArrayList<>();
       String id =  mAuth.getCurrentUser().getUid();
       mReference = mDatabase.getReference();
       mReference.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             User user = dataSnapshot.getValue(User.class);
             userList.add(user);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
       return userList.get(0);
* */

    private void createDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_main, null);
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        Button retryBtn = dialogView.findViewById(R.id.retry_button);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button regShopBtn = dialogView.findViewById(R.id.reg_shop_button);
        regShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegisterShopperActivity.class));
            }
        });

        Button regEmpBtn = dialogView.findViewById(R.id.reg_empl_button);
        regEmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegisterEmployerActivity.class));
            }
        });
        dialog.show();

    }
    //    public Intent selectProfile(String mail){
//      Query query =  mDatabase.getReference().orderByChild("email").equalTo(mail);
//      query.
//    }


}