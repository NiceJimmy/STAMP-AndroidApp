package org.techtown.sttampproject.Edit_Process.pic_info_update;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.sttampproject.R;
import org.techtown.sttampproject.SharingGridView2.ItemTouchHelperAdapter;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ImageClass2;

import java.util.Collections;
import java.util.List;

public class PicUpdateAdapter extends RecyclerView.Adapter<PicUpdateAdapter.CustomViewHolder> implements ItemTouchHelperAdapter {

    private List<ImageClass2> mList;


    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(toPosition);
        notifyItemChanged(fromPosition);



        return true;
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView image;
        protected TextView posit;
        protected TextView positString;

        public CustomViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.imageView112);
            this.posit = (TextView) view.findViewById(R.id.textView5);
            this.positString = (TextView) view.findViewById(R.id.textView6);
        }
    }

    public PicUpdateAdapter(List<ImageClass2> mList) {
        this.mList = mList;
    }


    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        String path = "http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/";

        Glide.with(viewholder.itemView.getContext())
                .load(path+mList.get(position).getImage())
                .apply(new RequestOptions().fitCenter().centerCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(viewholder.image);

        if(String.valueOf(position).equals("0")){
            viewholder.posit.setText("대표사진"); // 희한하게 settext 값에는 스트링값이 들어와야한다.

        }else {viewholder.posit.setText(String.valueOf(position)); // 희한하게 settext 값에는 스트링값이 들어와야한다.

        }




        viewholder.positString.setText(mList.get(position).getPicorder2()); // 이 파지션 값은 고정된 값이다.
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
