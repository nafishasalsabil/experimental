package com.example.chalkboardnew;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Users_fragment extends Fragment {


    RecyclerView users_recycler_view;
    ChatUserAdapter chatUserAdapter;
    List<ForSignupDatabase> chatUsers;

    public Users_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_fragment, container, false);
        users_recycler_view = view.findViewById(R.id.users_recycler_view);
        users_recycler_view.setHasFixedSize(true);
        users_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        chatUsers = new ArrayList<>();
        readUsers();


        return view;
    }

    private void readUsers() {
       FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        String userID = firebaseAuth.getCurrentUser().getUid();

        CollectionReference collectionReference = firestore.collection("users");
       collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

               chatUsers.clear();

               for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
               {
                    ForSignupDatabase chatUser = documentSnapshot.toObject(ForSignupDatabase.class);
                    /*if(chatUser.getUid()!=null && chatUser.getUid().equals(firebaseUser.getUid()))
                    {
                        chatUsers.add(chatUser);
                        System.out.println(chatUsers);
                    }
                    else
                    {

                    }*/
                   chatUsers.add(chatUser);
                   System.out.println(chatUsers);
                   System.out.println(chatUser.getUid());
                    chatUserAdapter = new ChatUserAdapter(getContext(),chatUsers);
                    users_recycler_view.setAdapter(chatUserAdapter);
                    chatUserAdapter.notifyDataSetChanged();
               }



           /*    classAdapter = new ClassAdapter(getApplicationContext(), classitems);
               List<CourseInfo> documentData = queryDocumentSnapshots.toObjects(CourseInfo.class);
*/
           }

       });
    }

}
