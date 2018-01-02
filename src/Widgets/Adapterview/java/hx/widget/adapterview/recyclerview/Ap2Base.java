package hx.widget.adapterview.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hx.widget.adapterview.ISFAp2Base;
import hx.widget.adapterview.Vh2Base;
import hx.widget.adapterview.VhBase;

/**
 * Created by rose on 16-8-12.
 *
 * Based on item position as the viewType
 *
 * the subclass check the raw type of mData to initiate viewholder as well as mData bind.
 *
 */

public abstract class Ap2Base<D extends Object> extends RecyclerView.Adapter<Vh2Base> {

    final String TAG = "--Ap2Base--";

    public Activity mAct;
    public RecyclerView _rv;
    private List<D> data = new ArrayList<>();

    protected abstract int getViewType(int position, Object data);
    @SuppressWarnings("unchecked")
    protected abstract <V extends Vh2Base> V getVh(Activity act, D data, int viewType);
    protected abstract void bind(Vh2Base holder, D data, int position);

     protected Ap2Base(Activity act, RecyclerView rv){
         this.mAct = act;
         this._rv = rv;
    }

    @Override
    public Vh2Base onCreateViewHolder(ViewGroup parent, int viewType) {
        return getVh(mAct, data.get(viewType), viewType);
    }

    @Override
    public void onBindViewHolder(Vh2Base holder, int position) {
        bind(holder, data.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        Object data = null;
        try {
            data = getData() == null || getData().isEmpty() ? null : getData().get(position);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            data = null;
        }
        return getViewType(position, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void  setData(List<D> d){
        data = new ArrayList<>();
        data.addAll(d);
        notifyDataSetChanged();
    }
    public void  addData(List<D> d){
        if(data == null) data = new ArrayList<>();
        data.addAll(d);
        notifyDataSetChanged();
    }
    public void addData(D obj){
        if(data == null) data = new ArrayList<>();
        data.add(obj);
        notifyDataSetChanged();
    }
    public List getData(){
        return data;
    }

}

