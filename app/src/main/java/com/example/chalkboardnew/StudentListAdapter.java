package com.example.chalkboardnew;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.grpc.internal.JsonUtil;

//import static com.example.chalkboardnew.Attendance_activity.Lecture_s;
import static com.example.chalkboardnew.Attendance_activity.clicked_courseTitle;
import static com.example.chalkboardnew.Attendance_activity.clicked_course_section;
import static com.example.chalkboardnew.Attendance_activity.detect1;
import static com.example.chalkboardnew.Attendance_activity.detect2;
//import static com.example.chalkboardnew.Attendance_activity.id1;
import static java.security.AccessController.getContext;

class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.studentlistViewHolder> {

    Context context;
    List<StudentItems> studentItems = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    private DocumentReference documentReference;
    CollectionReference collectionReference;
    StudentItems studentItems_object = new StudentItems();
    String ui="";




    public static final String TAG = "check";
    SharedPreferences sharedPreferences1, sharedPreferences2, sharedPreferences3;


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StudentListAdapter(Context context, List<StudentItems> studentItems) {
        this.studentItems = studentItems;
        this.context = context;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public static class studentlistViewHolder extends RecyclerView.ViewHolder {
        TextView roll;
        TextView name;
        CardView cardView;

        public studentlistViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll_list);
            name = itemView.findViewById(R.id.name_list);
            cardView = itemView.findViewById(R.id.cardview);
     }
    }

    @NonNull
    @Override
    public studentlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_list_only, parent, false);
        return new studentlistViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull studentlistViewHolder holder, int position) {
        holder.roll.setText(Integer.toString(studentItems.get(position).getId()));
        holder.name.setText(studentItems.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }
}
