package com.yui.customviews.emulate;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * 产生虚拟字符串，拼接格式
 * 手机号段 + **** + 随机四位位数 + 随机恢复类型 + 随机分钟显示
 *
 * @author liaoyuhuan
 * @name ${PROJECT_NAME}
 * @class
 * @time 2018/4/3  13:56
 * @description
 */
public class EmulateRadomData {
    ArrayList<String> arrayList = new ArrayList<>();
    /**
     * 随机数15
     */
    private int mRadomCount = 15;

    /**
     * 时间段为1-48小时
     */
    final int HOUR_MAX_NUM = 48;
    final int MINUTER_MAX_NUM = 60;

    /**
     * 中国电信号段 CHINA_TELECOM
     * 133、149、153、173、177、180、181、189、199
     * 中国联通号段 CHINA_MOBILE
     * 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段 CHINA_UNICON
     * 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     */
    public static final int[] CHINA_AVALIABLE_PHONE_NUM = {
            133, 149, 153, 173, 177, 180, 181, 189, 199,
            130, 131, 132, 145, 155, 156, 166, 175, 176, 185, 186,
            134, 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198
    };

    /**
     * 用户选择恢复的类型
     * 注意与主页面的恢复类型保持一致，但不显示"Wi-Fi密码查看"
     * "微信恢复", "QQ恢复", "照片恢复", "Wi-Fi密码查看", "Word文档恢复", "通话记录恢复", "视频音频恢复", "通讯录恢复", "短信恢复"
     */
    public static final String[] RECOVER_TYEP_LIST = {"微信恢复", "QQ恢复", "照片恢复", "Word文档恢复", "通话记录恢复", "视频音频恢复", "通讯录恢复", "短信恢复"};

    public EmulateRadomData() {
        generateRadomPhoneNum();
    }

    /**
     * 产生随机数为radomCount组
     *
     * @param radomCount
     */
    public EmulateRadomData(int radomCount) {
        mRadomCount = radomCount;
        generateRadomPhoneNum();
    }

    /**
     * 产生15个随机电话数据
     */
    private void generateRadomPhoneNum() {
        Random radom = new Random();

        for (int i = 0; i < mRadomCount; i++) {
            /** 产生的头数据从 CHINA_AVALIABLE_PHONE_NUM中获取 */
            int startNum = Math.abs(radom.nextInt());

            startNum = startNum % CHINA_AVALIABLE_PHONE_NUM.length;

            /** 产生的尾数据为正四位数，小于100000 */
            int endNum = Math.abs(radom.nextInt());
            endNum = endNum % 10000;

            /** 产生的用户选择类型数据，从 RECOVER_TYEP_LIST中获取*/
            int typeNum = Math.abs(radom.nextInt());
            typeNum = typeNum % RECOVER_TYEP_LIST.length;

            /** 产生的时间为 1分钟～48小时内 */
            int time = radom.nextInt();
            String strTime;
            if (time >= 0) {
                /** 分钟，为0时多加1 */
                time = time % MINUTER_MAX_NUM + 1;
                strTime = time + "分钟前";
            } else {
                /** 小时 */
                time = Math.abs(time) % HOUR_MAX_NUM;
                strTime = time + "小时前";
            }

            final String formateString = String.format("%3d****%4d %s %s", CHINA_AVALIABLE_PHONE_NUM[startNum], endNum, RECOVER_TYEP_LIST[typeNum], strTime);
            arrayList.add(formateString);
        }
        Log.w("", "");
    }

    public ArrayList<String> getEmulateRadomList() {
        return arrayList;
    }


}
