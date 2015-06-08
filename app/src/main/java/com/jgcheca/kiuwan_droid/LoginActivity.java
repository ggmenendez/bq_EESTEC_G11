package com.jgcheca.kiuwan_droid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.JsonObject;
import com.jgcheca.kiuwan_droid.rest.RestClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    ActionProcessButton btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSignIn = (ActionProcessButton) findViewById(R.id.btnLogin);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void Login(View v){
        btnSignIn.setProgress(50);
        final Intent loginIntent = new Intent(this,MainActivity.class);
        String credentials = ((EditText)findViewById(R.id.txtUser)).getText().toString() + ":" + ((EditText)findViewById(R.id.txtPassword)).getText().toString();
        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        RestClient.apiService.login(string, new Callback<JsonObject>() {

        @Override
        public void success(JsonObject s, Response response) {
            /*User userData = new User(((EditText)findViewById(R.id.email)).getText().toString(),"user");
            userData.setSessionId(s.get("user").getAsJsonObject().get("sessionId").getAsString());
            loginIntent.putExtra("user", userData);*/


            TypedInput body = response.getBody();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
                StringBuilder out = new StringBuilder();
                String newLine = System.getProperty("line.separator");
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                    out.append(newLine);
                }

                // Prints the correct String representation of body.
                Log.d("RETROFIT response", String.valueOf(out));
            } catch (IOException e) {
                e.printStackTrace();
            }

            startActivity(loginIntent);
            btnSignIn.setProgress(0);
            Toast.makeText(LoginActivity.this,
                    "Successfully logged in", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d("LoginActivity", "FAILURE");
            Log.d("LoginActivity", error.toString());

            Log.d("Tipo de error", error.getKind().toString());

           // Log.d("Tipo de error", error.);
            //error.isNetworkError()
           switch (error.getKind()){
               case NETWORK:
                   Toast.makeText(LoginActivity.this,
                           "Network unavailable", Toast.LENGTH_SHORT).show();
                   break;
               case HTTP:
                   Toast.makeText(LoginActivity.this,
                           "Credentials wrong", Toast.LENGTH_SHORT).show();
                   break;
               case UNEXPECTED:
                   Toast.makeText(LoginActivity.this,
                           "UNEXPECTED", Toast.LENGTH_SHORT).show();
                   break;
               case CONVERSION:
                   Toast.makeText(LoginActivity.this,
                           "CONVERSION", Toast.LENGTH_SHORT).show();
                   break;
           }

            btnSignIn.setProgress(0);

        }
    });
    }

}

