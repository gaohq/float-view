package cn.gg.floating_window.provider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import cn.gg.floating_window.service.FloatingBaseService;

/**
 * Created by gaohaoqing
 * Date : 15/11/19
 * Time : 15:23
 */
public class FloatingProvider {

    public static void startService(Context context, int layoutResourceID, int touchResourceID) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(FloatingBaseService.VIEW_RESOURCE_ID, layoutResourceID);
        bundle.putInt(FloatingBaseService.TOUCH_RESOURCE_ID, touchResourceID);
        intent.putExtras(bundle);
        intent.setClass(context, FloatingBaseService.class);
        context.startService(intent);
    }

    public static void stopService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, FloatingBaseService.class);
        context.stopService(intent);
    }
}
