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


public class InsideClassActivity extends AppCompatActivity{
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar_inside_class.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setElevation(0);
        toolbar_inside_class.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
        /*Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
       */// System.out.println(title);
        Intent intent = getIntent();
        String section = intent.getStringExtra("section");
        String title = intent.getStringExtra("Title");

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentList.class);
                intent.putExtra("section",section);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Materials.class);
                intent.putExtra("section",section);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Results.class);
                intent.putExtra("section",section);
                intent.putExtra("title",title);
                startActivity(intent);

            }
        });
        student_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentInfo.class);
                intent.putExtra("section",section);
                intent.putExtra("title",title);
                startActivity(intent);

            }
        });


    }

  /*  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
Intent intent = new Intent(InsideClassActivity.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//nafisa tomar id te repo naki?hm... tomar git log in daw.. ar e ai khane nah brwserr
                startActivity(intent);

           *//*     Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
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
*//*


                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.update_profile:
                startActivity(new Intent(getApplicationContext(), Update_profile.class));
                break;
        }
        return true;
    }*/
}

