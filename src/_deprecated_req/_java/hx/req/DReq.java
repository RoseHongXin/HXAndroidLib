package hx.req;

import android.app.Activity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;

import hx.lib.R;

/**
 * Created by rose on 16-8-2.
 */

public class DReq {

    private static DialogPlus dialog;
    private static DReq progress;

    private DReq(Activity act, String note){
        View l = act.getLayoutInflater().inflate(R.layout.d_req, null);
        DisplayMetrics metrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = (int)(metrics.widthPixels * 0.618 * (metrics.widthPixels * 1.0 / metrics.heightPixels));
        int height = (int)(width * 0.618);
        ViewGroup.LayoutParams params = l.getLayoutParams();
        if(params == null) params = new ViewGroup.LayoutParams(width, height);
        else {
            params.height = height;
            params.width = width;
        }
        l.setLayoutParams(params);

        TextView _tv_ = (TextView)l.findViewById(R.id._tv_);
        if(TextUtils.isEmpty(note)) _tv_.setVisibility(View.GONE);
        else{
            _tv_.setVisibility(View.VISIBLE);
            _tv_.setText(note);
        }

        DialogPlusBuilder builder = DialogPlus.newDialog(act);
        dialog = builder
                .setContentHolder(new ViewHolder(l))
                .setCancelable(true)
                .setOverlayBackgroundResource(R.color.dreq_bg_overlay)
                .setContentBackgroundResource(R.color.dreq_bg_content)
                //if set the exact size of window size, content wouldn't center.
                /*.setContentWidth(width)
                .setContentHeight(height)*/
                .setContentHeight(FrameLayout.LayoutParams.WRAP_CONTENT)
                .setContentWidth(FrameLayout.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .create();
    }

    public static DReq show(Activity act){
        progress = new DReq(act, "");
        dialog.show();
        return progress;
    }
    public static DReq show(Activity act, String note){
        progress = new DReq(act, note);
        dialog.show();
        return progress;
    }


    public void dismiss(){
        if(dialog != null && dialog.isShowing()) dialog.dismiss();
    }
    public boolean isShowing(){
        return dialog.isShowing();
    }

    public static void reset(){
        if(dialog != null) dialog.dismiss();
        progress = null;
    }

}
