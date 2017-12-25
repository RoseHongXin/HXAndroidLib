package hx.kit.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by RoseHongXin on 2017/9/15 0015.
 */

public class ViewKit {

    public static void hideInputManager(@NonNull EditText ... _ets){
        Context ctx = _ets[0].getContext();
        InputMethodManager inputMgr = ((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
        for (EditText _et : _ets) {
            boolean active = inputMgr.isActive(_et);
            if (active) {
                inputMgr.hideSoftInputFromWindow(_et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                _et.clearFocus();
                return;
            }
        }
    }

    public static void hideInputMgr(@NonNull View... _vs){
        Context ctx = _vs[0].getContext();
        InputMethodManager inputMgr = ((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
        for(View _v : _vs) {
            inputMgr.hideSoftInputFromWindow(_v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            _v.clearFocus();
        }
    }
}
