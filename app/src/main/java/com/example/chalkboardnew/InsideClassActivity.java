package com.example.chalkboardnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.chalkboardnew.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;


public class InsideClassActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CardView attendance,materials,results,student_info;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    Toolbar toolbar_inside_class;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView profilepic;
    public TextView username;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public String u = "";
    FirebaseFirestore firestore;
    String userID;
    String courseTitle="";
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_class);
        attendance = findViewById(R.id.attendance_cardview);
        materials = findViewById(R.id.materials_cardview);
        results = findViewById(R.id.results_cardview);
        student_info = findViewById(R.id.studentinfo_cardview);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //   signout = findViewById(R.id.signout);
        toolbar_inside_class = findViewById(R.id.inside_class_toolbar);
        drawerLayout = findViewById(R.id.inside_class_dlayout);
        navigationView = findViewById(R.id.navigationView);
//        navController = Navigation.findNavController(this, R.id.main);
        //firebaseDatabase = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID =  firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("users").document(userID);
//

        setSupportActionBar(toolbar_inside_class);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EFF3FB")));
        getSupportActionBar().setElevation(0);
//
        username = navigationView.getHeaderView(0).findViewById(R.id.username);
        profilepic = navigationView.getHeaderView(0).findViewById(R.id.profilepic);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_inside_class, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
