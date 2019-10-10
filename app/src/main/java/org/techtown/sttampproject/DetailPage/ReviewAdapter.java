package org.techtown.sttampproject.DetailPage;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.techtown.sttampproject.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    Context context;
    private List<ReviewModalClass> reviewModalClasses;


    public ReviewAdapter(Context mainActivityContacts, List<ReviewModalClass> listModalClassList) {
        this.reviewModalClasses = listModalClassList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);


        return new MyViewHolder(itemView);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ReviewModalClass modalClass = reviewModalClasses.get(position);
        holder.review_name.setText(modalClass.getUserID2());
        holder.review_date.setText(modalClass.getDate());
        holder.review_desc.setText(modalClass.getReview());
        holder.review_rate.setText(modalClass.getRate());

    }

    @Override
    public int getItemCount() {
        return (null != reviewModalClasses ? reviewModalClasses.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView review_name, review_date, review_desc, review_rate;


        public MyViewHolder(View view) {
            super(view);


            review_name = (TextView) view.findViewById(R.id.review_name);
            review_date = (TextView) view.findViewById(R.id.review_date);
            review_desc = (TextView) view.findViewById(R.id.review_desc);
            review_rate = (TextView) view.findViewById(R.id.review_rate);



        }

    }
}
