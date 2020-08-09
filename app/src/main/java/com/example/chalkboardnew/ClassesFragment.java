package com.example.chalkboardnew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassesFragment extends Fragment {
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


    private DocumentReference documentReference;
    private CollectionReference collectionReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("data");
            System.out.println(title);
        }

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        if (getArguments() != null) {
            title = getArguments().getString("data");
            System.out.println(title + "  01");
        }
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        ct = (EditText) view.findViewById(R.id.coursetitle);
        cn = (EditText) view.findViewById(R.id.courseno);
        recyclerView = (RecyclerView) view.findViewById(R.id.classesrecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


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
}
