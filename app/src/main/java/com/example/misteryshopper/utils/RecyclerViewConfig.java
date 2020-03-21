package com.example.misteryshopper.utils;

import android.content.Context;
import android.telephony.CellSignalStrength;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.R;
import com.example.misteryshopper.models.ShopperModel;

import java.util.List;


public class RecyclerViewConfig {

    private Context context;
    private ShopperAdapter shopperAdapter;
    public void setConfig(RecyclerView recyclerView,Context context, List<ShopperModel> shoppers, List<String> keys){
        this.context = context;
        shopperAdapter = new ShopperAdapter(shoppers,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(shopperAdapter);
    }

    class ShopperItemView extends RecyclerView.ViewHolder{

        TextView name;
        TextView surname;
        TextView city;
        TextView available;
        String key;

        public ShopperItemView(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.shop_list_item,parent,false));
            name = itemView.findViewById(R.id.nameLabel);
            surname = itemView.findViewById(R.id.surname_label);
            city = itemView.findViewById(R.id.cityTxt);
            available = itemView.findViewById(R.id.avialable);

        }

        public void bind(ShopperModel shopper, String key) {
            name.setText(shopper.getName());
            surname.setText(shopper.getSurname());
            city.setText(shopper.getCity());
            if(shopper.isAvailable()) {
                available.setVisibility(View.VISIBLE);
            }else {
                available.setVisibility(View.INVISIBLE);
            }
            this.key = key;
        }
    }

    class ShopperAdapter extends RecyclerView.Adapter<ShopperItemView>{
        private List<ShopperModel> shopperModelList;
        private List<String> keys;

        public ShopperAdapter(List<ShopperModel> shopperModelList, List<String> keys) {
            this.shopperModelList = shopperModelList;
            this.keys = keys;
        }

        @NonNull
        @Override
        public ShopperItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ShopperItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopperItemView holder, int position) {
            holder.bind(shopperModelList.get(position),keys.get(position));
        }

        @Override
        public int getItemCount() {
            return shopperModelList.size();
        }
    }
}
