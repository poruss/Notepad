package com.example.notepad.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notepad.Activities.AddnotesActivity;
import com.example.notepad.Adapters.NotesListAdapter;
import com.example.notepad.Adapters.UnusedNotesListAdapters;
import com.example.notepad.Constants.Constants;
import com.example.notepad.Models.NotesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonVollyRequest {

    Context context;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    public CommonVollyRequest(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public void getMyAllNotesUsed(final String userId, final String status, final NotesListAdapter notesListAdapter, final List<NotesModel> notesList) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading Notes"); // Setting Title
        progressDialog.setMessage("Please Wait..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.myNotesApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
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
                        recyclerView.setAdapter(notesListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("getNotes","getNotes");
                params.put("status",status);
                params.put("userId",userId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }



    public void getMyAllNotesUnUsed(final String userId, final String unused, final UnusedNotesListAdapters unusedNotesListAdapters, final List<NotesModel> unusedNotesList) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading Notes"); // Setting Title
        progressDialog.setMessage("Please Wait..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.myNotesApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                // Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
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
                            unusedNotesList.add(notesModel);
                        }
                        recyclerView.setAdapter(unusedNotesListAdapters);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("getNotes","getNotes");
                params.put("status",unused);
                params.put("userId",userId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }



    public void markAsUnusedNotes(final int notesId, final String userId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.markAsUnusedApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")){
                        Toast.makeText(context,"Marked as unused",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"Oops something error",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("markAsUnused","markAsUnused");
                params.put("notedId",String.valueOf(notesId));
                params.put("userId",userId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void deleteMyNotes(final int notesId, final String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.deleteMyNotesApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")){
                        Toast.makeText(context,"Notes Deleted !",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"Oops something error",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("deleteMyNotes","deleteMyNotes");
                params.put("notedId",String.valueOf(notesId));
                params.put("userId",userId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
