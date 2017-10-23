package hx.kit.view;

import android.content.Context;
import android.support.annotation.NonNull;
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
            boolean active = inputMgr.isActive(_et);
            if (active) {
                inputMgr.hideSoftInputFromWindow(_et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return;
            }
        }
    }

}
