package com.example.notepad.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class AddnotesActivity extends AppCompatActivity {

    private TextInputEditText etNotesTitle;
    private TextInputEditText etNotesMessage;
    private Button saveNotedBtn;
    SessionManager sessionManager;
    CoordinatorLayout rootLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotes);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Add Notes");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeUi();
    }

    private void initializeUi() {
        etNotesTitle = (TextInputEditText)findViewById(R.id.etNotesTitle);
        etNotesMessage = (TextInputEditText)findViewById(R.id.etNotesMessage);
        saveNotedBtn = (Button)findViewById(R.id.saveNotedBtn);
        rootLayout = (CoordinatorLayout)findViewById(R.id.rootLayout);

    }

    public void saveMyNotes(View view) {
        String notestTitle = etNotesTitle.getText().toString();
        String notestMessage = etNotesMessage.getText().toString();
        String userId = new SessionManager(this).getUserId();
        if (notestTitle.isEmpty()){
            Snackbar.make(rootLayout,"Please enter title",Snackbar.LENGTH_SHORT).show();
        }else if (notestMessage.isEmpty()){
            Snackbar.make(rootLayout,"Please enter message",Snackbar.LENGTH_SHORT).show();
        }else{
            saveNotesToDatabase(notestTitle,notestMessage,userId);
        }
    }

    private void saveNotesToDatabase(final String notestTitle, final String notestMessage, final String userId) {
        progressDialog = new ProgressDialog(AddnotesActivity.this);
        progressDialog.setMessage("Adding..."); // Setting Message
        progressDialog.setTitle("Add Notes"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.saveNotesApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Noted Added",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddnotesActivity.this,HomeActivity.class));
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
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("saveNotes","saveNotes");
                parms.put("notesTitle",notestTitle);
                parms.put("notesMessage",notestMessage);
                parms.put("userId",userId);
                return parms;
            }
        };;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
