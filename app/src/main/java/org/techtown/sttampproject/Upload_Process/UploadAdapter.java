package org.techtown.sttampproject.Upload_Process;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

             this.shop_img = (ImageView) view.findViewById(R.id.shop_img);
             this.shop_userID = (TextView) view.findViewById(R.id.user_id);
             this.shop_name = (TextView) view.findViewById(R.id.shop_name);
            this.shop_category = (TextView) view.findViewById(R.id.shop_category);
            this.shop_address = (TextView) view.findViewById(R.id.shop_address);
            this.edit_start = (Button) view.findViewById(R.id.edit_start);

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

        customViewHolder.shop_userID.setText(mList.get(position).getUser_id());
        customViewHolder.shop_name.setText(mList.get(position).getProName());
        customViewHolder.shop_category.setText(mList.get(position).getCategory());
        customViewHolder.shop_address.setText(mList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
