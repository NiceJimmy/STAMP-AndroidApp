package org.techtown.sttampproject.StampProcess;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.techtown.sttampproject.R;

import java.util.ArrayList;
import java.util.Dictionary;

public class StampAdapter extends RecyclerView.Adapter<StampAdapter.CustomViewHolder> {

    private ArrayList<StampDataClass> mList;
    private Context mContext;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView mStartTime;
        protected TextView mFinishTime;
        protected TextView mRate;

        public CustomViewHolder(@NonNull View view) {
            super(view);

            this.mStartTime = (TextView) view.findViewById(R.id.start_time2);
            this.mFinishTime = (TextView) view.findViewById(R.id.finish_time);
            this.mRate = (TextView) view.findViewById(R.id.rate2);
        }
    }


    public StampAdapter(Context context, ArrayList<StampDataClass> list) {
        mList = list;
        mContext = context;

    }


    @NonNull
    @Override
    public StampAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stamp_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StampAdapter.CustomViewHolder customViewHolder, int position) {

       customViewHolder.mStartTime.setText(mList.get(position).getStartTime());
       customViewHolder.mFinishTime.setText(mList.get(position).getFinishTime());
       customViewHolder.mRate.setText(mList.get(position).getDiscount());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
