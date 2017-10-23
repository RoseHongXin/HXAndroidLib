package hx.view.smoothtouch;

import android.view.ViewGroup;

/**
 * Created by RoseHongXin on 2017/6/27 0027.
 */

public class TouchStateImpl {

    private final int STATE_IDLE = 0x1;
    private final int STATE_TOP_DRAG = 0x2;
    private final int STATE_BOTTOM_DRAG = 0x3;
    private final int STATE_LEFT_DRAG = 0x4;
    private final int STATE_RIGHT_DRAG = 0x5;
    private final int STATE_RELEASE = 0x6;

    private int state = STATE_IDLE;
    private ViewGroup _vg;

    TouchStateImpl(ViewGroup _vg){
        this._vg = _vg;
    }

    boolean isVerticalDrag(){
        return isTopDrag() || isBottomDrag();
    }
    boolean isHorizontalDrag(){
        return isLeftDrag() || isRightDrag();
    }

    boolean isIdle(){
        return state == STATE_IDLE;
    }
    void idle(){
        state = STATE_IDLE;
    }

    boolean isTopDrag(){
        return state == STATE_TOP_DRAG;
    }
    void topDrag(){
        state = STATE_TOP_DRAG;
    }

    boolean isBottomDrag(){
        return state == STATE_BOTTOM_DRAG;
    }
    void bottomDrag(){
        state = STATE_BOTTOM_DRAG;
    }

    boolean isLeftDrag(){
        return state == STATE_LEFT_DRAG;
    }
    void leftDrag(){
        state = STATE_LEFT_DRAG;
    }

    boolean isRightDrag(){
        return state == STATE_RIGHT_DRAG;
    }
    void rightDrag(){
        state = STATE_RIGHT_DRAG;
    }

    boolean isRelease(){
        return state == STATE_RELEASE;
    }
    void release(){
        state = STATE_RELEASE;
    }


    boolean couldVerticalOffset(int yDelta){
        return couldTopOffset(yDelta) || couldBottomOffset(yDelta);
    }
    boolean couldHorizontalOffset(int xDelta){
        return couldLeftOffset(xDelta) || couldRightOffset(xDelta);
    }
    boolean couldTopOffset(int yDelta){
        if(yDelta > 0) { topDrag(); return true; }
        return false;
    }
    boolean couldBottomOffset(int yDelta){
        if(yDelta < 0) { bottomDrag(); return true; }
        return false;
    }
    boolean couldLeftOffset(int xDelta){
        if(xDelta > 0){ leftDrag(); return true; }
        return false;
    }
    boolean couldRightOffset(int xDelta){
        if(xDelta < 0) { rightDrag(); return true; }
        return false;
    }

}

//        View lastItemView = mLayoutManager.getChildAt(mLayoutManager.getChildCount() - 1);
//        if (lastItemView != null && mLayoutManager.getPosition(lastItemView) == mLayoutManager.getItemCount() - 1 && lastItemView.getBottom() == mRectOriginalPosition.bottom)
//            return true;
//        else {
//            hx.view.smoothtouch(yDelta);
//            return false;
//        }


//        View firstItemView = mLayoutManager.getChildAt(0);
//        if (firstItemView != null && mLayoutManager.getPosition(firstItemView) == 0 && firstItemView.getTop() == mRectOriginalPosition.top)
//            return true;
//        else {
//            hx.view.smoothtouch(yDelta);
//            return false;
//        }
