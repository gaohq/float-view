package cn.gg.floating_window.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by gaohaoqing
 * Date : 15/11/19
 * Time : 10:47
 */
public class FloatingView extends LinearLayout {

    private static String TAG = "floatingView";

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    private WindowManager mWm;
    private WindowManager.LayoutParams wmParams;


    public FloatingView(Context context) {
        super(context);
        init(context);
    }

    public FloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mWm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        setOnTouchListener(onTouchListener);
    }

    public WindowManager.LayoutParams getWmParams() {
        return this.wmParams;
    }

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            //获取相对屏幕的坐标，即以屏幕左上角为原点
            x = event.getRawX();
            y = event.getRawY() - 25;   //25是系统状态栏的高度
            Log.i(TAG, "currX" + x + "====currY" + y);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //获取相对View的坐标，即以此View左上角为原点
                    mTouchStartX = event.getX();
                    mTouchStartY = event.getY();

                    Log.i(TAG, "startX" + mTouchStartX + "====startY" + mTouchStartY);

                    break;
                case MotionEvent.ACTION_MOVE:
                    updateViewPosition();
                    break;

                case MotionEvent.ACTION_UP:
                    updateViewPosition();
                    mTouchStartX = mTouchStartY = 0;
                    break;
            }
            return true;
        }
    };


    private void updateViewPosition() {
        //更新浮动窗口位置参数
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        mWm.updateViewLayout(this, wmParams);

    }
}
