package com.example.chalkboardnew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentViewHolder> {

    Context context;
    List<StudentItems> studentItems;
    String status = " ";
    private static int p=0,a=0,l=0;


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
    }


    public static class studentViewHolder extends RecyclerView.ViewHolder {
        TextView roll;
        TextView name;
        CardView cardView;
        ImageView pres, abs, la;



        public studentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.cardview);
            pres =itemView. findViewById(R.id.present_status);
            abs = itemView.findViewById(R.id.absent_status);
            la =itemView. findViewById(R.id.late_status);
            int pos = getAdapterPosition();

            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));

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
        holder.pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //holder.cardView.

                 if(status==" "||status== "absent" || status == "late")
                {
                    status = "present";
                    holder.pres.setImageResource(R.drawable.p_filled);
                    holder.abs.setImageResource(R.drawable.a);
                    holder.la.setImageResource(R.drawable.l);

                    p++;
                    a--;
                    l--;
                    if(a<0)
                    {
                        a=0;
                    }
                    if(l<0)
                    {
                        l=0;
                    }
                    ((Attendance_activity)context).statusp(p);
                    ((Attendance_activity)context).statusa(a);
                    ((Attendance_activity)context).statusl(l);

                }
                else if(status == "present")
                {
                    status = " ";
                    holder.pres.setImageResource(R.drawable.p);
                    p--;
                    if(p<0)
                    {
                        p=0;
                    }

                    ((Attendance_activity)context).statusp(p);
                    ((Attendance_activity)context).statusa(a);
                    ((Attendance_activity)context).statusl(l);

                }



            }
        });
        holder.abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(status==" "||status== "present" || status == "late")
                {
                    status = "absent";
                    holder.abs.setImageResource(R.drawable.a_filled);
                    holder.pres.setImageResource(R.drawable.p);
                    holder.la.setImageResource(R.drawable.l);

                    a++;
                    p--;
                    l--;

                    if(p<0)
                    {
                        p=0;
                    }
                    if(l<0)
                    {
                        l=0;
                    }
                    ((Attendance_activity)context).statusp(p);
                    ((Attendance_activity)context).statusa(a);
                    ((Attendance_activity)context).statusl(l);

                }
                else if(status == "absent")
                {
                    status = " ";
                    holder.abs.setImageResource(R.drawable.a);
                    a--;
                    if(a<0)
                    {
                        a=0;
                    }

                    ((Attendance_activity)context).statusp(p);
                    ((Attendance_activity)context).statusa(a);
                    ((Attendance_activity)context).statusl(l);

                }



            }
        });
        holder.la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(status==" "||status== "present" || status == "absent")
                {
                    status = "late";
                    holder.la.setImageResource(R.drawable.l_filled);
                    holder.pres.setImageResource(R.drawable.p);
                    holder.abs.setImageResource(R.drawable.a);

                    l++;
                    a--;
                    p--;
                    if(a<0)
                    {
                        a=0;
                    }
                    if(p<0)
                    {
                        p=0;
                    }
                    ((Attendance_activity)context).statusp(p);
                    ((Attendance_activity)context).statusa(a);
                    ((Attendance_activity)context).statusl(l);


                }
                else if(status == "late")
                {
                    status = " ";
                    holder.la.setImageResource(R.drawable.l);
                    l--;
                    if(l<0)
                    {
                        l=0;
                    }
                    ((Attendance_activity)context).statusp(p);
                    ((Attendance_activity)context).statusa(a);
                    ((Attendance_activity)context).statusl(l);


                }

            }
        });
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
