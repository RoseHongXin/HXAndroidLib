package hx.widget.adapterview.recyclerview;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hx.widget.adapterview.IItemClickListener;
import hx.widget.adapterview.VhBase;

/**
 * Created by rose on 16-8-12.
 */

public abstract class ApBase<Vh extends VhBase<T>, T> extends RecyclerView.Adapter<Vh> {

    final String TAG = "--ApBase--";

    private List<T> mDatas = new ArrayList<T>();
    public Activity mAct;
    public RecyclerView _rv;
    private IItemClickListener<T> mClickListener;


    public abstract Vh getVh(Activity act);
    @CallSuper protected void bind(Vh holder, T data, int position){
        holder.bind(data, position);
    }
    protected void  registerItemClick(IItemClickListener<T> clickListener){
        this.mClickListener = clickListener;
    }

     public ApBase(Activity act, RecyclerView rv){
        this.mAct = act;
        this._rv = rv;
         LinearLayoutManager layoutManager = new LinearLayoutManager(mAct);
         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         _rv.setLayoutManager(layoutManager);
         _rv.setAdapter(this);
    }

    @Override
    public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        Vh holder = getVh(mAct);
//        viewType 未做额外处理，此处只作为position用。
//        if(mClickListener != null) holder.itemView.setOnClickListener(view -> mClickListener.onClick(mDatas.get(viewType), viewType));
        return holder;
    }

    @Override
    public void onBindViewHolder(Vh holder, int position) {
        bind(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setData(List<T> datas){
        if(mDatas == null) mDatas = new ArrayList<T>();
        else mDatas.clear();
        mDatas = new ArrayList<>();
        if(datas != null) mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(List<T> datas){
        if(mDatas == null) mDatas = new ArrayList<T>();
        if(datas != null) mDatas.addAll(datas);
        notifyDataSetChanged();
    }
    public void addData(T data){
        if(mDatas == null) mDatas = new ArrayList<T>();
        if(data != null) mDatas.add(data);
        notifyItemInserted(getItemCount());
        notifyItemRangeInserted(getItemCount(), 1);
//        notifyDataSetChanged();
    }
    public List<T> getData(){
        return mDatas;
    }

    public void clear(){
        if(mDatas != null) mDatas.clear();
        notifyDataSetChanged();
    }

}
