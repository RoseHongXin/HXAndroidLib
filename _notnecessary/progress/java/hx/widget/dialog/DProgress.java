package hx.widget.dialog;

import android.app.Activity;
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

public class DProgress {

    static DialogPlus dialog;
    static DProgress progress;

    private DProgress(Activity act){
        View l = act.getLayoutInflater().inflate(R.layout.l_dprogress, null);
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

        //ProgressBar progress = (ProgressBar) l.findViewById(R.id._pb);

        DialogPlusBuilder builder = DialogPlus.newDialog(act);
        dialog = builder
                .setContentHolder(new ViewHolder(l))
                .setCancelable(true)
                //.setOverlayBackgroundResource(builder.getContentBackgroundResource())
                //if set the exact size of window size, content wouldn't center.
                /*.setContentWidth(width)
                .setContentHeight(height)*/
                .setContentHeight(FrameLayout.LayoutParams.WRAP_CONTENT)
                .setContentWidth(FrameLayout.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .create();
    }

    public static DProgress show(Activity act){
//        progress = null;
        progress = new DProgress(act);
        dialog.show();
        return progress;
    }


    public void dismiss(){
        if(dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    public static void reset(){
        if(dialog != null) dialog.dismiss();
        progress = null;
    }

}
