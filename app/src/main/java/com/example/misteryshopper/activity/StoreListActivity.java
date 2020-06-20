package com.example.misteryshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.MainActivity;
import com.example.misteryshopper.R;
import com.example.misteryshopper.models.StoreModel;
import com.example.misteryshopper.utils.DBHelper;
import com.example.misteryshopper.utils.DialogUIHelper;
import com.example.misteryshopper.utils.FirebaseDBHelper;
import com.example.misteryshopper.utils.RecyclerViewConfig;
import com.example.misteryshopper.utils.SharedPrefConfig;


import java.util.List;

public class StoreListActivity extends AppCompatActivity {

    private DBHelper mDBHelper = FirebaseDBHelper.getInstance();
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<StoreModel> storeModelList;
    private TextView textEmpty;
    String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name).toUpperCase());

        userID = new SharedPrefConfig(getApplicationContext()).readLoggedUser().getId();
        recyclerView = findViewById(R.id.recyclerView_shopper);
        textEmpty = findViewById(R.id.emptyState);
            mDBHelper.readStoreOfSpecificUser(userID, new DBHelper.DataStatus() {
                @Override
                public void dataIsLoaded(List<?> obj, List<String> keys) {
                    if(obj.isEmpty())
                        textEmpty.setVisibility(View.VISIBLE);
                    else
                    new RecyclerViewConfig().setConfigList(recyclerView, StoreListActivity.this, (List<StoreModel>) obj,
                            keys, null);
                    textEmpty.setVisibility(View.GONE);
                }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        StoreModel model = new StoreModel();
                        DialogUIHelper.createHireDialog(model, StoreListActivity.this);
                        return true;
                    }
                });
                break;
            case R.id.log_out:
                mDBHelper.signOut(this);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onItemClick(int position) {
//
//    }
}
