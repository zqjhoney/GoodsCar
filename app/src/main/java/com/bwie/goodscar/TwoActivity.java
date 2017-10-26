package com.bwie.goodscar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.goodscar.utils.CircleTextProgressbar;


public class TwoActivity extends AppCompatActivity {

    private int progress=5;
    private CircleTextProgressbar bar;

    // private String url="http://img04.sogoucdn.com/app/a/100520093/013d20860a59d114-df5b4a05f7c173f3-7e866343d6ed14122f7512cd53e13aad.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
      //  Cur cc= (Cur) findViewById(R.id.cc);
     //   ImageView iv= (ImageView) findViewById(R.id.iv);
      //  Glide.with(this).load(url).into(iv);


      //  ImageView iv2= (ImageView) findViewById(R.id.iv2);
      //  BitmapUtils.loadImage(url,iv2);

        bar = (CircleTextProgressbar) findViewById(R.id.bar);
        bar.setProgressType(CircleTextProgressbar.ProgressType.COUNT);
        bar.setProgressLineWidth(10);//写入宽度。
        bar.setTimeMillis(3500);// 把倒计时时间改长一点。
        bar.setProgressColor(Color.RED);
        bar.setOutLineColor(Color.RED);
        bar.setCountdownProgressListener(10, progressListener);
        bar.reStart();

    }
    private CircleTextProgressbar.OnCountdownProgressListener progressListener = new CircleTextProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                bar.setText(progress + "%");
            } else if (what == 10) {
                bar.setText(progress + "%");
            }
            // 比如在首页，这里可以判断进度，进度到了100或者0的时候，你可以做跳过操作。
        }
    };
}
