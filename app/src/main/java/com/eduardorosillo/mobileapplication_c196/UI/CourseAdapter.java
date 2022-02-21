package com.eduardorosillo.mobileapplication_c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eduardorosillo.mobileapplication_c196.Entity.Course;
import com.eduardorosillo.mobileapplication_c196.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView1;
        private final TextView courseItemView2;
        private final TextView courseItemView3;

        private  CourseViewHolder(View itemView) {
            super (itemView);
            courseItemView1 = itemView.findViewById(R.id.courseTextView1);
            courseItemView2 = itemView.findViewById(R.id.courseTextView2);
            courseItemView3 = itemView.findViewById(R.id.courseTextView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("title", current.getCourseTitle());
                    intent.putExtra("start_date", current.getStartDate());
                    intent.putExtra("end_date", current.getEndDate());
                    intent.putExtra("status", current.getCourseStatus());
                    intent.putExtra("instructor_name", current.getCourseInstructorName());
                    intent.putExtra("instructor_phone", current.getCourseInstructorPhone());
                    intent.putExtra("instructor_email", current.getCourseInstructorEmail());
                    intent.putExtra("note", current.getCourseNotes());
                    intent.putExtra("term_ID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String title=current.getCourseTitle();
            String start_date= current.getStartDate();
            String end_date= current.getEndDate();
            holder.courseItemView1.setText(title);
            holder.courseItemView2.setText(start_date);
            holder.courseItemView3.setText(end_date);
        } else {
            holder.courseItemView1.setText("No Course Title");
            holder.courseItemView2.setText("No Start Date");
            holder.courseItemView3.setText("No End Date");
        }
    }

    public void setCourses(List<Course> coursesToSet) {
        mCourses = coursesToSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        }
        else return 0;
    }


}
