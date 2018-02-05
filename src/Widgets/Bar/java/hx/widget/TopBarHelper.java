package hx.widget;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hx.lib.R;

/**
 * Created by RoseHongXin on 2018/1/26 0026.
 */

public class TopBarHelper {

    private Activity mAct;
    private Toolbar _tb_;
    private TextView _tv_tbTitle;
    private ImageView _iv_tbRight;
    private TextView _tv_tbRight;

    private TopBarHelper(Activity act){
        mAct = act;
        _tb_ = (Toolbar) act.findViewById(R.id._tb_);
        if(_tb_ != null && act instanceof AppCompatActivity){
            ((AppCompatActivity)act).setSupportActionBar(_tb_);
        }
        _tv_tbTitle = (TextView) act.findViewById(R.id._tv_tbTitle);
        _iv_tbRight = (ImageView) act.findViewById(R.id._iv_tbRight);
        _tv_tbRight = (TextView) act.findViewById(R.id._tv_tbRight);
    }
    private TopBarHelper(View layout){
        _tb_ = (Toolbar) layout.findViewById(R.id._tb_);
        _tv_tbTitle = (TextView) layout.findViewById(R.id._tv_tbTitle);
        _iv_tbRight = (ImageView) layout.findViewById(R.id._iv_tbRight);
        _tv_tbRight = (TextView) layout.findViewById(R.id._tv_tbRight);
    }

    public static TopBarHelper obtain(Object obj){
        if(obj instanceof Activity) return new TopBarHelper((Activity)obj);
        else return new TopBarHelper((View)obj);
    }

    public TopBarHelper title(@StringRes int strRes){
        return title(mAct.getString(strRes));
    }
    public TopBarHelper title(String title){
        if(_tv_tbTitle != null){
            _tv_tbTitle.setText(title);
        }
        if(_tb_ != null) _tb_.setTitle("");
        return this;
    }
    public TopBarHelper icon(@DrawableRes int iconRes, View.OnClickListener listener){
        if(_iv_tbRight != null){
            _iv_tbRight.setImageResource(iconRes);
            _iv_tbRight.setOnClickListener(listener);
        }
        return this;
    }
    public TopBarHelper text(String text, View.OnClickListener listener){
        if(_tv_tbRight != null){
            _tv_tbRight.setText(text);
            _tv_tbRight.setOnClickListener(listener);
        }
        return this;
    }
    public TopBarHelper text(@StringRes int strRes, View.OnClickListener listener){
        return text(mAct.getString(strRes), listener);
    }

    public TopBarHelper navigation(@DrawableRes int iconRes){
        return navigation(iconRes, null);
    }
    public TopBarHelper navigation(@DrawableRes int iconRes, View.OnClickListener listener){
        return navigation(mAct, iconRes, listener);
    }
    private TopBarHelper navigation(Activity act, @DrawableRes int iconRes, View.OnClickListener listener){
//        if(_tb_ != null){
//            if(listener == null && act != null) {
//                ActionBar actionBar = act.getActionBar();
//                if(actionBar != null) {
//                    actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
//                    actionBar.setDisplayHomeAsUpEnabled(true);
//                }
//            } else{
//                _tb_.setNavigationOnClickListener(listener);
//            }
//            _tb_.setNavigationIcon(iconRes);
//        }
        if(_tb_ != null){
            if(listener != null) _tb_.setNavigationOnClickListener(listener);
            _tb_.setNavigationIcon(iconRes);
        }
        return this;
    }



}
