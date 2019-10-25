package org.techtown.sttampproject.MyShop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.sttampproject.DetailPage.DetailActivity;
import org.techtown.sttampproject.Edit_Process.EditActivity;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.Upload_Process.UploadAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyShopAdapter extends RecyclerView.Adapter<MyShopAdapter.CustomViewHolder> {

List<MyShopDataClass> mList;
private Context context;



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView myshop_img;
        protected TextView myshop_name;
        protected TextView myshop_address;
        protected Button myshop_delete;

        public CustomViewHolder(@NonNull final View view) {
            super(view);

            this.myshop_img = (ImageView) view.findViewById(R.id.shop_imageView);
            this.myshop_name = (TextView) view.findViewById(R.id.shop_name2);
            this.myshop_address = (TextView) view.findViewById(R.id.shop_address2);
            this.myshop_delete = (Button) view.findViewById(R.id.button5);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String shorder = mList.get(position).getShorder();
                    String userid = mList.get(position).getUser_id();
                    String proName = mList.get(position).getProName();
                    String about_shop = mList.get(position).getAbout_shop();
                    String Tel_con = mList.get(position).getStore_tel1();
                    String Tel_con2 = mList.get(position).getStore_tel2();
                    String address = mList.get(position).getAddress();
                    String address2 = mList.get(position).getAddress2();



                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("userid",userid);
                    intent.putExtra("shareorder",shorder);
                    intent.putExtra("proName",proName);
                    intent.putExtra("address",address);
                    intent.putExtra("address2",address2);
                    intent.putExtra("about_shop",about_shop);
                    intent.putExtra("tel_con",Tel_con);
                    intent.putExtra("tel_con2",Tel_con2);


                    view.getContext().startActivity(intent);



                }
            });

        }
    }



    public MyShopAdapter(List<MyShopDataClass> list, Context context) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      View view = LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.item_myshop, viewGroup, false);
      CustomViewHolder viewHolder = new CustomViewHolder(view);
      return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyShopAdapter.CustomViewHolder customViewHolder, int position) {
        String address = mList.get(position).getAddress()+" "+mList.get(position).getAddress2();
        customViewHolder.myshop_name.setText(mList.get(position).getProName());
        customViewHolder.myshop_address.setText(address);

        String path = "http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/imageupload/sharepic/"+mList.get(position).getUser_id()+"["+mList.get(position).getShorder()+"][0].jpg";

        Glide.with(customViewHolder.itemView.getContext())
                .load(path)
                .apply(new RequestOptions().fitCenter())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(customViewHolder.myshop_img);
        Log.d("imagedata","이미지경로 : " + path);

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
