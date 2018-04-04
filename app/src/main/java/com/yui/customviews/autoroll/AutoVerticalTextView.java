package com.yui.customviews.autoroll;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

/**
 * 自定义控件自动垂直滚动的textview
 * 参考： https://github.com/paradoxie/AutoVerticalTextview.git
 * 原有git不支持图片显示，对这方面进行了改良
 *
 * @author liaoyuhuan
 * @name ${PROJECT_NAME}
 * @class
 * @time 2018/4/3  9:44
 * @description
 */
public class AutoVerticalTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    protected static final int FLAG_START_AUTO_SCROLL = 0;
    protected static final int FLAG_STOP_AUTO_SCROLL = 1;
    /**
     * 只进行一次轮播
     */
    protected static final int FLAG_START_ONCE_SCROLL = 2;

    protected float mTextSize = 16;
    protected int mPadding = 5;
    protected int textColor = Color.BLACK;

    protected OnItemClickListener itemClickListener;
    protected Context mContext;
    protected int currentId = -1;
    protected ArrayList<String> textList;
    protected Handler handler;

    protected Drawable leftDrawable;
    protected Drawable rightDrawable;
    protected Drawable topDrawable;
    protected Drawable bottomDrawable;

    public AutoVerticalTextView(Context context) {
        this(context, null);
        mContext = context;
    }

    public AutoVerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        textList = new ArrayList<String>();
    }

    /**
     * 动画时长
     *
     * @param animDuration
     */
    public void setAnimTime(long animDuration) {
        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, animDuration, 0);
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -animDuration);
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
    }

    /**
     * 子类可以通过覆盖该函数，实现spannableString显示
     *
     * @return
     */
    protected void updateShowText() {
        if (textList.size() > 0) {
            currentId++;
            String text = textList.get(currentId % textList.size());
            text = VerticalRollUtils.replaceStringToMixString(text);
            setText(text);
        }
    }

    /**
     * 间隔时间
     *
     * @param time
     */
    public void setTextStillTime(final long time) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        updateShowText();
                        handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, time);
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        break;
                    case FLAG_START_ONCE_SCROLL:
                        updateShowText();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 设置数据源
     *
     * @param titles
     */
    public void setTextList(ArrayList<String> titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = -1;
    }

    /**
     * 设置图片
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setTextDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        leftDrawable = left;
        rightDrawable = right;
        topDrawable = top;
        bottomDrawable = bottom;
    }

    /**
     * 设置左边图片
     *
     * @param drawable
     */
    public void setLeftTextDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
        }
        setTextDrawable(drawable, null, null, null);
    }

    /**
     * @param textSize  字号
     * @param padding   内边距
     * @param textColor 字体颜色
     */
    public void setText(float textSize, int padding, int textColor) {
        mTextSize = textSize;
        mPadding = padding;
        this.textColor = textColor;
    }

    /**
     * 开始滚动
     */
    public void startAutoScroll() {
        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
    }

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
    }

    /**
     * 进行一次滚动
     */
    public void startOnceScroll() {
        handler.sendEmptyMessage(FLAG_START_ONCE_SCROLL);
    }

    @Override
    public View makeView() {
        AppCompatTextView textView = new AppCompatTextView(mContext);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setMaxLines(1);
        textView.setPadding(mPadding, mPadding, mPadding, mPadding);
        textView.setTextColor(textColor);
        textView.setTextSize(mTextSize);
        textView.setCompoundDrawablePadding(10);
        textView.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
        textView.setClickable(true);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
                    itemClickListener.onItemClick(currentId % textList.size());
                }
            }
        });
        return textView;
    }

    /**
     * 设置点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {
        /**
         * 点击回调
         *
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }

}
