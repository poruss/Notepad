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

public class UnusedNotesListAdapters extends RecyclerView.Adapter<UnusedNotesListAdapters.UnusedNotesViewHolder>  {

    List<NotesModel> unusednotesList;

    Context context;
    NotesAdapterListner notesAdapterListner;
    static final int UNUSED_NOTED_DELETEGROUPID = 30;


    public UnusedNotesListAdapters(Context context,List<NotesModel> unusednotesList,NotesAdapterListner notesAdapterListner){
        this.context = context;
        this.notesAdapterListner = notesAdapterListner;
        this.unusednotesList = unusednotesList;
    }

    @NonNull
    @Override
    public UnusedNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notes_item_row,parent,false);
        return new UnusedNotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnusedNotesViewHolder holder, int position) {
        NotesModel notesModel = unusednotesList.get(position);
        holder.notesTitleTv.setText(notesModel.getNotesTitle());
        holder.notesMessageTv.setText(notesModel.getNotesMessage());
    }


    public class UnusedNotesViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView notesTitleTv;
        TextView notesMessageTv;
        public UnusedNotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notesTitleTv  = (TextView)itemView.findViewById(R.id.notesTitleTv);
            notesMessageTv = (TextView)itemView.findViewById(R.id.notesMessageTv);
            notesTitleTv.setOnCreateContextMenuListener(this);
            notesTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesAdapterListner.notesItemClickListner(unusednotesList.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            //contextMenu.add(UNUSED_NOTED_DELETEGROUPID, 22, 0, "Delete");//groupId, itemId, order, title
            contextMenu.add(UNUSED_NOTED_DELETEGROUPID, 22, getAdapterPosition(), "Delete");//groupId, itemId, order, title
        }


    }

    @Override
    public int getItemCount() {
        return unusednotesList.size();
    }


}
