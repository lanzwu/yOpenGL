package com.york.media.opengl.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

public class DisplayUtil {

    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        return metric.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        return metric.heightPixels;
    }

    public static void resetViewSize(View view, int with, int height) {
        LogUtil.e("with="+with+",height="+height);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(with,height);;
        lp.width = with;
        lp.height = height;
        view.setLayoutParams(lp);
    }
}
