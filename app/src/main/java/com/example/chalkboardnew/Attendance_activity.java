package com.example.chalkboardnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Attendance_activity extends AppCompatActivity {

    RelativeLayout present, absent, late, layer;
    TextView t1, t2;
    EditText lecture;
    TextView date;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText id, name;
    List<StudentItems> studentItems = new ArrayList<>();
    TextView presentcount,absentcount,latecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_activity);
        present = findViewById(R.id.present_layout);
        absent = findViewById(R.id.absent_layout);
        late = findViewById(R.id.late_layout);
   //     layer = findViewById(R.id.lec_layer);
        t1 = findViewById(R.id.tv1);
        t2 = findViewById(R.id.tv2);
    //    date = findViewById(R.id.date);
   //     lecture = findViewById(R.id.lecture);
//        lecture.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        presentcount = findViewById(R.id.present_student_count);
        absentcount = findViewById(R.id.absent_student_count);
        latecount = findViewById(R.id.late_student_count);


        recyclerView = (RecyclerView) findViewById(R.id.students_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        studentAdapter = new StudentAdapter(this, studentItems);
        recyclerView.setAdapter(studentAdapter);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        System.out.println(sharedPreferences.getString("courseTitle",""));
        System.out.println("hiiiiiiiiiiiiiiiii");


/*        lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lecture.setFocusableInTouchMode(true);
                lecture.setCursorVisible(true);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(Attendance_activity.this);
                int day = datePicker.getDayOfMonth();
                int month = (datePicker.getMonth()) + 1;
                int year = datePicker.getYear();

                datePickerDialog = new DatePickerDialog(Attendance_activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }


        });*/
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.add_student);
        floatingActionButton.setOnClickListener(v -> showDialog());
        present.setVisibility(View.INVISIBLE);
        absent.setVisibility(View.INVISIBLE);
        late.setVisibility(View.INVISIBLE);
       // layer.setVisibility(View.INVISIBLE);




    }

     void statusp(int i) {
        presentcount.setText(Integer.toString(i));
    }
    void statusa(int j) {
        absentcount.setText(Integer.toString(j));
    }
    void statusl(int k) {
        latecount.setText(Integer.toString(k));
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
            present.setVisibility(View.VISIBLE);
            absent.setVisibility(View.VISIBLE);
            late.setVisibility(View.VISIBLE);
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);

            dialog.dismiss();


        });
    }

    private void addstudent() {
        String id1 = id.getText().toString();
        String name1 = name.getText().toString();
        studentItems.add(new StudentItems(id1, name1));
        studentAdapter.notifyDataSetChanged();

    }
}
