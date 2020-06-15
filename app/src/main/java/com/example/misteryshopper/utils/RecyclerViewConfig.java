package com.example.misteryshopper.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.R;
import com.example.misteryshopper.models.ShopModel;
import com.example.misteryshopper.models.ShopperModel;

import java.util.List;


public class RecyclerViewConfig {


    private Context context;
    private ShopperAdapter shopperAdapter;
    public void setConfigShoppersList(RecyclerView recyclerView, Context context, List shoppers, List<String> keys, OnItemClickListener onItemClickListener){
        this.context = context;
        shopperAdapter = new ShopperAdapter(shoppers,keys,onItemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(shopperAdapter);
    }




    class ShopperItemView extends RecyclerView.ViewHolder implements View.OnClickListener {

       private TextView name;
       private TextView surname;
       private TextView city;
       private TextView available;
       private String key;
       private ImageView image;
       private Button hireBtnConfirm;
       private OnItemClickListener clickListener;



        public ShopperItemView(@NonNull ViewGroup parent, OnItemClickListener clickListener) {
            super(LayoutInflater.from(context).inflate(R.layout.shoppers_list_item,parent,false));
            name = itemView.findViewById(R.id.nameLabel);
            surname = itemView.findViewById(R.id.surname_label);
            city = itemView.findViewById(R.id.cityTxt);
            available = itemView.findViewById(R.id.avialable);
            image = itemView.findViewById(R.id.imageView);
            hireBtnConfirm = itemView.findViewById(R.id.button_confirm);

            hireBtnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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
             clickListener.onItemClick(this.getBindingAdapterPosition());
        }
    }


    class ShopperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Object> shopperModelList;
        private List<String> keys;
        private OnItemClickListener clickListener;

        public ShopperAdapter(List shopperModelList, List<String> keys, OnItemClickListener clickListener) {
            this.shopperModelList = shopperModelList;
            this.keys = keys;
            this.clickListener = clickListener;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ShopperItemView(parent,clickListener);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ShopperItemView) {
                ShopperItemView itemView = (ShopperItemView) holder;
                itemView.bind((ShopperModel) shopperModelList.get(position), keys.get(position));
            }else if(holder instanceof ShopItemView) {
                ShopItemView itemView = (ShopItemView) holder;
                itemView.bind((ShopModel) shopperModelList.get(position), keys.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return shopperModelList.size();
        }
    }


    public class ShopItemView extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView idShop;
        private TextView city;
        private TextView address;
        private Button searchBtn;
        private List<ShopModel> shops;
        private OnItemClickListener clickListener;

        public ShopItemView(@NonNull ViewGroup parent, OnItemClickListener listener) {
            super(LayoutInflater.from(context).inflate(R.layout.shops_item_list,parent,false));
            idShop = itemView.findViewById(R.id.id_shop);
            city = itemView.findViewById(R.id.city_shop_text);
            address = itemView.findViewById(R.id.address_shop_text);
            searchBtn = itemView.findViewById(R.id.button_serch_shop);

            this.clickListener = listener;
        }

        public void bind(ShopModel shop, String key ){
            idShop.setText(shop.getIdStore());
            city.setText(shop.getCity());
            address.setText(shop.getAddress());
        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(this.getBindingAdapterPosition());
        }
    }



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
