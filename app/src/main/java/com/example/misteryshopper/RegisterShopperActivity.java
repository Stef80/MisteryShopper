package com.example.misteryshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.utils.FirebaseDBHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterShopperActivity extends AppCompatActivity {

   private EditText name;
   private EditText surname;
   private EditText address;
   private EditText cf;
   private EditText email;
   private EditText password;
   private FirebaseDBHelper dbHelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.nameEditText);

        surname = findViewById(R.id.surnameEditTxt);

        cf = findViewById(R.id.cfEditTxt);

        address = findViewById(R.id.addressEditTxt);

        email = findViewById(R.id.emailEditRegTxt);

        password = findViewById(R.id.passwordEditRegTxt);

        Button rButton = findViewById(R.id.buttonRegister);
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopperModel shopper = new ShopperModel();
                shopper.setName(name.getText().toString());
                shopper.setSurname(surname.getText().toString());
                shopper.setAddress(address.getText().toString());
                shopper.setCf(cf.getText().toString());
                shopper.setEmail(email.getText().toString());
                shopper.setPassword(password.getText().toString());
                dbHelper.addShopper(shopper);
            }
        });

    }
}
