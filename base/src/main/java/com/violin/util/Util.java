package com.violin.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;


/**
 * Created by wanghuilin on 2018/3/6.
 * <p>
 * email:violinlin@yeah.net
 */

public class Util {
    public static int dp2px(Context context,int dp){
        return (int) (context.getResources().getDisplayMetrics().density*dp+0.5f);
    }


    public static int px2dp(Context context,int px){
        return (int) (px/context.getResources().getDisplayMetrics().density+0.5f);
    }

    public static float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float px2dp(float px){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,px,Resources.getSystem().getDisplayMetrics());
    }

}
