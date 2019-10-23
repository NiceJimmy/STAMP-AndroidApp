package org.techtown.sttampproject.Upload_Process;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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

import java.util.List;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.CustomViewHolder> {

    List<SharingDataClass> mList;

    private Context context;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView shop_img;
        protected TextView shop_userID;
        protected TextView shop_name;
        protected TextView shop_category;
        protected TextView shop_address;
        protected Button edit_start;

        public CustomViewHolder(final View view) {
            super(view);

             this.shop_img = (ImageView) view.findViewById(R.id.shop_img3);
             this.shop_name = (TextView) view.findViewById(R.id.proName);
            this.shop_category = (TextView) view.findViewById(R.id.cate);

            this.edit_start = (Button) view.findViewById(R.id.edit_button7);


            GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.rounded_corner_5dp);

            shop_img.setBackground(drawable);
            shop_img.setClipToOutline(true);



            edit_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    String shorder = mList.get(position).getShorder();
                    String user_id = mList.get(position).getUser_id();

                    Intent intent = new Intent(view.getContext(), EditActivity.class);
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("shorder",shorder);
                    view.getContext().startActivity(intent);


                }
            });


        }
    }


    public UploadAdapter(List<SharingDataClass> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public UploadAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_upload, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UploadAdapter.CustomViewHolder customViewHolder, int position) {


        customViewHolder.shop_name.setText(mList.get(position).getProName());
        customViewHolder.shop_category.setText(mList.get(position).getCategory());


        String path = "http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/imageupload/sharepic/";

        Glide.with(customViewHolder.itemView.getContext())
                .load(path+mList.get(position).getUser_id()+"["+mList.get(position).getShorder()+"]"+"[0].jpg")
                .apply(new RequestOptions().fitCenter().centerCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(customViewHolder.shop_img);



    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
