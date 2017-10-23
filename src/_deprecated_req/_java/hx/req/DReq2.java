package hx.req;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;

import hx.lib.R;

/**
 * Created by rose on 16-8-2.
 */

public class DReq2 {

    static DialogPlus dialog;
    static DReq2 progress;

    private DReq2(Activity act, View layout){

        /*View l = mAct.getLayoutInflater().inflate(layoutRes, null);
        DisplayMetrics metrics = new DisplayMetrics();
        mAct.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = (int)(metrics.widthPixels * 0.618 * (metrics.widthPixels * 1.0 / metrics.heightPixels));
        int height = (int)(width * 0.618);
        ViewGroup.LayoutParams params = l.getLayoutParams();
        if(params == null) params = new ViewGroup.LayoutParams(width, height);
        else {
            params.height = height;
            params.width = width;
        }
        l.setLayoutParams(params);*/

        DialogPlusBuilder builder = DialogPlus.newDialog(act);
        dialog = builder
                .setContentHolder(new ViewHolder(layout))
                .setCancelable(true)
//                .setOverlayBackgroundResource(R.color.dreq_bg_overlay)
//                .setContentBackgroundResource(R.color.dreq_bg_content)
                //if set the exact size of window size, content wouldn't center.
                /*.setContentWidth(width)
                .setContentHeight(height)*/
                .setContentHeight(FrameLayout.LayoutParams.WRAP_CONTENT)
                .setContentWidth(FrameLayout.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .create();
    }

    public static DReq2 show(Activity act){
        View layout = act.getLayoutInflater().inflate(R.layout.d_req, null);
        progress = new DReq2(act, layout);
        dialog.show();
        return progress;
    }
    public static DReq2 show(Activity act, View layout){
        progress = new DReq2(act, layout);
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
