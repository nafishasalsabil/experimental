package com.example.chalkboardnew;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

class QuizMarksAdapter extends RecyclerView.Adapter<QuizMarksAdapter.QuizMarksViewHolder> {

    Context context;
    List<QuizNameClass> quizitems;

     FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    DocumentReference documentReference;
    public static String sec = "",title = "";

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public QuizMarksAdapter(Context context, List<QuizNameClass> quizitems) {
        this.quizitems = quizitems;
        this.context = context;


    }


    public static class QuizMarksViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView quiz_title;

        public QuizMarksViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            quiz_title = itemView.findViewById(R.id.quiztitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onItemClickListener.onClick(ClassViewHolder.this.getAdapterPosition());
                    Intent intent = new Intent(v.getContext(),QuizMarksRecord.class);
                    intent.putExtra("Title",title);
                    intent.putExtra("Section",sec);

                    // System.out.println(classname.getText());
                   v.getContext().startActivity(intent);

                }
            });
        }
    }

    @NonNull
    @Override
    public QuizMarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new QuizMarksViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizMarksViewHolder holder, int position) {
        holder.quiz_title.setText(quizitems.get(position).getQuiz());

    }


    @Override
    public int getItemCount() {
        return quizitems.size();
    }
}
