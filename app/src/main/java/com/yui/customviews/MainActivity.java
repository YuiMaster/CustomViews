package com.yui.customviews;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yui.customviews.autoroll.AutoVerticalTextView;
import com.yui.customviews.emulate.EmulateRadomData;

import java.util.ArrayList;

/**
 * @author liaoyuhuan
 * @name ${PROJECT_NAME}
 * @class
 * @time 2018/4/4  10:59
 * @description
 */
public class MainActivity extends AppCompatActivity {

    private AutoVerticalTextView autoVerticalTextView;
    /**
     * 模拟的随机订单数据
     */
    private ArrayList<String> emulateOrderList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    protected void initView() {
        autoVerticalTextView = findViewById(R.id.auto_vertical_text_view);
        initEmulateView();
    }

    protected void initData() {
    }


    /**
     * 初始化伪订单相关
     */
    private void initEmulateView() {
        EmulateRadomData emulateRadomData = new EmulateRadomData();
        emulateOrderList.addAll(emulateRadomData.getEmulateRadomList());

        autoVerticalTextView.setTextList(emulateOrderList);
        //设置属性
        autoVerticalTextView.setText(12, 0, Color.BLACK);
        final Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_warning);
        autoVerticalTextView.setLeftTextDrawable(drawable);
        //设置停留时长间隔
        autoVerticalTextView.setTextStillTime(3000);
        //设置进入和退出的时间间隔
        autoVerticalTextView.setAnimTime(300);
        autoVerticalTextView.setOnItemClickListener(new AutoVerticalTextView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoVerticalTextView.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoVerticalTextView.stopAutoScroll();
    }
}
