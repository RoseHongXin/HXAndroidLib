package hx.widget;


import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import hx.components.ABase;
import hx.lib.R;

/**
 * Created by rose on 16-8-12.
 */

public class Tb {

    public static <ACT extends ABase> View createOnlyTitle(ACT act, String title){
        View view = create(act, R.mipmap.i_back, title, null);
        view.findViewById(R.id._tb_iv_left).setVisibility(View.GONE);
        return view;
    }
    public static <ACT extends ABase> View createOriginalTb(ACT act, String title){
       return createOriginalTb(act, R.mipmap.i_back, view -> act.finish(), title);
    }
    public static <ACT extends ABase> View createOriginalTb(ACT act, @DrawableRes int icon, View.OnClickListener listener, String title){
        AppBarLayout appBarLayout = (AppBarLayout)act.getLayoutInflater().inflate(R.layout.tb_original, act.sGetLayout());
        Toolbar toolbar = (Toolbar) appBarLayout.getChildAt(0);
        toolbar.setTitle("");
        ((TextView)toolbar.findViewById(R.id._tb_title)).setText(title);
        toolbar.setNavigationIcon(icon);
        act.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(listener);
        return appBarLayout;
    }

    public static <ACT extends ABase> View create(ACT act){
        return create(act, R.mipmap.i_back, "", null);
    }
    public static <ACT extends ABase> View create(ACT act, String title){
        return create(act, R.mipmap.i_back, title, null);
    }
    public static <ACT extends ABase> View create(ACT act, @DrawableRes int iconRes, String title){
        View _tb = getLayout(act, R.layout.tb_left_iv);
        ImageView _tb_iv_left = (ImageView) _tb.findViewById(R.id._tb_iv_left);
        _tb_iv_left.setImageResource(iconRes);
        _tb_iv_left.setOnClickListener(view -> act.finish());
        ((TextView)_tb.findViewById(R.id._tb_tv_title)).setText(TextUtils.isEmpty(title) ? "" : title);
        return _tb;
    }
    public static <ACT extends ABase> View create(ACT act, String title, View.OnClickListener listener){
        return create(act, R.mipmap.i_back, title, listener);
    }
    public static <ACT extends ABase> View create(ACT act, @DrawableRes int iconRes, String title, View.OnClickListener listener){
        View _tb = getLayout(act, R.layout.tb_left_iv);
        ImageView _tb_iv_left = (ImageView) _tb.findViewById(R.id._tb_iv_left);
        _tb_iv_left.setImageResource(iconRes);
        _tb_iv_left.setOnClickListener(view -> {
            if(listener == null){
                act.finish();
                return;
            }
            listener.onClick(_tb_iv_left);
        });
        ((TextView)_tb.findViewById(R.id._tb_tv_title)).setText(TextUtils.isEmpty(title) ? "" : title);
        return _tb;
    }

    public static <ACT extends ABase> View create(ACT act, String title, String rightText, View.OnClickListener listener){
        View _tb = getLayout(act, R.layout.tb_right_tv);
        TextView _tb_tv_right = (TextView)_tb.findViewById(R.id._tb_tv_right);
        _tb_tv_right.setOnClickListener(view -> {
            if(listener == null){
                act.finish();
                return;
            }
            listener.onClick(_tb_tv_right);
        });
        _tb_tv_right.setText(rightText);
        ((TextView)_tb.findViewById(R.id._tb_tv_title)).setText(TextUtils.isEmpty(title) ? "" : title);
        return _tb;
    }
    
    public static <ACT extends ABase> View create(ACT act, String title,
                                                  @DrawableRes int leftIconRes, View.OnClickListener leftListener,
                                                  String rightText, View.OnClickListener rightListener){
        View _tb = getLayout(act, R.layout.tb_left_iv_right_tv);
        ((TextView)_tb.findViewById(R.id._tb_tv_title)).setText(title);
        ImageView _tb_iv_left = (ImageView) _tb.findViewById(R.id._tb_iv_left);
        _tb_iv_left.setImageResource(leftIconRes);
        _tb_iv_left.setOnClickListener(leftListener);
        TextView tv_right = (TextView)_tb.findViewById(R.id._tb_tv_right);
        tv_right.setText(rightText);
        tv_right.setOnClickListener(rightListener);
        return _tb;
    }
    public static <ACT extends ABase> View create(ACT act, String title,
                                                  @DrawableRes int leftIconRes, View.OnClickListener leftListener,
                                                  @DrawableRes int rightIconRes, View.OnClickListener rightListener){
        View _tb = getLayout(act, R.layout.tb_left_iv_right_iv);
        ((TextView)_tb.findViewById(R.id._tb_tv_title)).setText(title);
        ImageView _tb_iv_left = (ImageView) _tb.findViewById(R.id._tb_iv_left);
        _tb_iv_left.setImageResource(leftIconRes);
        _tb_iv_left.setOnClickListener(leftListener);
        ImageView _tb_iv_right = (ImageView)_tb.findViewById(R.id._tb_iv_right);
        _tb_iv_right.setImageResource(rightIconRes);
        _tb_iv_right.setOnClickListener(rightListener);
        return _tb;
    }

    public static <ACT extends ABase> View create(ACT act, String title,
                                                  String leftText, View.OnClickListener leftListener,
                                                  @DrawableRes int rightIconRes, View.OnClickListener rightListener){
        View _tb = getLayout(act, R.layout.tb_left_tv_right_iv);
        ((TextView)_tb.findViewById(R.id._tb_tv_title)).setText(title);
        TextView _tb_tv_left = (TextView) _tb.findViewById(R.id._tb_tv_left);
        _tb_tv_left.setText(leftText);
        _tb_tv_left.setOnClickListener(leftListener);
        ImageView _tb_iv_right = (ImageView)_tb.findViewById(R.id._tb_iv_right);
        _tb_iv_right.setImageResource(rightIconRes);
        _tb_iv_right.setOnClickListener(rightListener);
        return _tb;
    }
    public static <ACT extends ABase> View create(ACT act, String title,
                                                  String leftText, View.OnClickListener leftListener,
                                                  String rightText, View.OnClickListener rightListener){
        View _tb = getLayout(act, R.layout.tb_left_tv_right_iv);
        ((TextView)_tb.findViewById(R.id._tb_tv_title)).setText(title);
        TextView _tb_tv_left = (TextView) _tb.findViewById(R.id._tb_tv_left);
        _tb_tv_left.setText(leftText);
        _tb_tv_left.setOnClickListener(leftListener);
        TextView _tb_tv_right = (TextView)_tb.findViewById(R.id._tb_tv_right);
        _tb_tv_right.setOnClickListener(rightListener);
        _tb_tv_right.setText(rightText);
        return _tb;
    }

    public static <ACT extends ABase>  View create(ACT act, @LayoutRes int layoutRes){
        ViewGroup _tb = (ViewGroup) ((LayoutInflater)act.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).inflate(layoutRes, act.sGetLayout(), false);
        _tb.getChildAt(0).setOnClickListener(view -> act.finish());
        return _tb;
    }

    private static <ACT extends ABase>  View getLayout(ACT act, @LayoutRes int layoutRes){
        View _tb = ((LayoutInflater)act.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).inflate(layoutRes, act.sGetLayout(), false);
        return _tb;
    }
}
