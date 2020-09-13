package com.example.chalkboardnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyNotes extends AppCompatActivity {

    public static final int REQEST_CODE_ADD_NOTE = 1;
    RecyclerView notes_recyclerview;
    public static final int NUM_COLUMN = 2;
    private LinearLayout layoutWebURL;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();

    List<NotesClass> notesClassList = new ArrayList();
    NotesAdapter notesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        Intent intent  = getIntent();
        String title  =intent.getStringExtra("title");
        String sec = intent.getStringExtra("section");

        notes_recyclerview = findViewById(R.id.notesRecyclerView);
        notes_recyclerview.setHasFixedSize(true);

       StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUMN,LinearLayoutManager.VERTICAL);
        notes_recyclerview.setLayoutManager(layoutManager);

        CollectionReference collectionReference =  firestore.collection("users").document(userID)
                .collection("Courses").document(title)
                .collection("Sections").document(sec).collection("MyNotes");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<NotesClass> documentData = queryDocumentSnapshots.toObjects(NotesClass.class);
                 notesAdapter = new NotesAdapter(getApplicationContext(),notesClassList);
                notes_recyclerview.setAdapter(notesAdapter);
                notesClassList.addAll(documentData);
                notesAdapter.notifyDataSetChanged();
               /* for(int i = 0;i<notesClassList.size();i++)
                {
                    System.out.println("Hi");
                    System.out.println(notesClassList.get(i).toString());
                    if((notesClassList.get(i).toString().equals("No url added")))
                {
                    notesAdapter.setState("invisible");

                }
                else
                {
                    notesAdapter.setState("visible");


                }
                    notesAdapter.notifyDataSetChanged();
                }
*/

            }
        });

        ImageView imageAddNoteMain = findViewById(R.id.addnotes);
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent1 = new Intent(getApplicationContext(),CreateNoteActivity.class);
                intent1.putExtra("section",sec);
                intent1.putExtra("title",title);
                startActivity(intent1);

            }
        });

    }
}