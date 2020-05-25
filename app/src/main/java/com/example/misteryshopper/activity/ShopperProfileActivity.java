package com.example.misteryshopper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.misteryshopper.R;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.utils.DBHelper;
import com.example.misteryshopper.utils.FirebaseDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ShopperProfileActivity extends AppCompatActivity {

private DBHelper mDBHelper = FirebaseDBHelper.getInstance();
private TextView name;
private TextView surname;
private TextView address;
private TextView city;
private TextView email;
private TextView cf;
private ImageView imgProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_profile);

        name = findViewById(R.id.profile_name);
        surname = findViewById(R.id.profile_surname);
        address = findViewById(R.id.profile_address);
        cf = findViewById(R.id.profile_cf);
        city =findViewById(R.id.profile_city);
        email = findViewById(R.id.profile_email);

        String mail = getIntent().getStringExtra("email");
        mDBHelper.getShopperByMail(mail, new DBHelper.MyCallback() {
             @Override
             public void onCallback(List<ShopperModel> shopperList) {
                Log.i("CALLBACK",shopperList.toString());
                shopperList.add(shopperList.remove(0)) ;
                 Log.i("CALLBACK2",shopperList.toString());
                 ShopperModel shopperModel = shopperList.get(0);
                 Log.i("SHOPPERMODEL",shopperList.toString());


                 if(shopperModel != null) {
                     name.setText(shopperModel.getName());
                     surname.setText(shopperModel.getSurname());
                     address.setText(shopperModel.getAddress());
                     city.setText(shopperModel.getCity());
                     cf.setText(shopperModel.getCf());
                     email.setText(shopperModel.getEmail());
                 }
             }
         });




    }
}
