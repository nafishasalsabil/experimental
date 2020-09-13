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

class QuizMarksRecordAdapter extends RecyclerView.Adapter<QuizMarksRecordAdapter.QuizMarksRecordViewHolder> {

    Context context;
   // List<QuizNameClass> quizmakrsitems;
    List<StudentItems> studentItems;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public QuizMarksRecordAdapter(Context context,List<StudentItems> studentItems) {
     //   this.quizmakrsitems = quizmakrsitems;
        this.context = context;
        this.studentItems = studentItems;

    }


    public static class QuizMarksRecordViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView quiz_marks,name,id;
        ImageView add_marks;

        public QuizMarksRecordViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            quiz_marks = itemView.findViewById(R.id.quiz_marks_text);
            id  = itemView.findViewById(R.id.roll_quiz_record);
            name = itemView.findViewById(R.id.name_quiz_record);
            add_marks = itemView.findViewById(R.id.add_marks);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onItemClickListener.onClick(ClassViewHolder.this.getAdapterPosition());
                   /* Intent intent = new Intent(v.getContext(),QuizMarksRecord.class);
                    //   intent.putExtra("Title",classname.getText());
                    // System.out.println(classname.getText());
                    v.getContext().startActivity(intent);
*/
                }
            });
        }
    }

    @NonNull
    @Override
    public QuizMarksRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_record_item, parent, false);
        context = parent.getContext();

        return new QuizMarksRecordViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizMarksRecordViewHolder holder, int position) {
     //   holder.quiz_marks.setText(quizmakrsitems.get(position).getQuiz_marks());
        holder.id.setText(Integer.toString(studentItems.get(position).getId()));
        holder.name.setText(studentItems.get(position).getName());
        System.out.println(studentItems.get(position).getId());
        System.out.println(studentItems.get(position).getName());

        holder.add_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.quiz_marks_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EditText marks = dialog.findViewById(R.id.quiz_marks_edittext);
                Button ok = dialog.findViewById(R.id.done_quiz_marks_add);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String marks_quiz = marks.getText().toString();

                        System.out.println(marks_quiz);
                        holder.quiz_marks.setText(marks_quiz);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return studentItems.size();
    }
}
