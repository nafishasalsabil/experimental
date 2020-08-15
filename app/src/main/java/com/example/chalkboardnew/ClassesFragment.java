package com.example.chalkboardnew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassesFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    Toolbar toolbar_features;
    FirebaseUser firebaseUser;
    Button signout;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private NavController navController;
    BottomNavigationView bottomNavigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView profilepic;
    public TextView username;
    public DatabaseReference databaseReference;
    public String u = "";






    TextView t1, t2;
    private RecyclerView recyclerView;
    private ClassAdapter classAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<CourseInfo> classitems = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    //    String name = getArguments().getString("data");
    EditText ct, cn;
    String title = "";
    Toolbar classes_toolbar;


    private DocumentReference documentReference;
    private CollectionReference collectionReference;

    @Override
  /*  public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("data");
            System.out.println(title);
        }

    }
*/
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_classes, container, false);
      /*  if (getArguments() != null) {
            title = getArguments().getString("data");
            System.out.println(title + "  01");
        }*/
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        ct = (EditText) view.findViewById(R.id.coursetitle);
        cn = (EditText) view.findViewById(R.id.courseno);
//        drawerLayout = view.findViewById(R.id.classes_dlayout)
        recyclerView = (RecyclerView) view.findViewById(R.id.classesrecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
       // classes_toolbar = view.findViewById(R.id.classes_toolbar);
       // ((AppCompatActivity)getActivity()).setSupportActionBar(classes_toolbar);


        collectionReference = firestore.collection("users").document(userID).collection("Courses");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<CourseInfo> documentData = queryDocumentSnapshots.toObjects(CourseInfo.class);
                classAdapter = new ClassAdapter(getContext(), classitems);
                recyclerView.setAdapter(classAdapter);
                classitems.addAll(documentData);
                classAdapter.notifyDataSetChanged();
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                //    System.out.println();
                classAdapter.setOnItemClickListener(position -> gotoinsideclass(position));


            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addclass);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddNewClassActivity.class));
            }
        });
        drawerLayout = view.findViewById(R.id.classes_dlayout);
        navigationView = view.findViewById(R.id.navigationView);
        DocumentReference id_documentReference = firestore.collection("users").document(userID);
        username = navigationView.getHeaderView(0).findViewById(R.id.username);
        profilepic = navigationView.getHeaderView(0).findViewById(R.id.profilepic);
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, classes_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
//        toolbar.setNavigationIcon(R.drawable.ic_person_outline_black_24dp);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getActivity());
       /* id_documentReference.get().addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {
            @Override
            public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder fields = new StringBuilder("");
                    u = fields.append(doc.get("username")).toString();
                    //  fields.append("\nEmail: ").append(doc.get("email"));
                    //   fields.append("\nPhone: ").append(doc.get("phone"));
                    username.setText(u);
                    if (u.startsWith("a") || u.startsWith("A")) {
                        toolbar_features.setNavigationIcon(R.drawable.a_un);
                        profilepic.setImageResource(R.drawable.a_un);

                    }
                    if (u.startsWith("b") || u.startsWith("B")) {
                        toolbar_features.setNavigationIcon(R.drawable.b_letter);
                        profilepic.setImageResource(R.drawable.b_letter);

                    }
                    if (u.startsWith("c") || u.startsWith("C")) {
                        toolbar_features.setNavigationIcon(R.drawable.c);
                        profilepic.setImageResource(R.drawable.c);

                    }
                    if (u.startsWith("d") || u.startsWith("D")) {
                        toolbar_features.setNavigationIcon(R.drawable.d);
                        profilepic.setImageResource(R.drawable.d);

                    }
                    if (u.startsWith("e") || u.startsWith("E")) {
                        toolbar_features.setNavigationIcon(R.drawable.e);
                        profilepic.setImageResource(R.drawable.e);

                    }
                    if (u.startsWith("f") || u.startsWith("F")) {
                        toolbar_features.setNavigationIcon(R.drawable.f);
                        profilepic.setImageResource(R.drawable.f);

                    }
                    if (u.startsWith("g") || u.startsWith("G")) {
                        toolbar_features.setNavigationIcon(R.drawable.g);
                        profilepic.setImageResource(R.drawable.g);

                    }
                    if (u.startsWith("h") || u.startsWith("H")) {
                        toolbar_features.setNavigationIcon(R.drawable.h);
                        profilepic.setImageResource(R.drawable.h);

                    }
                    if (u.startsWith("i") || u.startsWith("I")) {
                        toolbar_features.setNavigationIcon(R.drawable.i);
                        profilepic.setImageResource(R.drawable.i);

                    }
                    if (u.startsWith("j") || u.startsWith("J")) {
                        toolbar_features.setNavigationIcon(R.drawable.j);
                        profilepic.setImageResource(R.drawable.j);

                    }
                    if (u.startsWith("k") || u.startsWith("K")) {
                        toolbar_features.setNavigationIcon(R.drawable.k);
                        profilepic.setImageResource(R.drawable.k);

                    }
                    if (u.startsWith("l") || u.startsWith("L")) {
                        toolbar_features.setNavigationIcon(R.drawable.l_un);
                        profilepic.setImageResource(R.drawable.l_un);

                    }
                    if (u.startsWith("m") || u.startsWith("M")) {
                        toolbar_features.setNavigationIcon(R.drawable.m);
                        profilepic.setImageResource(R.drawable.m);

                    }
                    if (u.startsWith("n") || u.startsWith("N")) {
                        classes_toolbar.setNavigationIcon(R.drawable.ic_n);
                        profilepic.setImageResource(R.drawable.ic_n);

                    }
                    if (u.startsWith("o") || u.startsWith("O")) {
                        toolbar_features.setNavigationIcon(R.drawable.o);
                        profilepic.setImageResource(R.drawable.o);

                    }
                    if (u.startsWith("p") || u.startsWith("P")) {
                        toolbar_features.setNavigationIcon(R.drawable.p_un);
                        profilepic.setImageResource(R.drawable.p_un);

                    }
                    if (u.startsWith("q") || u.startsWith("Q")) {
                        toolbar_features.setNavigationIcon(R.drawable.q);
                        profilepic.setImageResource(R.drawable.q);

                    }
                    if (u.startsWith("r") || u.startsWith("R")) {
                        toolbar_features.setNavigationIcon(R.drawable.r);
                        profilepic.setImageResource(R.drawable.r);

                    }
                    if (u.startsWith("s") || u.startsWith("S")) {
                        toolbar_features.setNavigationIcon(R.drawable.s);
                        profilepic.setImageResource(R.drawable.s);

                    }
                    if (u.startsWith("t") || u.startsWith("T")) {
                        toolbar_features.setNavigationIcon(R.drawable.t);
                        profilepic.setImageResource(R.drawable.t);

                    }
                    if (u.startsWith("u") || u.startsWith("U")) {
                        toolbar_features.setNavigationIcon(R.drawable.u);
                        profilepic.setImageResource(R.drawable.u);

                    }
                    if (u.startsWith("v") || u.startsWith("V")) {
                        toolbar_features.setNavigationIcon(R.drawable.v);
                        profilepic.setImageResource(R.drawable.v);

                    }
                    if (u.startsWith("w") || u.startsWith("W")) {
                        toolbar_features.setNavigationIcon(R.drawable.w);
                        profilepic.setImageResource(R.drawable.w);

                    }  if (u.startsWith("x") || u.startsWith("X")) {
                        toolbar_features.setNavigationIcon(R.drawable.x);
                        profilepic.setImageResource(R.drawable.x);

                    }
                    if (u.startsWith("y") || u.startsWith("Y")) {
                        toolbar_features.setNavigationIcon(R.drawable.y);
                        profilepic.setImageResource(R.drawable.y);

                    }
                    if (u.startsWith("z") || u.startsWith("Z")) {
                        toolbar_features.setNavigationIcon(R.drawable.z);
                        profilepic.setImageResource(R.drawable.z);

                    }
                    if (u.startsWith("0") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no0);
                        profilepic.setImageResource(R.drawable.no0);

                    }
                    if (u.startsWith("1") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no1);
                        profilepic.setImageResource(R.drawable.no1);

                    }
                    if (u.startsWith("2") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no2);
                        profilepic.setImageResource(R.drawable.no2);

                    }
                    if (u.startsWith("3") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no3);
                        profilepic.setImageResource(R.drawable.no3);

                    }
                    if (u.startsWith("4") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no4);
                        profilepic.setImageResource(R.drawable.no4);

                    }
                    if (u.startsWith("5") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no5);
                        profilepic.setImageResource(R.drawable.no5);

                    }
                    if (u.startsWith("6") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no6);
                        profilepic.setImageResource(R.drawable.no6);

                    }
                    if (u.startsWith("8") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no8);
                        profilepic.setImageResource(R.drawable.no8);

                    }
                    if (u.startsWith("7") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no7);
                        profilepic.setImageResource(R.drawable.no7);

                    }
                    if (u.startsWith("9") ) {
                        toolbar_features.setNavigationIcon(R.drawable.no9);
                        profilepic.setImageResource(R.drawable.no9);

                    }

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
*/
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });




        return view;
    }

    private void gotoinsideclass(int position) {
        startActivity(new Intent(getActivity(), InsideClassActivity.class));

    }


    public ClassesFragment(String data) {
        this.title = data;
    }

    public ClassesFragment() {

    }

    public static ClassesFragment newInstance() {
        ClassesFragment fragment = new ClassesFragment();


        return fragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
              /*  Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
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
                startActivity(new Intent(getActivity(), Update_profile.class));
                break;
        }
        return true;    }
}
