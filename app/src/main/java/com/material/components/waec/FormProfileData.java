package com.material.components.waec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.material.components.R;
import com.material.components.utils.Tools;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class FormProfileData extends AppCompatActivity {

    public String fullname, phonenumber, email, secret_pin;
    private BroadcastReceiver MyReceiver = null;
    Context context;
    EditText editFullname, editEmail, editPhonenumber, editPin;

    private List<Pair<String, String>> params;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_profile_data);
        MyReceiver = new MyReceiver();
        initToolbar();
        parent_view = findViewById(android.R.id.content);
        context = getApplicationContext();

        editFullname = findViewById(R.id.editFullname);
        editEmail = findViewById(R.id.editEmail);
        editPhonenumber = findViewById(R.id.editPhoneNumber);
        editPin = findViewById(R.id.editPin);


        findViewById(R.id.updateprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                broadcastIntent();
                Log.e("WeacApp", "Starting Fuel");
                phonenumber = editPhonenumber.getText().toString();
                fullname = editFullname.getText().toString();
                email = editEmail.getText().toString();
                secret_pin = editPin.getText().toString();

                params = new ArrayList<Pair<String, String>>() {{
                    add(new Pair<>("phonenumber", phonenumber));
                    add(new Pair<>("fullname", fullname));
                    add(new Pair<>("email", email));
                    add(new Pair<>("secret_pin", secret_pin));
                }};

                Fuel.post(URLs.URL_UPDATE_PROFILE, params).responseString(new Handler<String>() {
                    @Override
                    public void success(Request request, Response response, String s) {
                        try {

                            Log.e("WeacApp", "Worked");
                            //progressDialog.dismiss();
                            //JSONObject obj = new JSONObject(s);

                            if(s.contains("Account Updated Successfully")){

                                Toast.makeText(getApplicationContext(), "Your Account updated successfully", Toast.LENGTH_LONG).show();

                                SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("phonenumber", phonenumber);
                                editor.putString("fullname", fullname);
                                editor.putString("email", email);
                                editor.putString("secret_pin", secret_pin);
                                editor.apply();

                                Intent in = new Intent(getApplicationContext(), DashboardGridFab.class);
                                startActivity(in);

                            }else{

                                //JSONObject userJson = obj.getJSONObject("user");
                                Snackbar.make(parent_view, "Unable to update user Account", Snackbar.LENGTH_LONG).show();

                            }

                        } catch (Exception e) {
                            Snackbar.make(parent_view, "Sorry, a fatal error has occurred", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(Request request, Response response, FuelError fuelError) {

                        Log.e("WeacApp", "Error Fuel");
                        //Toast.makeText(getApplicationContext(), fuelError.toString(), Toast.LENGTH_LONG).show();
                        Snackbar.make(parent_view, fuelError.toString(), Snackbar.LENGTH_LONG).show();
                        //progressDialog.dismiss();
                    }
                });
            }
        });


    }

    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.blue_800);
    }

//    private void initComponent() {
//        ((Button) findViewById(R.id.spn_state)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showStateChoiceDialog((Button) v);
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(FormProfileData.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
