package com.material.components.waec;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LoginCardLight extends AppCompatActivity {

    private View parent_view;
    EditText editPhoneNumber;
    EditText editSecretPin;
    String phoneStored = "", secretpinStored = "";
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_light);
        parent_view = findViewById(android.R.id.content);

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

        MyReceiver = new MyReceiver();

        SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        phoneStored = pref.getString("phonenumber", null);


        if(phoneStored == null){

        }
        else{
            Intent in = new Intent(getApplicationContext(), DashboardGridFab.class);
            startActivity(in);
        }

        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editSecretPin = findViewById(R.id.editSecretPin);

        ((View) findViewById(R.id.signin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                broadcastIntent();
                loginUser();
            }
        });

        ((View) findViewById(R.id.forgot_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Forgot Password", Snackbar.LENGTH_SHORT).show();
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

    private void loginUser() {
        final String phonenumber = editPhoneNumber.getText().toString().trim();
        final String secret_pin = editSecretPin.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(phonenumber)) {
            editPhoneNumber.setError("Please enter a valid phone number");
            editPhoneNumber.requestFocus();
            return;
        }


        if(!Pattern.matches("[a-zA-Z]+", phonenumber)) {
            //if(phonenumber.length() < 6 || phonenumber.length() > 13) {
            if(phonenumber.length() != 10) {
                editPhoneNumber.setError("Not a Valid Number");
                editPhoneNumber.requestFocus();
                return;
            } else {

            }
        } else {
            return;
        }


        class LoginUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("phonenumber", phonenumber);
                params.put("secret_pin", secret_pin);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);

            }

            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if(s.contains("Login successfull")){

                        SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("phonenumber", phonenumber);
                        editor.putString("secret_pin", secret_pin);
                        editor.commit();

                        Intent in = new Intent(getApplicationContext(), DashboardGridFab.class);
                        startActivity(in);

                    } else {
                        Snackbar.make(parent_view, "Invalid Login Details, please try again", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Invalid Login Details, please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        LoginUser ru = new LoginUser();
        ru.execute();

    }

}
