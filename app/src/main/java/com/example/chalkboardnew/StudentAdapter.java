package com.example.chalkboardnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

class StudentAdapter extends BaseAdapter {

    Context context;
    private List<StudentItems> studentItems = new ArrayList<>();
    String status = "";
    private static int p, a, l;
    boolean x = false, y = false, z = false;
    public static final String PRESENT_TEXT = "present";
    public static final String ABS_TEXT = "abs";
    public static final String LATE_TEXT = "late";

    SparseBooleanArray statusOfPresent = new SparseBooleanArray();

    SparseBooleanArray statusOfAbsent = new SparseBooleanArray();
    SparseBooleanArray statusOfLate = new SparseBooleanArray();

    public static final String TAG = "check";

    public StudentAdapter(Context context, List<StudentItems> studentItems) {
        this.studentItems = studentItems;
        this.context = context;
        ResetStatus();
    }

    private void ResetStatus() {
        for (StudentItems student : studentItems) {
            student.setStatus("");
        }
    }


    class studentViewHolder extends RecyclerView.ViewHolder {


        public studentViewHolder(@NonNull View itemView) {
            super(itemView);


            int pos = getAdapterPosition();


        }


    }

    public void setOptions(StudentItems studentItem, int position, RadioGroup radioGroupStatus) {
        radioGroupStatus.setTag(position);

        if (studentItem.attendance) {
            radioGroupStatus.check(studentItem.getCheckedId());

        } else {
            radioGroupStatus.check(-1);
        }

        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int pos = (int) group.getTag();
                StudentItems studentItem = studentItems.get(pos);
                studentItem.attendance = true;
                studentItem.checkedId = checkedId;

            }
        });

    }

    @Override
    public int getCount() {
        return studentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, null);
        TextView roll = itemView.findViewById(R.id.roll);
        TextView name = itemView.findViewById(R.id.name);
        CardView cardView = itemView.findViewById(R.id.cardview);
        RadioGroup radioGroupStatus = itemView.findViewById(R.id.radioGroup_status);
        RadioButton radioButtonPresent, radioButtonAbs, radioButtonLate;
        radioButtonPresent = itemView.findViewById(R.id.radioButton_present);

        radioButtonAbs = itemView.findViewById(R.id.radioButton_abs);
        radioButtonLate = itemView.findViewById(R.id.radioButton_late);

        roll.setText(Integer.toString(studentItems.get(position).getId()));
        name.setText(studentItems.get(position).getName());
        if(statusOfLate.indexOfKey(position)>=0)
        {
            radioButtonLate.setChecked(true);
        }
        else if(statusOfAbsent.indexOfKey(position)>=0)
        {
            radioButtonAbs.setChecked(true);
        }
        else if(statusOfPresent.indexOfKey(position)>=0)
        {
            radioButtonPresent.setChecked(true);
        }
//        holder.setIsRecyclable(false);


        setOptions(studentItems.get(position), position, radioGroupStatus);

        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedItem = radioGroupStatus.getCheckedRadioButtonId();
                if (checkedItem == R.id.radioButton_present) {
                    status = PRESENT_TEXT;

                    if (Objects.equals(studentItems.get(position).getStatus(), "")) {
                        Attendance_activity.statusSingleP(true);
                    } else if (Objects.equals(studentItems.get(position).getStatus(), ABS_TEXT)) {
                        Attendance_activity.statusP(true, false);
                        statusOfAbsent.delete(statusOfAbsent.indexOfKey(position));

                    } else if (Objects.equals(studentItems.get(position).getStatus(), LATE_TEXT)) {
                        Attendance_activity.statusP(false, true);
                        statusOfLate.delete(statusOfLate.indexOfKey(position));
                    }
                    studentItems.get(position).setStatus(status);
                    statusOfPresent.put(position,true);
                } else if (checkedItem == R.id.radioButton_abs) {
                    status = ABS_TEXT;

                    if (Objects.equals(studentItems.get(position).getStatus(), "")) {
                        Attendance_activity.statusSingleA(true);
                    } else if (Objects.equals(studentItems.get(position).getStatus(), PRESENT_TEXT)) {
                        Attendance_activity.statusA(true, false);
                        statusOfPresent.delete(statusOfPresent.indexOfKey(position));
                    } else if (Objects.equals(studentItems.get(position).getStatus(), LATE_TEXT)) {
                        Attendance_activity.statusA(false, true);
                        statusOfLate.delete(statusOfLate.indexOfKey(position));
                    }
                    studentItems.get(position).setStatus(status);
                    statusOfAbsent.put(position,true);
                } else if (checkedItem == R.id.radioButton_late) {
                    status = LATE_TEXT;

                    if (Objects.equals(studentItems.get(position).getStatus(), "")) {
                        Attendance_activity.statusSingleL(true);
                    } else if (Objects.equals(studentItems.get(position).getStatus(), PRESENT_TEXT)) {
                        Attendance_activity.statusL(true, false);
                        statusOfPresent.delete(statusOfPresent.indexOfKey(position));
                    } else if (Objects.equals(studentItems.get(position).getStatus(), ABS_TEXT)) {
                        Attendance_activity.statusL(false, true);
                        statusOfAbsent.delete(statusOfAbsent.indexOfKey(position));
                    }
                    studentItems.get(position).setStatus(status);
                    statusOfLate.put(position,true);
                }
            }
        });
        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
