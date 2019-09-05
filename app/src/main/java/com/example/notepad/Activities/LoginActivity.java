package com.example.notepad.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notepad.Constants.Constants;
import com.example.notepad.R;
import com.example.notepad.Session.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView registerText;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private CoordinatorLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (! new SessionManager(this).isUserLoggedOut()){
            startHomeActivity();
        }
        registerText = (TextView)findViewById(R.id.registerText);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        initializeUi();

    }



    private void initializeUi() {

        etUsername = (TextInputEditText)findViewById(R.id.etUsername);
        etPassword = (TextInputEditText)findViewById(R.id.etPassword);
        rootLayout = (CoordinatorLayout)findViewById(R.id.rootLayout);
    }

    public void makeUserLogin(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (username.isEmpty()){
            Snackbar.make(rootLayout,"Please enter username",Snackbar.LENGTH_SHORT).show();
        }else if (password.isEmpty()){
            Snackbar.make(rootLayout,"Please enter password",Snackbar.LENGTH_SHORT).show();
        }else{
            validateLogin(username,password);
        }

    }

    private void validateLogin(final String username, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.loginApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")){
                        String userId = jsonObject.getString("userId");
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                        saveUserIdInSession(userId);
                        startHomeActivity();
                    }else{
                        Snackbar.make(rootLayout,"Invalid username or password",Snackbar.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parms = new HashMap<String, String>() ;
                parms.put("login","login");
                parms.put("username",username);
                parms.put("password",password);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void saveUserIdInSession(String userId) {
        new SessionManager(this).saveUserId(userId);
    }

    private void startHomeActivity() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
}
