package com.example.chalkboardnew;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class studentList extends AppCompatActivity {

    TextView t1, t2;
    DatePickerDialog datePickerDialog;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    com.getbase.floatingactionbutton.FloatingActionsMenu fab;
    com.getbase.floatingactionbutton.FloatingActionsMenu add_new_student_fab_menu;
    private StudentListAdapter studentListAdapter;
    private ClassAdapter classAdapter;
    List<StudentItems> studentItems = new ArrayList<>();
    List<CourseInfo> classitems = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    Button done;
    String clicked_courseTitle = "";
    DocumentReference studentsdocument;
    CollectionReference collectionReference, studentcollection;
    Button b;
    public static String detect1 = "";
    public static String detect2 = "";
    com.getbase.floatingactionbutton.FloatingActionButton add_student_new;
    com.getbase.floatingactionbutton.FloatingActionButton add_student;
    EditText id, name;

    String Lecture_s = "";
    StudentItems studentItems_object = new StudentItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        b = findViewById(R.id.b);
        done = findViewById(R.id.done_button);
        add_new_student_fab_menu = findViewById(R.id.add_new_student_fab_menu);
        add_student_new = findViewById(R.id.add_student_manually_fab);

        recyclerView = findViewById(R.id.recyclerview_student_item);

        t1 = findViewById(R.id.tv1);
        t2 = findViewById(R.id.tv2);

        Intent intent = getIntent();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        clicked_courseTitle = intent.getStringExtra("title");


        studentcollection = firestore.collection("users").document(userID)
                .collection("Courses").document(clicked_courseTitle).collection("Students");
        studentcollection.orderBy("id", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<StudentItems> documentData = queryDocumentSnapshots.toObjects(StudentItems.class);
                studentListAdapter = new StudentListAdapter(getApplicationContext(), studentItems);
                recyclerView.setAdapter(studentListAdapter);
                studentItems.addAll(documentData);

                studentListAdapter.notifyDataSetChanged();
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);

            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(studentList.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        fab = findViewById(R.id.main_fab);
        add_student = findViewById(R.id.add_student_fab);
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_func();


            }
        });
        add_student_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDoneDialog();

            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton take_attendance = findViewById(R.id.take_attendance_fab);

        take_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentListAdapter.getItemCount() == 0) {

                    showNoStudentsToTakeAttendanceDialog();
                } else {

                    showNoticeDialog();

                }
            }
        });


        collectionReference = firestore.collection("users").document(userID).collection("Courses");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<CourseInfo> documentData = queryDocumentSnapshots.toObjects(CourseInfo.class);
                classAdapter = new ClassAdapter(getApplicationContext(), classitems);
                classitems.addAll(documentData);

                for (int i = 0; i < classitems.size(); i++) {
                    System.out.println(classitems.get(i).toString());
                }
                System.out.println(classAdapter.getPos());

            }
        });

    }

    private void add_func() {
        detect1 = "make_invisible";
        studentListAdapter.notifyDataSetChanged();

        done.setVisibility(View.VISIBLE);
        add_new_student_fab_menu.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);

    }

    private void showNoticeDialog() {
        AlertDialog.Builder alerDialog2 = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.notice_dialogbox, null);
        alerDialog2.setView(view);
        AlertDialog dialog = alerDialog2.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Button yes_button = (Button) view.findViewById(R.id.yes_button);
        Button no = (Button) view.findViewById(R.id.no_button);

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_func();
                dialog.dismiss();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateAndLectureDialog();
                dialog.dismiss();


            }
        });


    }

    private void showDateAndLectureDialog() {
        AlertDialog.Builder alerDialog2 = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.lecture_and_date, null);
        alerDialog2.setView(view);
        AlertDialog dialog = alerDialog2.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        TextView lecture_text = view.findViewById(R.id.lecture_edittext);
        Button done_button = (Button) view.findViewById(R.id.done_lecture);
        TextView date_textview = view.findViewById(R.id.date_textview);
        String date_date;

        date_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(studentList.this);
                int day = datePicker.getDayOfMonth();
                int month = (datePicker.getMonth()) + 1;
                int year = datePicker.getYear();

                datePickerDialog = new DatePickerDialog(studentList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date_date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        date_textview.setText(date_date);
//                        object.setLecture_date(date_date);
                        studentItems_object.setLecture_date(date_date);

                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });


        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                studentListAdapter.notifyDataSetChanged();
                Lecture_s = lecture_text.getText().toString().trim();
                studentItems_object.setLecture_name(Lecture_s);

                dialog.dismiss();
                Intent intent = new Intent(studentList.this,Attendance_activity.class);
                intent.putExtra("title",clicked_courseTitle);
                startActivity(intent);


            }
        });


    }


    private void showNoStudentsToTakeAttendanceDialog() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_for_taking_attendance_warning, null);
        alerDialog.setView(view);
        AlertDialog dialog = alerDialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        Button ok = view.findViewById(R.id.ok_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


    }

    private void showDoneDialog() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialogbox, null);
        alerDialog.setView(view);
        AlertDialog dialog = alerDialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        Button done_done = view.findViewById(R.id.done_done);
        Button add_more = view.findViewById(R.id.add_more);

        done_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_student_fab_menu.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);
                add_student.setEnabled(false);
                add_student.setVisibility(View.GONE);

                dialog.dismiss();

            }
        });
        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_add_new_students, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        id = view.findViewById(R.id.student_id);
        name = view.findViewById(R.id.student_name);

        Button cancel = view.findViewById(R.id.cancelbutton);
        Button add = view.findViewById(R.id.add);
        cancel.setOnClickListener(v -> dialog.dismiss());
        add.setOnClickListener(v -> {
            addstudent();
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            fab.setVisibility(View.INVISIBLE);


            dialog.dismiss();


        });
    }

    private void addstudent() {
        String id1 = id.getText().toString();
        String name1 = name.getText().toString();
        String d_d = studentItems_object.getLecture_date();
        String l_l = studentItems_object.getLecture_name();
        studentItems_object.setName(name1);
        studentItems_object.setId(Integer.parseInt(id1));
        studentItems.add(new StudentItems(Integer.parseInt(id1), name1, "", l_l, d_d));
        studentListAdapter.notifyDataSetChanged();

        studentsdocument = firestore.collection("users").document(userID)
                .collection("Courses").document(clicked_courseTitle).collection("Students").document(id1);
        Map<String, Object> inuser = new HashMap<>();
        inuser.put("id", id1);
        inuser.put("name", name1);

        studentsdocument.set(inuser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(studentList.this, "The student is added!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}


