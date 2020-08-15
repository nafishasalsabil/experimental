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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Attendance_activity extends AppCompatActivity {

    RelativeLayout present, absent, late, layer;
    TextView t1, t2;
    DatePickerDialog datePickerDialog;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private ClassAdapter classAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public boolean x;
    EditText id, name;

    com.getbase.floatingactionbutton.FloatingActionsMenu fab;
    List<StudentItems> studentItems = new ArrayList<>();
    List<CourseInfo> classitems = new ArrayList<>();

    public static TextView presentcount, absentcount, latecount;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID;
    //    String name = getArguments().getString("data");
    String title = "";
    Button done;

    SharedPreferences sharedPreferences1, sharedPreferences2, sharedPreferences3;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    int p = 0, a = 0, l = 0;
    Button b;
    String detect1 = "";
    String detect2 = "";
    com.getbase.floatingactionbutton.FloatingActionButton add_student_new_fab;
    com.getbase.floatingactionbutton.FloatingActionButton add_student;
    private AlertDialog.Builder alerDialog;
    private AlertDialog.Builder alertdialog_for_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_activity);
        present = findViewById(R.id.present_layout);
        absent = findViewById(R.id.absent_layout);
        late = findViewById(R.id.late_layout);
        b = findViewById(R.id.b);
        done = findViewById(R.id.done_button);
        add_student_new_fab = findViewById(R.id.add_student_new_fab);
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
        present.setVisibility(View.INVISIBLE);
        absent.setVisibility(View.INVISIBLE);
        late.setVisibility(View.INVISIBLE);
        done.setVisibility(View.INVISIBLE);
        add_student_new_fab.setVisibility(View.INVISIBLE);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(Attendance_activity.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });
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

        //FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.add_student);
        //  floatingActionButton.setOnClickListener(v -> showDialog());

        fab = findViewById(R.id.main_fab);
        add_student = findViewById(R.id.add_student_fab);

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Toast.makeText(getApplicationContext(),"You clicked 1",Toast.LENGTH_SHORT).show();

