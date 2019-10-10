package org.techtown.sttampproject.SharingGridView2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;


import org.techtown.sttampproject.R;

import java.util.ArrayList;
import java.util.Collections;

public class CustumAdapter extends RecyclerView.Adapter<CustumAdapter.CustomViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Dictionary3> mList;
    String s;

Context context;


    @Override // 인터페이스 개념이다. ItemTouchHelperAdapter 라는 인터페이스를 만든 뒤에
    // 여기 어댑터에다가 인터페이스에 대한 행위들을 정의해준다.
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


    public CustumAdapter(ArrayList<Dictionary3> list) {
        this.mList = list;
    }




    // RecyclerView에 새로운 데이터를 보여주기 위해 필요한 ViewHolder를 생성해야 할 때 호출됩니다.
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }



    // Adapter의 특정 위치(position)에 있는 데이터를 보여줘야 할때 호출됩니다.
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {




        Glide.with(viewholder.itemView.getContext())
                .load(mList.get(position).getImage())
                .apply(new RequestOptions().fitCenter().centerCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(viewholder.image);

        if(String.valueOf(position).equals("0")){
            viewholder.posit.setText("대표사진"); // 희한하게 settext 값에는 스트링값이 들어와야한다.

        }else {viewholder.posit.setText(String.valueOf(position)); // 희한하게 settext 값에는 스트링값이 들어와야한다.

        }




            viewholder.positString.setText(mList.get(position).getPosit()); // 이 파지션 값은 고정된 값이다.

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }






}
