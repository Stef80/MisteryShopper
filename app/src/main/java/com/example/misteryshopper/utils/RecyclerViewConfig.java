package com.example.misteryshopper.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.CellSignalStrength;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.R;
import com.example.misteryshopper.activity.ShopperProfileActivity;
import com.example.misteryshopper.models.ShopperModel;

import java.util.List;


public class RecyclerViewConfig {


    private Context context;
    private ShopperAdapter shopperAdapter;
    public void setConfig(RecyclerView recyclerView,Context context, List<ShopperModel> shoppers, List<String> keys,OnItemClickListener onItemClickListener){
        this.context = context;
        shopperAdapter = new ShopperAdapter(shoppers,keys,onItemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(shopperAdapter);
    }


    class ShopperItemView extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView surname;
        TextView city;
        TextView available;
        String key;
        ImageView image;
        OnItemClickListener clickListener;



        public ShopperItemView(@NonNull ViewGroup parent, OnItemClickListener clickListener) {
            super(LayoutInflater.from(context).inflate(R.layout.shop_list_item,parent,false));
            name = itemView.findViewById(R.id.nameLabel);
            surname = itemView.findViewById(R.id.surname_label);
            city = itemView.findViewById(R.id.cityTxt);
            available = itemView.findViewById(R.id.avialable);
            image = itemView.findViewById(R.id.imageView);
            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
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



        @Override
        public void onClick(View v) {
             clickListener.onItemClick(this.getAdapterPosition());
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    class ShopperAdapter extends RecyclerView.Adapter<ShopperItemView> {
        private List<ShopperModel> shopperModelList;
        private List<String> keys;
        private OnItemClickListener clickListener;

        public ShopperAdapter(List<ShopperModel> shopperModelList, List<String> keys, OnItemClickListener clickListener) {
            this.shopperModelList = shopperModelList;
            this.keys = keys;
            this.clickListener = clickListener;
        }


        @NonNull
        @Override
        public ShopperItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ShopperItemView(parent,clickListener);
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
