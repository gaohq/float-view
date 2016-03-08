package cn.gg.floating_window.provider;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.*;
import android.widget.LinearLayout;
import com.example.gg_foating_window.R;

/**
 * Created by gaohaoqing
 * Date : 15/11/19
 * Time : 14:08
 */
public class FloatingViewProvider {

    /**
     * 创建View
     *
     * @param context
     * @param viewResourceID  layoutID
     * @param touchResourceID 要移动的view ID
     */
    public static LinearLayout createFloatingView(Context context, int viewResourceID, int touchResourceID) {

        LinearLayout floatingView = getFloatingView(context, viewResourceID, touchResourceID);
        if (floatingView == null) return null;

        WindowManager mWm = getWindowManager(context);
        if (mWm == null) return null;

        WindowManager.LayoutParams wmParams = (WindowManager.LayoutParams) floatingView.getTag(R.string.tag_vm);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags =
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
        ;

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;

        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;

        /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //添加mFloatLayout
        mWm.addView(floatingView, wmParams);
        return floatingView;
    }

    /**
     * 删除view
     *
     * @param context
     * @param floatingView
     */
    public static void removeView(Context context, LinearLayout floatingView) {
        if (floatingView == null) return;
        WindowManager mWm = getWindowManager(context);

        mWm.removeView(floatingView);
    }

    /**
     * 获取浮动窗口View
     *
     * @param context
     * @param viewResourceID
     * @return
     */
    private static LinearLayout getFloatingView(Context context, int viewResourceID, int touchResourceID) {
        if (viewResourceID < 0) return null;
        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
        //获取浮动窗口视图所在布局
        LinearLayout floatingView = (LinearLayout) inflater.inflate(viewResourceID, null);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        floatingView.setTag(R.string.tag_vm, wmParams);
        View touchView = floatingView.findViewById(touchResourceID);
        if (touchView == null) return null;
        touchView.setOnTouchListener(onTouchListener);
        return floatingView;
    }

    /**
     * 获取WindowManager对象
     *
     * @param context
     * @return
     */
    private static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * touch事件
     */
    private static View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            WindowManager.LayoutParams vm = (WindowManager.LayoutParams) ((View) view.getParent()).getTag(R.string.tag_vm);
            //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
            vm.x = (int) event.getRawX() - view.getMeasuredWidth() / 2;
            //Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
            //25为状态栏的高度
            vm.y = (int) event.getRawY() - view.getMeasuredHeight() / 2 - 25;
            // Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredHeight()/2);
            //刷新
            getWindowManager(view.getContext()).updateViewLayout((View) view.getParent(), vm);
            return false;
        }
    };
}
