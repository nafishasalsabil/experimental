package com.example.chalkboardnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuizMarksRecord extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    private QuizMarksRecordAdapter quizMarksRecordAdapter;
    private ClassAdapter classAdapter;
    private RecyclerView.LayoutManager layoutManager;
    CollectionReference studentcollection;
    List<StudentItems> studentItems1 = new ArrayList<>();
    Toolbar toolbar_record;

    RecyclerView quiz_rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_marks_record);
        quiz_rv = findViewById(R.id.quiz_record_recyclerview);
        toolbar_record = findViewById(R.id.toolbar_quiz_record);
        setSupportActionBar(toolbar_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_record.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setElevation(0);
        toolbar_record.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
       String title =  intent.getStringExtra("Title");
       String section =  intent.getStringExtra("Section");
        System.out.println(title);
        System.out.println(section);
        quiz_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        quiz_rv.setLayoutManager(layoutManager);
        studentcollection = firestore.collection("users").document(userID)
                .collection("Courses").document(title).collection("Sections")
                .document(section)
                .collection("Students");
        studentcollection.orderBy("id", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<StudentItems> documentData = queryDocumentSnapshots.toObjects(StudentItems.class);
                quizMarksRecordAdapter = new QuizMarksRecordAdapter(getApplicationContext(), studentItems1);
                quiz_rv.setAdapter(quizMarksRecordAdapter);
                studentItems1.addAll(documentData);
                quizMarksRecordAdapter.notifyDataSetChanged();
              /*  for (int i = 0; i < studentItems1.size(); i++) {
                    System.out.println(studentItems1.get(i).toString());
                }
*/
            }
        });
    }
}