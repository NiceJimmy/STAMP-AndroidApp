package org.techtown.sttampproject.MyPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.sttampproject.DetailPage.DetailActivity;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;

import java.util.ArrayList;
import java.util.List;

public class MypageAdapter extends RecyclerView.Adapter<MypageAdapter.CustomViewHolder> implements Filterable {

    List<SharingDataClass> mList, MypageFilter;

    private Context context;
    CustomFilter filter;

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected ImageView mainImg;
        protected TextView userID;
        protected TextView Title;
        protected TextView hPrice;
        protected TextView dPrice;
        protected TextView sPrice;

        public CustomViewHolder(final View view) {




            super(view);
            this.mainImg = (ImageView) view.findViewById(R.id.imageView3);
            this.userID = (TextView) view.findViewById(R.id.textView2);
            this.Title = (TextView) view.findViewById(R.id.textView3);
            this.hPrice = (TextView) view.findViewById(R.id.textView8);
            this.dPrice = (TextView) view.findViewById(R.id.textView9);
            this.sPrice = (TextView) view.findViewById(R.id.textView13);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    view.getContext().startActivity(intent);
                }
            });

            Log.d("loaddata223","불러오는 목록 : "+ userID.getText().toString());

        }
    }



    public MypageAdapter(List<SharingDataClass> mList, Context context){
        this.context = context;
        this.mList = mList;
        this.MypageFilter = mList;
    }





    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       View view = LayoutInflater.from(viewGroup.getContext())
               .inflate(R.layout.main_list, viewGroup, false);
       CustomViewHolder viewHolder = new CustomViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {




        customViewHolder.userID.setText(mList.get(position).getUser_id());
        customViewHolder.Title.setText(mList.get(position).getProName());
        customViewHolder.hPrice.setText(mList.get(position).getAddress());
        customViewHolder.dPrice.setText(mList.get(position).getCategory());


        String path = "http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/";

        Glide.with(customViewHolder.itemView.getContext())
                .load(path + mList.get(position).getPath())
                .apply(new RequestOptions().centerCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(customViewHolder.mainImg);



    }

    @Override
    public int getItemCount() {// 중요한부분이다 이게 없으면 로드가 되지 않더라...
        return (null != mList ? mList.size() : 0);
    }


    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilter((ArrayList<SharingDataClass>) MypageFilter,this);

        }
        return filter;
    }
}
