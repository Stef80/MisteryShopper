package com.example.misteryshopper.utils;

import androidx.annotation.NonNull;

import com.example.misteryshopper.models.ShopperModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    List<ShopperModel> shoppers = new ArrayList<>();

    public interface DataStatus{
        void dataIsLoaded(List<? extends Object> obj, List<String> keys);
        void dataIsIneserted();
        void dataIsUpdated();
        void dataIsDeleted();

    }

    public FirebaseDBHelper() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void readShoppers(final DataStatus dataStatus){
        mReference = mDatabase.getReference("Shopper");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shoppers.clear();
                List<String> kyes = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()) {
                    kyes.add(keyNode.getKey());
                    ShopperModel shopper = keyNode.getValue(ShopperModel.class);
                    shoppers.add(shopper);
                }
                dataStatus.dataIsLoaded(shoppers,kyes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addShopper(ShopperModel shopperModel){

    }
}
