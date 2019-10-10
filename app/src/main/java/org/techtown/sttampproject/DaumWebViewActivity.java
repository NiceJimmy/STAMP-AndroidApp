package org.techtown.sttampproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.techtown.sttampproject.ShareActivity.ShareActivity;
import org.techtown.sttampproject.ShareActivity.ShareActivity2;

public class DaumWebViewActivity extends AppCompatActivity {

    private WebView daum_webView;

    private TextView daum_result;

    private Handler handler;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daum_web_view);

        daum_result = (TextView) findViewById(R.id.daum_result);
        // WebView 초기화

        init_webView();


        // 핸들러를 통한 JavaScript 이벤트 반응

        handler = new Handler();


        daum_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.putExtra("result",daum_result.getText().toString());
                setResult(RESULT_OK,intent);
                finish();


            }
        });


    }


    public void init_webView() {

        daum_webView = ( WebView )findViewById( R.id.daum_webview);
        daum_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        daum_webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
       //자바스크립트에서는 AndroidBridge()에 "TestApp" 이라는 이름으로 접근할 수 있게 되었다.

        daum_webView.setWebViewClient(new WebViewClient());
        daum_webView.setWebChromeClient(new WebChromeClient());

        daum_webView.setNetworkAvailable(true);
//        daum_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        daum_webView.getSettings().setSupportMultipleWindows(true);
        daum_webView.getSettings().setJavaScriptEnabled(true);
        daum_webView.getSettings().setDomStorageEnabled(true);
        daum_webView.loadUrl("http://13.209.15.23/daum_address.php");




    }


    private class AndroidBridge {
// 자바스크립트로부터 데이터를 전달받을 클래스
        @JavascriptInterface

        public void setAddress(final String arg1, final String arg2, final String arg3) {

            handler.post(new Runnable() {

                @Override

                public void run() {

                    daum_result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음

                    init_webView();

                }

            });

        }

    }





}
