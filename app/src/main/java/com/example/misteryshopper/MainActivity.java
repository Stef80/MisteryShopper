package com.example.misteryshopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misteryshopper.activity.RegisterEmployerActivity;
import com.example.misteryshopper.activity.RegisterShopperActivity;
import com.example.misteryshopper.activity.ShopperListActivity;
import com.example.misteryshopper.activity.ShopperProfileActivity;
import com.example.misteryshopper.activity.ShopsListActivity;
import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.utils.DBHelper;
import com.example.misteryshopper.utils.FirebaseDBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String SHOPPER = "shopper";
    private EditText userName;
    private EditText password;

    private DBHelper mDbHelper= FirebaseDBHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(mDbHelper.getCurrentUser()!= null){
            mDbHelper.getRole(((FirebaseUser) mDbHelper.getCurrentUser()).getUid(), this, new DBHelper.DataStatus() {
                @Override
                public void dataIsLoaded(List<?> obj, List<String> keys) {
                    String role = (String) obj.get(0);
                   goByRole(role);
                }

                @Override
                public void dataIsInserted() {

                }

                @Override
                public void dataNotLoaded() {

                }
            });
            startActivity(new Intent(MainActivity.this,ShopperListActivity.class));
            finish();
        }
      userName  = findViewById(R.id.eMailTxt);
      password = findViewById(R.id.pwdTxt);

        Button ok = findViewById(R.id.enter);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String passwordStr = password.getText().toString();
                if (TextUtils.isEmpty(user) && !user.contains("@")) {
                    userName.setError(getResources().getString(R.string.email_not_inserted));
                }
                if(TextUtils.isEmpty(passwordStr)){
                    password.setError(getString(R.string.invalid_password));
                }
             try {
                 mDbHelper.login(user, passwordStr, MainActivity.this, new DBHelper.DataStatus() {
                     @Override
                     public void dataIsLoaded(List<?> obj, List<String> keys) {
                      goByRole((String) obj.get(0));
                     }

                     @Override
                     public void dataIsInserted() {

                     }


                     @Override
                     public void dataNotLoaded() {

                     }
                 });

             }catch (InvalidParamsException e){
                 Toast.makeText(MainActivity.this,"invalid params",Toast.LENGTH_LONG).show();
             }
            }
        });

        TextView register = findViewById(R.id.registerText);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterEmployerActivity.class));
                finish();
            }
        });

    }

    private void goByRole(String role){
        Log.i("SHOPPERROLE :", role);
        if(role.equals(SHOPPER)){
            Intent intent = new Intent(MainActivity.this, ShopperProfileActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        }else{
            startActivity(new Intent(MainActivity.this, ShopsListActivity.class));
        }
    }
}