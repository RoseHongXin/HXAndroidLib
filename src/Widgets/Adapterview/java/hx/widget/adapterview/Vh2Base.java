package hx.widget.adapterview;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import hx.widget.adapterview.recyclerview.Ap2Base;
import hx.widget.adapterview.recyclerview.ApBase;

/**
 * Created by rose on 16-9-9.
 */
public class Vh2Base<T>  extends RecyclerView.ViewHolder  {

    protected Activity mAct;
    protected T data;
    protected int position;
    protected Ap2Base mAdapter;
    protected RecyclerView _rv;

    public <Ap extends Ap2Base> Vh2Base(Ap adapter, @LayoutRes int layoutRes){
        this(adapter.mAct.getLayoutInflater().inflate(layoutRes, adapter._rv, false));
        this.mAdapter = adapter;
        this.mAct = adapter.mAct;
        this._rv = adapter._rv;
    }
    private Vh2Base(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @CallSuper
    public void bind(T data, int position){
        this.data = data;
        this.position = position;
    }

    public T getData(){
        return data;
    }
}
