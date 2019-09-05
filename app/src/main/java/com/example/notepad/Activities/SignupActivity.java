package com.example.notepad.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

public class SignupActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private TextView loginTextMessage;
    private TextInputEditText etname;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private Button signupBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeComponent();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateSignup();
            }
        });


        loginTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validateSignup() {
        String inputName = etname.getText().toString();
        String inputUsername = etUsername.getText().toString();
        String inputUserPassword = etPassword.getText().toString();

        if (inputName.isEmpty()){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please enter name", Snackbar.LENGTH_SHORT);snackbar.show();
        }else if (inputUsername.isEmpty()){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please enter username", Snackbar.LENGTH_SHORT);snackbar.show();

        }else if (inputUserPassword.isEmpty()){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please enter password", Snackbar.LENGTH_SHORT);snackbar.show();
        }else{
            saveUserData(inputName,inputUsername,inputUserPassword);

        }
    }

    private void saveUserData(final String inputName, final String inputUsername, final String inputUserPassword) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.signupApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                     if (status.equals("1")){
                         String userId = jsonObject.getString("userId");
                         Toast.makeText(getApplicationContext(),"Signup successfull !",Toast.LENGTH_SHORT).show();
                         saveUserIdInSession(userId);
                         finish();
                         startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                     }else{
                         Snackbar.make(coordinatorLayout,"Username already exist,please try another",Snackbar.LENGTH_SHORT).show();
                     }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("signup","signup");
                parms.put("name",inputName);
                parms.put("username",inputUsername);
                parms.put("password",inputUserPassword);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void initializeComponent() {
        loginTextMessage = (TextView)findViewById(R.id.loginTextMessage);
        etname = (TextInputEditText)findViewById(R.id.etname);
        etUsername = (TextInputEditText)findViewById(R.id.etUsername);
        etPassword = (TextInputEditText)findViewById(R.id.etPassword);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
    }
    private void saveUserIdInSession(String userId) {
        new SessionManager(this).saveUserId(userId);
    }

}
