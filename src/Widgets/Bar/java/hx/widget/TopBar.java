package hx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import hx.lib.R;

/**
 * Created by RoseHongXin on 2018/1/26 0026.
 */

public class TopBar extends AppBarLayout{

    TopBarHelper mTopBarHelper;

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta =  context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        String title = ta.getString(R.styleable.TopBar_tb_title);
        int navDrawable = ta.getResourceId(R.styleable.TopBar_tb_navigation, 0);
        ta.recycle();
        LayoutInflater.from(context).inflate(R.layout.l_topbar_tb, this, true);
        mTopBarHelper = TopBarHelper.obtain(this).title(title);
        if(navDrawable != 0) mTopBarHelper.navigation(navDrawable);
    }

    public void icon(@DrawableRes int iconRes, View.OnClickListener listener){
        mTopBarHelper.icon(iconRes, listener);
    }
    public void text(String text, View.OnClickListener listener){
        mTopBarHelper.text(text, listener);
    }
    public void text(@StringRes int strRes, View.OnClickListener listener){
        mTopBarHelper.text(strRes, listener);
    }



}
