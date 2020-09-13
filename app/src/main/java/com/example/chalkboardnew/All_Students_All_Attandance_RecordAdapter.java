package com.example.chalkboardnew;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.chalkboardnew.Attendance_activity.clicked_courseTitle;
import static com.example.chalkboardnew.Attendance_activity.clicked_course_section;
import static com.example.chalkboardnew.Attendance_activity.p;

class All_Students_All_Attandance_RecordAdapter extends RecyclerView.Adapter<All_Students_All_Attandance_RecordAdapter.All_Students_All_Attandance_RecordViewHolder> {

    Context context;
    List<StudentItems> studentItemsrecord;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();
    DocumentReference documentReference;
    String lectureName,title,section;

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSection(String section) {
        this.section = section;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public All_Students_All_Attandance_RecordAdapter(Context context, List<StudentItems> studentItemsrecord) {
        this.studentItemsrecord = studentItemsrecord;
        this.context = context;
        setHasStableIds(true);


    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class All_Students_All_Attandance_RecordViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView roll;
        TextView name;
        TextView status;

        public All_Students_All_Attandance_RecordViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll_record);
            name = itemView.findViewById(R.id.name_record);
            status = itemView.findViewById(R.id.status_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onItemClickListener.onClick(ClassViewHolder.this.getAdapterPosition());
                /*    Intent intent = new Intent(v.getContext(),Sections_Inside_Courses.class);
                    intent.putExtra("Title",classname.getText());
                    // System.out.println(classname.getText());
                    v.getContext().startActivity(intent);
*/                }
            });
        }
    }

    @NonNull
    @Override
    public All_Students_All_Attandance_RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_students_all_attendance_record_item, parent, false);
        return new All_Students_All_Attandance_RecordViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull All_Students_All_Attandance_RecordViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.roll.setText(Integer.toString(studentItemsrecord.get(position).getId()));
        holder.name.setText(studentItemsrecord.get(position).getName());
        documentReference = firestore.collection("users").document(userID)
                .collection("Courses").document(title).collection("Sections")
                .document(section)
                .collection("Attendance").document(lectureName).collection(Integer.toString(studentItemsrecord.get(position).getId())).document("Status");
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    StatusClass statusClass = documentSnapshot.toObject(StatusClass.class);

                    holder.status.setText(statusClass.getStatus());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        System.out.println(studentItemsrecord.get(position).getStatus());
        // System.out.println(position);
        //  System.out.println(holder.getAdapterPosition());

        // setPos(holder.getAdapterPosition());

        //    holder.editcoursetitle.setText(classitems.get(position).getCourseTitle());
        // holder.editcourseno.setText(classitems.get(position).getCourseNo());

    /*    holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_class_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                documentReference = firestore.collection("users").document(userID).collection("Courses").document(classitems.get(position).getCourseTitle());

                EditText editcourseno = (EditText) dialog.findViewById(R.id.edit_courseno);
                editcourseno.setText(classitems.get(position).getCourseNo());
                EditText editcoursetype= (EditText) dialog.findViewById(R.id.edit_coursetype);
                editcoursetype.setText(classitems.get(position).getCourseType());
                EditText editcredithour = (EditText) dialog.findViewById(R.id.edit_credithours);
                editcredithour.setText(classitems.get(position).getCredits());
                //  System.out.println(classitems.get(position).getCredits());
                Button update = (Button) dialog.findViewById(R.id.updatebutton);
                Button cancel = (Button) dialog.findViewById(R.id.cancelbutton);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((isTypeChanged() && isNumberChanged() && isCreditChanged())||( isTypeChanged()&& isNumberChanged())|| (isCreditChanged()&& isTypeChanged())||
                                (isCreditChanged() && isNumberChanged()) || isTypeChanged() || isNumberChanged() || isCreditChanged()
                        ){
                            //     Toast.makeText(context,"Your profile has been updated!", Toast.LENGTH_SHORT).show();
                            //!(classitems.get(position).getCourseTitle()).equals(editcoursetitle.getText()) || !(classitems.get(position).getCourseNo()).equals(editcourseno.getText())
                            dialog.dismiss();

                        }

                    }

                    private boolean isCreditChanged() {
                        if(!((classitems.get(position).getCredits()).equals(editcredithour.getText())))
                        {
                            String cc = editcredithour.getText().toString();
                            //  classitems.get(position).setCourseTitle(cp);
                            documentReference.update("credits", cc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context,"Your credits has been updated!", Toast.LENGTH_SHORT).show();

                                }
                            });
                            //  holder.classname.setText(cp);
                            return true;
                        }
                        else {
                            return false;
                        }
                    }

                    private boolean isTypeChanged() {
                        if(!((classitems.get(position).getCourseType()).equals(editcoursetype.getText())))
                        {
                            String cp = editcoursetype.getText().toString();
                            //  classitems.get(position).setCourseTitle(cp);
                            documentReference.update("courseType", cp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context,"Your coursetype has been updated!", Toast.LENGTH_SHORT).show();

                                }
                            });
                            return true;

                            //  holder.classname.setText(cp);
                        }
                        else {
                            return false;
                        }
                    }
                    private boolean isNumberChanged() {
                        if(!((classitems.get(position).getCourseNo()).equals(editcourseno.getText())))
                        {
                            String cq = editcourseno.getText().toString();
                            //    classitems.get(position).setCourseNo(cq);
                            documentReference.update("courseNo", cq).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context,"Your courseno has been updated!", Toast.LENGTH_SHORT).show();

                                }
                            });
                            // holder.course.setText(cq);
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }


        });

        int cardcolors[] = context.getResources().getIntArray(R.array.classcolors);
        int randomAndroidColor = cardcolors[new Random().nextInt(cardcolors.length)];
        //  int remainder = getAdapterPosition() % cardcolors.size();
        holder.cardView.setBackgroundColor(randomAndroidColor);
        holder.cardView.setRadius(6);
*/

    }


    @Override
    public int getItemCount() {
        return studentItemsrecord.size();
    }
}
