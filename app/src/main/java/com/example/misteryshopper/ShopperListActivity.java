package com.example.misteryshopper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.utils.FirebaseDBHelper;
import com.example.misteryshopper.utils.RecyclerViewConfig;

import java.util.List;

public class ShopperListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_list);
        recyclerView = findViewById(R.id.recyclerView_shopper);
        new FirebaseDBHelper().readShoppers(new FirebaseDBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<? extends Object> obj, List<String> keys) {
                new RecyclerViewConfig().setConfig(recyclerView,ShopperListActivity.this,(List<ShopperModel>) obj,keys);
            }

            @Override
            public void dataIsIneserted() {

            }

            @Override
            public void dataIsUpdated() {

            }

            @Override
            public void dataIsDeleted() {

            }
        });
    }
}
