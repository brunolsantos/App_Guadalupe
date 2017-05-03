package com.example.phiin.app_guadalupe;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import control.product.ProductControl;

/**
 * Created by phiin on 04/04/2017.
 */

public class CheckoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getProducts(this.findViewById(android.R.id.content));
        loadTableValues();
    }
    ProductControl product_control = ProductControl.getInstance();

    public void loadTableValues(){
        TableLayout tv=(TableLayout) findViewById(R.id.table_products);
        try{

            int background_color;


            TableLayout.LayoutParams tableRowParams =
                    new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.MATCH_PARENT);

            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            for(int i=0;i<product_control.getProductList().size();i++) {
                if(product_control.getProductList().get(i).getQuantity() > 0){
                    if(product_control.getProductList().get(i).getCurrently_selected() == true){
                        background_color = ContextCompat.getColor(this,R.color.selected_row);
                    }else{
                        background_color = ContextCompat.getColor(this,product_control.getProductList().get(i).getStatus_color());
                    }


                    final TableRow tr = new TableRow(CheckoutActivity.this);
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


                    TextView b = new TextView(CheckoutActivity.this);
                    TextView b1 = new TextView(CheckoutActivity.this);
                    TextView b2 = new TextView(CheckoutActivity.this);
                    TextView b3 = new TextView(CheckoutActivity.this);
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
                }
                getTotalSpent();
            }
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void getTotalSpent(){
        TextView total_spent = (TextView)findViewById(R.id.total_spent);
        double total=0;
        for(int i=0;i<product_control.getProductList().size();i++){
            total = total + product_control.getProductList().get(i).getTotal();
        }
        total_spent.setText(NumberFormat.getCurrencyInstance().format(total));
    }

    public void newProductList(View v){
        Toast.makeText(getApplicationContext(),"Adicionar Envio depois",Toast.LENGTH_SHORT).show();
    }
}
