package com.example.notepad.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notepad.Adapters.NotesListAdapter;
import com.example.notepad.Constants.Constants;
import com.example.notepad.Models.NotesModel;
import com.example.notepad.R;
import com.example.notepad.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserId;
    SessionManager sessionManager;
    private RecyclerView recylerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NotesModel> notesList;
    NotesListAdapter notesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recylerView = (RecyclerView)findViewById(R.id.recylerView);
        layoutManager = new LinearLayoutManager(this);
        recylerView.setLayoutManager(layoutManager);
        notesList = new ArrayList<>();
        loadMyNotes(new SessionManager(this).getUserId());
        recylerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(this,notesList);
    }

    private void loadMyNotes(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.myNotesApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObjectData = jsonArray.getJSONObject(i);
                            NotesModel notesModel = new NotesModel();
                            notesModel.setNotesId(jsonObjectData.getInt("id"));
                            notesModel.setNotesTitle(jsonObjectData.getString("notesTitle"));
                            notesModel.setNotesMessage(jsonObjectData.getString("notesMessage"));
                            notesModel.setNotesDate(jsonObjectData.getString("notesDate"));
                            notesList.add(notesModel);
                        }
                        recylerView.setAdapter(notesListAdapter);
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
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("getNotes","getNotes");
                params.put("userId",userId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settingMenu:
                Toast.makeText(this,"Setting Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profileMenu:
                Toast.makeText(this,"Profile Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logoutMenu:
                logoutUser();
                break;
            case R.id.addNewMenu:
            startActivity(new Intent(MainActivity.this,AddnotesActivity.class));
                break;

        }
        return true;
    }

    private void logoutUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure to logout??");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sessionManager.logoutUser();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });
        builder.show();

    }
}
