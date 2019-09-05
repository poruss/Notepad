package com.example.notepad.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.Models.NotesModel;
import com.example.notepad.R;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {

    ArrayList<NotesModel> notesList;
    Context context;

    public NotesListAdapter(Context context,ArrayList<NotesModel> notesList){
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notes_item_row,parent,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesModel notesModel = notesList.get(position);
        holder.notesTitleTv.setText(notesModel.getNotesTitle());
        holder.notesMessageTv.setText(notesModel.getNotesMessage());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView notesTitleTv;
        TextView notesMessageTv;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notesTitleTv  = (TextView)itemView.findViewById(R.id.notesTitleTv);
            notesMessageTv = (TextView)itemView.findViewById(R.id.notesMessageTv);
        }
    }
}
