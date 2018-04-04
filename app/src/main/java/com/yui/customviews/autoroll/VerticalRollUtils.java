package com.yui.customviews.autoroll;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * 自定义控件：上下滚动的textView 的辅助类
 *
 * @author liaoyuhuan
 * @name ${PROJECT_NAME}
 * @class
 * @time 2018/4/3  13:37
 * @description
 */
public class VerticalRollUtils {

    /**
     * 手机号码长度
     */
    private static final int PHONE_NUM_LENGTH = 11;
    /**
     * 混淆替换字符串
     */
    private static final String MIX_STRING = "****";

    private static final int defaultSpannableColor = Color.parseColor("#FF7A5A");

    /**
     * 手机字符串转spannable
     *
     * @param text
     * @return
     */
    public static SpannableString phoneNumToSpannablleString(String text) {
        if (text == null) {
            text = "";
        }
        SpannableString spannableString = new SpannableString(text);
        /** 过短的字符串不进行转换 */
        if (text.length() > PHONE_NUM_LENGTH) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(defaultSpannableColor);
            spannableString.setSpan(colorSpan, 0, PHONE_NUM_LENGTH, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }


    /**
     * 在线客服字符串转spannableString
     *
     * @param text
     * @return
     */
    public static SpannableString onlineServiceInfoToSpannablleString(String text) {
        if (text == null) {
            text = "";
        }
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan ColorSpan15 = new ForegroundColorSpan(defaultSpannableColor);
        /** 字符串中15的位置 */
        spannableString.setSpan(ColorSpan15, 7, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan3 = new ForegroundColorSpan(defaultSpannableColor);
        /** 字符串中3的位置 */
        spannableString.setSpan(colorSpan3, 24, 25, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 将字符串中第四到第八字符改为 ****
     *
     * @param data
     * @return
     */
    public static String replaceStringToMixString(String data) {
        if (data != null) {
            if (data.length() > 7) {
                StringBuilder stringBuilder = new StringBuilder(data);
                stringBuilder.replace(3, 7, MIX_STRING);
                return stringBuilder.toString();
            }
        }
        return data;
    }
}
