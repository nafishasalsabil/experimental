package com.example.chalkboardnew;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ChatUserViewHolder> {

    Context context;
    List<ForSignupDatabase> users;


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChatUserAdapter(Context context, List<ForSignupDatabase> users) {
        this.users = users;
        this.context = context;


    }


    public static class ChatUserViewHolder extends RecyclerView.ViewHolder {
        TextView chat_sername;
        ImageView user_profile_pic;

        public ChatUserViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            chat_sername = itemView.findViewById(R.id.chat_username);
            user_profile_pic = itemView.findViewById(R.id.user_image);
            //itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public ChatUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_user_item, parent, false);
        return new ChatUserViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserViewHolder holder, int position) {
            ForSignupDatabase forSignupDatabase = users.get(position);
            holder.chat_sername.setText(forSignupDatabase.getUsername());
            System.out.println(forSignupDatabase.getUsername());
            System.out.println(forSignupDatabase.getUid());
           // Log.d("hello",forSignupDatabase.getUsername());
            /*if(chatUser.getImageUrl().equals("default"))
            {
                holder.user_profile_pic.setImageResource(R.drawable.user);
            }
            else {
                Glide.with(context).load(chatUser.getImageUrl()).into(holder.user_profile_pic);
            }*/
    }




    @Override
    public int getItemCount() {
        return users.size();
    }
}
