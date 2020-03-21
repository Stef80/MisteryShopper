package com.example.misteryshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.misteryshopper.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText userName = findViewById(R.id.eMailTxt);
        final EditText password = findViewById(R.id.pwdTxt);

        final Intent toRegister = new Intent(this, RegisterShopperActivity.class);
        final Intent getLogin = new Intent(this,ShopperListActivity.class);
        Button ok = findViewById(R.id.enter);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String user = userName.getText().toString();
              String passwordStr = password.getText().toString();
              if(user.equals("user") && passwordStr.equals("password")){
                  AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                  builder.setMessage("Login effettuato con successo");
                  builder.show();
                  startActivity(getLogin);
                  finish();
              }else{
                  final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                  builder.setMessage("Username o password errati\nriprova o effettua la registrazione");
                  builder.setPositiveButton("registrati" ,new  DialogInterface.OnClickListener(){
                      public void onClick(DialogInterface dialog, int id) {
                          startActivity(toRegister);
                          finish();
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

        TextView register = findViewById(R.id.registerText);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toRegister);
            }
        });

    }
}