*/
                add_func();


             /*   detect1 = "make_invisible";
                studentAdapter.notifyDataSetChanged();
                present.setVisibility(View.GONE);
                absent.setVisibility(View.GONE);
                late.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                add_student_new_fab.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
*/// recyclerView.findViewHolderForAdapterPosition()
                //   recyclerView.findViewHolderForAdapterPosition().itemView.findViewById(R.id.radioButton_present);
            }
        });
        add_student_new_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDoneDialog();
                //   System.out.println(detect2);
               /* alerDialog = new AlertDialog.Builder(Attendance_activity.this);
                alerDialog.setTitle("Warning");
                alerDialog.setMessage("You cannot add students once you start taking attendance. Do you want to add more students?");
                alerDialog.setIcon(R.drawable.alert);
                alerDialog.setPositiveButton("Add more", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         *//*   present.setVisibility(View.GONE);
                            absent.setVisibility(View.GONE);
                            late.setVisibility(View.GONE);

                            done.setVisibility(View.VISIBLE);
                            add_student_new_fab.setVisibility(View.VISIBLE);
                            fab.setVisibility(View.INVISIBLE);
*//*
                    }
                });
                alerDialog.setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_student_new_fab.setVisibility(View.INVISIBLE);
                        done.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        add_student.setEnabled(false);
                        add_student.setVisibility(View.GONE);

                    }
                });
                AlertDialog alertDialognew = alerDialog.create();
                alertDialognew.show();

*/
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton take_attendance = findViewById(R.id.take_attendance_fab);

        take_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentAdapter.getItemCount() == 0) {

                  /*  alertdialog_for_attendance = new AlertDialog.Builder(Attendance_activity.this);
                    alertdialog_for_attendance.setTitle("Warning");
                    alertdialog_for_attendance.setMessage("You have no students added to take attendance!");
                    alertdialog_for_attendance.setIcon(R.drawable.alert);
                    alertdialog_for_attendance.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialognewattendance = alertdialog_for_attendance.create();
                    alertDialognewattendance.show();
*/
                  showTakeAttendanceDialog();
                }
                else
                {

                    showNoticeDialog();

                }

                //   Toast.makeText(getApplicationContext(),"You clicked 2",Toast.LENGTH_SHORT).show();


            }
        });


        // layer.setVisibility(View.INVISIBLE);

        presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));

   /*     userID = firebaseAuth.getCurrentUser().getUid();
        collectionReference = firestore.collection("users").document(userID).collection("Courses");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {


            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<CourseInfo> documentData = queryDocumentSnapshots.toObjects(CourseInfo.class);
                classAdapter = new ClassAdapter(getApplicationContext(), classitems);
                //  recyclerView.setAdapter(classAdapter);
                classitems.addAll(documentData);
                //    System.out.println();

                for (int i = 0; i < classitems.size(); i++) {
                    System.out.println(classitems.get(i).toString());
                }
                System.out.println(classAdapter.getPos());

            }
        });
*/
        //  sharedPreferences1 = getSharedPreferences("selected",Context.MODE_PRIVATE);
        //    x= sharedPreferences1.getBoolean("lockedState", true);


    }

    private void add_func() {
        detect1 = "make_invisible";
        studentAdapter.notifyDataSetChanged();
        present.setVisibility(View.GONE);
        absent.setVisibility(View.GONE);
        late.setVisibility(View.GONE);
        done.setVisibility(View.VISIBLE);
        add_student_new_fab.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
    }

    private void showNoticeDialog() {
        AlertDialog.Builder alerDialog2 = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.notice_dialogbox, null);
        alerDialog2.setView(view);
        AlertDialog dialog = alerDialog2.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Button yes_button =(Button)view.findViewById(R.id.yes_button);
        Button no =(Button)view.findViewById(R.id.no_button);

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
                /*detect2 = "make_visible";
                studentAdapter.notifyDataSetChanged();
                present.setVisibility(View.VISIBLE);
                absent.setVisibility(View.VISIBLE);
                late.setVisibility(View.VISIBLE);

                */
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

        Button done_button =(Button)view.findViewById(R.id.done_lecture);
        TextView date_textview = view.findViewById(R.id.date_textview);

        date_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(Attendance_activity.this);
                int day = datePicker.getDayOfMonth();
                int month = (datePicker.getMonth()) + 1;
                int year = datePicker.getYear();

                datePickerDialog = new DatePickerDialog(Attendance_activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_textview.setText(dayOfMonth + "/" + (month +1)+ "/" + year);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });


        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detect2 = "make_visible";
                studentAdapter.notifyDataSetChanged();
                present.setVisibility(View.VISIBLE);
                absent.setVisibility(View.VISIBLE);
                late.setVisibility(View.VISIBLE);

                dialog.dismiss();

            }
        });


    }

    private void showTakeAttendanceDialog() {
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
                add_student_new_fab.setVisibility(View.INVISIBLE);
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


    void statusA(boolean present, boolean late) {
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

    void statusL(boolean present, boolean abs) {
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

    void statusP(boolean abs, boolean late) {
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

    void statusSingleP(boolean status) {
        p = Integer.parseInt(presentcount.getText().toString()) + 1;

        presentcount.setText(String.valueOf(p));
       /* a = Integer.parseInt(absentcount.getText().toString());
        l = Integer.parseInt(latecount.getText().toString());
       */ /*presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));
*/


    }

    void statusSingleA(boolean status) {
        a = Integer.parseInt(absentcount.getText().toString()) + 1;

        absentcount.setText(String.valueOf(a));
       /* p = Integer.parseInt(presentcount.getText().toString());
        l = Integer.parseInt(latecount.getText().toString());
       *//* presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));
*/


    }

    void statusSingleL(boolean status) {
        l = Integer.parseInt(latecount.getText().toString()) + 1;

        latecount.setText(String.valueOf(l));
       /* a = Integer.parseInt(absentcount.getText().toString());
        p = Integer.parseInt(presentcount.getText().toString());
        presentcount.setText(String.valueOf(p));
        absentcount.setText(String.valueOf(a));
        latecount.setText(String.valueOf(l));
*/

    }

    /*
     *//*  void statusp(String i) {
        if(i=="present")
        {
            p++;
            a--;
            l--;
            //  x = true;
            if (a < 0) {
                a = 0;
            }
            if (l < 0) {
                l = 0;
            }
            presentcount.setText(String.valueOf(p));

        }

         if(i=="absent")
        {
            a++;
            p--;
            l--;
            if (p < 0) {
                p = 0;
            }
            if (l < 0) {
                l = 0;
            }
            absentcount.setText(String.valueOf(a));
        }
         if(i=="late")
        {
            l++;
            a--;
            p--;
            if (a < 0) {
                a = 0;
            }
            if (p < 0) {
                p = 0;
            }
            latecount.setText(String.valueOf(l));

        }


     *//*   else if(i=="selected")
            {
                p--;
                if(p<0)
                {
                    p=0;
                }
                presentcount.setText(String.valueOf(p));

            }
     *//*
    }

   *//* void statusa(String j) {
        if(j=="absent")
        {
            a++;
            p--;
            l--;
            if (p < 0) {
                p = 0;
            }
            if (l < 0) {
                l = 0;
            }
        }
        absentcount.setText(String.valueOf(a));
    }

    void statusl(String k) {
        if(k=="late")
        {
            l++;
            a--;
            p--;
            if (a < 0) {
                a = 0;
            }
            if (p < 0) {
                p = 0;
            }
        }
        latecount.setText(String.valueOf(l));
    }
*/
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
         /*   present.setVisibility(View.VISIBLE);
            absent.setVisibility(View.VISIBLE);
            late.setVisibility(View.VISIBLE);
         */
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            fab.setVisibility(View.INVISIBLE);


            dialog.dismiss();


        });
    }

    private void addstudent() {
        String id1 = id.getText().toString();
        String name1 = name.getText().toString();
        studentItems.add(new StudentItems(id1, name1, ""));
        studentAdapter.notifyDataSetChanged();

    }

}