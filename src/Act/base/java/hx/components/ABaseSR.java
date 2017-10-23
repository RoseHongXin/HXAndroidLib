package hx.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import hx.lib.R;
import hx.view.swiperefresh.SwipeRefreshContainer;
import hx.widget.adapterview.swiperefresh.BaseSwipeRefreshLoader;
import hx.widget.adapterview.swiperefresh.SwipeRefreshLoader;

/**
 * Created by RoseHongXin on 2017/6/21 0021.
 */

public abstract class ABaseSR<T extends BaseSwipeRefreshLoader> extends ABase {

    SwipeRefreshContainer _sr_container;

    public T mSwipeRefreshLoader;

    public abstract T getSwipeRefreshLoader(SwipeRefreshContainer _sr_container, RecyclerView _rv);

    @Override
    public int sGetLayoutRes() {
        return R.layout.a_base_sr_rv;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _sr_container = (SwipeRefreshContainer)findViewById(R.id._sr_container);
        mSwipeRefreshLoader = getSwipeRefreshLoader(_sr_container, (RecyclerView)_sr_container.getTargetView());
        if(mSwipeRefreshLoader == null){
            throw new NullPointerException("Must specify a SwipeRefreshLoader");
        }
        mSwipeRefreshLoader.refresh();
    }
}
