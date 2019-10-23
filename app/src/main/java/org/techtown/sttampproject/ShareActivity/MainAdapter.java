package org.techtown.sttampproject.ShareActivity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.sttampproject.DetailPage.DetailActivity;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {


    List<SharingDataClass> mList;

    private Context context;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

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
                    int position = getAdapterPosition();
                    String shorder = mList.get(position).getShorder();
                    String userid = mList.get(position).getUser_id();
                    String proName = mList.get(position).getProName();
                    String address = mList.get(position).getAddress();
                    String address2 = mList.get(position).getAddress2();
                    String about_shop = mList.get(position).getDescription();
                    String tel_con = mList.get(position).getStore_tel1();
                    String tel_con2 = mList.get(position).getStore_tel2();



                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("userid",userid);
                    intent.putExtra("shareorder",shorder);
                    intent.putExtra("proName",proName);
                    intent.putExtra("address",address);
                    intent.putExtra("address2",address2);
                    intent.putExtra("about_shop",about_shop);
                    intent.putExtra("tel_con",tel_con);
                    intent.putExtra("tel_con2",tel_con2);


                    view.getContext().startActivity(intent);
                    Log.d("datapackage2","넘어온 데이터"+shorder+"&"+userid);


                }
            });

            Log.d("loaddata","불러오는 목록 : "+ mList.toArray().toString());

        }
    }


    public MainAdapter(List<SharingDataClass> list, Context context) {
        this.context = context;
        this.mList = list;
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

        String address = mList.get(position).getAddress()+" "+mList.get(position).getAddress2();
        customViewHolder.userID.setText(mList.get(position).getUser_id());
        customViewHolder.Title.setText(mList.get(position).getProName());
        customViewHolder.hPrice.setText(address);
        customViewHolder.dPrice.setText(mList.get(position).getCategory());



String path = "http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/";

        Glide.with(customViewHolder.itemView.getContext())
                .load(path+mList.get(position).getPath())
                .apply(new RequestOptions().fitCenter().centerCrop())
//                .transition(new DrawableTransitionOptions().crossFade())
                .into(customViewHolder.mainImg);

        Log.d("dataval","타이틀데이터 : " + mList.get(position).getAddress2());
    }

    @Override
    public int getItemCount() {// 중요한부분이다 이게 없으면 로드가 되지 않더라...
        return (null != mList ? mList.size() : 0);
    }


}


