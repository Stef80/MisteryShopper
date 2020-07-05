package com.example.misteryshopper.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.R;
import com.example.misteryshopper.activity.ShopperListActivity;
import com.example.misteryshopper.models.ShopperModel;
import com.example.misteryshopper.models.StoreModel;

import java.util.List;


public class RecyclerViewConfig {


    private Context context;
    private MyListAdapter shopperAdapter;
    private Intent intent;

    public RecyclerViewConfig(Intent intent) {
        this.intent = intent;
    }

    public void setConfigList(RecyclerView recyclerView, Context context, List shoppers, List<String> keys, OnItemClickListener onItemClickListener) {
        this.context = context;
        shopperAdapter = new MyListAdapter(shoppers, keys, onItemClickListener);
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
        private Button hireBtn;
        private OnItemClickListener clickListener;

        public ShopperItemView(@NonNull ViewGroup parent, OnItemClickListener clickListener) {
            super(LayoutInflater.from(context).inflate(R.layout.shoppers_list_item, parent, false));
            name = itemView.findViewById(R.id.nameLabel);
            surname = itemView.findViewById(R.id.surname_label);
            city = itemView.findViewById(R.id.cityTxt);
            available = itemView.findViewById(R.id.avialable);
            image = itemView.findViewById(R.id.imageView);
            hireBtn = itemView.findViewById(R.id.button_confirm);

            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
        }

        public void bind(ShopperModel shopper, String key) {
            name.setText(shopper.getName());
            surname.setText(shopper.getSurname());
            city.setText(shopper.getCity());
            if (shopper.isAvailable()) {
                available.setVisibility(View.VISIBLE);
            } else {
                available.setVisibility(View.INVISIBLE);
            }
            this.key = key;
            hireBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = intent.getExtras();
                    StoreModel store = (StoreModel) extras.get("store");
                    String idEmployer = extras.getString("id_employer");
                    Log.i("SHOPPER",shopper.getEmail());
                    DialogUIHelper.buildDialogHire(context,store,idEmployer,shopper.getEmail());
                }
            });
        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(this.getBindingAdapterPosition());
        }
    }



    class StoreItemView extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView idShop;
        private TextView city;
        private TextView address;
        private TextView manger;
        private Button searchBtn;
        private TextView employerName;
        private String key;
        private OnItemClickListener clickListener;

        public StoreItemView(@NonNull ViewGroup parent, OnItemClickListener listener) {
            super(LayoutInflater.from(context).inflate(R.layout.store_item_list, parent, false));
            idShop = itemView.findViewById(R.id.id_shop);
            city = itemView.findViewById(R.id.city_shop_text);
            manger = itemView.findViewById(R.id.shop_manager_name);
            address = itemView.findViewById(R.id.address_shop_text);
            searchBtn = itemView.findViewById(R.id.button_serch_shop);

            this.clickListener = listener;
        }

        public void bind(StoreModel storeModel, String key) {
            idShop.setText(storeModel.getIdStore());
            manger.setText(storeModel.getManager());
            city.setText(storeModel.getCity());
            address.setText(storeModel.getAddress());
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent search = new Intent(context, ShopperListActivity.class);
                    search.putExtra("store",storeModel);
                    search.putExtra("id_employer", storeModel.getIdEmployer());
                    //todo mettere extras per selezionare quelli piu vicino
                    context.startActivity(search);
                }
            });
            this.key = key;
        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(this.getBindingAdapterPosition());
        }
    }



    class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int ITEM_STORE = 1 , ITEM_SHOPPER = 0 ;
        private List<Object> modelList;
        private List<String> keys;
        private OnItemClickListener clickListener;

        public MyListAdapter(List shopperModelList, List<String> keys, OnItemClickListener clickListener) {
            this.modelList = shopperModelList;
            this.keys = keys;
            this.clickListener = clickListener;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder= null;
            switch (viewType) {
                case ITEM_SHOPPER:
                holder = new ShopperItemView(parent, clickListener);
                break;
                case ITEM_STORE:
                   holder = new StoreItemView(parent,clickListener);
                   break;
            }
            return holder;
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int viewType = holder.getItemViewType();
           switch (viewType) {
               case ITEM_SHOPPER:
               ShopperItemView itemView = (ShopperItemView) holder;
               itemView.bind((ShopperModel) modelList.get(position), keys.get(position));
               break;
               case ITEM_STORE:
               StoreItemView itemViewStore = (StoreItemView) holder;
               itemViewStore.bind((StoreModel) modelList.get(position), keys.get(position));
               break;
           }

        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }


        public int getItemViewType(int position){
            if (modelList.get(position) instanceof ShopperModel) {
                 return ITEM_SHOPPER;
            } else if (modelList.get(position) instanceof StoreModel) {
               return ITEM_STORE;
            }
            return -1;
        }


    }





    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
