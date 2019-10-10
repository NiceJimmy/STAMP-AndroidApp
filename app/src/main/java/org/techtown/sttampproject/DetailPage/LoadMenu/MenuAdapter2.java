package org.techtown.sttampproject.DetailPage.LoadMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.techtown.sttampproject.MenuProcess.MenuAdapter;
import org.techtown.sttampproject.MenuProcess.MenuDataClass;
import org.techtown.sttampproject.R;

import java.util.ArrayList;

public class MenuAdapter2 extends RecyclerView.Adapter<MenuAdapter2.CustomViewHolder> {

    private ArrayList<MenuDataClass> mList;
    private Context mContext;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView mMenu;
        protected TextView mPrice;

        public CustomViewHolder(@NonNull View view) {
            super(view);

            this.mMenu = (TextView) view.findViewById(R.id.menu_name);
            this.mPrice = (TextView) view.findViewById(R.id.menu_price);
        }
    }

    public MenuAdapter2(Context context, ArrayList<MenuDataClass> list){
        mList = list;
        mContext = context;
    }


    @NonNull
    @Override
    public MenuAdapter2.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MenuAdapter2.CustomViewHolder customViewHolder, int position) {

        customViewHolder.mMenu.setText(mList.get(position).getMenu());
        customViewHolder.mPrice.setText(mList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}