//        toolbar.setNavigationIcon(R.drawable.ic_person_outline_black_24dp);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder fields = new StringBuilder("");
                    u = fields.append(doc.get("username")).toString();
                    //  fields.append("\nEmail: ").append(doc.get("email"));
                    //   fields.append("\nPhone: ").append(doc.get("phone"));
                    username.setText(u);
                  /*  if (u.startsWith("a") || u.startsWith("A")) {
                        toolbar1.setNavigationIcon(R.drawable.a_un);
                        profilepic.setImageResource(R.drawable.a_un);

                    }
                    if (u.startsWith("b") || u.startsWith("B")) {
                        toolbar1.setNavigationIcon(R.drawable.b_letter);
                        profilepic.setImageResource(R.drawable.b_letter);

                    }
                    if (u.startsWith("c") || u.startsWith("C")) {
                        toolbar1.setNavigationIcon(R.drawable.c);
                        profilepic.setImageResource(R.drawable.c);

                    }
                    if (u.startsWith("d") || u.startsWith("D")) {
                        toolbar1.setNavigationIcon(R.drawable.d);
                        profilepic.setImageResource(R.drawable.d);

                    }
                    if (u.startsWith("e") || u.startsWith("E")) {
                        toolbar1.setNavigationIcon(R.drawable.e);
                        profilepic.setImageResource(R.drawable.e);

                    }
                    if (u.startsWith("f") || u.startsWith("F")) {
                        toolbar1.setNavigationIcon(R.drawable.f);
                        profilepic.setImageResource(R.drawable.f);

                    }
                    if (u.startsWith("g") || u.startsWith("G")) {
                        toolbar1.setNavigationIcon(R.drawable.g);
                        profilepic.setImageResource(R.drawable.g);

                    }
                    if (u.startsWith("h") || u.startsWith("H")) {
                        toolbar1.setNavigationIcon(R.drawable.h);
                        profilepic.setImageResource(R.drawable.h);

                    }
                    if (u.startsWith("i") || u.startsWith("I")) {
                        toolbar1.setNavigationIcon(R.drawable.i);
                        profilepic.setImageResource(R.drawable.i);

                    }
                    if (u.startsWith("j") || u.startsWith("J")) {
                        toolbar1.setNavigationIcon(R.drawable.j);
                        profilepic.setImageResource(R.drawable.j);

                    }
                    if (u.startsWith("k") || u.startsWith("K")) {
                        toolbar1.setNavigationIcon(R.drawable.k);
                        profilepic.setImageResource(R.drawable.k);

                    }
                    if (u.startsWith("l") || u.startsWith("L")) {
                        toolbar1.setNavigationIcon(R.drawable.l);
                        profilepic.setImageResource(R.drawable.l);

                    }
                    if (u.startsWith("m") || u.startsWith("M")) {
                        toolbar1.setNavigationIcon(R.drawable.m);
                        profilepic.setImageResource(R.drawable.m);

                    }
                    if (u.startsWith("n") || u.startsWith("N")) {
                        toolbar1.setNavigationIcon(R.drawable.ic_n);
                        profilepic.setImageResource(R.drawable.ic_n);

                    }
                    if (u.startsWith("o") || u.startsWith("O")) {
                        toolbar1.setNavigationIcon(R.drawable.o);
                        profilepic.setImageResource(R.drawable.o);

                    }
                    if (u.startsWith("p") || u.startsWith("P")) {
                        toolbar1.setNavigationIcon(R.drawable.p);
                        profilepic.setImageResource(R.drawable.p);

                    }
                    if (u.startsWith("q") || u.startsWith("Q")) {
                        toolbar1.setNavigationIcon(R.drawable.q);
                        profilepic.setImageResource(R.drawable.q);

                    }
                    if (u.startsWith("r") || u.startsWith("R")) {
                        toolbar1.setNavigationIcon(R.drawable.r);
                        profilepic.setImageResource(R.drawable.r);

                    }
                    if (u.startsWith("s") || u.startsWith("S")) {
                        toolbar1.setNavigationIcon(R.drawable.s);
                        profilepic.setImageResource(R.drawable.s);

                    }
                    if (u.startsWith("t") || u.startsWith("T")) {
                        toolbar1.setNavigationIcon(R.drawable.t);
                        profilepic.setImageResource(R.drawable.t);

                    }
                    if (u.startsWith("u") || u.startsWith("U")) {
                        toolbar1.setNavigationIcon(R.drawable.u);
                        profilepic.setImageResource(R.drawable.u);

                    }
                    if (u.startsWith("v") || u.startsWith("V")) {
                        toolbar1.setNavigationIcon(R.drawable.v);
                        profilepic.setImageResource(R.drawable.v);

                    }
                    if (u.startsWith("w") || u.startsWith("W")) {
                        toolbar1.setNavigationIcon(R.drawable.w);
                        profilepic.setImageResource(R.drawable.w);

                    }
                    if (u.startsWith("x") || u.startsWith("X")) {
                        toolbar1.setNavigationIcon(R.drawable.x);
                        profilepic.setImageResource(R.drawable.x);

                    }
                    if (u.startsWith("y") || u.startsWith("Y")) {
                        toolbar1.setNavigationIcon(R.drawable.y);
                        profilepic.setImageResource(R.drawable.y);

                    }
                    if (u.startsWith("z") || u.startsWith("Z")) {
                        toolbar1.setNavigationIcon(R.drawable.z);
                        profilepic.setImageResource(R.drawable.z);

                    }
                    if (u.startsWith("0") ) {
                        toolbar1.setNavigationIcon(R.drawable.no0);
                        profilepic.setImageResource(R.drawable.no0);

                    }
                    if (u.startsWith("1") ) {
                        toolbar1.setNavigationIcon(R.drawable.no1);
                        profilepic.setImageResource(R.drawable.no1);

                    }
                    if (u.startsWith("2") ) {
                        toolbar1.setNavigationIcon(R.drawable.no2);
                        profilepic.setImageResource(R.drawable.no2);

                    }
                    if (u.startsWith("3") ) {
                        toolbar1.setNavigationIcon(R.drawable.no3);
                        profilepic.setImageResource(R.drawable.no3);

                    }
                    if (u.startsWith("4") ) {
                        toolbar1.setNavigationIcon(R.drawable.no4);
                        profilepic.setImageResource(R.drawable.no4);

                    }
                    if (u.startsWith("5") ) {
                        toolbar1.setNavigationIcon(R.drawable.no5);
                        profilepic.setImageResource(R.drawable.no5);

                    }
                    if (u.startsWith("6") ) {
                        toolbar1.setNavigationIcon(R.drawable.no6);
                        profilepic.setImageResource(R.drawable.no6);

                    }
                    if (u.startsWith("8") ) {
                        toolbar1.setNavigationIcon(R.drawable.no8);
                        profilepic.setImageResource(R.drawable.no8);

                    }
                    if (u.startsWith("7") ) {
                        toolbar1.setNavigationIcon(R.drawable.no7);
                        profilepic.setImageResource(R.drawable.no7);

                    }
                    if (u.startsWith("9") ) {
                        getSupportActionBar().setNavigationIcon(R.drawable.no9);
                        profilepic.setImageResource(R.drawable.no9);

                    }
*/
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_person_outline_black_24dp);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
       // System.out.println(title);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Attendance_activity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Materials.class));

            }
        });
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Results.class));

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(InsideClassActivity.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

           /*     Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    //gotoMainActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Session not close", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
*/


                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.update_profile:
                startActivity(new Intent(getApplicationContext(), Update_profile.class));
                break;
        }
        return true;
    }
}

