package com.example.misteryshopper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.misteryshopper.R;
import com.example.misteryshopper.models.EmployerModel;
import com.example.misteryshopper.utils.FirebaseDBHelper;

import java.util.List;

public class RegisterEmployerActivity extends AppCompatActivity {

    private EditText emNameTxt;
    private EditText categoryTxt;
    private EditText pIva;
    private EditText email;
    private EditText password;
    private Button regButton;
    private FirebaseDBHelper mDbHepler = FirebaseDBHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employer);

        emNameTxt = findViewById(R.id.nameEmployerEditText);
        categoryTxt = findViewById(R.id.categoryEditTxt);
        pIva = findViewById(R.id.pIvaEditTxt);
        regButton = findViewById(R.id.emRegButton);
        email = findViewById(R.id.emEmailEditRegTxt);
        password = findViewById(R.id.passwordEditRegTxt);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployerModel model = new EmployerModel();
                model.setEmName(emNameTxt.getText().toString());
                model.setCategory(categoryTxt.getText().toString());
                model.setpIva(pIva.getText().toString());
                String mail = email.getText().toString();
                String pas = password.getText().toString();
                if(TextUtils.isEmpty(mail) || ! mail.contains("@")){
                     email.setError("mail required");
                }
                if(TextUtils.isEmpty(pas)){
                    email.setError("password required");
                }
                model.seteMail(mail);
                mDbHepler.addToDb(model, mail, pas, new FirebaseDBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(List<?> obj, List<String> keys) {

                    }

                    @Override
                    public void dataIsInserted() {
                        Toast.makeText(RegisterEmployerActivity.this,"inserted succeffull",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void dataIsUpdated() {

                    }

                    @Override
                    public void dataIsDeleted() {

                    }

                    @Override
                    public void dataNotLoaded() {

                    }
                });
            }
        });
    }
}
