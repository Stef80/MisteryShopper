package com.example.misteryshopper.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.misteryshopper.R;
import com.example.misteryshopper.activity.RegisterEmployerActivity;
import com.example.misteryshopper.activity.RegisterShopperActivity;
import com.example.misteryshopper.models.ShopModel;

public class DialogUIHelper {


    public static void createRegistationDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_main, null);
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        Button retryBtn = dialogView.findViewById(R.id.retry_button);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button regShopBtn = dialogView.findViewById(R.id.reg_shop_button);
        regShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegisterShopperActivity.class));
            }
        });

        Button regEmpBtn = dialogView.findViewById(R.id.reg_empl_button);
        regEmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegisterEmployerActivity.class));
            }
        });
        dialog.show();

    }

    public static void createHireDialog(final ShopModel model, Context context){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_shop, null);
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        EditText idShop = dialogView.findViewById(R.id.id_shop_add);
        EditText manager = dialogView.findViewById(R.id.shop_manager_name_add);
        EditText city = dialogView.findViewById(R.id.shop_city_add);
        EditText address = dialogView.findViewById(R.id.shop_address_add);
        Button btnAdd = dialogView.findViewById(R.id.button_shop_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setIdStore(idShop.getText().toString());
                model.setManager(manager.getText().toString());
                model.setCity(city.getText().toString());
                model.setAddress(address.getText().toString());


            }
        });
        dialog.show();
    }
}
