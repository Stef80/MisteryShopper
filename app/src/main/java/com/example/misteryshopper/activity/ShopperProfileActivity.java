package com.example.misteryshopper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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

private final String ROLE = "role";
private final String EMAIL = "email";


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

        String role = getIntent().getStringExtra(ROLE);
        if(role == null) {
            String mail = getIntent().getStringExtra(EMAIL);
            Log.i("SHOPPERPROFILEMAIL", mail);
            if(mail != null)
        mDBHelper.getShopperByMail(mail, new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<?> shopperList, List<String> keys) {
                Log.i("SHOPPERPROFILE", shopperList.get(0).toString());
                ShopperModel shopperModel = (ShopperModel) shopperList.get(0);

                if(shopperModel != null) {
                    name.setText(shopperModel.getName());
                    surname.setText(shopperModel.getSurname());
                    address.setText(shopperModel.getAddress());
                    city.setText(shopperModel.getCity());
                    cf.setText(shopperModel.getCf());
                    email.setText(shopperModel.getEmail());
                }
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataNotLoaded() {

            }
        });

        }else{


    }

    }
}
