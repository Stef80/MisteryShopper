package com.example.misteryshopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misteryshopper.activity.RegisterShopperActivity;
import com.example.misteryshopper.activity.ShopperListActivity;
import com.example.misteryshopper.exception.InvalidParamsException;
import com.example.misteryshopper.utils.FirebaseDBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;

    private FirebaseDBHelper mDbHelper= FirebaseDBHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      userName  = findViewById(R.id.eMailTxt);
      password = findViewById(R.id.pwdTxt);


        final Intent toRegister = new Intent(this, RegisterShopperActivity.class);
        final Intent getLogin = new Intent(this, ShopperListActivity.class);


        Button ok = findViewById(R.id.enter);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String passwordStr = password.getText().toString();
                if (TextUtils.isEmpty(user) && !user.contains("@")) {
                    userName.setError(getResources().getString(R.string.email_not_iserted));
                }
                if(TextUtils.isEmpty(passwordStr)){
                    password.setError("invalid password");
                }
             try {
                 mDbHelper.login(user, passwordStr)
                         .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                     builder.setMessage("Login effettuato con successo");
                                     builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                             startActivity(getLogin);
                                             finish();
                                         }
                                     });
                                     builder.show();
                                 } else {
                                     final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                     builder.setMessage("Username o password errati\nriprova o effettua la registrazione");
                                     builder.setPositiveButton("registrati", new DialogInterface.OnClickListener() {
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

             }catch (InvalidParamsException e){
                 Toast.makeText(MainActivity.this,"invalid params",Toast.LENGTH_LONG).show();
             }
            }
        });

        TextView register = findViewById(R.id.registerText);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toRegister);
                finish();
            }
        });

    }
}