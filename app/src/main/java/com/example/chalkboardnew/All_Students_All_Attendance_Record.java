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

import static com.example.chalkboardnew.Attendance_activity.clicked_courseTitle;
import static com.example.chalkboardnew.Attendance_activity.clicked_course_section;

public class All_Students_All_Attendance_Record extends AppCompatActivity {
    private RecyclerView recyclerView;
    private All_Students_All_Attandance_RecordAdapter all_students_all_attendance_recordAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<StudentItems> studentItems = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    private CollectionReference collectionReference;
  Toolbar asar_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__students__all__attendance__record);
        Intent intent = getIntent();
        String lec = intent.getStringExtra("lecture");
        String title = intent.getStringExtra("title");
        String section = intent.getStringExtra("section");
        asar_toolbar = findViewById(R.id.toolbar_asar);
        setSupportActionBar(asar_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        asar_toolbar.setNavigationIcon(R.drawable.ic_back);
        recyclerView = (RecyclerView)findViewById(R.id.all_attendance_record_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        collectionReference = firestore.collection("users").document(userID)
                .collection("Courses").document(title).collection("Sections")
                .document(section)
                .collection("Students");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<StudentItems> documentData = queryDocumentSnapshots.toObjects(StudentItems.class);
                all_students_all_attendance_recordAdapter = new All_Students_All_Attandance_RecordAdapter(getApplicationContext(), studentItems);
                all_students_all_attendance_recordAdapter.setLectureName(lec);
                recyclerView.setAdapter(all_students_all_attendance_recordAdapter);
                all_students_all_attendance_recordAdapter.setTitle(title);
                all_students_all_attendance_recordAdapter.setSection(section);
                studentItems.addAll(documentData);
                all_students_all_attendance_recordAdapter.notifyDataSetChanged();

                //   studentItems.add(new StudentItems(id1, name1, ""));

            }
        });
        asar_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}