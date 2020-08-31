package com.example.chalkboardnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance_activity extends AppCompatActivity {

    RelativeLayout present, absent, late;
    TextView t1, t2;
    DatePickerDialog datePickerDialog;
    private ListView listView;
    private StudentAdapter studentAdapter;
    private RecyclerView.LayoutManager layoutManager;

    FloatingActionButton attendance_done_fab;
    List<StudentItems> studentItems = new ArrayList<>();

    public static TextView presentcount, absentcount, latecount;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();

    String clicked_courseTitle = "";
    private DocumentReference studentsdocument;
    private CollectionReference collectionReference, studentcollection;
    static int p = 0, a = 0, l = 0;

    public static String detect1 = "";
    public static String detect2 = "";


    String Lecture_s = "";
    StudentItems studentItems_object = new StudentItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_activity);
        present = findViewById(R.id.present_layout);
        absent = findViewById(R.id.absent_layout);
        late = findViewById(R.id.late_layout);

        attendance_done_fab = findViewById(R.id.attendance_done_fab);
        t1 = findViewById(R.id.tv1);
        t2 = findViewById(R.id.tv2);

        presentcount = findViewById(R.id.present_student_count);
        absentcount = findViewById(R.id.absent_student_count);
        latecount = findViewById(R.id.late_student_count);
        listView = findViewById(R.id.students_list_view);

        Intent intent = getIntent();

        clicked_courseTitle = intent.getStringExtra("title");

        studentcollection = firestore.collection("users").document(userID)
                .collection("Courses").document(clicked_courseTitle).collection("Students");
        studentcollection.orderBy("id", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<StudentItems> documentData = queryDocumentSnapshots.toObjects(StudentItems.class);
                studentAdapter = new StudentAdapter(getApplicationContext(), studentItems);
                listView.setAdapter(studentAdapter);
                studentItems.addAll(documentData);
                studentAdapter.notifyDataSetChanged();
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);

            }
        });


        presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));

    }


    public static void statusA(boolean present, boolean late) {
        a = Integer.parseInt(absentcount.getText().toString()) + 1;

        if (present) {
            p = Integer.parseInt(presentcount.getText().toString()) - 1;

        } else {

            l = Integer.parseInt(latecount.getText().toString()) - 1;
        }
        presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));
    }

    public static void statusL(boolean present, boolean abs) {
        l = Integer.parseInt(latecount.getText().toString()) + 1;

        if (abs) {
            a = Integer.parseInt(absentcount.getText().toString()) - 1;
        } else {

            p = Integer.parseInt(presentcount.getText().toString()) - 1;
        }

        presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));
    }

    public static void statusP(boolean abs, boolean late) {
        p = Integer.parseInt(presentcount.getText().toString()) + 1;

        if (abs) {
            a = Integer.parseInt(absentcount.getText().toString()) - 1;

        } else {

            l = Integer.parseInt(latecount.getText().toString()) - 1;
        }

        presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));
    }

    public static void statusSingleP(boolean status) {
        p = Integer.parseInt(presentcount.getText().toString()) + 1;
        presentcount.setText(String.valueOf(p));

    }

    public static void statusSingleA(boolean status) {
        a = Integer.parseInt(absentcount.getText().toString()) + 1;
        absentcount.setText(String.valueOf(a));
    }

    public static void statusSingleL(boolean status) {
        l = Integer.parseInt(latecount.getText().toString()) + 1;
        latecount.setText(String.valueOf(l));

    }

}
