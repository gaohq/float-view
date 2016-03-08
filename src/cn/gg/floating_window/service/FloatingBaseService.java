package cn.gg.floating_window.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.LinearLayout;
import cn.gg.floating_window.provider.FloatingViewProvider;

/**
 * Created by gaohaoqing
 * Date : 15/11/19
 * Time : 14:28
 */
public class FloatingBaseService extends Service {

    public static final String VIEW_RESOURCE_ID = "resourceID";
    public static final String TOUCH_RESOURCE_ID = "touchResourceID";

    private LinearLayout floatingView;

    private int mViewResourceID;
    private int mTouchResourceID;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FloatingViewProvider.removeView(getApplicationContext(), floatingView);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            mViewResourceID = intent.getExtras().getInt(VIEW_RESOURCE_ID);
            mTouchResourceID = intent.getExtras().getInt(TOUCH_RESOURCE_ID);
            floatingView = FloatingViewProvider.createFloatingView(getApplicationContext(),
                    mViewResourceID,
                    mTouchResourceID);
        }
        return START_STICKY;
    }
}
