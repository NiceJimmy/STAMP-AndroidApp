package org.techtown.sttampproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import retrofit2.http.Headers;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        ImageView imageView = (ImageView)findViewById(R.id.imageView7);
        ImageView imageView2 = (ImageView)findViewById(R.id.imageView9);
        LinearLayout layout = (LinearLayout)findViewById(R.id.lay1);
        LinearLayout layout2 = (LinearLayout)findViewById(R.id.lay2);


        Animation anim = AnimationUtils
                .loadAnimation(getApplicationContext(), R.anim.set_anim);
        imageView.startAnimation(anim);
        imageView2.startAnimation(anim);


        Handler handler = new Handler(){

            public void handleMessage(Message msg){
               super.handleMessage(msg);
               startActivity(new Intent(IntroActivity.this, LoginActivity.class));
               finish();
            }

        };
        handler.sendEmptyMessageDelayed(0, 2500);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });





    }
}
