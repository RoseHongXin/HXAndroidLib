package hx.kit.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by RoseHongXin on 2017/9/15 0015.
 */

public class ViewKit {

    public static void hideInputManager(@NonNull EditText ... _ets){
        Context ctx = _ets[0].getContext();
        InputMethodManager inputMgr = ((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
        for (EditText _et : _ets) {
            inputMgr.hideSoftInputFromWindow(_et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            _et.clearFocus();
//            boolean active = inputMgr.isActive(_et);
//            if (active) {
//                inputMgr.hideSoftInputFromWindow(_et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                _et.clearFocus();
//                return;
//            }
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

    public static void showInputMgr(@NonNull View _v){
        _v.requestFocus();
        _v.postDelayed(() -> {
            InputMethodManager inputMgr = ((InputMethodManager)_v.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE));
//        inputMgr.showSoftInputFromInputMethod(_v.getWindowToken(), InputMethodManager.SHOW_FORCED);
//        inputMgr.showSoftInput(_v, InputMethodManager.SHOW_FORCED);
            inputMgr.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }, 100);

    }
}
