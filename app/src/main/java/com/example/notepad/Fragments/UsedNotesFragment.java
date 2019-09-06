package com.example.notepad.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notepad.Activities.NotesDetailActivity;
import com.example.notepad.Adapters.NotesListAdapter;
import com.example.notepad.Common.CommonVollyRequest;
import com.example.notepad.Constants.Constants;
import com.example.notepad.Interfaces.NotesAdapterListner;
import com.example.notepad.Models.NotesModel;
import com.example.notepad.R;
import com.example.notepad.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsedNotesFragment extends Fragment implements NotesAdapterListner {

    private TextView tvUserId;
    SessionManager sessionManager;
    private RecyclerView recylerView;
    RecyclerView.LayoutManager layoutManager;
    List<NotesModel> notesList;
    NotesListAdapter notesListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.used_noted_fragment,container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recylerView = (RecyclerView)view.findViewById(R.id.recylerView);
        notesList = new ArrayList<>();
        notesListAdapter = new NotesListAdapter(getActivity(),notesList,this);

        layoutManager = new LinearLayoutManager(getActivity());
        recylerView.setLayoutManager(layoutManager);
        recylerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        CommonVollyRequest commonVollyRequest = new CommonVollyRequest(getActivity(),recylerView);
        commonVollyRequest.getMyAllNotes(new SessionManager(getActivity()).getUserId(),"used",notesListAdapter,notesList);
    }

    @Override
    public void notesItemClickListner(NotesModel notesModel) {
        Intent intent = new Intent(getActivity(), NotesDetailActivity.class);
        intent.putExtra("notes_title_i",notesModel.getNotesTitle());
        intent.putExtra("notes_message_i",notesModel.getNotesMessage());
        startActivity(intent);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int adapterPosition = item.getGroupId();
        NotesModel notesModel = notesList.get(adapterPosition);

        switch (item.getItemId()){
            case 1:
                Toast.makeText(getActivity(),"Mark as read",Toast.LENGTH_SHORT).show();
                CommonVollyRequest commonVollyRequest = new CommonVollyRequest(getActivity(),recylerView);
                commonVollyRequest.performNotesOperation("markRead",notesModel.getNotesId());
                break;
            case 2:
                Toast.makeText(getActivity(),"delete",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


}
