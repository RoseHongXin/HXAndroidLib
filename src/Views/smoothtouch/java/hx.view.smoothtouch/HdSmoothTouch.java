package hx.view.smoothtouch;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by RoseHongXin on 2017/6/27 0027.
 *
 * 实现滑动View控件拖拽的柔和手感.
 *
 */

public class HdSmoothTouch {

    private final int INVALID_POINTER_ID = -1;

    private float mDragRatio = ISmooth.DEFAULT_DRAG_RATIO;
    private float mMaximumTouchVelocity;

    private Rect mRectOriginalPosition;
    private float mLastX;
    private float mLastY;
    private float mInitDownX;
    private float mInitDownY;
    private int mActivePointerId;
    private boolean mTouchFling = false;

    private TranslateAnimation mBounceAnim;
    private TouchStateImpl mTouchStateImpl;
    private VelocityTracker mVelocityTracker;

    private ViewGroup _vg;
    @LinearLayoutCompat.OrientationMode private int mOrientation;

    private HdSmoothTouch(@NonNull ViewGroup _vg, @LinearLayoutCompat.OrientationMode int orientation){
        this._vg = _vg;
        this.mOrientation = orientation;
        if(_vg instanceof RecyclerView) {}
        else if(_vg instanceof AbsListView){}
        else if(_vg instanceof HorizontalScrollView) {
            this.mOrientation = LinearLayoutCompat.HORIZONTAL;
        }
        else if(_vg instanceof ScrollView) {
            this.mOrientation = LinearLayoutCompat.VERTICAL;
        }
        else throw new IllegalArgumentException("View must be a RecyclerView or ScrollView or AbsListView.");
        final int[] globalLayoutCallbackCount = {0};
        _vg.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if(globalLayoutCallbackCount[0]++ == 0) return;
            init();
            addTouchListener();
        });
    }
    public static HdSmoothTouch handle(@NonNull ViewGroup _vg){
        return handle(_vg, LinearLayoutCompat.VERTICAL);
    }
    public static HdSmoothTouch handle(@NonNull ViewGroup _vg, @LinearLayoutCompat.OrientationMode int orientation){
        return new HdSmoothTouch(_vg, orientation);
    }

    private void init(){
//        mTouchSlop = ViewConfiguration.get(_vg.getContext()).getScaledTouchSlop();
        mTouchStateImpl = new TouchStateImpl(_vg);
        mRectOriginalPosition = new Rect();
        mRectOriginalPosition.left = _vg.getLeft();
        mRectOriginalPosition.top = _vg.getTop();
        mRectOriginalPosition.right = _vg.getRight();
        mRectOriginalPosition.bottom = _vg.getBottom();
        mVelocityTracker = VelocityTracker.obtain();
        mMaximumTouchVelocity = ViewConfiguration.get(_vg.getContext()).getScaledMaximumFlingVelocity();
    }
    private void addTouchListener() {
        _vg.setOnTouchListener(((v, event) -> {
            int actionIndex = MotionEventCompat.getActionIndex(event);
            mVelocityTracker.addMovement(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mInitDownX = mLastX = event.getX();
                    mInitDownY = mLastY = event.getY();
                    mActivePointerId = event.getPointerId(0);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mInitDownX = mLastX = event.getX(actionIndex);
                    mInitDownY = mLastY = event.getY(actionIndex);
                    mActivePointerId = event.getPointerId(actionIndex);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float y = event.getY();
                    float x = event.getX();
                    int yDelta = (int) ((y - mLastY) * mDragRatio + 0.5f);
                    int xDelta = (int) ((x - mLastX) * mDragRatio + 0.5f);
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumTouchVelocity);
                    if (mOrientation == LinearLayoutCompat.HORIZONTAL && mTouchStateImpl.couldHorizontalOffset(xDelta)) {
                        float xVel = mVelocityTracker.getXVelocity(mActivePointerId);
                        if(Math.abs(xVel) > ISmooth.MAXIMUM_HORIZONTAL_TOUCH_VELOCITY || Math.abs(xDelta) > ISmooth.MAXIMUM_HORIZONTAL_TOUCH_DELTA) {
                            mTouchFling = true;
//                            return true;
                        }
//                        _vg.layout(_vg.getLeft() + xDelta, mRectOriginalPosition.top, _vg.getRight() + xDelta, mRectOriginalPosition.bottom);
                        ViewCompat.offsetLeftAndRight(_vg, xDelta);
                    } else if (mOrientation == LinearLayoutCompat.VERTICAL && mTouchStateImpl.couldVerticalOffset(yDelta)) {
                        float yVel = mVelocityTracker.getYVelocity(mActivePointerId);
                        if(Math.abs(yVel) > ISmooth.MAXIMUM_VERTICAL_TOUCH_VELOCITY || Math.abs(yDelta) > ISmooth.MAXIMUM_VERTICAL_TOUCH_DELTA) {
                            mTouchFling = true;
//                            return true;
                        }
//                        _vg.layout(mRectOriginalPosition.left, _vg.getTop() + yDelta, mRectOriginalPosition.right, _vg.getBottom() + yDelta);
                        ViewCompat.offsetTopAndBottom(_vg, yDelta);
                    }
                    mLastX = x;
                    mLastY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    if (mTouchStateImpl.isVerticalDrag()) {
                        yDelta = (int) ((mLastY - mInitDownY) * mDragRatio + 0.5f);
                        mBounceAnim = new TranslateAnimation(0, 0, yDelta, 0);
                        startAnim();
                    } else if (mTouchStateImpl.isHorizontalDrag()) {
                        xDelta = (int) ((event.getX() - mInitDownX) * mDragRatio + 0.5f);
                        mBounceAnim = new TranslateAnimation(xDelta, 0, 0, 0);
                        startAnim();
                    } else {
                        mTouchStateImpl.idle();
                    }
                    mVelocityTracker.clear();
                    mActivePointerId = INVALID_POINTER_ID;
                    if(mTouchFling){
                        mTouchFling = false;
                        return true;
                    }
                    break;
            }
            return false;
        }));
    }

    private void startAnim(){
        mBounceAnim.setDuration(ISmooth.ANIMATION_DURATION);
        mBounceAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {
                mTouchStateImpl.idle();
                mBounceAnim = null;
            }
            @Override public void onAnimationRepeat(Animation animation) {}
        });
        mTouchStateImpl.release();
        _vg.layout(mRectOriginalPosition.left, mRectOriginalPosition.top, mRectOriginalPosition.right, mRectOriginalPosition.bottom);
        _vg.startAnimation(mBounceAnim);
    }
}
