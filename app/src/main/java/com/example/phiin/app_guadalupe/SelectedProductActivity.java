package com.example.phiin.app_guadalupe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.text.NumberFormat;

import control.product.Product;
import control.product.ProductControl;

/**
 * Created by phiin on 18/04/2017.
 */

public class SelectedProductActivity extends AppCompatActivity {

    ProductControl product_control = ProductControl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_product);
        Intent activityCall = getIntent();
        int position = activityCall.getExtras().getInt("positionSelectedProduct");
        loadValues(position);
    }

    public void loadValues(int position){
        Product selected_product = product_control.getProductList().get(position);

        EditText product_name = (EditText) findViewById(R.id.edit_product);
        EditText product_quantity = (EditText)findViewById(R.id.edit_quanity);
        EditText product_total = (EditText)findViewById(R.id.edit_total);
        EditText product_unit_value = (EditText)findViewById(R.id.edit_unit_value);

        int int_quantity = selected_product.getQuantity();
        double double_unit_price = selected_product.getPrice_unit();
        double total_price = double_unit_price * int_quantity;

        String string_unit_price = NumberFormat.getCurrencyInstance()
                .format(selected_product.getPrice_unit());

        String string_total_price = NumberFormat.getCurrencyInstance()
                .format(total_price);

        product_name.setText(selected_product.getProductName());
        product_quantity.setText(String.valueOf(selected_product.getQuantity()));
        product_unit_value.setText(string_unit_price);
        product_total.setText(string_total_price);
    }

}
