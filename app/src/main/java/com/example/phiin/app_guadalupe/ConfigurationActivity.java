package com.example.phiin.app_guadalupe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import control.connection.ConnectionParams;

/**
 * Created by phiin on 11/04/2017.
 */

public class ConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadConfigurationParams();
    }
    ConnectionParams conn = ConnectionParams.getInstance();

    public void ok_onclick(View v){
        EditText ip = (EditText)findViewById(R.id.ip);
        EditText port = (EditText)findViewById(R.id.port);
        if((ip.getText().toString() != "") && (port.getText().toString() != "")){
            conn.setIp(ip.getText().toString());
            conn.setPort(Integer.parseInt(port.getText().toString()));

            Intent goToLogin = new Intent(this, LoginActivity.class);
            startActivity(goToLogin);
        }else{
            Toast.makeText(getApplicationContext(),"IP ou Porta vazio(s)",Toast.LENGTH_SHORT).show();
        }
    }

    public void loadConfigurationParams(){
        EditText ip_text = (EditText)findViewById(R.id.ip);
        EditText port_text = (EditText)findViewById(R.id.port);

        ip_text.setText(String.valueOf(conn.getIp()));
        port_text.setText(String.valueOf(conn.getPort()));
    }
}
