package com.example.phiin.app_guadalupe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by phiin on 21/03/2017.
 */

public class OptionsActivity extends AppCompatActivity {
    int login_type;
    Button sell;
    Button production;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sell = (Button)findViewById(R.id.vendas_button);
        production = (Button)findViewById(R.id.production_button);

        Intent activityCall = getIntent();
        login_type = activityCall.getExtras().getInt("login_type");

        switch (login_type){
            case 0: // Root or kitchen
                break;
            case 1: // General users
                production.setEnabled(false);
                production.setVisibility(View.GONE);
                break;
        }
    }

    public void sellOnClick(View v){
        Intent goToSellActivity = new Intent(this, SellActivity.class);
        goToSellActivity.putExtra("login_type", this.login_type);
        startActivity(goToSellActivity);
    }
    public void productionOnClick(View v){
        Intent goToProductionActivity = new Intent(this, ProductionActivity.class);
        goToProductionActivity.putExtra("login_type", this.login_type);
        startActivity(goToProductionActivity);
    }
}
