package com.example.notepad.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.Activities.NotesDetailActivity;
import com.example.notepad.Adapters.NotesListAdapter;
import com.example.notepad.Common.CommonVollyRequest;
import com.example.notepad.Interfaces.NotesAdapterListner;
import com.example.notepad.Models.NotesModel;
import com.example.notepad.R;
import com.example.notepad.Session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class UnusedNotesFragment extends Fragment implements NotesAdapterListner {
    RecyclerView recylerViewUnused;
    RecyclerView.LayoutManager layoutManager;
    List<NotesModel> unusedNotesList;
    NotesListAdapter notesListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.unused_noted_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recylerViewUnused = (RecyclerView)view.findViewById(R.id.recylerViewUnused);
        unusedNotesList = new ArrayList<>();
        notesListAdapter = new NotesListAdapter(getActivity(),unusedNotesList,this);

        layoutManager = new LinearLayoutManager(getActivity());
        recylerViewUnused.setLayoutManager(layoutManager);
        recylerViewUnused.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        CommonVollyRequest commonVollyRequest = new CommonVollyRequest(getActivity(),recylerViewUnused);
        commonVollyRequest.getMyAllNotes(new SessionManager(getActivity()).getUserId(),"unused",notesListAdapter,unusedNotesList);

    }

    @Override
    public void notesItemClickListner(NotesModel notesModel) {
        Intent intent = new Intent(getActivity(), NotesDetailActivity.class);
        intent.putExtra("notes_title_i",notesModel.getNotesTitle());
        intent.putExtra("notes_message_i",notesModel.getNotesMessage());
        startActivity(intent);
    }


}
