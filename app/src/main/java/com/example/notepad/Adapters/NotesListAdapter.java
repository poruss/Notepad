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

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> implements Filterable {

    List<NotesModel> filterednotesList;
    List<NotesModel> notesList;

    Context context;
    NotesAdapterListner notesAdapterListner;

    public NotesListAdapter(Context context,List<NotesModel> notesList,NotesAdapterListner notesAdapterListner){
        this.context = context;
        this.notesAdapterListner = notesAdapterListner;
        this.filterednotesList = notesList;
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
        NotesModel notesModel = filterednotesList.get(position);
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
                    notesAdapterListner.notesItemClickListner(filterednotesList.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            contextMenu.add(this.getAdapterPosition(), 1, 0, "Mark as Read");//groupId, itemId, order, title
            contextMenu.add(this.getAdapterPosition(), 2, 0, "Delete");//groupId, itemId, order, title
        }
    }

    @Override
    public int getItemCount() {
        return filterednotesList.size();
    }

    @Override
    public Filter getFilter() {
       return new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence charSequence) {
               String charString = charSequence.toString();
               if (charString.isEmpty()) {
                   filterednotesList = notesList;
               } else {
                   List<NotesModel> newList = new ArrayList<>();
                   for (NotesModel row : notesList) {
                       if (row.getNotesTitle().toLowerCase().contains(charString.toLowerCase())) {
                           newList.add(row);
                       }
                   }
                   filterednotesList = newList;
               }

               FilterResults filterResults = new FilterResults();
               filterResults.values = filterednotesList;
               return filterResults;
           }

           @Override
           protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
               filterednotesList =(ArrayList<NotesModel>)filterResults.values;
               notifyDataSetChanged();
           }
       };
    }

}
