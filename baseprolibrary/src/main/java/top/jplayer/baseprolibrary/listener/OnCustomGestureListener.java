package top.jplayer.baseprolibrary.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/5/18.
 * top.jplayer.baseprolibrary.listener
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class OnCustomGestureListener implements GestureDetector.OnGestureListener {


    private float mPosX, mPosY, mCurPosX, mCurPosY;//记录mListViewDevice 滑动的位置
    private static final int FLING_MIN_DISTANCE = 20;//mListView  滑动最小距离
    private static final int FLING_MIN_VELOCITY = 200;//mListView 滑动最大速度

    public OnCustomGestureListener() {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtil.e("onSingleTapUp");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        LogUtil.e("onShowPress");
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        LogUtil.e("onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        LogUtil.e("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        //向上滑动
        if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE) {
            LogUtil.e("-----向上滑动");
        }
        // 向下滑动
        if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            LogUtil.e("-----向下滑动");
        }

        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        LogUtil.e("onDown");
        return false;
    }
}
