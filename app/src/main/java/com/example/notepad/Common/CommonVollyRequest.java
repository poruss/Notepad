package com.example.notepad.Common;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notepad.Adapters.NotesListAdapter;
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
    public CommonVollyRequest(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public void getMyAllNotes(final String userId, final String status, final NotesListAdapter notesListAdapter, final List<NotesModel> notesList) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.myNotesApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void performNotesOperation(String markRead, int notesId) {
       // Toast.makeText(context,"id->"+notesId,Toast.LENGTH_SHORT).show();
        // perform delete and mark as read here
    }
}
