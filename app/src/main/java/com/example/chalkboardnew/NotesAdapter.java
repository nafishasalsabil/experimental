package com.example.chalkboardnew;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    Context context;
    List<NotesClass> notes;

    String state = "";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    DocumentReference documentReference;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NotesAdapter(Context context, List<NotesClass> notes) {
        this.notes = notes;
        this.context = context;


    }

    public void setState(String state) {
        this.state = state;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView note_title,note_subtitle,my_note,url;

        public NotesViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            note_title = itemView.findViewById(R.id.notes_title);
            note_subtitle = itemView.findViewById(R.id.notes_subtitle);
            my_note = itemView.findViewById(R.id.mynotes);
            url = itemView.findViewById(R.id.url_tv);
                  }
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);
        return new NotesViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.note_title.setText(notes.get(position).getNoteTitle());
        holder.note_subtitle.setText(notes.get(position).getSubtitle());
        holder.my_note.setText(notes.get(position).getMynote());
        holder.url.setText(notes.get(position).getUrl());
        if (notes.get(position).getUrl().equals("No url added"))
        {
            holder.url.setVisibility(View.GONE);

        }
        else
        {
            holder.url.setVisibility(View.VISIBLE);
            Linkify.addLinks(holder.url, Linkify.WEB_URLS);
            holder.url.setLinkTextColor(Color.parseColor("#85BFB4"));


        }


        // System.out.println(position);
        //  System.out.println(holder.getAdapterPosition());

        // setPos(holder.getAdapterPosition());

        //    holder.editcoursetitle.setText(classitems.get(position).getCourseTitle());
        // holder.editcourseno.setText(classitems.get(position).getCourseNo());

    }


    @Override
    public int getItemCount() {
        return notes.size();
    }
}
