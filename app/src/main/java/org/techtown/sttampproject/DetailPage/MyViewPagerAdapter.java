package org.techtown.sttampproject.DetailPage;

import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.sttampproject.R;
import org.techtown.sttampproject.SharingGridView2.SharingPictures2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyViewPagerAdapter extends PagerAdapter {

    Context context;
    List<ViewPagerDataClass> mArrayList;

//    public MyViewPagerAdapter(Context context, List<ViewPagerDataClass> data) {
//        this.context =  context;
//        this.data = data;
//    }

    public MyViewPagerAdapter(Context context, List<ViewPagerDataClass> mArrayList) {

        this.context = context;
        this.mArrayList = mArrayList;

    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        String path = "http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/";

        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_hotel_detail_image,null);
        ImageView image_container = (ImageView) v.findViewById(R.id.image_container);
        Glide
                .with(context)
                .applyDefaultRequestOptions(new RequestOptions().fitCenter().centerCrop())
                .load(path+mArrayList.get(position).getPath())
                .into(image_container);
        container.addView(v);

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), FullScreenImage.class);
//                intent.putExtra("position",position);
//                v.getContext().startActivity(intent);
//            }
//        });

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);

    }




    @Override
    public int getCount() {


        return (null != mArrayList ? mArrayList.size() : 0);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
