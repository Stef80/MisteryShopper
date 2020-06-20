package com.example.misteryshopper.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.misteryshopper.MainActivity;
import com.example.misteryshopper.R;
import com.example.misteryshopper.activity.RegisterEmployerActivity;
import com.example.misteryshopper.activity.RegisterShopperActivity;
import com.example.misteryshopper.models.EmployerModel;
import com.example.misteryshopper.models.StoreModel;

import java.util.List;

public class DialogUIHelper {

    private static DBHelper mDBHelper = FirebaseDBHelper.getInstance();


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

    public static void createHireDialog(final StoreModel model, Context context) {

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
                String id = idShop.getText().toString();
                String managerStr = manager.getText().toString();
                String cityStr = city.getText().toString();
                String adr = address.getText().toString();
                if(TextUtils.isEmpty(id))
                    idShop.setError(context.getString(R.string.field_required));
                if(TextUtils.isEmpty(managerStr))
                    manager.setError(context.getString(R.string.field_required));
                if(TextUtils.isEmpty(cityStr))
                    city.setError(context.getString(R.string.field_required));
                if(TextUtils.isEmpty(adr))
                    address.setError(context.getString(R.string.field_required));
                model.setIdStore(id);
                model.setManager(manager.getText().toString());
                model.setCity(city.getText().toString());
                model.setAddress(address.getText().toString());
                EmployerModel user = (EmployerModel) new SharedPrefConfig(context).readLoggedUser();
                Log.i("USERINDIALOG", user.toString());
                model.setIdEmployer(user.getId());
             if(!(TextUtils.isEmpty(id) && TextUtils.isEmpty(managerStr) && TextUtils.isEmpty(cityStr) && TextUtils.isEmpty(adr))) {
                 mDBHelper.addStoreOfScificId(model, new DBHelper.DataStatus() {
                     @Override
                     public void dataIsLoaded(List<?> obj, List<String> keys) {
                         if (obj != null) {
                             Toast.makeText(context, context.getString(R.string.shop_added), Toast.LENGTH_LONG).show();
                             dialog.dismiss();
                         } else {
                             Toast.makeText(context, context.getString(R.string.shop_not_added), Toast.LENGTH_LONG).show();
                             idShop.setText("");
                             manager.setText("");
                             city.setText("");
                             address.setText("");
                         }
                     }

                 });
             }
            }
        });
        dialog.show();
    }
}
