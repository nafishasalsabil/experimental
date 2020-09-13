package com.example.chalkboardnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizMarks extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
   public static String sec = "";
   public static String title_course = "";
    FloatingActionButton floatingActionButton_quiz;
    QuizNameClass quizNameClass = new QuizNameClass();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    RecyclerView quiz_recylerview ;
    private QuizMarksAdapter quizMarksAdapter;
    private RecyclerView.LayoutManager layoutManager;
    CollectionReference quizcolllection;
    List<QuizNameClass> quizItems = new ArrayList<>();
    TextView t1;
    Toolbar toolbar_quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_marks);
        Intent intent = getIntent();
        sec =  intent.getStringExtra("section");
        title_course =  intent.getStringExtra("title");
        floatingActionButton_quiz = findViewById(R.id.add_quiz_fab);
        t1 = findViewById(R.id.nqt1);
        toolbar_quiz = findViewById(R.id.toolbar_quiz);
        setSupportActionBar(toolbar_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_quiz.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setElevation(0);
        toolbar_quiz.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        quiz_recylerview = (RecyclerView) findViewById(R.id.quiz_recyclerview);
        quiz_recylerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        quiz_recylerview.setLayoutManager(layoutManager);
        quizcolllection = firestore.collection("users").document(userID)
                .collection("Courses").document(title_course)
                .collection("Sections").document(sec).collection("Quizes");
        quizcolllection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<QuizNameClass> documentData = queryDocumentSnapshots.toObjects(QuizNameClass.class);
                quizMarksAdapter = new QuizMarksAdapter(getApplicationContext(), quizItems);
                quiz_recylerview.setAdapter(quizMarksAdapter);
                quizItems.addAll(documentData);
                quizMarksAdapter.setSec(sec);
                quizMarksAdapter.setTitle(title_course);
                quizMarksAdapter.notifyDataSetChanged();
                t1.setVisibility(View.GONE);
                if(quizMarksAdapter.getItemCount()==0)
                {
                    t1.setVisibility(View.VISIBLE);

                }
            }
        });



        floatingActionButton_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerDialog2 = new AlertDialog.Builder(QuizMarks.this);
                View view = LayoutInflater.from(QuizMarks.this).inflate(R.layout.add_quiz_dialog_box, null);
                alerDialog2.setView(view);
                AlertDialog dialog = alerDialog2.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                Button add_quiz_done = view.findViewById(R.id.done_quiz_add);
                EditText quiz_name =view. findViewById(R.id.quiz_name_edittext);
                TextView quiz_date = view.findViewById(R.id.quiz_date_textview);
                quiz_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePicker datePicker = new DatePicker(QuizMarks.this);
                        int day = datePicker.getDayOfMonth();
                        int month = (datePicker.getMonth()) + 1;
                        int year = datePicker.getYear();

                        datePickerDialog = new DatePickerDialog(QuizMarks.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date_date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                quiz_date.setText(date_date);
                                quizNameClass.setQuiz_date(date_date);
//                        object.setLecture_date(date_date);
                                //    studentItems_object.setLecture_date(date_date);

                            }
                        }, day, month, year);
                        datePickerDialog.show();

                    }
                });
                add_quiz_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String q_n = quiz_name.getText().toString();
                        String q_d = quizNameClass.getQuiz_date();
                        if (TextUtils.isEmpty(q_n)) {
                            quiz_name.setError("Quiz name is required");
                            return;
                        }
                        if (TextUtils.isEmpty(q_d)) {
                            quiz_date.setError("Quiz date is required");
                            return;
                        }
                        DocumentReference documentReference = firestore.collection("users").document(userID)
                                .collection("Courses").document(title_course)
                                .collection("Sections").document(sec).collection("Quizes").document(q_n);
                        Map<String, Object> user = new HashMap<>();
                        user.put("quiz", q_n);
                        user.put("quiz_date",q_d);
                        documentReference.set(user);

                        dialog.dismiss();




                    }
                });

            }
        });
    }
}