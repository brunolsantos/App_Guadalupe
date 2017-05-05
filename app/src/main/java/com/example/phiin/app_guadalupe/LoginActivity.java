package com.example.phiin.app_guadalupe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import control.connection.ConnectionParams;
import control.connection.Message;
import control.connection.TCPConn;
import control.product.Product;
import control.product.ProductControl;

public class LoginActivity extends AppCompatActivity {
    public static final int
            SELL = 0,
            PRODUCTION = 1,
            WRONG_USER = 2,
            WRONG_PASSWORD = 3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.getItemId() == R.id.menu_settings ){
                Intent intentConfig = new Intent(this, ConfigurationActivity.class);
                startActivity(intentConfig);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //loadProducts();
    }
    ProductControl product_control = ProductControl.getInstance();
    ConnectionParams conn = ConnectionParams.getInstance();
    Message message = Message.getInstance();

    public void login_onClick(View v) throws Exception {
        EditText user = (EditText)findViewById(R.id.login_text);
        EditText password = (EditText)findViewById(R.id.password_text);

        String userString = user.getText().toString();
        String passwdString = password.getText().toString();

        Intent goToOptions = new Intent(this, OptionsActivity.class);

        if((userString.isEmpty() == false) && (passwdString.isEmpty() == false)){
            int userType = checkUser(userString,passwdString);

            switch(userType){
                case SELL:
                    goToOptions.putExtra("login_type", SELL);
                    startActivity(goToOptions);
                    break;
                case PRODUCTION:
                    goToOptions.putExtra("login_type", PRODUCTION);
                    startActivity(goToOptions);
                    break;
                case WRONG_USER:
                    Toast.makeText(getApplicationContext(),"Usuário incorreto",Toast.LENGTH_SHORT).show();
                    break;
                case WRONG_PASSWORD:
                    Toast.makeText(getApplicationContext(),"Senha incorreta",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"Usuário inexistente",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public int checkUser(String user, String password) throws Exception {
        String data_to_send[][] = {{"1","","",""},{user,password,"",""},{"","","",""}};
        String data_received[][];
        TCPConn tcp = new TCPConn(conn.getIp(),conn.getPort());
        message.setData_to_send(data_to_send);
        data_received = tcp.execute().get();
        int result;
        if(data_received[0][0].compareTo("OK") == 0){
            if((data_received[1][3].compareTo(password) == 0)){
                result = Integer.parseInt(data_received[1][2]);
                //APAGAR
                result=0;
                return result;
            }else{
                return WRONG_PASSWORD;
            }
        }else{
            return WRONG_USER;
        }
    }
    public void loadProducts(){
        product_control.getProductList().clear();
        Product refrigerante = new Product("Refrigerante",Double.parseDouble("7.40"),0,0,Product.available);
        Product pastel_palmito = new Product("Pastel de Palmito",Double.parseDouble("5.30"),0,0,Product.available);
        Product pastel_carne = new Product("Pastel de carne",Double.parseDouble("5.35"),0,0,Product.available);
        Product pastel_frango = new Product("Pastel de frango",Double.parseDouble("5.42"),0,0,Product.finishing);
        Product pastel_queijo = new Product("Pastel de Queijo",Double.parseDouble("6.20"),0,0,Product.unavailable);
        product_control.addProduct(refrigerante);
        product_control.addProduct(pastel_carne);
        product_control.addProduct(pastel_palmito);
        product_control.addProduct(pastel_frango);
        product_control.addProduct(pastel_queijo);
    }

}
