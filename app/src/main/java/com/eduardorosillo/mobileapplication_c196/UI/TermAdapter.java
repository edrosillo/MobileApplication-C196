package com.eduardorosillo.mobileapplication_c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eduardorosillo.mobileapplication_c196.Entity.Term;
import com.eduardorosillo.mobileapplication_c196.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemView1;
        private final TextView termItemView2;
        private final TextView termItemView3;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView1=itemView.findViewById(R.id.termTextView1);
            termItemView2=itemView.findViewById(R.id.termTextView2);
            termItemView3=itemView.findViewById(R.id.termTextView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Term current=mTerms.get(position);
                    Intent intent=new Intent(context, TermDetail.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("title", current.getTermTitle());
                    intent.putExtra("start", current.getStartDate());
                    intent.putExtra("end", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context= context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.list_item_term,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null) {
            Term current=mTerms.get(position);
            String title=current.getTermTitle();
            String start=current.getStartDate();
            String end=current.getEndDate();
            holder.termItemView1.setText(title);
            holder.termItemView2.setText(start);
            holder.termItemView3.setText(end);
        }
        else {
            holder.termItemView1.setText("No term title");
            holder.termItemView2.setText("No Start Date");
            holder.termItemView3.setText("No End Date");
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms!= null ) {
            return mTerms.size();
        } else return 0;
    }
}
