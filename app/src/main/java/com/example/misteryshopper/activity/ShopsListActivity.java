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
import com.example.misteryshopper.models.ShopModel;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.utils.DBHelper;
import com.example.misteryshopper.utils.DialogUIHelper;
import com.example.misteryshopper.utils.FirebaseDBHelper;
import com.example.misteryshopper.utils.RecyclerViewConfig;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ShopsListActivity extends AppCompatActivity implements RecyclerViewConfig.OnItemClickListener {

    private DBHelper mDBHelper = FirebaseDBHelper.getInstance();
    private RecyclerView recyclerView;
    private List<ShopModel> shops;
    private List<String> keys;
    private Toolbar toolbar;
    private TextView textEmpty;
    String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name).toUpperCase());

        userID = ((FirebaseUser)mDBHelper.getCurrentUser()).getUid();

        recyclerView = findViewById(R.id.recyclerView_shopper);
        textEmpty = findViewById(R.id.emptyState);
        if(shops == null){
            textEmpty.setVisibility(View.VISIBLE);
        }else {
            mDBHelper.readShopsOfSpecificUser(userID, new DBHelper.DataStatus() {
                @Override
                public void dataIsLoaded(List<?> obj, List<String> keyses) {
                    new RecyclerViewConfig().setConfigShoppersList(recyclerView, ShopsListActivity.this, (List<ShopModel>) shops,
                            keys, ShopsListActivity.this);
                }

                @Override
                public void dataIsInserted() {

                }

                @Override
                public void dataNotLoaded() {

                }

            });

        }
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
                        ShopModel model = new ShopModel();
                        DialogUIHelper.createHireDialog(model,ShopsListActivity.this);
                        return false;
                    }
                });
                break;
            case R.id.log_out:
                mDBHelper.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

    }
}
