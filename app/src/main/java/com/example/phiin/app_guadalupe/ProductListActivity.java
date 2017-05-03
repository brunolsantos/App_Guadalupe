package com.example.phiin.app_guadalupe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import control.product.Product;
import control.product.ProductControl;

/**
 * Created by phiin on 15/04/2017.
 */

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadItens();
    }

    int row_index=1;
    ProductControl product_control = ProductControl.getInstance();




    public void loadItens() {
        TableLayout table = (TableLayout)ProductListActivity.this.findViewById(R.id.product_list);
        table.removeAllViews();
        int background_color;
        for(int i=0;i<product_control.getProductList().size();i++) {
            // Inflate your row "template" and fill out the fields.
            final TableRow row = (TableRow)LayoutInflater.from(ProductListActivity.this)
                    .inflate(R.layout.merge_row, null);
            row.setTag(i);
            product_control.getProductList().get(i).setRow(i);

            //PRODUCT NAME
            ((TextView)row.findViewById(R.id.product_name_list))
                    .setText(product_control.getProductList().get(i).getProduct());

            //PRODUCT PRICE
            String price = String.valueOf(product_control.getProductList().get(i).getPrice_unit());
            price = NumberFormat.getCurrencyInstance().format(Double.parseDouble(price));
            ((TextView)row.findViewById(R.id.product_price_list)).setText(price);

            //PRODUCT QUANTITY
            String quantity = String.valueOf(product_control.getProductList().get(i).getQuantity());
            ((TextView)row.findViewById(R.id.product_quantity_list)).setText(quantity+" UN");

            //PRODUCT DATE
            ((TextView)row.findViewById(R.id.product_date_list)).setText("Criado em: Jan 7, 2017");

            //SET BACKGROUND COLOR
            if(product_control.getProductList().get(i).getCurrently_selected() == true){
                background_color = ContextCompat.getColor(this,R.color.selected_row);
            }else{
                background_color = ContextCompat.getColor(this,
                        product_control.getProductList().get(i).getStatus_color());
            }
            row.setBackgroundColor(background_color);

            row.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = Integer.parseInt(v.getTag().toString());

                    if(product_control.getProductList().get(position).getStatus() == Product.unavailable){
                        Toast.makeText(getApplicationContext(),"Produto Indisponível",Toast.LENGTH_SHORT).show();
                    }else{
                        String pName = product_control.getProductList()
                                .get(position)
                                .getProduct();

                        //EDIT PRODUCT
                        Intent goToSelectedProduct = new Intent(v.getContext(),SelectedProductActivity.class);
                        goToSelectedProduct.putExtra("positionSelectedProduct", position);
                        startActivity(goToSelectedProduct);
                    }
                }
            });

            table.addView(row);
        }
        table.requestLayout();     // Not sure if this is needed.
    }


}
