package hx.widget;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import hx.lib.R;
import hx.widget.dialog.DialogHelper;

/**
 * Created by RoseHongXin on 2017/8/1 0001.
 */

public class DInput extends DialogFragment{

    private TextInputLayout _til_edit;
    private TextInputEditText _et_edit;
    private Button _bt_editConfirm;

    String TAG = "DInput";
    private TextView _tv_anchor;
    private Callback mCb;
    private String mText, mHint, mBtTxt;
    private FragmentManager mFraManager;
    private boolean mFillAfterInput;

    public static Builder builder(){
        return new Builder();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.d_input, container, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _til_edit = (TextInputLayout)view.findViewById(R.id._til_edit);
        _et_edit = (TextInputEditText)view.findViewById(R.id._et_edit);
        _bt_editConfirm = (Button) view.findViewById(R.id._bt_editConfirm);
        view.findViewById(R.id._bt_editConfirm).setOnClickListener(v -> _bt_editConfirm());
        Dialog dialog = getDialog();
        DialogHelper.erasePadding(dialog, Gravity.BOTTOM);
        Window window = dialog.getWindow();
        if(window != null) {
            window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(mText)) _et_edit.setText(mText);
        _et_edit.setHint(mHint);
        _bt_editConfirm.setText(TextUtils.isEmpty(mBtTxt) ? getString(R.string.HX_confirm) : mBtTxt);
        _et_edit.requestFocus();
    }

    public void _bt_editConfirm(){
        dismiss();
        String text = _et_edit.getText().toString();
        if(!TextUtils.isEmpty(text)) {
            if(mFillAfterInput) _tv_anchor.setText(text);
            if(mCb != null) mCb.onConfirm(text);
        }
    }

    public DInput show(){
        show(mFraManager, TAG);
        return this;
    }

    public static class Builder{
        private String mText, mHint, mBtTxt;
        private Callback mCb;
        private FragmentManager mFraManager;
        private TextView _tv_anchor;
        private boolean mFillAfterInput;

        public Builder text(String text){
            this.mText = text;
            return this;
        }
        public Builder hint(String hint){
            this.mHint = hint;
            return this;
        }
        public Builder callback(Callback cb){
            this.mCb = cb;
            return this;
        }
        public Builder bt(String btTxt){
            this.mBtTxt = btTxt;
            return this;
        }
        public Builder fraManager(FragmentManager fragmentManager){
            this.mFraManager = fragmentManager;
            return this;
        }
        public Builder anchor(TextView _tv_anchor){
            this._tv_anchor = _tv_anchor;
            return this;
        }
        public Builder fillAfterInput(boolean yes){
            this.mFillAfterInput = yes;
            return this;
        }
        public DInput build(){
            DInput dialog = new DInput();
            dialog.mFraManager = mFraManager;
            dialog._tv_anchor = _tv_anchor;
            dialog.mCb = mCb;
            dialog.mText = mText;
            dialog.mHint = mHint;
            dialog.mBtTxt = mBtTxt;
            dialog.mFillAfterInput = mFillAfterInput;
            return dialog;
        }
    }

    public interface Callback {
        void onConfirm(String str);
    }

}
