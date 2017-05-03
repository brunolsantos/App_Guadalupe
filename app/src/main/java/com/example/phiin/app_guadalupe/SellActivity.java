package com.example.phiin.app_guadalupe;

import android.content.Intent;
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

import control.connection.ConnectionParams;
import control.connection.Message;
import control.connection.TCPConn;
import control.product.Product;
import control.product.ProductControl;

/**
 * Created by phiin on 21/03/2017.
 */

public class SellActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            getProducts();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        loadTableValues();
    }

    int row_index=1;
    ProductControl product_control = ProductControl.getInstance();
    ConnectionParams conn = ConnectionParams.getInstance();
    Message message = Message.getInstance();


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


                final TableRow tr = new TableRow(SellActivity.this);
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


                TextView b = new TextView(SellActivity.this);
                TextView b1 = new TextView(SellActivity.this);
                TextView b2 = new TextView(SellActivity.this);
                TextView b3 = new TextView(SellActivity.this);
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
                        TextView product_name = (TextView) findViewById(R.id.product_name_selected);
                        TextView product_price = (TextView) findViewById(R.id.product_unit_selected);
                        TextView product_total = (TextView)findViewById(R.id.total_selected);
                        EditText product_quantity = (EditText) findViewById(R.id.product_qtde_selected);
                        Button edit_button = (Button)findViewById(R.id.edit_button);
                        Button cancel_edit_button = (Button)findViewById(R.id.cancel_product_edit);

                        for(int i=0;i<product_control.getProductList().size();i++){
                            if(tr.getId() == product_control.getProductList().get(i).getRow()){

                                if(product_control.getProductList().get(i).getStatus() == Product.unavailable){
                                    Toast.makeText(getApplicationContext(),"Produto Indisponível",Toast.LENGTH_SHORT).show();
                                }else{
                                    TableLayout product_table = (TableLayout) findViewById(R.id.table_products);
                                    product_control.getProductList().get(row_index).setCurrently_selected(false);
                                    product_quantity.setEnabled(true);
                                    edit_button.setEnabled(true);
                                    cancel_edit_button.setEnabled(true);
                                    row_index = i;
                                    product_name.setText(String.valueOf(product_control.getProductList().get(i).getProduct()));
                                    product_price.setText(NumberFormat.getCurrencyInstance().format(product_control.getProductList().get(i).getPrice_unit()));
                                    product_total.setText(NumberFormat.getCurrencyInstance().format(product_control.getProductList().get(i).getTotal()));
                                    if(product_control.getProductList().get(i).getQuantity() == 0){
                                        product_quantity.setText("");
                                    }else{
                                        product_quantity.setText(String.valueOf(product_control.getProductList().get(i).getQuantity()));
                                    }
                                    product_control.getProductList().get(i).setCurrently_selected(true);
                                    refreshRows(false);
                                }
                            }
                        }
                    }
                });
            }
            getTotalSpent();
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void cancelEditProduct(View v){
        Button cancel_edit_button = (Button)findViewById(R.id.cancel_product_edit);
        product_control.getProductList().get(row_index).setQuantity(0);
        cleanSelectedProduct();
        refreshRows(true);
        cancel_edit_button.setEnabled(false);
    }

    public void cleanSelectedProduct(){
        TextView product_name = (TextView) findViewById(R.id.product_name_selected);
        TextView product_price = (TextView) findViewById(R.id.product_unit_selected);
        EditText product_quantity = (EditText) findViewById(R.id.product_qtde_selected);
        TextView product_total = (TextView)findViewById(R.id.total_selected);
        Button edit_button = (Button)findViewById(R.id.edit_button);

        product_name.setText("...");
        product_price.setText("...");
        product_total.setText("...");
        product_quantity.setText("");
        product_quantity.setEnabled(false);
        edit_button.setEnabled(false);
    }
    public void editProduct(View v){
        EditText product_quantity = (EditText) findViewById(R.id.product_qtde_selected);
        String product_quantity_text = product_quantity.getText().toString();
        Button cancel_edit_button = (Button)findViewById(R.id.cancel_product_edit);

        if(product_quantity_text.matches("")){
            Toast.makeText(getApplicationContext(),"Digite a quantidade do produto",Toast.LENGTH_SHORT).show();
        }else {
            double unity_price = product_control.getProductList().get(row_index).getPrice_unit();
            int new_quantity = Integer.parseInt(product_quantity.getText().toString());

            product_control.getProductList().get(row_index).setQuantity(new_quantity);
            product_control.getProductList().get(row_index).setTotal(new_quantity * unity_price);

            //try {
                //getProducts();
            //}catch (Exception e){
                //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            //}

            cleanSelectedProduct();
            getTotalSpent();
            cancel_edit_button.setEnabled(false);
            refreshRows(false);
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
        TableLayout table_layout = (TableLayout)findViewById(R.id.table_products);
        Button edit_button = (Button)findViewById(R.id.edit_button);

        //UPDATE CLASS VALUES
        for(int i=0;i< product_control.getProductList().size();i++){
            product_control.getProductList().get(i).setQuantity(0);
            product_control.getProductList().get(i).setTotal(0);
        }

        refreshRows(true);
        getTotalSpent();
        Toast.makeText(getApplicationContext(),"Nova lista criada",Toast.LENGTH_SHORT).show();
    }

    public void getProducts_old(View v){
        TableLayout product_table = (TableLayout) findViewById(R.id.table_products);

        //FIRST LOAD
        if(product_table.getChildCount() == 1){
            product_control.getProductList().clear();
            Product refrigerante = new Product("Refrigerante",Double.parseDouble("7.40"),0,0,Product.available);
            Product pastel = new Product("Pastel",Double.parseDouble("5.30"),0,0,Product.available);
            Product pastel_carne = new Product("Pastel de carne",Double.parseDouble("5.35"),0,0,Product.available);
            Product pastel_frango = new Product("Pastel de frango",Double.parseDouble("5.42"),0,0,Product.finishing);
            Product pastel_queijo = new Product("Pastel de Queijo",Double.parseDouble("6.20"),0,0,Product.unavailable);

            //this.product_list.add(refrigerante);
            //this.product_list.add(pastel_carne);
            //this.product_list.add(pastel);
            //this.product_list.add(pastel_frango);
            //this.product_list.add(pastel_queijo);
            product_control.addProduct(refrigerante);
            product_control.addProduct(pastel_carne);
            product_control.addProduct(pastel);
            product_control.addProduct(pastel_frango);
            product_control.addProduct(pastel_queijo);
        }else{
            refreshRows(true);
        }
    }

    public void refreshRows(boolean cancel_edit){
        TableLayout product_table = (TableLayout) findViewById(R.id.table_products);

        if(cancel_edit == true){
            product_control.getProductList().get(row_index).setCurrently_selected(false);
            cleanSelectedProduct();
        }

        //REMOVE ALL ROWS
        while (product_table.getChildCount() > 1)
            product_table.removeView(product_table.getChildAt(product_table.getChildCount() - 1));

        //LOAD VALUES
        loadTableValues();
    }

    public void checkoutProduct(View v) {
        boolean has_item = false;
        for(int i=0;i<product_control.getProductList().size();i++){
            if(product_control.getProductList().get(i).getQuantity() > 0){
                has_item = true;
                break;
            }
        }
        if(has_item == true){
            Intent goToCheckout = new Intent(this, CheckoutActivity.class);
            startActivity(goToCheckout);
        }else{
            Toast.makeText(getApplicationContext(),"Lista vazia",Toast.LENGTH_SHORT).show();
        }

    }

    public void getProducts() throws Exception{
        String data_to_send[][] = {{"2","","",""},{"","","",""},{"","","",""}};
        String data_received[][];
        TCPConn tcp = new TCPConn(conn.getIp(),conn.getPort());
        message.setData_to_send(data_to_send);
        data_received = tcp.execute().get();

        TableLayout product_table = (TableLayout) findViewById(R.id.table_products);
        if(product_table.getChildCount() == 1) {
            product_control.getProductList().clear();
            for (int i = 2; i < data_received.length; i++) {
                Product product = new Product();
                product.setProduct(data_received[i][2]);
                product.setTotal(Integer.parseInt(data_received[i][0]));

                if (product.getTotal() > 5) {
                    product.setStatus_color(Product.available);
                } else if ((product.getTotal() < 5) && (product.getTotal() != 0)) {
                    product.setStatus_color(Product.finishing);
                } else {
                    product.setStatus_color(Product.unavailable);
                }
                product.setCurrently_selected(false);
                product_control.addProduct(product);
            }
        }else{
            refreshRows(true);
        }
    }
}
