package hx.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import hx.kit.log.Log4Android;
import hx.lib.R;


/**
 * Created by Rose on 3/2/2017.
 *
 * process waiting handle.
 * like request waiting, data process waiting.
 *
 * Dialog width & height must be set at onStart() life circle.
 *
 */

public class DWaiting extends DialogFragment{

    private final static String TAG = "DWaiting";

    TextView _tv_hint;
    private boolean mCancelable;
    private String mHint;
    private Activity mAct;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_FRAME, R.style.Dialog_Waiting);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.d_waiting, container, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog dialog = getDialog();
        Window window;
        if(mCancelable && (window = dialog.getWindow()) != null){
            window.setDimAmount(0f);
//            view.setBackgroundResource(R.drawable.sh_dialog_waiting);
        }
        dialog.setOnKeyListener((d, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_BACK){
                d.dismiss();
                DWaiting.this.dismiss();
            }
            return true;
        });
        _tv_hint = (TextView) view.findViewById(R.id._tv_hint);
        if(!TextUtils.isEmpty(mHint)) {
            _tv_hint.setVisibility(View.VISIBLE);
            _tv_hint.setText(mHint);
        }
        setCancelable(mCancelable);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window;
        if(mCancelable && (window = getDialog().getWindow()) != null){
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = (int)(displayMetrics.widthPixels * 0.618f + 0.5f);
            int height = (int)(width * 0.618f + 0.5f);
            window.setLayout(width, height);
        }
    }

    public void hint(String hint){
        mHint = hint;
        if(getDialog() != null && getDialog().isShowing()){
            if(_tv_hint != null) _tv_hint.setText(mHint);
        }
    }

    public static DWaiting create(Activity act){
        return create(act, null);
    }
    public static DWaiting create(Activity act, String hint){
        return create(act, hint, true);
    }

    public static DWaiting force(Activity act, String hint){
        return create(act, hint, false);
    }
    public static DWaiting force(Activity act){
        return create(act, null, false);
    }
    public static DWaiting create(Activity act, String hint, boolean cancelable){
        DWaiting dWaiting = new DWaiting();
        dWaiting.mHint = hint;
        dWaiting.mCancelable = cancelable;
        dWaiting.mAct = act;
        return dWaiting;
    }
    /* Call this method after builder or force, carefully. */
    public DialogFragment show(){
        //预防Activity异常退出,或者切换太快,引起的getSupportFragmentManager() 为null.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (mAct != null && (mAct instanceof AppCompatActivity) && !mAct.isFinishing() && !mAct.isDestroyed())
                    show(((AppCompatActivity) mAct).getSupportFragmentManager(), TAG);
            } else {
                if (mAct != null && !mAct.isFinishing())
                    show(((AppCompatActivity) mAct).getSupportFragmentManager(), TAG);
            }
        }
        catch (NullPointerException e){ Log4Android.w(this, "show exception, null pointer."); }
        catch (IllegalStateException e) { Log4Android.w(this, "show exception, illegal state."); }
        return this;
    }

    public static DialogFragment show(Activity act){
        return _show(act, null, true);
    }
    public static DialogFragment show(Activity act, String hint){
        return _show(act, hint, true);
    }
    public static DialogFragment showForce(Activity act){
        return _show(act, null, false);
    }
    public static DialogFragment showForce(Activity act, String hint){
        return _show(act, hint, false);
    }

    private static DialogFragment _show(Activity act, String hint, boolean cancelable){
        DWaiting dWaiting = new DWaiting();
        dWaiting.mAct = act;
        dWaiting.mHint = hint;
        dWaiting.mCancelable = cancelable;
        return dWaiting.show();
    }

    @Override
    public void dismiss() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!mAct.isFinishing() && !mAct.isDestroyed()) super.dismiss();
            } else {
                if (mAct != null && !mAct.isFinishing()) super.dismiss();
            }
        }
        catch (NullPointerException e){ Log4Android.w(this, "dismiss exception, null pointer."); }
        catch (IllegalStateException e) { Log4Android.w(this, "dismiss exception, illegal state."); }
    }
}
