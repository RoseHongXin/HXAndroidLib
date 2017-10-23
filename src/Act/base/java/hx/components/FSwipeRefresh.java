package hx.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import hx.lib.R;
import hx.view.swiperefresh.SwipeRefreshContainer;
import hx.widget.adapterview.swiperefresh.BaseSwipeRefreshLoader;

/**
 * Created by Rose on 3/1/2017.
 */

public class FSwipeRefresh extends FBase {

    private SwipeRefreshContainer _sr_container;
    private RecyclerView _sr_target;
    protected BaseSwipeRefreshLoader mSwipeRefreshLoader;
    private InitCallback mCb;

    public static FSwipeRefresh newInstance(InitCallback cb) {
        if(cb == null) throw new NullPointerException("Must specify a InitCallback");
        FSwipeRefresh fragment = new FSwipeRefresh();
        fragment.mCb = cb;
        return fragment;
    }

    public RecyclerView sGetRv(){
        return _sr_target;
    }

    @Override
    public int sGetLayoutRes() {
        return R.layout.l_swipe_refresh_rv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _sr_container = (SwipeRefreshContainer)view.findViewById(R.id._sr_container);
        _sr_target = (RecyclerView) _sr_container.getTargetView();
        init();
    }

    private void init(){
        if(mCb == null || _sr_container == null) return;
        mSwipeRefreshLoader = mCb.getSwipeRefreshLoader(_sr_container, _sr_target);
        sRegisterRefreshCb(() -> mSwipeRefreshLoader.refresh());
    }

    protected void sInit(InitCallback cb){
        if(cb == null) throw new NullPointerException("Must specify a InitCallback");
        this.mCb = cb;
        init();
    }

    public interface InitCallback<T extends BaseSwipeRefreshLoader>{
         T getSwipeRefreshLoader(SwipeRefreshContainer _sr_container, RecyclerView _rv);
    }
}
