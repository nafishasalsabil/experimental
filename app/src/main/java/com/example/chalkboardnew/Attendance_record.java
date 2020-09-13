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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Attendance_record extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AttendanceRecordAdapter attendanceRecordAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<StudentItems> studentItems = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    CollectionReference collectionReference,collectionReference2;
    Toolbar toolbar_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_record);
        recyclerView = (RecyclerView) findViewById(R.id.attendance_record_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
            toolbar_record = findViewById(R.id.toolbar_r);
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
        toolbar_record.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String section = intent.getStringExtra("section");
        collectionReference = firestore.collection("users").document(userID)
                .collection("Courses").document(title)
                .collection("Sections").document(section).collection("Attendance");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<StudentItems> documentData = queryDocumentSnapshots.toObjects(StudentItems.class);
                attendanceRecordAdapter = new AttendanceRecordAdapter(getApplicationContext(), studentItems);
                recyclerView.setAdapter(attendanceRecordAdapter);
                studentItems.addAll(documentData);
                attendanceRecordAdapter.setTitle(title);
                attendanceRecordAdapter.setSection(section);
                attendanceRecordAdapter.notifyDataSetChanged();
                /*for(int i = 0;i<studentItems.size();i++)
                {
                  //  System.out.println(studentItems.get(i).toString());
                }
*/


            }
        });


    }
}