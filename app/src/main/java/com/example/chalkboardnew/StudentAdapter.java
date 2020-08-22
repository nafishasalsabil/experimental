package com.example.chalkboardnew;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.chalkboardnew.Attendance_activity.detect1;
import static com.example.chalkboardnew.Attendance_activity.detect2;
import static java.security.AccessController.getContext;

class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentViewHolder> {

    Context context;
    List<StudentItems> studentItems = new ArrayList<>();
    String status = "";
    private static int p , a, l;
    boolean x = false, y = false, z = false;
    public static final String PRESENT_TEXT = "present";
    public static final String ABS_TEXT = "abs";
    public static final String LATE_TEXT = "late";




    public static final String TAG = "check";
    SharedPreferences sharedPreferences1, sharedPreferences2, sharedPreferences3;


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StudentAdapter(Context context, List<StudentItems> studentItems) {
        this.studentItems = studentItems;
        this.context = context;
        Log.d("check","studentAdapter");
    }


    public static class studentViewHolder extends RecyclerView.ViewHolder {
        TextView roll;
        TextView name;
        CardView cardView;
        ImageView pres, abs, la;

        RadioGroup radioGroupStatus;
        RadioButton radioButtonPresent,radioButtonAbs,radioButtonLate;


        public studentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.cardview);
//            pres = itemView.findViewById(R.id.present_status);
//            abs = itemView.findViewById(R.id.absent_status);
//            la = itemView.findViewById(R.id.late_status);
            radioGroupStatus = itemView.findViewById(R.id.radioGroup_status);
            radioButtonPresent = itemView.findViewById(R.id.radioButton_present);
            radioButtonAbs = itemView.findViewById(R.id.radioButton_abs);
            radioButtonLate = itemView.findViewById(R.id.radioButton_late);


            int pos = getAdapterPosition();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(studentViewHolder.this.getAdapterPosition());
                }
            });

        }
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new studentViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewHolder holder, int position) {
        holder.roll.setText(studentItems.get(position).getId());
        holder.name.setText(studentItems.get(position).getName());
        studentItems.get(position).setStatus(status);
        System.out.println(studentItems.get(position).getId());
        //studentItems.get(position).setName();
       // Attendance_activity attendance_activity = new Attendance_activity();

        String ui = Attendance_activity.detect1;
        System.out.println(ui);
       if( ui.equals("make_invisible")) {

           System.out.println("oooooooooooooooo");
           holder.radioGroupStatus.setVisibility(View.GONE);
           holder.radioButtonPresent.setVisibility(View.GONE);
           holder.radioButtonAbs.setVisibility(View.GONE);
           holder.radioButtonLate.setVisibility(View.GONE);

       }
        String uv = Attendance_activity.detect2;
        System.out.println(uv);

         if(  uv.equals("make_visible")) {

            System.out.println("sesdfwxdrcftvybuio");
            holder.radioGroupStatus.setVisibility(View.VISIBLE);
            holder.radioButtonPresent.setVisibility(View.VISIBLE);
            holder.radioButtonAbs.setVisibility(View.VISIBLE);
            holder.radioButtonLate.setVisibility(View.VISIBLE);

        }





        holder.radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               int checkedItem =  holder.radioGroupStatus.getCheckedRadioButtonId();
               if(checkedItem==R.id.radioButton_present )
               {
                   status = PRESENT_TEXT;
                  /* holder.radioButtonPresent.setButtonDrawable(R.drawable.p_filled);
                   holder.radioButtonAbs.setButtonDrawable(R.drawable.a);
                   holder.radioButtonLate.setButtonDrawable(R.drawable.l);
                  */ if(Objects.equals(studentItems.get(position).getStatus(),""))
                   {
                       Attendance_activity.statusSingleP(true);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p_filled);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l);


                   }
                   else if(Objects.equals(studentItems.get(position).getStatus(),ABS_TEXT))
                   {

                       Attendance_activity.statusP(true,false);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p_filled);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l);


                   }
                   else if(Objects.equals(studentItems.get(position).getStatus(),LATE_TEXT))
                   {
                       Attendance_activity.statusP(false,true);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p_filled);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l);

                   }
                   studentItems.get(position).setStatus(status);
               }
               else if(checkedItem == R.id.radioButton_abs)
               {
                   status = ABS_TEXT;
                /*   holder.radioButtonAbs.setButtonDrawable(R.drawable.a_filled);
                   holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                   holder.radioButtonLate.setButtonDrawable(R.drawable.l);
*/
                   if(Objects.equals(studentItems.get(position).getStatus(),""))
                   {
                       Attendance_activity.statusSingleA(true);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a_filled);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l);



                   }
                   else if(Objects.equals(studentItems.get(position).getStatus(),PRESENT_TEXT))
                   {
                       Attendance_activity.statusA(true,false);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a_filled);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l);



                   }
                   else if(Objects.equals(studentItems.get(position).getStatus(),LATE_TEXT))
                   {
                       Attendance_activity.statusA(false,true);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a_filled);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l);


                   }
                   studentItems.get(position).setStatus(status);
               }
               else if( checkedItem == R.id.radioButton_late)
               {
                   status = LATE_TEXT;
                  /* holder.radioButtonLate.setButtonDrawable(R.drawable.l_filled);
                   holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                   holder.radioButtonAbs.setButtonDrawable(R.drawable.a);
*/
                   if(Objects.equals(studentItems.get(position).getStatus(),""))
                   {
                       Attendance_activity.statusSingleL(true);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l_filled);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a);




                   }
                   else if(Objects.equals(studentItems.get(position).getStatus(),PRESENT_TEXT))
                   {
                       Attendance_activity.statusL(true,false);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l_filled);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a);



                   }
                   else if(Objects.equals(studentItems.get(position).getStatus(),ABS_TEXT))
                   {
                       Attendance_activity.statusL(false,true);
                       holder.radioButtonLate.setButtonDrawable(R.drawable.l_filled);
                       holder.radioButtonPresent.setButtonDrawable(R.drawable.p);
                       holder.radioButtonAbs.setButtonDrawable(R.drawable.a);


                   }
                   studentItems.get(position).setStatus(status);
               }
           }
       });



