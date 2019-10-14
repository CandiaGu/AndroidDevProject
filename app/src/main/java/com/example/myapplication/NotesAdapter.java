package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(String filename);
    }

    private List<NotesBuilder> notesList;
    private final OnItemClickListener listener;



public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, content;



    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        content = (TextView) view.findViewById(R.id.content);

    }
}

    public NotesAdapter(List<NotesBuilder> notesList, OnItemClickListener listener) {
        this.notesList = notesList;
        this.listener = listener;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listrow, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotesBuilder note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(((TextView)v).getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
