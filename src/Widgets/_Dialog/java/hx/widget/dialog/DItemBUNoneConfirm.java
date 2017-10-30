package hx.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hx.lib.R;
import hx.widget.adapterview.VhBase;
import hx.widget.adapterview.recyclerview.ApBase;

/**
 * Created by Rose on 3/2/2017.
 */

public class DItemBUNoneConfirm extends DialogFragment{

    private RecyclerView _rv_items;
    private Callback mCb;
    private List<String> mTexts;
    private Activity mAct;

    public static DItemBUNoneConfirm obtain() {
        return new DItemBUNoneConfirm();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.d_item_select_none_confirm, container, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog dialog = getDialog();
        DialogHelper.erasePadding(dialog, Gravity.BOTTOM);
        Window window = dialog.getWindow();
        if(window != null) {
            window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        }
        _rv_items = (RecyclerView) view.findViewById(R.id._rv_items);
        ApItem adapter = new ApItem(mAct, _rv_items);
        adapter.setData(mTexts);
    }
    private TextView getTextView(){
        TextView tv = new TextView(getContext());
        int pv = getResources().getDimensionPixelSize(R.dimen.l_p_v);
        tv.setPadding(0, pv, 0, pv);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    public DItemBUNoneConfirm host(Activity act){
        mAct = act;
        return this;
    }

    public DItemBUNoneConfirm callback(Callback cb){
        mCb = cb;
        return this;
    }
    public DItemBUNoneConfirm texts(List<String> texts){
        this.mTexts = texts;
        return this;
    }
    public DItemBUNoneConfirm texts(String[] texts){
        List<String> list = new ArrayList<>();
        Collections.addAll(list, texts);
        return texts(list);
    }

    public DItemBUNoneConfirm show(FragmentManager fraManager){
        show(fraManager, "DItemBUNoneConfirm");
        return this;
    }

    private class ApItem extends ApBase<VhItem, String>{
        public ApItem(Activity act, RecyclerView rv) {
            super(act, rv);
        }
        @Override
        public VhItem getVh(Activity act) {
            return new VhItem(this, R.layout.ir_item_select);
        }
    }

    private class VhItem extends VhBase<String>{
        <Ap extends ApBase> VhItem(Ap adapter, @LayoutRes int layoutRes) {
            super(adapter, layoutRes);
            itemView.setOnClickListener(view -> {
                mCb.onSelect(position, data);
                dismiss();
            });
        }
        @Override
        public void bind(String data, int position) {
            super.bind(data, position);
            ((TextView)itemView.findViewById(R.id._tv_)).setText(data);
        }
    }
    
    public interface Callback{
        void onSelect(int idx, String text);
    }
}
