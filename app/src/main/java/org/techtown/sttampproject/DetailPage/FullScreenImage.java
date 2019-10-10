package org.techtown.sttampproject.DetailPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.techtown.sttampproject.R;

import java.util.List;

public class FullScreenImage extends AppCompatActivity {
    List<ViewPagerDataClass> mArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_image);

        // get intent data

        Intent intent = getIntent(); /*데이터 수신*/

        int position = intent.getExtras().getInt("position");

        MyViewPagerAdapter imageAdapter = new MyViewPagerAdapter(this,mArrayList);

        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(Integer.parseInt(imageAdapter.mArrayList.get(0).getPath()));
    }
}