//
//        holder.pres.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //holder.cardView.
//
//                /*p = Integer.parseInt(Attendance_activity.presentcount.getText().toString());
//                a = Integer.parseInt(Attendance_activity.absentcount.getText().toString());
//                l = Integer.parseInt(Attendance_activity.latecount.getText().toString());
//*/
//                Log.d(TAG,"OnCLick");
//                if (status == " " || status == "absent" || status == "late") {
//                    status = "present";
//                    x = true;
//                    holder.getAdapterPosition();
//                    holder.pres.setImageResource(R.drawable.p_filled);
//                    holder.abs.setImageResource(R.drawable.a);
//                    holder.la.setImageResource(R.drawable.l);
//
//                 /*   p++;
//                    a--;
//                    l--;
//                  //  x = true;
//                    if (a < 0) {
//                        a = 0;
//                    }
//                    if (l < 0) {
//                        l = 0;
//                    }
//                  */  /*sharedPreferences1 = context.getSharedPreferences("selected", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences1.edit();
//                    editor.putBoolean("lockedState", x);
//                    editor.apply();
//*/
//                    studentItems.get(position).setStatus(status);
//
//                    Log.d("checkstatus",studentItems.get(position).getStatus());
//
//                    ((Attendance_activity) context).statusp(status);
//                  //  ((Attendance_activity) context).statusa(a);
//               //     ((Attendance_activity) context).statusl(l);
//                  //  notifyDataSetChanged();
//                 //   notifyItemChanged(position);
//                }
//                 else if(x==true || status == "present")
//                {
//                    String x = "selected";
//                    ((Attendance_activity) context).statusp(x);
//                    status = " ";
//                    holder.pres.setImageResource(R.drawable.p);
//
//
//                }
//
//                status = " ";
//
//            }
//
//
//        });
//
//        holder.abs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (status == " " || status == "present" || status == "late") {
//                    status = "absent";
//                    holder.abs.setImageResource(R.drawable.a_filled);
//                    holder.pres.setImageResource(R.drawable.p);
//                    holder.la.setImageResource(R.drawable.l);
//                 /*   p = Integer.parseInt(Attendance_activity.presentcount.getText().toString());
//                    a = Integer.parseInt(Attendance_activity.absentcount.getText().toString());
//                    l = Integer.parseInt(Attendance_activity.latecount.getText().toString());
//*/
//
//                  /*  a++;
//                    p--;
//                    l--;
//                    y = true;
//                    if (p < 0) {
//                        p = 0;
//                    }
//                    if (l < 0) {
//                        l = 0;
//                    }
//*/                   /* sharedPreferences2 = context.getSharedPreferences("selected", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences2.edit();
//                    editor.putBoolean("lockedState", y);
//                    editor.apply();
//*/
//                 //   ((Attendance_activity) context).statusp(p);
//                    studentItems.get(position).setStatus(status);
//
//                    ((Attendance_activity) context).statusp(status);
//                  //  ((Attendance_activity) context).statusl(l);
//
//                }
//
//
//            }
//        });
//        holder.la.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (status == " " || status == "present" || status == "absent") {
//                    status = "late";
//                    holder.la.setImageResource(R.drawable.l_filled);
//                    holder.pres.setImageResource(R.drawable.p);
//                    holder.abs.setImageResource(R.drawable.a);
//                 /*   p = Integer.parseInt(Attendance_activity.presentcount.getText().toString());
//                    a = Integer.parseInt(Attendance_activity.absentcount.getText().toString());
//                    l = Integer.parseInt(Attendance_activity.latecount.getText().toString());
//*/
//                 /*   l++;
//                    a--;
//                    p--;
//                    z = true;
//                    if (a < 0) {
//                        a = 0;
//                    }
//                    if (p < 0) {
//                        p = 0;
//                    }
//                    sharedPreferences1 = context.getSharedPreferences("selected", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences1.edit();
//                    editor.putBoolean("lockedState", z);
//                    editor.apply();
//*/
//                    ((Attendance_activity) context).statusp(status);
//                   /* ((Attendance_activity) context).statusp(p);
//                    ((Attendance_activity) context).statusa(a);
//
//*/
//
//                }
//
//            }
//        });
//        holder.cardView.setCardBackgroundColor(getcolor(position));

    }

  /*  private int getcolor(int position) {
        String status = studentItems.get(position).getStatus();
        if(status.equals("P"))
        {
            return Color.parseColor("#"+ Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        }
        else if(status.equals("A"))
        {
            return Color.parseColor("#"+ Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));

        }
        return Color.parseColor("#"+ Integer.toHexString(ContextCompat.getColor(context,R.color.normal)));


    }*/

    @Override
    public int getItemCount() {
        return studentItems.size();
    }
}
