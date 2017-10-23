package hx.widget.adapterview.swiperefresh;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import hx.view.swiperefresh.SwipeRefreshContainer;
import hx.widget.adapterview.IReq2;
import hx.widget.adapterview.recyclerview.Ap2Base;

/**
 * Created by RoseHongXin on 2017/6/20 0020.
 */

public class SwipeRefreshLoader2 extends BaseSwipeRefreshLoader {


    protected SwipeRefreshLoader2(SwipeRefreshContainer _src, boolean loadMoreEnable) {
        super(_src, loadMoreEnable);
    }

    public static SwipeRefreshLoader2 get(SwipeRefreshContainer _src, boolean loadMoreEnable){
        return new SwipeRefreshLoader2(_src, loadMoreEnable);
    }
    public static SwipeRefreshLoader2 get(SwipeRefreshContainer _src){
        return new SwipeRefreshLoader2(_src, true);
    }

    public <Ap extends Ap2Base> SwipeRefreshLoader2 create(Ap adapter, IReq2<Object> api){
        return create(adapter, 1, api);
    }
    public <Ap extends Ap2Base> SwipeRefreshLoader2 create(Ap adapter, int idx, IReq2<Object> api){
        this.mFistPageIdx = idx;
        if(mLoadMoreEnable) {
            _sr_target.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && !ViewCompat.canScrollVertically(recyclerView, 1))
                        _src.setLoadingMore(true);
                }
            });
        }
        _sr_target.setAdapter(adapter);
        if(api != null) {
            _src.setOnRefreshListener(() -> {
                api.get(mFistPageIdx)
                        .takeWhile(this::dataIsReady)
                        .doOnCompleted(() -> _src.setRefreshing(false))
                        .subscribe(datas -> {
                            mCurPage = mFistPageIdx;
                            adapter.setData(datas);
                        });
            });
            if(mLoadMoreEnable) {
                _src.setOnLoadMoreListener(() -> {
                    api.get(mCurPage + 1)
                            .doOnTerminate(() -> _src.setRefreshing(false))
                            .doOnCompleted(() -> _src.setLoadingMore(false))
                            .takeWhile(this::dataIsReady)
                            .subscribe(datas -> {
                                ++mCurPage;
                                adapter.addData(datas);
                            });
                });
            }
        }
        return this;
    }
}
