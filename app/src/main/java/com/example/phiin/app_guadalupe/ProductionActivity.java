package com.example.phiin.app_guadalupe;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import control.product.Product;
import control.product.ProductControl;

/**
 * Created by phiin on 07/04/2017.
 */

public class ProductionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getProducts(this.findViewById(android.R.id.content));
        refreshRows();
    }
    ProductControl product_control = ProductControl.getInstance();
    int row_index=1;
    public void loadTableValues(){
        try{
            TableLayout tv=(TableLayout) findViewById(R.id.table_products);
            int background_color;


            TableLayout.LayoutParams tableRowParams =
                    new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.MATCH_PARENT);

            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            for(int i=0;i<product_control.getProductList().size();i++) {

                if(product_control.getProductList().get(i).getCurrently_selected() == true){
                    background_color = ContextCompat.getColor(this,R.color.selected_row);
                }else{
                    background_color = ContextCompat.getColor(this,product_control.getProductList().get(i).getStatus_color());
                }


                final TableRow tr = new TableRow(ProductionActivity.this);
                tr.setId(i+10);
                product_control.getProductList().get(i).setRow(tr.getId());

                //PRODUCT ROW PARAMETERS
                Resources r = getResources();
                int px_margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        1,
                        r.getDisplayMetrics());

                int px_padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        20,
                        r.getDisplayMetrics());
                tr.setLayoutParams(tableRowParams);
                tr.setGravity(Gravity.CENTER);


                TextView b = new TextView(ProductionActivity.this);
                TextView b1 = new TextView(ProductionActivity.this);
                TextView b2 = new TextView(ProductionActivity.this);
                TextView b3 = new TextView(ProductionActivity.this);
                tr.addView(b);
                tr.addView(b1);
                tr.addView(b2);
                tr.addView(b3);
                tv.addView(tr);  // add line below each row

                //PRODUCT
                LinearLayout.LayoutParams textview_params =  (LinearLayout.LayoutParams)b.getLayoutParams();
                b.setLayoutParams(textview_params);
                b.setBackgroundColor(background_color);
                String str = product_control.getProductList().get(i).getProduct();
                b.setText(str);
                b.setTextColor(Color.BLACK);
                b.setGravity(Gravity.LEFT);
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);


                //PRICE
                LinearLayout.LayoutParams textview_params1 =  (LinearLayout.LayoutParams)b1.getLayoutParams();
                b1.setLayoutParams(textview_params1);
                b1.setBackgroundColor(background_color);
                String str1 = String.valueOf(product_control.getProductList().get(i).getPrice_unit());
                str1 = formatter.format(Double.parseDouble(str1));
                b1.setText(str1);
                b1.setTextColor(Color.BLACK);
                b1.setGravity(Gravity.CENTER);
                b1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);


                //QUANTITY
                LinearLayout.LayoutParams textview_params2 =  (LinearLayout.LayoutParams)b2.getLayoutParams();
                b2.setLayoutParams(textview_params2);
                b2.setBackgroundColor(background_color);
                String str2 = String.valueOf(product_control.getProductList().get(i).getQuantity());
                b2.setText(str2);
                b2.setTextColor(Color.BLACK);
                b2.setGravity(Gravity.CENTER);
                b2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);


                //TOTAL
                LinearLayout.LayoutParams textview_params3 =  (LinearLayout.LayoutParams)b3.getLayoutParams();
                b3.setLayoutParams(textview_params3);
                b3.setBackgroundColor(background_color);
                double total_price = product_control.getProductList().get(i).getPrice_unit()*product_control.getProductList().get(i).getQuantity();

                String str3 = formatter.format(total_price);
                b3.setText(str3);
                b3.setTextColor(Color.BLACK);
                b3.setGravity(Gravity.CENTER);
                b3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                product_control.getProductList().get(i).setTotal(total_price);
                tr.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        EditText product_name = (EditText) findViewById(R.id.product_selected);
                        EditText product_quantity = (EditText) findViewById(R.id.product_qtde_selected);
                        Button add_button = (Button)findViewById(R.id.add_product);
                        Button remove_edit_button = (Button)findViewById(R.id.remove_product);

                        for(int i=0;i<product_control.getProductList().size();i++){
                            if(tr.getId() == product_control.getProductList().get(i).getRow()){

                                if(product_control.getProductList().get(i).getStatus() == Product.unavailable){
                                    Toast.makeText(getApplicationContext(),"Produto Indisponível",Toast.LENGTH_SHORT).show();
                                }else{
                                    product_control.getProductList().get(row_index).setCurrently_selected(false);
                                    product_quantity.setEnabled(true);
                                    add_button.setEnabled(false);
                                    remove_edit_button.setEnabled(false);
                                    row_index = i;
                                    product_name.setText(String.valueOf(product_control.getProductList().get(i).getProduct()));
                                    add_button.setEnabled(false);
                                    if(product_control.getProductList().get(i).getQuantity() == 0){
                                        product_quantity.setText("");
                                    }else{
                                        product_quantity.setText(String.valueOf(product_control.getProductList().get(i).getQuantity()));
                                    }
                                    product_control.getProductList().get(i).setCurrently_selected(true);
                                    refreshRows();
                                }
                            }
                        }
                    }
                });
            }
        }catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Erro: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshRows(){
        TableLayout product_table = (TableLayout) findViewById(R.id.table_products);

        for(int i=0;i< product_control.getProductList().size();i++){
            product_control.getProductList().get(i).setCurrently_selected(false);
        }

        //REMOVE ALL ROWS
        while (product_table.getChildCount() > 1)
            product_table.removeView(product_table.getChildAt(product_table.getChildCount() - 1));

        //LOAD VALUES
        loadTableValues();
    }

    public void cancelNewProduct(View v){
        EditText product_name = (EditText) findViewById(R.id.product_selected);
        EditText product_quantity = (EditText) findViewById(R.id.product_qtde_selected);
        Button add_button = (Button)findViewById(R.id.add_product);
        Button remove_edit_button = (Button)findViewById(R.id.remove_product);

        product_name.setText("");
        product_quantity.setText("");
        add_button.setEnabled(true);
        remove_edit_button.setEnabled(true);
        refreshRows();
    }

    public void addNewProduct(View v){
        EditText product_name = (EditText) findViewById(R.id.product_selected);
        EditText product_quantity = (EditText) findViewById(R.id.product_qtde_selected);

        if(product_quantity.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(),"Produto Vazio",Toast.LENGTH_SHORT).show();
        }else{
            Product newProduct = new Product();
            newProduct.setQuantity(Integer.parseInt(product_quantity.getText().toString()));
            newProduct.setProduct(product_name.getText().toString());

            newProduct.setStatus(Product.available);

            product_control.addProduct(newProduct);
            refreshRows();

            product_name.setText("");
            product_quantity.setText("");
        }
    }
    public void removeProduct(View v){
        if(product_control.getProductList().get(row_index).getCurrently_selected() == true){
            product_control.getProductList().remove(row_index);
        }else{
            Toast.makeText(getApplicationContext(),"Selecione um produto",Toast.LENGTH_SHORT).show();
        }
        refreshRows();
    }


}
