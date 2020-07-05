package com.example.misteryshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.MainActivity;
import com.example.misteryshopper.R;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.datbase.DBHelper;
import com.example.misteryshopper.datbase.impl.FirebaseDBHelper;
import com.example.misteryshopper.utils.RecyclerViewConfig;

import java.util.List;


public class ShopperListActivity extends AppCompatActivity implements RecyclerViewConfig.OnItemClickListener {

    private RecyclerView recyclerView;
    private DBHelper mDbHelper = FirebaseDBHelper.getInstance();
    private Toolbar toolbar;
    private List<ShopperModel> shopperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name).toUpperCase());

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.recyclerView_shopper);
        mDbHelper.readShoppers(new FirebaseDBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<? extends Object> obj, List<String> keys) {
                shopperList = (List<ShopperModel>) obj;
                new RecyclerViewConfig(intent).setConfigList(recyclerView, ShopperListActivity.this, (List<ShopperModel>) obj,
                        keys,ShopperListActivity.this);
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Intent go = new Intent(this,ShopperProfileActivity.class);
        go.putExtra("email",shopperList.get(position).getEmail());
        startActivity(go);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        menu.removeItem(R.id.item_add);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                mDbHelper.signOut(this);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

