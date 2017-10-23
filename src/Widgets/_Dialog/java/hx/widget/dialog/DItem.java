package hx.widget.dialog;

import android.app.Activity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import hx.kit.data.UnitConverter;

/**
 * Created by RoseHongXin on 2017/7/28 0028.
 */

public class DItem {

    private static final int WIDTH_DEFAULT = 100;
    
    private ListPopupWindow mPopup;
    private Callback mCb;
    private String[] mValues;
    private boolean mFillAfterSelect = true;

    private DItem(Activity act, TextView _tv_anchor, String[] texts){
        mPopup = new ListPopupWindow(act);
        mPopup.setAnchorView(_tv_anchor);
        mPopup.setWidth(UnitConverter.dp2px(act, WIDTH_DEFAULT));

        mPopup.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return texts.length;
            }
            @Override
            public Object getItem(int position) {
                return texts[position];
            }
            @Override
            public long getItemId(int position) {
                return position;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                VhItem holder;
                if(convertView == null) {
                    TextView _tv = new TextView(_tv_anchor.getContext());
                    _tv.setGravity(Gravity.CENTER);
                    _tv.setPadding(0, 8, 0, 8);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    _tv.setLayoutParams(layoutParams);
                    holder = new VhItem(_tv);
                    holder.itemView.setOnClickListener(view -> {
                        String text = texts[position];
                        if (mCb != null) {
                            if (mValues != null && mValues.length == getCount()) mCb.onClick(text, mValues[position], position);
                            else mCb.onClick(text, text, position);
                        }
                        if (mFillAfterSelect) _tv_anchor.setText(text);
                        mPopup.dismiss();
                    });
                    convertView = holder.itemView;
                    convertView.setTag(holder);
                }else{
                    holder = (VhItem)convertView.getTag();
                }
                holder.bind(texts[position]);
                return convertView;
            }
        });
    }

    public static DItem create(Activity act, TextView anchor, String[] texts){
        return new DItem(act, anchor, texts);
    }

    public DItem width(int width){
        this.mPopup.setWidth(width);
        return this;
    }
    public DItem values(String[] values){
        this.mValues = values;
        return this;
    }
    public DItem click(Callback cb){
        mCb = cb;
        return this;
    }
    public DItem fillAfterSelect(boolean fillAfterSelect){
        this.mFillAfterSelect = fillAfterSelect;
        return this;
    }

    public void show(){
        mPopup.show();
    }

    private class VhItem extends RecyclerView.ViewHolder{
        TextView _tv;
        public VhItem(View itemView) {
            super(itemView);
            _tv = (TextView)itemView;
        }
        public void bind(String text){
            _tv.setText(text);
        }
    }

    public interface Callback{
        void onClick(String text, String value, int position);
    }


}
