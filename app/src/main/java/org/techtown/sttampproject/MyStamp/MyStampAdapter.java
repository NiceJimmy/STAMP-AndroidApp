package org.techtown.sttampproject.MyStamp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.sttampproject.DetailPage.DetailActivity;
import org.techtown.sttampproject.MyShop.MyShopAdapter;
import org.techtown.sttampproject.MyShop.MyShopDataClass;
import org.techtown.sttampproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyStampAdapter extends RecyclerView.Adapter<MyStampAdapter.CustomViewHolder> {

    List<MyStampDataClass> mList;
    private Context context;

    int position;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView mystamp_img;
        protected TextView myshop_name;
        protected TextView off_time;
        protected TextView off_rate;
        protected Button myshop_delete;


        public CustomViewHolder(@NonNull final View view) {
            super(view);

            this.mystamp_img = (ImageView) view.findViewById(R.id.shop_img2);
            this.myshop_name = (TextView) view.findViewById(R.id.proName);
            this.off_time = (TextView) view.findViewById(R.id.off_time);
            this.off_rate = (TextView) view.findViewById(R.id.off_rate);
            this.myshop_delete = (Button) view.findViewById(R.id.delete_button6);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final List<String> ListItems = new ArrayList<>();
                    ListItems.add("");

                    ListItems.add("Shop Name : "+mList.get(position).getProName());
                    ListItems.add("Time : "+mList.get(position).getStartTime()+"~"+mList.get(position).getFinishTime());
                    ListItems.add("Rate : "+mList.get(position).getOff_rate()+"%");

                    final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    View view = LayoutInflater.from(context)
                            .inflate(R.layout.selected_stamp, null, false);
                    builder.setView(view);
                    builder.setTitle("STAMP INFORMATION");

                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int pos) {

                        }
                    });
                    builder.show();
                }
            });

        }
    }

    public MyStampAdapter(List<MyStampDataClass> list, Context context) {
        this.context = context;
        this.mList = list;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       View view = LayoutInflater.from(viewGroup.getContext())
               .inflate(R.layout.item_mystamp, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyStampAdapter.CustomViewHolder customViewHolder, int position) {

        customViewHolder.myshop_name.setText(mList.get(position).getProName());
        customViewHolder.off_time.setText(mList.get(position).getStartTime()+" ~ "+mList.get(position).getFinishTime());
        customViewHolder.off_rate.setText(mList.get(position).getOff_rate());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
