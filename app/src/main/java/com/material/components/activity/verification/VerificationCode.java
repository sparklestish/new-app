package com.material.components.activity.verification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.material.components.activity.form.FormProfileData;
import com.material.components.helper.URLs;
import com.material.components.utils.Tools;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class VerificationCode extends AppCompatActivity {
    public String phonenumber, verify_code;
    Context context;
    EditText v1, v2, v3, v4;

    private List<Pair<String, String>> params;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        parent_view = findViewById(android.R.id.content);
        context = getApplicationContext();

        initToolbar();

        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);


        findViewById(R.id.btn_verify_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("WeacApp", "Starting Fuel");
                verify_code = v1.getText().toString() + v2.getText().toString() + v3.getText().toString() + v4.getText().toString();
                params = new ArrayList<Pair<String, String>>() {{
                    add(new Pair<>("phonenumber", phonenumber));
                    add(new Pair<>("verify_code", verify_code));
                }};

                Fuel.post(URLs.URL_ACTIVATE, params).responseString(new Handler<String>() {
                    @Override
                    public void success(Request request, Response response, String s) {
                        try {

                            Log.e("WeacApp", "Worked");
                            //progressDialog.dismiss();
                            JSONObject obj = new JSONObject(s);
                            Log.e("WeacApp", "Response from server "+s.toString());

                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else{
                                startActivity(new Intent(getApplicationContext(), FormProfileData.class));
                            }
                            //startActivity(new Intent(getApplicationContext(), FormProfileData.class));

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

        codeEditTextFull(v1, v2);
        codeEditTextFull(v2, v3);
        codeEditTextFull(v3, v4);
        codeEditTextFull(v4, v1);

    }

    public void codeEditTextFull(EditText edtFrom, final EditText edtTo) {
        edtFrom.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtTo.requestFocus();
            }
        });
    }



    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
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
