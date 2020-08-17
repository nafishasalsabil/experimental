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
   // Toolbar classes_toolbar;


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
               /* classAdapter.setOnItemClickListener(new ClassAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        gotoinsideclass(position);
                    }
                });*/


            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addclass);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddNewClassActivity.class));
            }
        });
     /*   drawerLayout = view.findViewById(R.id.classes_dlayout);
        navigationView = view.findViewById(R.id.navigationView);
     */   DocumentReference id_documentReference = firestore.collection("users").document(userID);
       /* username = navigationView.getHeaderView(0).findViewById(R.id.username);
        profilepic = navigationView.getHeaderView(0).findViewById(R.id.profilepic);
       *//* actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, classes_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);*/
//        toolbar.setNavigationIcon(R.drawable.ic_person_outline_black_24dp);

   /*     drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getActivity());
       */



        return view;
    }

    public void test()
    {

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
