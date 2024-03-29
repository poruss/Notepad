package com.example.notepad.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.Interfaces.NotesAdapterListner;
import com.example.notepad.Models.NotesModel;
import com.example.notepad.R;

import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {

    List<NotesModel> notesList;
    Context context;
    NotesAdapterListner notesAdapterListner;
    private int position;
    static final int MARK_AS_READ = 10;
    static final int USED_NOTED_DELETEGROUPID = 20;



    public NotesListAdapter(Context context,List<NotesModel> notesList,NotesAdapterListner notesAdapterListner){
        this.context = context;
        this.notesAdapterListner = notesAdapterListner;
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


    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView notesTitleTv;
        TextView notesMessageTv;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notesTitleTv  = (TextView)itemView.findViewById(R.id.notesTitleTv);
            notesMessageTv = (TextView)itemView.findViewById(R.id.notesMessageTv);
            notesTitleTv.setOnCreateContextMenuListener(this);
            notesTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesAdapterListner.notesItemClickListner(notesList.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            contextMenu.add(MARK_AS_READ, 1, getAdapterPosition(), "Mark as unused");//groupId, itemId, order, title
            contextMenu.add(USED_NOTED_DELETEGROUPID, 2, getAdapterPosition(), "Delete");        //groupId, itemId, order, title
           // contextMenu.add(0, 10, getAdapterPosition(), "action 1");
            //contextMenu.add(0, 20, getAdapterPosition(), "action 2");
        }
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }



}
