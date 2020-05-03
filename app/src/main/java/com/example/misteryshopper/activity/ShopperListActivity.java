package com.example.misteryshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.MainActivity;
import com.example.misteryshopper.R;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.utils.FirebaseDBHelper;
import com.example.misteryshopper.utils.RecyclerViewConfig;

import java.util.List;

public class ShopperListActivity extends AppCompatActivity implements RecyclerViewConfig.OnItemClickListener {

    private RecyclerView recyclerView;
    private FirebaseDBHelper mDbHelper = FirebaseDBHelper.getInstance();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mistery Shopper");

        recyclerView = findViewById(R.id.recyclerView_shopper);
        mDbHelper.readShoppers(new FirebaseDBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<? extends Object> obj, List<String> keys) {
                new RecyclerViewConfig().setConfig(recyclerView, ShopperListActivity.this, (List<ShopperModel>) obj,
                        keys,ShopperListActivity.this);
            }

            @Override
            public void dataIsInserted() {

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


    @Override
    public void onItemClick(int position) {
        Intent go = new Intent(this, MainActivity.class);
        startActivity(go);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.log_out:
                mDbHelper.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

