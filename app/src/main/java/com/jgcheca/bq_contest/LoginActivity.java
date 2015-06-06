package com.jgcheca.bq_contest;

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
import com.jgcheca.bq_contest.rest.RestClient;

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
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnLogin);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Log.i("RETROFIT response", String.valueOf(out));
            } catch (IOException e) {
                e.printStackTrace();
            }

            startActivity(loginIntent);
            btnSignIn.setProgress(0);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.i("LoginActivity", "EPIC FAIL!");
            Log.i("LoginActivity", error.toString());

            Toast.makeText(LoginActivity.this,
                    "Your Message", Toast.LENGTH_SHORT).show();
            btnSignIn.setProgress(0);
        }
    });
    }
}

