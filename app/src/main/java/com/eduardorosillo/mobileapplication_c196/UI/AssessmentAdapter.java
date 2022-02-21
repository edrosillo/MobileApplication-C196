package com.eduardorosillo.mobileapplication_c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eduardorosillo.mobileapplication_c196.Entity.Assessment;
import com.eduardorosillo.mobileapplication_c196.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItem1;
        private final TextView assessmentItem2;
        private final TextView assessmentItem3;

        private AssessmentViewHolder(View itemView) {
            super (itemView);
            assessmentItem1 = itemView.findViewById(R.id.assessmentTextView1);
            assessmentItem2 = itemView.findViewById(R.id.assessmentTextView2);
            assessmentItem3 = itemView.findViewById(R.id.assessmentTextView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("title", current.getAssessmentTitle());
                    intent.putExtra("start_date", current.getAssessmentStartDate());
                    intent.putExtra("end_date", current.getAssessmentEndDate());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("course_ID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_assessment, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String title = current.getAssessmentTitle();
            String start_date = current.getAssessmentStartDate();
            String end_date = current.getAssessmentEndDate();
            holder.assessmentItem1.setText(title);
            holder.assessmentItem2.setText(start_date);
            holder.assessmentItem3.setText(end_date);
        } else {
            holder.assessmentItem1.setText("No Assessment Title");
            holder.assessmentItem2.setText("No Start Date");
            holder.assessmentItem3.setText("No End Date");
        }
    }

    public void setAssessments(List<Assessment> assessmentsToSet) {
        mAssessments = assessmentsToSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null) {
            return mAssessments.size();
        }
        else return 0;
    }
}
