package org.techtown.sttampproject.DetailPage.LoadStamp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.sttampproject.R;
import org.techtown.sttampproject.StampProcess.StampAdapter;
import org.techtown.sttampproject.StampProcess.StampDataClass;

import java.util.ArrayList;

public class StampAdapter2 extends RecyclerView.Adapter<StampAdapter2.CustomViewHolder> {

    private ArrayList<StampDataClass> mList;
    private Context mContext;

    int selectedPosition=-1;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView mStartTime;
        protected TextView mFinishTime;
        protected TextView mRate;
        protected ImageView mImage;
        protected ImageView mImage2;

        protected TextView for_data1;
        protected TextView for_data2;
        protected TextView for_data3;

        public CustomViewHolder(@NonNull View view) {
            super(view);

            this.mStartTime = (TextView) view.findViewById(R.id.start_time2);
            this.mFinishTime = (TextView) view.findViewById(R.id.finish_time);
            this.mRate = (TextView) view.findViewById(R.id.rate2);
            this.mImage = (ImageView) view.findViewById(R.id.imageView4);
            this.mImage2 = (ImageView) view.findViewById(R.id.imageView5);

            this.for_data1 = (TextView) view.findViewById(R.id.for_data1);
            this.for_data2 = (TextView) view.findViewById(R.id.for_data2);
            this.for_data3 = (TextView) view.findViewById(R.id.for_data3);

//            if(view.isSelected()){
//                mImage.setVisibility(View.INVISIBLE);
//                mImage2.setVisibility(View.VISIBLE);
//            }

//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    v.setSelected(true);
//                    if(v.isSelected()){
//                        mImage.setVisibility(View.INVISIBLE);
//                        mImage2.setVisibility(View.VISIBLE);
//                    }
//                }
//            });

        }
    }


    public StampAdapter2(Context context, ArrayList<StampDataClass> list) {
        mList = list;
        mContext = context;

    }


    @NonNull
    @Override
    public StampAdapter2.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stamp_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StampAdapter2.CustomViewHolder customViewHolder, final int position) {

if(selectedPosition==position){
    customViewHolder.mImage.setVisibility(View.INVISIBLE);
    customViewHolder.mImage2.setVisibility(View.VISIBLE);
}else {
    customViewHolder.mImage.setVisibility(View.VISIBLE);
    customViewHolder.mImage2.setVisibility(View.INVISIBLE);
}
customViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        selectedPosition=position;
        notifyDataSetChanged();

        SharedPreferences pref = mContext.getSharedPreferences("stamp_info", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("start_time",String.valueOf(mList.get(selectedPosition).getStartTime()));
        editor.putString("finish_time",String.valueOf(mList.get(selectedPosition).getFinishTime()));
        editor.putString("off_rate",String.valueOf(mList.get(selectedPosition).getDiscount()));

        editor.apply();
        Log.d("selected_time","선택시간 : " + String.valueOf(mList.get(selectedPosition).getStartTime())+" "+String.valueOf(mList.get(selectedPosition).getFinishTime())+" "+String.valueOf(mList.get(selectedPosition).getDiscount()));




    }
});

        customViewHolder.mStartTime.setText(mList.get(position).getStartTime());
        customViewHolder.mFinishTime.setText(mList.get(position).getFinishTime());
        customViewHolder.mRate.setText(mList.get(position).getDiscount());



    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
