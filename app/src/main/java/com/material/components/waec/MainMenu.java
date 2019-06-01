package com.material.components.waec;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import com.material.components.R;

import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    public String phoneStored = "", emailStored = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        emailStored = pref.getString("email", null);
        phoneStored = pref.getString("phonenumber", null);


        if(phoneStored == null){

        }
        else{
            Intent in = new Intent(getApplicationContext(), DashboardGridFab.class);
            startActivity(in);
        }

        Button nextpage = findViewById(R.id.get_started);
        nextpage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // use Intent
                Intent intent1= new Intent(MainMenu.this, VerificationPhone.class);
                startActivity(intent1);
            }
        });

        ((View) findViewById(R.id.get_signin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use Intent
                Intent intent2= new Intent(MainMenu.this, LoginCardLight.class);
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
