package hx.view.smoothtouch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by RoseHongXin on 2017/6/23 0023.
 */

public class SmoothRecyclerView extends RecyclerView implements ISmooth {


    HdSmoothTouch mHdSmoothTouch;

    public SmoothRecyclerView(Context context) {
        this(context, null);
    }

    public SmoothRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmoothRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context ctx, @Nullable AttributeSet attrs, int defStyle){
        mHdSmoothTouch = HdSmoothTouch.handle(this);
    }

    public void sHandleTouch(@LinearLayoutCompat.OrientationMode int orientation){
        mHdSmoothTouch = HdSmoothTouch.handle(this, orientation);
    }


}
